package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.utils.Result;
import com.example.coursemanagesystem.dto.LoginRequest;
import com.example.coursemanagesystem.dto.LoginResponse;
import com.example.coursemanagesystem.dto.StudentRegisterRequest;
import com.example.coursemanagesystem.dto.TeacherRegisterRequest;
import com.example.coursemanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * POST /api/user/login
     * 请求体: { "account":"...", "password":"..." }
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse resp = userService.login(req);
        if (resp != null) {
            return Result.success(resp);
        } else {
            return Result.error("账号或密码错误");
        }
    }

    /**
     * 学生注册接口
     * POST /api/user/register/student
     * 请求体:
     * {
     *   "account":"S20250601",
     *   "password":"pwd123",
     *   "studentName":"张三",
     *   "major":"软件工程",
     *   "className":"软工2023-1"
     * }
     */
    @PostMapping("/register/student")
    public Result<?> registerStudent(@RequestBody StudentRegisterRequest req) {
        boolean ok = userService.registerStudent(req);
        if (ok) {
            return Result.success("学生注册成功", null);
        } else {
            return Result.error("学生注册失败：账号已存在或信息不合法");
        }
    }

    /**
     * 教师注册接口
     * POST /api/user/register/teacher
     * 请求体:
     * {
     *   "account":"T20250601",
     *   "password":"pwd123",
     *   "teacherName":"李四",
     *   "teacherCourse":"Java编程,数据库",
     *   "maxHoursPerWeek":20
     * }
     */
    @PostMapping("/register/teacher")
    public Result<?> registerTeacher(@RequestBody TeacherRegisterRequest req) {
        boolean ok = userService.registerTeacher(req);
        if (ok) {
            return Result.success("教师注册成功", null);
        } else {
            return Result.error("教师注册失败：账号已存在或信息不合法");
        }
    }
}
