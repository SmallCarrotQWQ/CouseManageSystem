package com.example.coursemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("schedule_result")
public class ScheduleResult {
    @TableField("schedule_id")
    private String scheduleId;

    @TableField("course_id")
    private String courseId;

    @TableField("course_name")
    private String courseName;

    @TableField("class_name")
    private String className;

    @TableField("course_time")
    private String courseTime;

    @TableField("total_hours")
    private Integer totalHours;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("location")
    private String location;
}