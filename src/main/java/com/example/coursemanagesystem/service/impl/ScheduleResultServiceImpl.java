package com.example.coursemanagesystem.service.impl;

import com.example.coursemanagesystem.entity.Course;
import com.example.coursemanagesystem.entity.ScheduleResult;
import com.example.coursemanagesystem.mapper.CourseMapper;
import com.example.coursemanagesystem.mapper.ScheduleResultMapper;
import com.example.coursemanagesystem.service.ScheduleResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleResultServiceImpl implements ScheduleResultService {
    @Autowired private ScheduleResultMapper resultMapper;
    @Autowired private CourseMapper courseMapper;

    @Override
    public boolean addScheduleResult(ScheduleResult scheduleResult) {
        Course c = courseMapper.getCourseById(scheduleResult.getCourseId());
        if (c == null) return false;

        scheduleResult.setCourseName(c.getCourseName());
        scheduleResult.setClassName(c.getClassName());
        scheduleResult.setTotalHours(c.getTotalHours());
        scheduleResult.setCourseTime(c.getStartEndTime());

        return resultMapper.insert(scheduleResult) > 0;
    }

    @Override
    public boolean updateScheduleResult(ScheduleResult scheduleResult) {
        return resultMapper.updateScheduleResult(scheduleResult) > 0;
    }

    @Override
    public boolean deleteScheduleResult(String scheduleId, String courseId) {
        return resultMapper.deleteByScheduleIdAndCourseId(scheduleId, courseId) > 0;
    }

    @Override
    public boolean updateSpecificInfo(ScheduleResult result) {
        ScheduleResult existing = resultMapper.getScheduleResultByCourseId(result.getCourseId());
        if (existing == null) return false;

        existing.setStartTime(result.getStartTime());
        existing.setEndTime(result.getEndTime());
        existing.setLocation(result.getLocation());

        return resultMapper.updateScheduleResult(existing) > 0;
    }

    @Override
    public List<ScheduleResult> getAll() {
        return resultMapper.getAllScheduleResults();
    }

    @Override
    public List<ScheduleResult> getByScheduleId(String scheduleId) {
        return resultMapper.getScheduleResultsByScheduleId(scheduleId);
    }

    @Override
    public ScheduleResult getByCourseId(String courseId) {
        return resultMapper.getScheduleResultByCourseId(courseId);
    }

    @Override
    public List<ScheduleResult> getByCourseName(String courseName) {
        return resultMapper.getScheduleResultsByCourseName(courseName);
    }

    @Override
    public List<ScheduleResult> getByClassName(String className) {
        return resultMapper.getScheduleResultsByClassName(className);
    }

    @Override
    public List<ScheduleResult> getByCourseIds(List<String> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) return List.of();
        return resultMapper.getScheduleResultsByCourseIds(courseIds);
    }


}
