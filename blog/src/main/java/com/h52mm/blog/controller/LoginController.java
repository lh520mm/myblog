package com.h52mm.blog.controller;

import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.service.LoginService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public BlogResponse login(@ApiParam(name="account",value="用户账号",required=true) @RequestParam(value = "account") String account,
                              @ApiParam(name="pwd",value="用户密码",required=true) @RequestParam(value = "pwd") String pwd,
                              @ApiParam(name="verifycode",value="用户验证码",required=true) @RequestParam(value = "verifycode") String verifycode,
                              @ApiParam(name="vtoken",value = "验证码key",required = true) @RequestParam(value = "vtoken") String vtoken,
                              HttpServletRequest request){

        return loginService.doLogin(account,pwd,verifycode,vtoken);
    }

    @GetMapping("checktoken")
    public BlogResponse checkToken(@ApiParam(name="token",value="token",required=false) @RequestParam(value = "token",required = false) String  token){
        return loginService.checkToken(token);
    }
}
