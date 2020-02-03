package com.h52mm.blog.service;


import com.h52mm.blog.domain.entity.User;

public interface UserService {


    User getUserByAccount(String username);

}
