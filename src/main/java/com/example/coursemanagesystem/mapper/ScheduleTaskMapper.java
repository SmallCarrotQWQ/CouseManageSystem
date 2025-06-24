package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.ScheduleTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface ScheduleTaskMapper extends BaseMapper<ScheduleTask> {

    @Select("SELECT * FROM schedule_task WHERE schedule_id = #{scheduleId}")
    ScheduleTask getScheduleTaskById(String scheduleId);

    @Select("SELECT * FROM schedule_task WHERE course_id = #{courseId}")
    List<ScheduleTask> getScheduleTasksByCourseId(String courseId);

    @Update("UPDATE schedule_task SET course_name = #{courseName}, class_name = #{className}, " +
            "course_time = #{courseTime}, total_hours = #{totalHours} WHERE schedule_id = #{scheduleId}")
    int updateScheduleTask(ScheduleTask scheduleTask);

    @Select("SELECT * FROM schedule_task")
    List<ScheduleTask> getAllScheduleTasks();
}
