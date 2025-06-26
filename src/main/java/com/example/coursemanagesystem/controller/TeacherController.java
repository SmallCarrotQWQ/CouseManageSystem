package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.utils.Result;
import com.example.coursemanagesystem.dto.TeacherUpdateRequest;
import com.example.coursemanagesystem.entity.Teacher;
import com.example.coursemanagesystem.entity.User;
import com.example.coursemanagesystem.service.TeacherService;
import com.example.coursemanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    private UserService userService;

    /**
     * 示例: 添加教师
     * POST /api/teacher/add
     * Content-Type: application/json
     * Body:
     * {
     *   "teacherId": "T20250603",
     *   "teacherName": "李雷",
     *   "teacherCourse": "网络安全,操作系统",
     *   "maxHoursPerWeek": 20
     * }
     * 同时会在 user 表插入一条记录：
     * account = teacherId, password = "123456", userType = "teacher"
     */
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public Result<?> add(@RequestBody Teacher t) {
        User u = new User();
        u.setAccount(t.getTeacherId());
        u.setPassword("123456");
        u.setUserType("teacher");
        int uc = userService.createUser(u);
        if (uc <= 0) {
            return Result.error("添加教师失败：User 表插入错误");
        }
        boolean ok = teacherService.save(t);
        return ok ? Result.success("添加教师成功", null)
                : Result.error("添加教师失败：Teacher 表插入错误");
    }

    /**
     * 示例: 查询教师
     * GET /api/teacher/{id}
     * 例如: GET /api/teacher/T20250603
     */
    @GetMapping("/{id}")
    public Result<Teacher> get(@PathVariable String id) {
        Teacher t = teacherService.getById(id);
        return t != null ? Result.success(t) : Result.error("未找到教师");
    }

    /**
     * 示例: 列出所有教师
     * GET /api/teacher/all
     */
    @GetMapping("/all")
    public Result<List<Teacher>> list() {
        return Result.success(teacherService.list());
    }

    /**
     * 示例: 更新教师信息（含 ID 变更）
     * PUT /api/teacher/update
     * Content-Type: application/json
     * Body:
     * {
     *   "oldAccount": "T20250603",
     *   "teacherId": "T20250630",
     *   "teacherName": "李雷雷",
     *   "teacherCourse": "Java并发,数据库",
     *   "maxHoursPerWeek": 25
     * }
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody TeacherUpdateRequest req) {
        boolean ok = teacherService.updateTeacher(req);
        return ok ? Result.success("更新教师成功", null)
                : Result.error("更新教师失败");
    }

    /**
     * 示例: 删除教师
     * DELETE /api/teacher/delete/{id}
     * 例如: DELETE /api/teacher/delete/T20250630
     * 同时在 user 表删除对应记录
     */
    @DeleteMapping("/delete/{id}")
    @Transactional(rollbackFor = Exception.class)
    public Result<?> delete(@PathVariable String id) {
        boolean td = teacherService.removeById(id);
        if (td) {
            userService.deleteUser(id);
            return Result.success("删除教师成功", null);
        }
        return Result.error("删除教师失败");
    }
}
