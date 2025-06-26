package com.example.coursemanagesystem.dto;

import lombok.Data;

@Data
public class StudentUpdateRequest {
    /** 原始 student_id，也是 user.account */
    private String oldAccount;
    /** 新的 student_id（也作为 user.account） */
    private String studentId;
    private String studentName;
    private String major;
    private String className;
}
