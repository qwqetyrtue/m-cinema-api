package cn.hnist.sharo.mcinema.adminapi.config;

import cn.hnist.sharo.mcinema.adminapi.util.ApiUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GlobalLoadHandler implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    ApplicationContext context;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 加载aip
        ApiUtil.loadApiInfos(context,"cn.hnist.sharo.mcinema",600);
    }
}
