package com.h52mm.blog.service;

import com.h52mm.blog.commons.BlogResponse;

public interface LoginService {
    BlogResponse doLogin(String account, String pwd, String verifycode, String vtoken);

    BlogResponse checkToken(String token);
}
