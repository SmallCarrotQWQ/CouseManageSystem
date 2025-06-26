package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.utils.Result;
import com.example.coursemanagesystem.dto.LoginRequest;
import com.example.coursemanagesystem.dto.LoginResponse;
import com.example.coursemanagesystem.dto.StudentRegisterRequest;
import com.example.coursemanagesystem.dto.TeacherRegisterRequest;
import com.example.coursemanagesystem.entity.User;
import com.example.coursemanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * POST /api/user/login
     * 请求体：
     * {
     *   "account": "S20250601",
     *   "password": "123456"
     * }
     * 返回：
     * {
     *   "account": "S20250601",
     *   "userType": "student",
     *   "studentName": "张三",
     *   "major": "软件工程",
     *   "className": "软工2023-1"
     * }
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest req) {
        LoginResponse resp = userService.login(req);
        return resp != null ? Result.success(resp) : Result.error("账号或密码错误");
    }

    /**
     * 学生注册接口
     * POST /api/user/register/student
     * 请求体：
     * {
     *   "account": "S20250601",
     *   "password": "123456",
     *   "studentName": "张三",
     *   "major": "软件工程",
     *   "className": "软工2023-1"
     * }
     */
    @PostMapping("/register/student")
    public Result<?> registerStudent(@RequestBody StudentRegisterRequest req) {
        boolean ok = userService.registerStudent(req);
        return ok ? Result.success("学生注册成功", null) : Result.error("学生注册失败：账号已存在或信息不合法");
    }

    /**
     * 教师注册接口
     * POST /api/user/register/teacher
     * 请求体：
     * {
     *   "account": "T20250601",
     *   "password": "123456",
     *   "teacherName": "李四",
     *   "teacherCourse": "数据库,操作系统",
     *   "maxHoursPerWeek": 20
     * }
     */
    @PostMapping("/register/teacher")
    public Result<?> registerTeacher(@RequestBody TeacherRegisterRequest req) {
        boolean ok = userService.registerTeacher(req);
        return ok ? Result.success("教师注册成功", null) : Result.error("教师注册失败：账号已存在或信息不合法");
    }

    /**
     * 获取单个用户
     * GET /api/user/S20250601
     */
    @GetMapping("/{account}")
    public Result<User> getUser(@PathVariable String account) {
        User u = userService.getUserByAccount(account);
        return u != null ? Result.success(u) : Result.error("未找到用户");
    }

    /**
     * 获取所有用户列表
     * GET /api/user/all
     */
    @GetMapping("/all")
    public Result<List<User>> listUsers() {
        return Result.success(userService.listUsers());
    }

    /**
     * 更新用户密码（只能更新密码，不能更新账号和类型）
     * PUT /api/user/update
     * 请求体：
     * {
     *   "account": "S20250601",
     *   "password": "newPwd123"
     * }
     */
    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody User u) {
        boolean ok = userService.updateUser(u);
        return ok ? Result.success("用户更新成功", null) : Result.error("用户更新失败");
    }

    /**
     * 删除用户（同时删除 student/teacher 表中记录）
     * DELETE /api/user/delete/S20250601
     */
    @DeleteMapping("/delete/{account}")
    public Result<?> deleteUser(@PathVariable String account) {
        boolean ok = userService.deleteUser(account);
        return ok ? Result.success("用户删除成功", null) : Result.error("用户删除失败");
    }
}
