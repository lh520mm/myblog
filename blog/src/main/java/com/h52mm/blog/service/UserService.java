package com.h52mm.blog.service;


import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.domain.entity.User;

public interface UserService {


    User getUserByAccount(String username);

    BlogResponse getUserList(Integer pageSize, Integer pageNumber, String account, String nickname, String tel);

    BlogResponse save(User user);
}
