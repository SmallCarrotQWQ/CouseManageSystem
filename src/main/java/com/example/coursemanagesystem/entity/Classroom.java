package com.example.coursemanagesystem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
@TableName("classroom")
public class Classroom {
    @TableId("classroom_id")
    private String classroomId;

    @TableField("max_students")
    private Integer maxStudents;

    @TableField("functions")
    private String functions;
}