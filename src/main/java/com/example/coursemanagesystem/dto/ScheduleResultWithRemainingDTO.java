package com.example.coursemanagesystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleResultWithRemainingDTO {
    private String courseId;
    private String courseName;
    private String className;
    private String courseTime;
    private Integer totalHours;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
    private Integer remainingHours;
}
