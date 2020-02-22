package com.h52mm.blog.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.h52mm.blog.commons.BlogResponse;
import com.h52mm.blog.commons.CodeMessage;
import com.h52mm.blog.domain.dao.MenuDao;
import com.h52mm.blog.domain.dao.UserDao;
import com.h52mm.blog.domain.entity.Menu;
import com.h52mm.blog.domain.entity.User;
import com.h52mm.blog.service.MenuService;
import com.h52mm.blog.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private UserDao userDao;

    @Override
    public BlogResponse getMenuList(String userId, HttpServletRequest request) {
        BlogResponse response = BlogResponse.newInstance();
        if (userId == null || "".equals(userId)) {
            String authorization = request.getHeader("Authorization");
            if (authorization == null || "".equals(authorization)) {
                response.checkSuccess(false, CodeMessage.PARAMS_ERR.name());
                return response;
            }


            String token = authorization.split("Bearer")[1].replaceAll(" ", "");
            if (!redisTemplate.hasKey(ConstantUtil.TOKEN_NAME + token)) {
                response.checkSuccess(false, CodeMessage.TOKEN_TIME_OUT.name());
                return response;
            }
            User user = (User) redisTemplate.opsForValue().get(ConstantUtil.TOKEN_NAME + token);
            if (user == null) {
                response.checkSuccess(false, CodeMessage.TOKEN_TIME_OUT.name());
                return response;
            }
            userId=user.getRowId();
        }

        User user=userDao.getUserByRowId(userId);
        if(user==null){
            response.checkSuccess(false,CodeMessage.TOKEN_NO_ERR.name());
            return response;
        }
        if(user.getRoleId()==null||"".equals(user.getRoleId())){
            response.checkSuccess(false,CodeMessage.TOKEN_NO_ERR.name());
            return response;
        }
        List<Menu> menus=menuDao.getMenuByRoleId(user.getRoleId());
        JSONArray array=getMenuJson(menus);
        response.setData(array);
        return response;
    }

    private JSONArray getMenuJson(List<Menu> menus) {
        JSONArray array=new JSONArray();
        if(menus!=null&&menus.size()>0){
            for(Menu menu:menus){
                if(menu.getParentId()==null||"".equals(menu.getParentId())){
                    JSONObject json=new JSONObject();
                    json.put("title",menu.getName());
                    json.put("uri",menu.getUri());
                    json.put("lable",menu.getIcon());
                    JSONArray items=new JSONArray();

                    for(Menu m:menus){
                        if(m.getParentId()!=null&&m.getParentId()!=0){
                            if(menu.getId()==m.getParentId()){
                                JSONObject child=new JSONObject();
                                child.put("title",m.getName());
                                child.put("uri",m.getUri());
                                items.add(child);
                            }
                        }
                    }
                    json.put("items",items);
                    array.add(json);
                }
            }
        }
        return array;
    }
}
