package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.ScheduleResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface ScheduleResultMapper extends BaseMapper<ScheduleResult> {
    @Select("SELECT * FROM schedule_result WHERE schedule_id = #{scheduleId}")
    ScheduleResult getScheduleResultById(String scheduleId);

    @Select("SELECT * FROM schedule_result WHERE course_id = #{courseId}")
    ScheduleResult getScheduleResultByCourseId(String courseId);

    @Update("UPDATE schedule_result SET course_name = #{courseName}, class_name = #{className}, " +
            "course_time = #{courseTime}, total_hours = #{totalHours}, specific_time = #{specificTime}, location = #{location} WHERE schedule_id = #{scheduleId}")
    int updateScheduleResult(ScheduleResult scheduleResult);

    @Select("SELECT * FROM schedule_result")
    List<ScheduleResult> getAllScheduleResults();
}