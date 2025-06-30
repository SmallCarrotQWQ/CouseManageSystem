package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.ScheduleResult;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ScheduleResultMapper {

    @Insert("INSERT INTO schedule_result (schedule_id, course_id, course_name, class_name, course_time, total_hours, start_time, end_time, location) " +
            "VALUES (#{scheduleId}, #{courseId}, #{courseName}, #{className}, #{courseTime}, #{totalHours}, #{startTime}, #{endTime}, #{location})")
    int insert(ScheduleResult scheduleResult);

    @Update("UPDATE schedule_result SET course_name = #{courseName}, class_name = #{className}, " +
            "course_time = #{courseTime}, total_hours = #{totalHours}, start_time = #{startTime}, end_time = #{endTime}, location = #{location} " +
            "WHERE schedule_id = #{scheduleId} AND course_id = #{courseId}")
    int updateScheduleResult(ScheduleResult scheduleResult);

    @Update("UPDATE schedule_result SET start_time = #{startTime}, end_time = #{endTime}, location = #{location} " +
            "WHERE schedule_id = #{scheduleId} AND course_id = #{courseId}")
    int updateSpecificInfo(ScheduleResult result);

    @Delete("DELETE FROM schedule_result WHERE schedule_id = #{scheduleId} AND course_id = #{courseId}")
    int deleteByScheduleIdAndCourseId(@Param("scheduleId") String scheduleId, @Param("courseId") String courseId);

    @Select("SELECT * FROM schedule_result")
    List<ScheduleResult> getAllScheduleResults();

    @Select("SELECT * FROM schedule_result WHERE schedule_id = #{scheduleId}")
    List<ScheduleResult> getScheduleResultsByScheduleId(String scheduleId);

    @Select("SELECT * FROM schedule_result WHERE course_id = #{courseId}")
    ScheduleResult getScheduleResultByCourseId(String courseId);

    @Select("SELECT * FROM schedule_result WHERE course_name = #{courseName}")
    List<ScheduleResult> getScheduleResultsByCourseName(String courseName);
}
