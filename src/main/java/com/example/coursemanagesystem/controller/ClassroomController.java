package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.entity.Classroom;
import com.example.coursemanagesystem.service.ClassroomService;
import com.example.coursemanagesystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classroom")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    /**
     * 添加教室
     * POST /api/classroom/add
     * {
     *   "classroomId": "C101",
     *   "maxStudents": 60,
     *   "functions": "投影,白板"
     * }
     */
    @PostMapping("/add")
    public Result<?> add(@RequestBody Classroom classroom) {
        return classroomService.save(classroom) ? Result.success("添加成功", null) : Result.error("添加失败");
    }

    /**
     * 查询指定教室
     * GET /api/classroom/{id}
     */
    @GetMapping("/{id}")
    public Result<Classroom> get(@PathVariable String id) {
        Classroom c = classroomService.getById(id);
        return c != null ? Result.success(c) : Result.error("未找到教室");
    }

    /**
     * 查询所有教室
     * GET /api/classroom/all
     */
    @GetMapping("/all")
    public Result<List<Classroom>> list() {
        return Result.success(classroomService.list());
    }

    /**
     * 更新教室信息
     * PUT /api/classroom/update
     * {
     *   "classroomId": "C101",
     *   "maxStudents": 80,
     *   "functions": "多媒体,空调"
     * }
     */
    @PutMapping("/update")
    public Result<?> update(@RequestBody Classroom classroom) {
        return classroomService.updateById(classroom) ? Result.success("更新成功", null) : Result.error("更新失败");
    }

    /**
     * 删除教室
     * DELETE /api/classroom/delete/{id}
     */
    @DeleteMapping("/delete/{id}")
    public Result<?> delete(@PathVariable String id) {
        return classroomService.removeById(id) ? Result.success("删除成功", null) : Result.error("删除失败");
    }
}
