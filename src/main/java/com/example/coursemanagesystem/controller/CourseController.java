package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.entity.Course;
import com.example.coursemanagesystem.service.CourseService;
import com.example.coursemanagesystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 添加课程
     * POST /api/course/add
     * {
     *   "courseId": "CS101",
     *   "courseName": "操作系统",
     *   "courseTeacher": "T20250601",
     *   "location": "C101",
     *   "className": "计科一班",
     *   "totalHours": 64,
     *   "startEndTime": "2025-09-01 ~ 2025-12-30",
     *   "remainingHours": 64
     * }
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Course course) {
        return courseService.save(course) ? Result.success("添加成功", null) : Result.error("添加失败");
    }

    /**
     * 查询指定课程
     * GET /api/course/{id}
     */
    @GetMapping("/{id}")
    public Result<Course> get(@PathVariable String id) {
        Course c = courseService.getById(id);
        return c != null ? Result.success(c) : Result.error("未找到课程");
    }

    /**
     * 查询所有课程
     * GET /api/course/all
     */
    @GetMapping("/all")
    public Result<List<Course>> list() {
        return Result.success(courseService.list());
    }

    /**
     * 更新课程信息
     * PUT /api/course/update
     * {
     *   "courseId": "CS101",
     *   "courseName": "高级操作系统",
     *   "courseTeacher": "T20250601",
     *   "location": "C102",
     *   "className": "计科一班",
     *   "totalHours": 64,
     *   "startEndTime": "2025-09-01 ~ 2025-12-30",
     *   "remainingHours": 60
     * }
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody Course course) {
        return courseService.updateById(course) ? Result.success("更新成功", null) : Result.error("更新失败");
    }

    /**
     * 删除课程
     * DELETE /api/course/delete/{id}
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable String id) {
        return courseService.removeById(id) ? Result.success("删除成功", null) : Result.error("删除失败");
    }
}