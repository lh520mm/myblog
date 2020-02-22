package com.h52mm.blog.service.impl;

import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.commons.CodeMessage;
import com.h52mm.blog.domain.dao.UserDao;
import com.h52mm.blog.domain.entity.User;
import com.h52mm.blog.service.UserService;
import com.h52mm.blog.util.DateUtils;
import com.h52mm.blog.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public User getUserByAccount(String username) {
        return userDao.getUserByAccount(username);
    }

    @Override
    public BlogResponse getUserList(Integer pageSize, Integer pageNumber, String account, String nickname, String tel) {
        BlogResponse response=BlogResponse.newInstance();
        Integer total=userDao.getUserTotal(account,nickname,tel);
        if(total!=null){
            List<User> users=userDao.getUserList(pageSize,(pageNumber-1)*pageSize,account,nickname,tel);
            response.setData(users);
            response.setTotalCount(total);
        }else{
            response.setTotalCount(0);
            response.setData(new ArrayList<User>());
        }
        return response;
    }

    @Override
    public BlogResponse save(User user) {
        BlogResponse response=BlogResponse.newInstance();
        if(user!=null){
            if(user.getAccount()==null||"".equals(user.getAccount())){
                response.checkSuccess(false,CodeMessage.USER_ERR_ACCOUNT_NULL.name());
                return response;
            }
            if(user.getPwd()==null||"".equals(user.getPwd())){
                response.checkSuccess(false,CodeMessage.USER_ERR_PWD_NULL.name());
                return response;
            }
            user.setRowId(MD5Utils.getMD5ForRandomUUID().replaceAll("-",""));
            user.setLev(1);
            user.setCreateTime(DateUtils.now());
            user.setState(1);
            user.setDel(false);
            user.setPwd(MD5Utils.getMessageDigest(user.getPwd().getBytes()));
            if(user.getSex()!=null&&user.getSex()){
                user.setHeadPortrait("man.jpg");
            }else{
                user.setHeadPortrait("woman.png");
            }
            userDao.save(user);
        }else{
            response.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
        }
        return response;
    }
}
