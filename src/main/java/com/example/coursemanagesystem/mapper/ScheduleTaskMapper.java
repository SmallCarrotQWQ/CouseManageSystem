package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.ScheduleTask;
import org.apache.ibatis.annotations.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

@Mapper
public interface ScheduleTaskMapper extends BaseMapper<ScheduleTask> {

    @Insert("INSERT INTO schedule_task (schedule_id, course_id, course_name, class_name, course_time, total_hours) " +
            "VALUES (#{scheduleId}, #{courseId}, #{courseName}, #{className}, #{courseTime}, #{totalHours})")
    int insert(ScheduleTask task);

    @Update("UPDATE schedule_task SET course_name = #{courseName}, class_name = #{className}, " +
            "course_time = #{courseTime}, total_hours = #{totalHours} " +
            "WHERE schedule_id = #{scheduleId} AND course_id = #{courseId}")
    int updateScheduleTask(ScheduleTask task);

    @Delete("DELETE FROM schedule_task WHERE schedule_id = #{scheduleId} AND course_id = #{courseId}")
    int deleteByScheduleIdAndCourseId(@Param("scheduleId") String scheduleId, @Param("courseId") String courseId);

    @Select("SELECT * FROM schedule_task WHERE schedule_id = #{scheduleId} AND course_id = #{courseId}")
    ScheduleTask getByScheduleIdAndCourseId(@Param("scheduleId") String scheduleId, @Param("courseId") String courseId);

    @Select("SELECT * FROM schedule_task")
    List<ScheduleTask> getAllScheduleTasks();

    @Select("SELECT * FROM schedule_task WHERE schedule_id = #{scheduleId}")
    List<ScheduleTask> getScheduleTasksByScheduleId(String scheduleId);

    @Select("SELECT * FROM schedule_task WHERE course_id = #{courseId}")
    List<ScheduleTask> getScheduleTasksByCourseId(String courseId);

    @Select("SELECT * FROM schedule_task WHERE course_name LIKE CONCAT('%', #{courseName}, '%')")
    List<ScheduleTask> getScheduleTasksByCourseName(String courseName);
}

