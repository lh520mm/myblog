package com.h52mm.blog.service;

import com.h52mm.blog.commons.BlogResponse;

import javax.servlet.http.HttpServletRequest;

public interface MenuService {
    BlogResponse getMenuList(String userId, HttpServletRequest request);
}
