package cn.hnist.sharo.mcinema.adminapi.config;

import cn.hnist.sharo.mcinema.adminapi.config.shiro.AdminRealm;
import cn.hnist.sharo.mcinema.adminapi.config.shiro.AdminSessionDAO;
import cn.hnist.sharo.mcinema.adminapi.config.shiro.AdminSessionManagement;
import cn.hnist.sharo.mcinema.db.redis.RedisSessionUtil;
import cn.hnist.sharo.mcinema.db.redis.RedisUtil;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <ul>
 * <li>anon: 无需认证就能访问
 * <li>authc: 必须认证才能访问
 * <li>user: 开启记住我功能才能用
 * <li>perms: 拥有对某个资源的权限才能访问
 * <li>role: 拥有某个角色权限才能访问
 * </ul>
 */
@Configuration
public class ShiroConfig {
    @Resource
    RedisUtil redisUtil;
    @Resource
    RedisSessionUtil redisSessionUtil;

    @Value(value = "${mcinema.auth.session-token-key}")
    private String SESSION_TOKEN_KEY;
    @Value(value = "${mcinema.auth.session-timeout}")
    private Long SESSION_TIMEOUT;
    @Value(value = "${mcinema.redis-key.session-admin}")
    private String sessionRoot;

    /**
     * <h3>注册shiroFilterFactoryBean</h3>
     *
     * @param securityManager
     * @return
     */
    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        Map<String, String> filter = new LinkedHashMap<>();
        // 设置拦截器
        bean.setFilterChainDefinitionMap(filter);
        return bean;
    }


    /**
     * <h3>注册DefaultWebSecurityManager</h3>
     *
     * @param adminRealm realm
     * @return securityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager adminWebSecurityManager(
            AdminRealm adminRealm,
            AdminSessionManagement sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(adminRealm); // 设置Realm对象
        securityManager.setSessionManager(sessionManager); // 设置session管理
        return securityManager;
    }

    /**
     * <h3>注册Realm</h3>
     *
     * @return realm对象
     */
    @Bean(name = "adminRealm")
    public AdminRealm adminRealm() {
        return new AdminRealm();
    }

    /**
     * <h3>注册sessionManager</h3>
     *
     * @return sessionManager对象
     */
    @Bean(name = "sessionManager")
    public AdminSessionManagement adminSessionManagement(AdminSessionDAO adminSessionDAO) {
        AdminSessionManagement sessionManagement = new AdminSessionManagement(SESSION_TOKEN_KEY,SESSION_TIMEOUT);
        sessionManagement.setSessionDAO(adminSessionDAO);
        return sessionManagement;
    }

    @Bean(name = "adminSessionDAO")
    public AdminSessionDAO adminSessionDAO() {
        return new AdminSessionDAO(redisSessionUtil, sessionRoot,SESSION_TIMEOUT, TimeUnit.SECONDS);
    }


    /**
     * <h3>开启注解功能</h3>
     * <a href="https://blog.csdn.net/hjseo_seg/article/details/126496478">xml的配置</a>
     * <p>
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),
     * 需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证 *
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)
     * 和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * </p>
     *
     * @return
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public static DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
