package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DynamicTableMapper {

    @Update("""
        CREATE TABLE IF NOT EXISTS `${tableName}` (
            course_id VARCHAR(50) PRIMARY KEY,
            course_name VARCHAR(100),
            course_teacher VARCHAR(100),
            request TEXT,
            class_name VARCHAR(100),
            total_hours INT,
            start_end_time VARCHAR(100),
            remaining_hours INT
        )
    """)
    void createBackupTable(@Param("tableName") String tableName);

    @Insert("""
        INSERT INTO `${tableName}` (
            course_id, course_name, course_teacher, request,
            class_name, total_hours, start_end_time, remaining_hours
        ) VALUES (
            #{course.courseId}, #{course.courseName}, #{course.courseTeacher}, #{course.request},
            #{course.className}, #{course.totalHours}, #{course.startEndTime}, #{course.remainingHours}
        )
    """)
    void insertCourseIntoBackupTable(@Param("tableName") String tableName, @Param("course") Course course);

    @Update("""
    DROP TABLE IF EXISTS `${tableName}`
""")
    void dropBackupTable(@Param("tableName") String tableName);

}
