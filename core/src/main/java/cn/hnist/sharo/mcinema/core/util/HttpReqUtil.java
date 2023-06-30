package cn.hnist.sharo.mcinema.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpReqUtil {
    public final static Map<String, String> generalRequestProperty = new HashMap<String, String>() {{
        put("accept", "*/*");
        put("connection", "Keep-Alive");
        put("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        put("Content-Type", "application/json; charset=utf-8");
    }};

    public static Map<String, String> sendGet(String baseUrl, Map<String, String> params) {
        return sendGet(baseUrl, null, params);
    }

    /**
     * 向指定的URL发送GET方法的请求
     *
     * @param baseUrl 基础连接
     * @param params  参数map
     * @return 远程资源的响应结果
     */
    public static Map<String, String> sendGet(String baseUrl, Map<String, String> headers, Map<String, String> params) {
        Map<String, String> res = null;
        StringBuilder data;
        BufferedReader bufferedReader = null;
        try {
            String url = baseUrl + "?" + concatParams(params);
            URL urlObj = new URL(url);

            URLConnection connection = urlObj.openConnection();
            setGeneralRequestProperty(connection);
            setRequestProperty(connection, headers);

            connection.connect();

            Map<String, List<String>> resHeader = connection.getHeaderFields();

            // BufferedReader输入流来读取URL的响应内容
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            data = new StringBuilder();
            String line;
            while (null != (line = bufferedReader.readLine())) {
                data.append(line);
            }

//            int tmp;
//            while((tmp = bufferedReader.read()) != -1){
//                result += (char)tmp;
//            }

            res = new HashMap<>();
            res.put("data", String.valueOf(data));
            res.put("Content-Type", String.valueOf(resHeader.get("Content-Type")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {        //使用finally块来关闭输入流
            try {
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return res;
    }

    /**
     * 向指定的URL发送POST方法的请求
     * @param baseUrl 基础链接
     * @param params 参数
     * @return 远程资源的响应结果
     */
    public static String sendPost(String baseUrl, Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        BufferedReader bufferedReader = null;
        PrintWriter out = null;
        try {
            URL urlObj = new URL(baseUrl);

            URLConnection connection = urlObj.openConnection();
            setGeneralRequestProperty(connection);


            // 发送POST请求必须设置如下两行
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //获取URLConnection对象对应的输出流
            out = new PrintWriter(connection.getOutputStream());
            //发送请求参数
            out.print(concatParams(params));
            //flush输出流的缓冲
            out.flush();


            //6、定义BufferedReader输入流来读取URL的响应内容
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while (null != (line = bufferedReader.readLine())) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {        //使用finally块来关闭输出流、输入流
            try {
                if (null != out) {
                    out.close();
                }
                if (null != bufferedReader) {
                    bufferedReader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }

    /**
     * <h3>拼接url参数</h3>
     *
     * @param params 参数
     * @return 拼接结果
     */
    public static String concatParams(Map<String, String> params) {
        StringBuilder res = new StringBuilder();
        // map需要对key去重
        for (String key : params.keySet()) {
            res.append(key);
            res.append("=");
            res.append(params.get(key));
            res.append("&");
        }
        return res.substring(0, res.length() - 1);
    }

    /**
     * <h3>设置通用的请求属性</h3>
     *
     * @param connection url连接
     */
    public static void setGeneralRequestProperty(URLConnection connection) {
        setRequestProperty(connection, generalRequestProperty);
    }

    /**
     * <h3>设置请求属性</h3>
     *
     * @param connection url请求对象
     * @param property   要设置的属性
     */
    public static void setRequestProperty(URLConnection connection, Map<String, String> property) {
        if (connection == null)
            return;
        property.forEach(connection::setRequestProperty);
    }
}
