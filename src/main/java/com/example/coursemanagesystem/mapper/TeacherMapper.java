package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("SELECT * FROM teacher WHERE teacher_id = #{teacherId}")
    Teacher getTeacherById(String teacherId);

    @Select("SELECT * FROM teacher WHERE teacher_name = #{teacherName}")
    Teacher getTeacherByName(String teacherName);

    @Select("SELECT * FROM teacher WHERE teacher_course = #{teacherCourse}")
    Teacher getTeacherByCourse(String teacherCourse);

    @Update("UPDATE teacher SET teacher_name = #{teacherName}, max_hours_per_week = #{maxHoursPerWeek} WHERE teacher_id = #{teacherId}")
    int updateTeacher(Teacher teacher);

    @Select("SELECT * FROM teacher")
    List<Teacher> getAllTeachers();
}