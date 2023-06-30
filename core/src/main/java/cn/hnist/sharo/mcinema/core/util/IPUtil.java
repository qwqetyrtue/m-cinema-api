package cn.hnist.sharo.mcinema.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.ServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * 参考: <a href="https://www.ituring.com.cn/article/510334">Spring 客户端 IP 地址获取及存储细节</a>
 * </p>
 *
 * <ul>
 *      <li>
 *          proxy：<br/>
 *          Q: 部分客户端使用代理后此方法返回的是代理网络的IP地址，非用户真实 IP <br/>
 *          A: 经过代理后通常可用通过 http header 的 Proxy-Client-IP 获取用户真实 IP地址
 *      </li>
 *      <li>
 *          SLB：<br/>
 *          Q: 后台经过负载均衡，如阿里云的SLB实例，方法返回地址是SLB实例 IP，并非用户真实 IP<br/>
 *          A: 经过SLB实例后可通过 http header 的 X-Forwarded-For 获取用户真实IP
 *      </li>
 *      <li>
 *          环回地址：<br/>
 *          Q: 在本地测试时获取到的是ipv4：127.0.0.1 或者 ipv6：0:0:0:0:0:0:0:1，并非本机分配地址<br/>
 *          A: 如果是环回地址，则根据网卡取本机配置的IP，如192.168.199.123 等
 *      </li>
 *      <li>
 *          存储长度: </br>
 *          Q: 获取地址可能是IPv6 地址，长度不同，数据库需要兼容处理，适配以后 IPv6需求<br/>
 *          A: 不同版本 IP 长度不同，取最长作为数据库存储长度（47最长）
 *      </li>
 * </ul>
 */
public class IPUtil {
    // 含IP的请求头IP
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"
    };

    private static final Logger logger = LoggerFactory.getLogger(IPUtil.class);

    public static String getIPAddress(NativeWebRequest webRequest) {
        // 提取header得到IP地址列表（多重代理场景），取第一个IP
        for (String header : IP_HEADER_CANDIDATES) {
            String ipList = webRequest.getHeader(header);
            if (ipList != null && ipList.length() != 0 &&
                    !"unknown".equalsIgnoreCase(ipList)) {
                return ipList.split(",")[0];
            }
        }

        // 没有经过代理或者SLB，直接 getRemoteAddr 方法获取IP
        String ip = ((ServletRequest) webRequest.getNativeRequest()).getRemoteAddr();

        // 如果是本地环回IP，则根据网卡取本机配置的IP
        if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
            try {
                InetAddress inetAddress = InetAddress.getLocalHost();
                return inetAddress.getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
                return ip;
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) { // "***.***.***.***".length()
            // = 15
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }
}
