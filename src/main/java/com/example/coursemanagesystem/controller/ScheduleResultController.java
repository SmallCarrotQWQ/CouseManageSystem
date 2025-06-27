package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.entity.ScheduleResult;
import com.example.coursemanagesystem.service.ScheduleResultService;
import com.example.coursemanagesystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleResultController {

    @Autowired
    private ScheduleResultService scheduleResultService;

    /**
     * 添加排课结果
     * POST /api/schedule/add
     * Body: {
     *   "scheduleId": "SC20250001",
     *   "courseId": "C1001",
     *   "courseName": "Java基础",
     *   "className": "软工23-1",
     *   "courseTime": "周一第1-2节",
     *   "totalHours": 32,
     *   "specificTime": "2025-09-01 08:00-10:00",
     *   "location": "教学楼101"
     * }
     */
    @PostMapping("/add")
    public Result<?> addScheduleResult(@RequestBody ScheduleResult result) {
        boolean ok = scheduleResultService.addScheduleResult(result);
        return ok ? Result.success("添加成功", null) : Result.error("添加失败");
    }

    /**
     * 删除排课结果
     * DELETE /api/schedule/delete/SC20250001/C1001
     */
    @DeleteMapping("/delete/{scheduleId}/{courseId}")
    public Result<?> deleteScheduleResult(@PathVariable String scheduleId, @PathVariable String courseId) {
        boolean ok = scheduleResultService.deleteScheduleResult(scheduleId, courseId);
        return ok ? Result.success("删除成功", null) : Result.error("删除失败");
    }

    /**
     * 修改具体时间与地点
     * PUT /api/schedule/update
     * Body: {
     *   "scheduleId": "SC20250001",
     *   "courseId": "C1001",
     *   "specificTime": "2025-09-02 14:00-16:00",
     *   "location": "教学楼202"
     * }
     */
    @PutMapping("/update")
    public Result<?> updateSpecificInfo(@RequestBody ScheduleResult result) {
        boolean ok = scheduleResultService.updateSpecificInfo(result);
        return ok ? Result.success("更新成功", null) : Result.error("更新失败");
    }

    /**
     * 根据排课任务 ID 查询
     * GET /api/schedule/query/byScheduleId/SC20250001
     */
    @GetMapping("/query/byScheduleId/{scheduleId}")
    public Result<List<ScheduleResult>> getByScheduleId(@PathVariable String scheduleId) {
        return Result.success(scheduleResultService.getByScheduleId(scheduleId));
    }

    /**
     * 根据课程 ID 查询
     * GET /api/schedule/query/byCourseId/C1001
     */
    @GetMapping("/query/byCourseId/{courseId}")
    public Result<ScheduleResult> getByCourseId(@PathVariable String courseId) {
        return Result.success(scheduleResultService.getByCourseId(courseId));
    }

    /**
     * 根据课程名模糊查询
     * GET /api/schedule/query/byCourseName/Java
     */
    @GetMapping("/query/byCourseName/{courseName}")
    public Result<List<ScheduleResult>> getByCourseName(@PathVariable String courseName) {
        return Result.success(scheduleResultService.getByCourseName(courseName));
    }

    /**
     * 获取所有排课结果
     * GET /api/schedule/all
     */
    @GetMapping("/all")
    public Result<List<ScheduleResult>> getAllResults() {
        return Result.success(scheduleResultService.getAll());
    }
}