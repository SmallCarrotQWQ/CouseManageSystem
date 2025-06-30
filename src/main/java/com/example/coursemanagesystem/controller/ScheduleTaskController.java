package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.entity.ScheduleTask;
import com.example.coursemanagesystem.service.ScheduleTaskService;
import com.example.coursemanagesystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class ScheduleTaskController {

    @Autowired
    private ScheduleTaskService taskService;

    /**
     * 添加排课任务
     * POST /api/task/add
     * Body: {
     *   "scheduleId": "SC20250001",
     *   "courseId": "C1001"
     * }
     */
    @PostMapping("/add")
    public Result<?> addTask(@RequestBody ScheduleTask task) {
        return taskService.addScheduleTask(task)
                ? Result.success("添加成功", null)
                : Result.error("添加失败，课程不存在");
    }

    /**
     * 更新排课任务
     * PUT /api/task/update
     * Body: {
     *   "scheduleId": "SC20250001",
     *   "courseId": "C1001",
     *   "courseName": "...",
     *   "className": "...",
     *   ...
     * }
     */
    @PutMapping("/update")
    public Result<?> updateTask(@RequestBody ScheduleTask task) {
        return taskService.updateScheduleTask(task)
                ? Result.success("更新成功", null)
                : Result.error("更新失败");
    }

    /**
     * 删除排课任务
     * DELETE /api/task/delete/SC20250001/C1001
     */
    @DeleteMapping("/delete/{scheduleId}/{courseId}")
    public Result<?> deleteTask(@PathVariable String scheduleId, @PathVariable String courseId) {
        return taskService.deleteScheduleTask(scheduleId, courseId)
                ? Result.success("删除成功", null)
                : Result.error("删除失败");
    }

    /**
     * 查询所有排课任务
     * GET /api/task/all
     */
    @GetMapping("/all")
    public Result<List<ScheduleTask>> getAll() {
        return Result.success(taskService.getAll());
    }

    /**
     * 根据课程ID查询任务
     * GET /api/task/byCourseId/C1001
     */
    @GetMapping("/byCourseId/{courseId}")
    public Result<List<ScheduleTask>> getByCourseId(@PathVariable String courseId) {
        return Result.success(taskService.getByCourseId(courseId));
    }

    /**
     * 根据任务ID查询
     * GET /api/task/byScheduleId/SC20250001
     */
    @GetMapping("/byScheduleId/{scheduleId}")
    public Result<ScheduleTask> getByScheduleId(@PathVariable String scheduleId) {
        return Result.success(taskService.getByScheduleId(scheduleId));
    }
}
