package com.example.coursemanagesystem.service;

import com.example.coursemanagesystem.entity.ScheduleResult;

import java.util.List;

public interface ScheduleResultService {
    boolean addScheduleResult(ScheduleResult scheduleResult);

    boolean updateScheduleResult(ScheduleResult scheduleResult);

    boolean deleteScheduleResult(String scheduleId, String courseId);

    boolean updateSpecificInfo(ScheduleResult result);

    List<ScheduleResult> getAll();

    List<ScheduleResult> getByScheduleId(String scheduleId);

    ScheduleResult getByCourseId(String courseId);

    List<ScheduleResult> getByCourseName(String courseName);

    List<ScheduleResult> getByClassName(String className);

    List<ScheduleResult> getByCourseIds(List<String> courseIds);

}