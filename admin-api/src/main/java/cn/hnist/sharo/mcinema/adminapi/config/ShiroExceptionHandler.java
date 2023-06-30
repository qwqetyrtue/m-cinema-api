package cn.hnist.sharo.mcinema.adminapi.config;

import cn.hnist.sharo.mcinema.core.model.HttpRes;
import cn.hnist.sharo.mcinema.core.util.HttpResUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class ShiroExceptionHandler {
    private final Log logger = LogFactory.getLog(ShiroExceptionHandler.class);

    /**
     * <h3>无限权错误处理</h3>
     * <p>
     * </p>
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public HttpRes unauthorizedHandler(UnauthorizedException e) {
        logger.warn(e.getMessage(), e);
        return HttpResUtil.unAuth();
    }

    /**
     * <h3>未登录错误处理</h3>
     * <p>
     *     否则默认跳转到500页面
     * </p>
     * @param e
     * @return
     */
    @ExceptionHandler(UnauthenticatedException.class)
    @ResponseBody
    public HttpRes unauthenticatedHandler(AuthorizationException e) {
        logger.warn(e.getMessage(), e);
        return HttpResUtil.unLogin();
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public HttpRes unauthorizedHandler(AuthenticationException e) {
        logger.warn(e.getMessage(), e);
        return HttpResUtil.unAuth();
    }

}
