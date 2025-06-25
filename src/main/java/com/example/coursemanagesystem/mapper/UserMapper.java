package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT * FROM user WHERE account = #{account}")
    User getUserByAccount(String account);

    @Select("SELECT * FROM user WHERE user_type = #{usertype}")
    User getUserByUserType(String usertype);

    @Update("UPDATE user SET password = #{password}, user_type = #{userType} WHERE account = #{account}")
    int updateUser(User user);

    @Select("SELECT * FROM user")
    List<User> getAllUsers();
}