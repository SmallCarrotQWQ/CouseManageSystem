package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.entity.ScheduleResult;
import com.example.coursemanagesystem.utils.Result;
import com.example.coursemanagesystem.dto.StudentUpdateRequest;
import com.example.coursemanagesystem.entity.Student;
import com.example.coursemanagesystem.entity.User;
import com.example.coursemanagesystem.service.StudentService;
import com.example.coursemanagesystem.service.ScheduleResultService;
import com.example.coursemanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired private StudentService studentService;
    @Autowired private UserService userService;
    @Autowired private ScheduleResultService ScheduleResultService;

    /**
     * 示例: 添加学生
     * POST /api/student/add
     * Content-Type: application/json
     * Body:
     * {
     *   "studentId": "S20250603",
     *   "studentName": "王五",
     *   "major": "信息工程",
     *   "className": "信工2023-2"
     * }
     * 同时会在 user 表插入一条记录：
     * account=studentId, password="123456", userType="student"
     */
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public Result<?> add(@RequestBody Student s) {
        // 1. 插入 user 表
        User u = new User();
        u.setAccount(s.getStudentId());
        u.setPassword("123456");
        u.setUserType("student");
        int ui = userService.createUser(u);
        if (ui <= 0) {
            return Result.error("添加学生失败：user表插入错误");
        }
        // 2. 插入 student 表
        boolean ok = studentService.save(s);
        return ok
                ? Result.success("添加学生成功", null)
                : Result.error("添加学生失败：student表插入错误");
    }

    /**
     * 示例: 查询学生
     * GET /api/student/{id}
     * 例如: GET /api/student/S20250603
     */
    @GetMapping("/{id}")
    public Result<Student> get(@PathVariable String id) {
        Student s = studentService.getById(id);
        return s != null
                ? Result.success(s)
                : Result.error("未找到学生");
    }

    /**
     * 示例: 列出所有学生
     * GET /api/student/all
     */
    @GetMapping("/all")
    public Result<List<Student>> list() {
        return Result.success(studentService.list());
    }

    /**
     * 示例: 更新学生信息（包括ID变更）
     * PUT /api/student/update
     * Content-Type: application/json
     * Body:
     * {
     *   "oldAccount": "S20250603",
     *   "studentId": "S20250630",
     *   "studentName": "王五五",
     *   "major": "软件工程",
     *   "className": "软工2023-3"
     * }
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody StudentUpdateRequest req) {
        boolean ok = studentService.updateStudent(req);
        return ok
                ? Result.success("更新学生成功", null)
                : Result.error("更新学生失败");
    }

    /**
     * 示例: 删除学生
     * DELETE /api/student/delete/{id}
     * 例如: DELETE /api/student/delete/S20250630
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable String id) {
        // 同步删除 user
        userService.deleteUser(id);
        return studentService.removeById(id)
                ? Result.success("删除学生成功", null)
                : Result.error("删除学生失败");
    }

    /**
     * 示例: 获取某个学生的课表信息
     * GET /api/student/schedule/{studentId}
     * 示例: GET /api/student/schedule/S20250630
     */
    @GetMapping("/schedule/{studentId}")
    public Result<List<ScheduleResult>> getScheduleByStudentId(@PathVariable String studentId) {
        Student student = studentService.getById(studentId);
        if (student == null) {
            return Result.error("未找到该学生信息");
        }
        String className = student.getClassName();
        List<ScheduleResult> results = ScheduleResultService.getByClassName(className);
        // 排除 scheduleId 字段不需要做额外处理，前端可自行忽略
        return Result.success(results);
    }

}
