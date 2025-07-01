package com.example.coursemanagesystem.controller;

import com.example.coursemanagesystem.service.AiScheduleService;
import com.example.coursemanagesystem.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiScheduleController {

    @Autowired
    private AiScheduleService aiScheduleService;

    /**
     * 自动排课
     * POST /api/ai/autoSchedule/SC20250001
     */
    @PostMapping("/autoSchedule/{scheduleTaskId}")
    public Result<?> autoSchedule(@PathVariable String scheduleTaskId) {
        boolean ok = aiScheduleService.autoSchedule(scheduleTaskId);
        return ok ? Result.success("AI 排课成功", null) : Result.error("AI 排课失败");
    }
}
