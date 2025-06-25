package com.example.coursemanagesystem.service;

import com.example.coursemanagesystem.dto.LoginRequest;
import com.example.coursemanagesystem.dto.LoginResponse;
import com.example.coursemanagesystem.dto.StudentRegisterRequest;
import com.example.coursemanagesystem.dto.TeacherRegisterRequest;

public interface UserService {
    /**
     * 登录：检查 account/password，成功返回 LoginResponse，否则返回 null
     */
    LoginResponse login(LoginRequest req);

    /**
     * 学生注册：account 作为 student_id；插入 user 和 student，失败返回 false
     */
    boolean registerStudent(StudentRegisterRequest req);

    /**
     * 教师注册：account 作为 teacher_id；插入 user 和 teacher，失败返回 false
     */
    boolean registerTeacher(TeacherRegisterRequest req);
}
