package cn.hnist.sharo.mcinema.adminapi.util;

import cn.hnist.sharo.mcinema.core.annotation.RequestApiMenu;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h3>API 接口工具类</h3>
 * <ul>
 * <li><h4>loadApiInfos()</h4> 加载API信息,存储到apiInfos对象中</li>
 * <li><h4>getApi()</h4> 获取API信息 如果api距离上次获取超过设定的时间,执行loadApiInfo()</li>
 * </ul>
 */
public class ApiUtil {
    public static List<ApiInfo> apiInfos = null;
    public static LocalDateTime lastLoadTime = null;
    private static String packageName = "cn.hnist.sharo.mcinema";
    private static ApplicationContext context = null;
    private static int apiExpireTime = 600;

    private static void loadApiInfos(ApplicationContext context, String packageName) {
        loadApiInfos(context, packageName, apiExpireTime);
    }

    public static List<ApiInfo> getPermissionApi(Set<String> permissionSet) {
        // 超级管理员权限: 全部api都可访问
        if (permissionSet.contains("*")) return apiInfos;
        // 普通管理员:  需要筛选可访问api
        return apiInfos.stream().filter(apiInfo -> {
            String permission = apiInfo.getPermission();
            if (permission != null) {
                return permissionSet.contains(permission);
            } else return true;
        }).collect(Collectors.toList());
    }

    // 加载API
    public static void loadApiInfos(ApplicationContext context, String packageName, int apiExpireTime) {
        List<ApiInfo> res = new ArrayList<>();
        Map<String, Object> controllers = context.getBeansWithAnnotation(Controller.class);
        controllers.forEach((name, object) -> {
            // 排除默认的controller
            if (!object.getClass().getPackage().getName().contains(packageName)) return;
            // 这里获取的是spring的代理类,并不是原始类
            Class<?> agency = object.getClass();
            // 获取原始controller类
            Class<?> origin = agency.getSuperclass();
            // 获取controller RequestMapping注解中的根路径
            RequestMapping rootMappingAno = AnnotationUtils.findAnnotation(origin, RequestMapping.class);
            RequiresPermissions rootPermAno = AnnotationUtils.findAnnotation(origin, RequiresPermissions.class);
            RequiresAuthentication rootAuthAno = AnnotationUtils.findAnnotation(origin, RequiresAuthentication.class);
            StringBuilder rootMethod = new StringBuilder();
            StringBuilder rootPath = new StringBuilder();
            if (rootMappingAno != null) {
                for (String each : rootMappingAno.value())
                    rootPath.append(each);
                for (RequestMethod each : rootMappingAno.method())
                    rootMethod.append(each.name());
            }

            List<Method> routers = MethodUtils.getMethodsListWithAnnotation(origin, RequestMapping.class);

            for (Method router : routers) {
                RequestMapping childMappingAno = router.getAnnotation(RequestMapping.class);
                RequestApiMenu reqApiMenuAno = router.getAnnotation(RequestApiMenu.class);
                ApiOperation reqApiOperationAno = router.getAnnotation(ApiOperation.class);
                // 权限和需要登录都以controller优先级最大
                RequiresPermissions childPermAno = rootPermAno == null ?
                        router.getAnnotation(RequiresPermissions.class) : rootPermAno;
                RequiresAuthentication childAuthAno = rootAuthAno == null ?
                        router.getAnnotation(RequiresAuthentication.class) : rootAuthAno;
                StringBuilder method = new StringBuilder();
                method.append(rootMethod);
                if (method.length() == 0)
                    for (RequestMethod each : childMappingAno.method()) {
                        method.append(each.name());
                    }

                StringBuilder path = new StringBuilder();
                for (String each : childMappingAno.value()) {
                    path.append(rootPath);
                    path.append(each);
                }
                ApiInfo one = new ApiInfo();
                one.setDesc("");
                one.setPath(path.toString());
                one.setMethod(method.toString());
                if (reqApiOperationAno != null) {
                    String val = reqApiOperationAno.value();
                    // 出去描述前面的编号
                    int i = val.indexOf(' ');
                    if (i == -1)
                        one.setDesc(val);
                    else
                        one.setDesc(val.substring(i + 1));
                }
                if (reqApiMenuAno != null) {
                    one.setMenu(reqApiMenuAno.menu());
                }
                if (childPermAno != null) {
                    one.setPermission(childPermAno.value()[0]);
                    one.setAuthentic(true);
                } else one.setAuthentic(childAuthAno != null);
                res.add(one);
            }
        });
        ApiUtil.apiInfos = res;
        ApiUtil.lastLoadTime = LocalDateTime.now();
        ApiUtil.context = context;
        ApiUtil.packageName = packageName;
        ApiUtil.apiExpireTime = apiExpireTime;
    }

    public static List<ApiInfo> getAPI() {
        LocalDateTime t = LocalDateTime.now();
        // API过期
        if (lastLoadTime == null || Duration.between(t, lastLoadTime).getSeconds() > apiExpireTime) {
            System.out.println("更新API");
            loadApiInfos(context, packageName);
            lastLoadTime = t;
        }
        return apiInfos;
    }

    public static class ApiInfo {
        private String desc; // 描述
        private String path; // 路径
        private String method; // 请求方法
        private Boolean authentic; // 是否登录
        private String permission; // 需要的限权
        private String[] menu; // 菜单项

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public Boolean getAuthentic() {
            return authentic;
        }

        public void setAuthentic(Boolean authentic) {
            this.authentic = authentic;
        }

        public String[] getMenu() {
            return menu;
        }

        public void setMenu(String[] menu) {
            this.menu = menu;
        }

        public String getPermission() {
            return permission;
        }

        public void setPermission(String permission) {
            this.permission = permission;
        }
    }
}
