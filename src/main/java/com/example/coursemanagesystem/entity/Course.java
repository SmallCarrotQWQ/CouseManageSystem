package com.example.coursemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("course")
public class Course {
    @TableId("course_id")
    private String courseId;

    @TableField("course_name")
    private String courseName;

    @TableField("course_teacher")
    private String courseTeacher;

    @TableField("request")
    private String request;

    @TableField("class_name")
    private String className;

    @TableField("total_hours")
    private Integer totalHours;

    @TableField("start_end_time")
    private String startEndTime;

    @TableField("remaining_hours")
    private Integer remainingHours;
}