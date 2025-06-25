package com.example.coursemanagesystem.dto;

import lombok.Data;

/**
 * 学生注册请求：
 * account（学号）、password、studentName、major、className
 */
@Data
public class StudentRegisterRequest {
    private String account;
    private String password;
    private String studentName;
    private String major;
    private String className;
}
