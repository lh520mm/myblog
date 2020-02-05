package com.h52mm.blog.shiro.filter;

import com.alibaba.fastjson.JSONObject;
import com.h52mm.blog.commons.CodeMessage;
import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.service.UserService;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @author 刘豪
 * 用于查验请求是否超时，校验token
 */
public class SessionExpireFilter extends AdviceFilter {
    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    RedisTemplate<String, Object> redisTemplate;

    private static final Logger log = LoggerFactory.getLogger(SessionExpireFilter.class);

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info("进行token验证SessionExpireFilter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse rep = (HttpServletResponse) response;
        boolean flag = false;
        if ("OPTIONS".equals(req.getMethod())) {
            rep.setHeader("Access-Control-Allow-Origin", "*");
            rep.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            rep.setHeader("Access-Control-Max-Age", "3600");
            rep.setHeader("Access-Control-Allow-Headers", "x-requested-with,Content-Type,token,type,canteenId");
            return flag;
        }
        try {
            String token = req.getSession().getId();
            flag = false;
            if (token != null && !"".equals(token)) {
                if (redisTemplate.hasKey(token) != null && redisTemplate.hasKey(token)) {
                    flag = true;
                    redisTemplate.expire(token, 7200, TimeUnit.MINUTES);
                } else {
                    log.info("token超时");
                    String requestType = ((HttpServletRequest) request).getHeader("X-Requested-With");
                    if ("XMLHttpRequest".equals(requestType)) {
                        PrintWriter out = rep.getWriter();
                        BlogResponse blogResponse = BlogResponse.newInstance();
                        blogResponse.checkSuccess(false, CodeMessage.TOKEN_TIME_OUT.name());
                        out.print(JSONObject.toJSONString(blogResponse));
                    } else {
                        rep.sendRedirect("/login");
                    }
                }
            } else {
                log.info("请求中没有token值");
                String requestType = ((HttpServletRequest) request).getHeader("X-Requested-With");
                if ("XMLHttpRequest".equals(requestType)) {
                    PrintWriter out = rep.getWriter();
                    BlogResponse blogResponse = BlogResponse.newInstance();
                    blogResponse.checkSuccess(false, CodeMessage.TOKEN_TIME_OUT.name());
                    out.print(JSONObject.toJSONString(blogResponse));
                } else {
                    rep.sendRedirect("/login");
                }
            }
        } catch (Exception e) {
            log.info("token校验抛出异常=============：={}", e.getMessage());
            String requestType = ((HttpServletRequest) request).getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestType)) {
                PrintWriter out = rep.getWriter();
                BlogResponse blogResponse = BlogResponse.newInstance();
                blogResponse.checkSuccess(false, CodeMessage.SYSTEM_BUSY.name());
                out.print(JSONObject.toJSONString(blogResponse));
            } else {
                rep.sendRedirect("/login");
            }
        }
        return flag;
    }


}