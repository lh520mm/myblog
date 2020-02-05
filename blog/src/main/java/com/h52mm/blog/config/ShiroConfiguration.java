package com.h52mm.blog.config;


import com.h52mm.blog.shiro.filter.CredentialsMatcher;
import com.h52mm.blog.shiro.filter.RequestFilter;
import com.h52mm.blog.shiro.filter.SimpleFormAuthenticationFilter;
import com.h52mm.blog.shiro.realm.UserDbRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author 刘豪
 * @date 2018/10/30
 * shiro 配置类
 */
@Configuration
@PropertySource({"classpath:config/redis.properties"})
public class ShiroConfiguration {
    @Autowired
    private RedisTemplate redisTemplate;

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("requestFilter", new RequestFilter());
        SimpleFormAuthenticationFilter authcFilter = new SimpleFormAuthenticationFilter();
        authcFilter.setRedisTemplate(redisTemplate);
        filterMap.put("authc", authcFilter);
        bean.setFilters(filterMap);
        bean.setSecurityManager(manager);
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/checktoken", "anon");
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");
        filterChainDefinitionMap.put("/configuration/**", "anon");
        filterChainDefinitionMap.put("/kaptcha/**", "anon");
        filterChainDefinitionMap.put("/*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }

    //配置核心安全事务管理器
    @Bean(name = "securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") UserDbRealm authRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        return manager;
    }

    //配置自定义的权限登录器
    @Bean(name = "authRealm")
    public UserDbRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
        UserDbRealm authRealm = new UserDbRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }

    //配置自定义的密码比较器
    @Bean(name = "credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }

}
