package com.example.coursemanagesystem.dto;

import lombok.Data;

@Data
public class TeacherUpdateRequest {
    /** 原始 teacher_id，也是 user.account */
    private String oldAccount;
    /** 新的 teacher_id（也作为 user.account） */
    private String teacherId;
    private String teacherName;
    private String teacherCourse;
    private Integer maxHoursPerWeek;
}
