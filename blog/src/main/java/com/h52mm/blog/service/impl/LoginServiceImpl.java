package com.h52mm.blog.service.impl;

import com.h52mm.blog.commons.CodeMessage;
import com.h52mm.blog.commons.WechatResponse;
import com.h52mm.blog.domain.dao.UserDao;
import com.h52mm.blog.domain.entity.User;
import com.h52mm.blog.domain.entity.dto.UserDto;
import com.h52mm.blog.exception.BusinessException;
import com.h52mm.blog.service.LoginService;
import com.h52mm.blog.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserDao userDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public WechatResponse doLogin(String account, String pwd, String verifycode, String vtoken) {

        WechatResponse response=WechatResponse.newInstance();
        if(StringUtils.isEmpty(account)){
            //用户账号不能为空
          throw new BusinessException("登录账号不能为空");
        }
        if(StringUtils.isEmpty(pwd)){
            throw new BusinessException("登录密码不能为空");
        }
        if(StringUtils.isEmpty(verifycode)){
            throw new BusinessException("验证码不能为空");
        }
        if(StringUtils.isEmpty(vtoken)){
            throw new BusinessException("KEY不能为空");
        }
        //校验验证码是否正确
        if(!redisTemplate.hasKey(vtoken)){
            throw  new BusinessException("验证码失效请刷新验证码");
        }

        if(!verifycode.equals(redisTemplate.opsForValue().get(vtoken).toString())){
            throw  new BusinessException("验证码错误");
        }
        //获取用户信息
        User user=userDao.getUserByAccount(account);
        if(user==null){
         response.checkSuccess(false, CodeMessage.USER_ERR_ACCOUNT.name());
         return response;
        }
        try {
            String password = MD5Utils.getMessageDigest(pwd.getBytes());
            UsernamePasswordToken token = new UsernamePasswordToken(account, password);
            //用户登录
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            User userToken = (User) subject.getPrincipal();
            subject.isRemembered();
            //生成token
            String breaertoken= MD5Utils.getMD5ForRandomUUID();
            if (userToken != null) {
                redisTemplate.opsForValue().set("user:"+breaertoken, user, 7200, TimeUnit.SECONDS);
            }
            UserDto userDto=new UserDto();
            userDto.setToken(breaertoken);
            userDto.setAccount(account);
            userDto.setNickname(user.getNickname());
            userDto.setHeadPortrait(user.getHeadPortrait());
            userDto.setRowId(user.getRowId());
            response.setData(userDto);
        } catch (IncorrectCredentialsException e) {
            response.checkSuccess(false, CodeMessage.USER_ERR_ACCOUNT.name());
        } catch (UnknownAccountException e) {
            response.checkSuccess(false, CodeMessage.USER_ERR_ACCOUNT.name());
        }
        return response;
    }
}
