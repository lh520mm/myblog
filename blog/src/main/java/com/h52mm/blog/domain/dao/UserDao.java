package com.h52mm.blog.domain.dao;



import com.h52mm.blog.domain.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    List<User> getList(@Param(value = "pageSize") Integer pageSize, @Param(value = "pageNumber") Integer pageNumber, @Param(value = "userName") String userName);

    void saveUser(User user);

    User getUserByAccount(@Param(value = "account") String account);

    Integer getUserCount(@Param(value = "userName") String userName, @Param(value = "account") String account, @Param(value = "depId") String depId, @Param(value = "state") Integer state);

    void addRole(@Param(value = "rowId") String rowId, @Param(value = "userId") String userId, @Param(value = "roleId") String roleId);

    User getUserByRowId(@Param(value = "rowId") String rowId);

    void updateUser(User user);

    void delRole(@Param(value = "userId") String userId);

    List<User> getSoliderList(@Param(value = "pageSize") Integer pageSize, @Param(value = "pageNumber") Integer pageNumber,
                              @Param(value = "userName") String userName, @Param(value = "account") String account, @Param(value = "depId") String depId);
}
