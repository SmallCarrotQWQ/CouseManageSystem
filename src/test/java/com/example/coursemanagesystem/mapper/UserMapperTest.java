package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import java.util.List;

@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testGetUserByAccount() {

        User user = userMapper.getUserByAccount("admin");
        Assertions.assertNotNull(user);
        System.out.println(user);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = userMapper.getAllUsers();
        Assertions.assertNotNull(users);
        users.forEach(System.out::println);
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setAccount("test_user");
        user.setPassword("new_password");
        user.setRole("student");
        user.setUserType("Student");
        int result = userMapper.updateUser(user);
        System.out.println("Update result: " + result);
    }
}