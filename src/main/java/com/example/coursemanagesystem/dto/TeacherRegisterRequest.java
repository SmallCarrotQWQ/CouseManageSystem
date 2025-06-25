package com.example.coursemanagesystem.dto;

import lombok.Data;

/**
 * 教师注册请求：
 * account（教师ID）、password、teacherName、teacherCourse、maxHoursPerWeek
 */
@Data
public class TeacherRegisterRequest {
    private String account;
    private String password;
    private String teacherName;
    private String teacherCourse;
    private Integer maxHoursPerWeek;
}
