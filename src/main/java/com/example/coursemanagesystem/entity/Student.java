package com.example.coursemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("student")
public class Student {
    @TableId("student_id")
    private String studentId;

    @TableField("student_name")
    private String studentName;

    @TableField("major")
    private String major;

    @TableField("class_name")
    private String className;
}