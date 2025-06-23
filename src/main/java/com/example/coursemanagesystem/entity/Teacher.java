package com.example.coursemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("teacher")
public class Teacher {
    @TableField("teacher_id")
    private String teacherId;

    @TableField("teacher_name")
    private String teacherName;

    @TableField("max_hours_per_week")
    private Integer maxHoursPerWeek;
}