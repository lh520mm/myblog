package com.h52mm.blog.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.h52mm.blog.commons.CodeMessage;
import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.util.ConstantUtil;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.concurrent.TimeUnit;

public class SimpleFormAuthenticationFilter extends FormAuthenticationFilter {
    private static final Logger log = LoggerFactory.getLogger(SimpleFormAuthenticationFilter.class);
    private RedisTemplate redisTemplate;
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        String token=WebUtils.toHttp(request).getHeader("Authorization");
        if(token==null||"".equalsIgnoreCase(token)){
            BlogResponse blogResponse = BlogResponse.newInstance();
            blogResponse.checkSuccess(false, CodeMessage.TOKEN_NO_ERR.name());
            String res=JSON.toJSONString(blogResponse);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().println(res);
            return false;
        }
        //token存在校验token是否超时
        log.info(token);
        token=token.split("Bearer")[1].replaceAll(" ","");
        log.info(token);
        if(!redisTemplate.hasKey(ConstantUtil.TOKEN_NAME+token)){
            //token 失效
            BlogResponse blogResponse = BlogResponse.newInstance();
            blogResponse.checkSuccess(false, CodeMessage.TOKEN_TIME_OUT.name());
            String res=JSON.toJSONString(blogResponse);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            response.getWriter().println(res);
            return false;
        }
        redisTemplate.expire(ConstantUtil.TOKEN_NAME+token,ConstantUtil.LOGINTIME_SECONDS, TimeUnit.SECONDS);
        //校验用户登录信息
        return true;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
