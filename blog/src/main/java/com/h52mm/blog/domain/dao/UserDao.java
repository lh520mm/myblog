package com.h52mm.blog.domain.dao;


import com.h52mm.blog.domain.entity.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {


    User getUserByAccount(@Param(value = "account") String account);
    User getUserByRowId(@Param(value = "rowId") String rowId);
}
