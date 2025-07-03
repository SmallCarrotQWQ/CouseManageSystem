package com.example.coursemanagesystem.dto;

import lombok.Data;

/**
 * 登录响应，包含 account、userType 及对应学生/教师详情
 */
@Data
public class LoginResponse {
    private String account;
    private String userType;
    // 学生
    private String studentName;
    private String major;
    private String className;
    // 教师
    private String teacherName;
    private String teacherCourse;
    private Integer maxHoursPerWeek;
}
