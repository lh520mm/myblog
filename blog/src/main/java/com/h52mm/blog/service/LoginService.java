package com.h52mm.blog.service;

import com.h52mm.blog.commons.WechatResponse;

public interface LoginService {
    WechatResponse doLogin(String account, String pwd, String verifycode,  String vtoken);
}
