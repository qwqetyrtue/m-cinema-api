package cn.hnist.sharo.mcinema.userapi.annotation;

import cn.hnist.sharo.mcinema.core.exception.UserException;
import cn.hnist.sharo.mcinema.core.model.ErrorEnum;
import cn.hnist.sharo.mcinema.userapi.util.jwt.JwtHelper;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

@Component
public class UserJWTGetResolvers implements HandlerMethodArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(UserJWTGetResolvers.class);
    @Resource
    JwtHelper jwtHelper;

    @Value("${mcinema.auth.token-key}")
    private String TOKEN_KEY;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(UserJWT.class) && parameter.getParameterType().isAssignableFrom(Long.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader(TOKEN_KEY);
        if (token == null) throw new UserException("用户未登录", ErrorEnum.WITHOUT_AUTHENTICATED);
        DecodedJWT decodedJWT = jwtHelper.verifyToken(token);
        if (decodedJWT == null) throw new UserException("用户认证已过期", ErrorEnum.TIMEOUT_TOKEN);
        return decodedJWT.getClaim("userId").asLong();
    }
}
