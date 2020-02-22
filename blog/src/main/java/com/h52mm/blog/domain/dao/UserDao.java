package com.h52mm.blog.domain.dao;


import com.h52mm.blog.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {


    User getUserByAccount(@Param(value = "account") String account);
    User getUserByRowId(@Param(value = "rowId") String rowId);

    Integer getUserTotal(@Param(value = "account")String account, @Param(value = "nickname") String nickname, @Param(value = "tel") String tel);

    List<User> getUserList(@Param(value = "pageSize")Integer pageSize, @Param(value = "pageNumber") Integer pageNumber,
                           @Param(value = "account")String account,@Param(value = "nickname") String nickname,@Param(value = "tel") String tel);

    void save(User user);
}
