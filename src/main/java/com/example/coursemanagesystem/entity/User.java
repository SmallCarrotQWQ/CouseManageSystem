package com.example.coursemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId("account")
    private String account;

    @TableField("password")
    private String password;

    @TableField("user_type")
    private String userType;
}
