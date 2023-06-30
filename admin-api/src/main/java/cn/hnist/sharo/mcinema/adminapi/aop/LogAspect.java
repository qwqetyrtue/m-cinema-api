package cn.hnist.sharo.mcinema.adminapi.aop;

import cn.hnist.sharo.mcinema.adminapi.annotation.RequestLog;
import cn.hnist.sharo.mcinema.adminapi.service.LogService;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import cn.hnist.sharo.mcinema.db.pojo.AdminBase;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {
    @Resource
    LogService logService;

    @Pointcut("@annotation(cn.hnist.sharo.mcinema.adminapi.annotation.RequestLog)")
    public void needLogMethod() {
    }

    @Before("needLogMethod()")
    public void beforeAdvice() {
    }

    @After("needLogMethod()")
    public void afterAdvice() {
    }

    @Around("needLogMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        HttpRes rtValue = null;
        try {
            Class<?> orgClass = pjp.getTarget().getClass();
            MethodSignature ms = (MethodSignature) pjp.getSignature();
            Method method = orgClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
            RequestLog requestLog = AnnotationUtils.findAnnotation(method, RequestLog.class);
            if (requestLog == null) return HttpResUtil.fail(ErrorEnum.UNKNOWN_EXCEPTION);
            AdminBase admin = (AdminBase) SecurityUtils.getSubject().getPrincipal();
            // 方法参数
            Object[] args = pjp.getArgs();
            // 执行切点方法
            rtValue = (HttpRes) pjp.proceed(args);

            Integer adminId = null;
            // 获取session的持有者ID
            if (admin != null && admin.getAdminId() != null)
                adminId = admin.getAdminId();
            // 出现未知错误,当启用Log时,返回未知错误意味着data中附带着Exception对象
            if (rtValue.getCode() != 0) {
                // 获取Exception
                Exception e = (Exception) rtValue.getData();
                // 清空Data
                rtValue.setData(null);
                String msg = rtValue.getMsg();
                // 截取报错信息,只截取100的长度防止存储时超出字段长度
                if (e != null) {
                    msg = e.getMessage();
                    if (msg != null && msg.length() > 250)
                        msg = msg.substring(0, 250);
                    msg += ";";
                    if (e.getClass() != null)
                        msg += e.getClass().getName();
                }
                logService.failLog(
                        requestLog.type(),
                        adminId,
                        requestLog.action(),
                        msg);
            } else {
                logService.successLog(
                        requestLog.type(),
                        adminId,
                        requestLog.action());
            }
            return rtValue;
        } finally {
        }
    }
}
