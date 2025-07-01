package com.example.coursemanagesystem.service;

import com.example.coursemanagesystem.entity.ScheduleTask;
import java.util.List;

public interface ScheduleTaskService {
    boolean addScheduleTask(ScheduleTask task);
    boolean updateScheduleTask(ScheduleTask task);
    boolean deleteScheduleTask(String scheduleId, String courseId);

    List<ScheduleTask> getAll();
    List<ScheduleTask> getByScheduleId(String scheduleId);
    List<ScheduleTask> getByCourseId(String courseId);
    ScheduleTask getByScheduleIdAndCourseId(String scheduleId, String courseId);
    List<ScheduleTask> getByCourseName(String courseName);
}

