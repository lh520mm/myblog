package com.h52mm.blog.domain.dao;

import com.h52mm.blog.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {
    List<Menu> getMenuByRoleId(@Param(value = "roleId") Integer roleId);
}
