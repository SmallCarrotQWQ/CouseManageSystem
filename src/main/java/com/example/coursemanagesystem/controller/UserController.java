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

    /**
     * 示例: 新增用户
     * POST /api/user/add
     * Body: {"account":"U1001","password":"123456","userType":"student"}
     */
    @PostMapping("/add")
    public Result<?> addUser(@RequestBody User u) {
        int id = userService.createUser(u);
        return id > 0
                ? Result.success("用户添加成功", null)
                : Result.error("用户添加失败");
    }

    /**
     * 示例: 获取用户
     * GET /api/user/{account}
     */
    @GetMapping("/{account}")
    public Result<User> getUser(@PathVariable String account) {
        User u = userService.getUserByAccount(account);
        return u != null
                ? Result.success(u)
                : Result.error("未找到用户");
    }

    /**
     * 示例: 列出所有用户
     * GET /api/user/all
     */
    @GetMapping("/all")
    public Result<List<User>> listUsers() {
        return Result.success(userService.listUsers());
    }

    /**
     * 示例: 更新用户
     * PUT /api/user/update
     * Body: {"account":"U1001","password":"newPwd","userType":"teacher"}
     */
    @PutMapping("/update")
    public Result<?> updateUser(@RequestBody User u) {
        boolean ok = userService.updateUser(u);
        return ok
                ? Result.success("用户更新成功", null)
                : Result.error("用户更新失败");
    }

    /**
     * 示例: 删除用户
     * DELETE /api/user/delete/{account}
     */
    @DeleteMapping("/delete/{account}")
    public Result<?> deleteUser(@PathVariable String account) {
        boolean ok = userService.deleteUser(account);
        return ok
                ? Result.success("用户删除成功", null)
                : Result.error("用户删除失败");
    }
}
