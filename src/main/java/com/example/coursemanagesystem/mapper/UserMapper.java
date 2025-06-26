package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.User;
import org.apache.ibatis.annotations.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE account = #{account}")
    User getUserByAccount(String account);

    @Update("UPDATE user SET account = #{newAccount} WHERE account = #{oldAccount}")
    int updateAccount(@Param("oldAccount") String oldAccount,
                      @Param("newAccount") String newAccount);

    @Update("UPDATE user SET password = #{password}, user_type = #{userType} WHERE account = #{account}")
    int updateUser(User user);

    @Select("SELECT * FROM user")
    List<User> getAllUsers();
}
