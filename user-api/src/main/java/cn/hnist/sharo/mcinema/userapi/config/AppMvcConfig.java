package cn.hnist.sharo.mcinema.userapi.config;

import cn.hnist.sharo.mcinema.userapi.annotation.UserJWTGetResolvers;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

@Configuration
public class AppMvcConfig implements WebMvcConfigurer {
    @Resource
    UserJWTGetResolvers userJWTGetResolvers;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userJWTGetResolvers);
        WebMvcConfigurer.super.addArgumentResolvers(resolvers);
    }
}
