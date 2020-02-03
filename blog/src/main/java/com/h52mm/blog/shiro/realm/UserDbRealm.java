package com.h52mm.blog.shiro.realm;

import com.alibaba.fastjson.JSON;
import com.h52mm.blog.domain.entity.User;
import com.h52mm.blog.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liruilong on 2017/6/19.
 */
public class UserDbRealm extends AuthorizingRealm {

    private final Logger logger = LoggerFactory.getLogger(UserDbRealm.class);
    @Autowired
    @Lazy
    private UserService userService;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("给当前用户设置权限");
        List<String> roleSnList = new ArrayList<>();
        List<String> resStrList = new ArrayList<>();
//        List<Resources> resources;
        //获取主体信息
        Object object = principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info;
        User user = (User) object;
        if(user!=null)
        {

        }
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("--- MyRealm doGetAuthenticationInfo ---");
        String username = authenticationToken.getPrincipal().toString();
        try {
//            User u=new User();
            User u =userService.getUserByAccount(username);
            if (u != null) {
                logger.info("用户登录：={}", JSON.toJSON(u));
                SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(u, u.getPwd(), getName());
                return info;
            }

        } catch (Exception e) {
            logger.error("用户登录失败={}", e.getMessage());
        }

        return null;
    }

//    @Override
//    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
//        Cache c = getAuthenticationCache();
//        logger.info("清除【认证】缓存之前");
//        for (Object o : c.keys()) {
//            logger.info(o + " , " + c.get(o));
//        }
//        super.clearCachedAuthenticationInfo(principals);
//        logger.info("调用父类清除【认证】缓存之后");
//        for (Object o : c.keys()) {
//            logger.info(o + " , " + c.get(o));
//        }
//
//        // 添加下面的代码清空【认证】的缓存
//        User user = (User) principals.getPrimaryPrincipal();
////        SimplePrincipalCollection spc = new SimplePrincipalCollection(user.getName(), getName());
//        SimplePrincipalCollection spc = new SimplePrincipalCollection("", getName());
//        super.clearCachedAuthenticationInfo(spc);
//        logger.info("添加了代码清除【认证】缓存之后");
//        int cacheSize = c.keys().size();
//        logger.info("【认证】缓存的大小:" + c.keys().size());
//        if (cacheSize == 0) {
//            logger.info("说明【认证】缓存被清空了。");
//        }
//    }
//
//    @Override
//    protected void clearCachedAuthorizationInfo(PrincipalCollection principals) {
//        logger.info("清除【授权】缓存之前");
//        Cache c = getAuthorizationCache();
//        for (Object o : c.keys()) {
//            logger.info(o + " , " + c.get(o));
//        }
//        super.clearCachedAuthorizationInfo(principals);
//        logger.info("清除【授权】缓存之后");
//        int cacheSize = c.keys().size();
//        logger.info("【授权】缓存的大小:" + cacheSize);
//
//        for (Object o : c.keys()) {
//            logger.info(o + " , " + c.get(o));
//        }
//        if (cacheSize == 0) {
//            logger.info("说明【授权】缓存被清空了。");
//        }
//
//    }
}
