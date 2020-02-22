package com.h52mm.blog.controller;

import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.domain.entity.User;
import com.h52mm.blog.service.UserService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "list",method = RequestMethod.POST)
    public BlogResponse getUserList(@ApiParam(name = "pageSize", value = "每页显示条数", required = true) @RequestParam(value = "pageSize") Integer pageSize,
                                    @ApiParam(name = "pageNumber", value = "第几页", required = true) @RequestParam(value = "pageNumber") Integer pageNumber,
                                    @ApiParam(name="account",value = "用户名") @RequestParam(value ="account",required = false) String account,
                                    @ApiParam(name="nickname",value = "昵称") @RequestParam(value ="nickname",required = false) String nickname,
                                    @ApiParam(name="tel",value = "联系电话") @RequestParam(value ="tel",required = false) String tel){
        return userService.getUserList(pageSize,pageNumber,account,nickname,tel);
    }

    @RequestMapping(value = "save",method = RequestMethod.POST)
    public BlogResponse save(User user){
        return userService.save(user);
    }

}
