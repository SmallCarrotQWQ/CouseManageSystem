package com.example.coursemanagesystem.service.impl;

import com.example.coursemanagesystem.entity.Course;
import com.example.coursemanagesystem.entity.ScheduleTask;
import com.example.coursemanagesystem.mapper.CourseMapper;
import com.example.coursemanagesystem.mapper.ScheduleTaskMapper;
import com.example.coursemanagesystem.service.ScheduleTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    @Autowired private ScheduleTaskMapper taskMapper;
    @Autowired private CourseMapper courseMapper;

    @Override
    public boolean addScheduleTask(ScheduleTask task) {
        Course c = courseMapper.getCourseById(task.getCourseId());
        if (c == null) return false;

        task.setCourseName(c.getCourseName());
        task.setClassName(c.getClassName());
        task.setTotalHours(c.getTotalHours());
        task.setCourseTime(c.getStartEndTime());

        return taskMapper.insert(task) > 0;
    }

    @Override
    public boolean updateScheduleTask(ScheduleTask task) {
        return taskMapper.updateScheduleTask(task) > 0;
    }

    @Override
    public boolean deleteScheduleTask(String scheduleId, String courseId) {
        return taskMapper.deleteByScheduleIdAndCourseId(scheduleId, courseId) > 0;
    }

    @Override
    public List<ScheduleTask> getAll() {
        return taskMapper.getAllScheduleTasks();
    }

    @Override
    public List<ScheduleTask> getByScheduleId(String scheduleId) {
        return taskMapper.getScheduleTasksByScheduleId(scheduleId);
    }

    @Override
    public List<ScheduleTask> getByCourseId(String courseId) {
        return taskMapper.getScheduleTasksByCourseId(courseId);
    }

    @Override
    public ScheduleTask getByScheduleIdAndCourseId(String scheduleId, String courseId) {
        return taskMapper.getByScheduleIdAndCourseId(scheduleId, courseId);
    }

    @Override
    public List<ScheduleTask> getByCourseName(String courseName) {
        return taskMapper.getScheduleTasksByCourseName(courseName);
    }
}

