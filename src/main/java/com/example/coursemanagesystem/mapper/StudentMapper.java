package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    @Select("SELECT * FROM student WHERE student_id = #{studentId}")
    Student getStudentById(String studentId);

    @Select("SELECT * FROM student WHERE student_name = #{studentName}")
    Student getStudentByName(String studentName);

    @Update("UPDATE student SET student_name = #{studentName}, major = #{major}, class_name = #{className} WHERE student_id = #{studentId}")
    int updateStudent(Student student);

    @Select("SELECT * FROM student")
    List<Student> getAllStudents();
}