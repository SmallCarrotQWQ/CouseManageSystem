package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    @Select("SELECT * FROM course WHERE course_id = #{courseId}")
    Course getCourseById(String courseId);

    @Select("SELECT * FROM course WHERE course_name = #{courseName}")
    Course getCourseByName(String courseName);

    @Update("UPDATE course SET course_name = #{courseName}, course_teacher = #{courseTeacher}, " +
            "request = #{request}, class_name = #{className}, total_hours = #{totalHours}, " +
            "start_end_time = #{startEndTime}, remaining_hours = #{remainingHours} WHERE course_id = #{courseId}")
    int updateCourse(Course course);

    @Select("SELECT * FROM course")
    List<Course> getAllCourses();

    @Select("SELECT * FROM course WHERE class_name = #{className}")
    List<Course> getCoursesByClassName(String className);

}