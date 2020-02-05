package com.h52mm.blog.controller;

import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.service.MenuService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    public BlogResponse getMenuList(@ApiParam(name="userid",value="用户ID") @RequestParam(value = "userid",required = false) String userId,
                                    HttpServletRequest request){

        return menuService.getMenuList(userId,request);
    }
}
