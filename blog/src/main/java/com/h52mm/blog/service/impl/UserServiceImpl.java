package com.h52mm.blog.service.impl;

import com.h52mm.blog.domain.dao.UserDao;
import com.h52mm.blog.domain.entity.User;
import com.h52mm.blog.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

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

}
