package com.example.coursemanagesystem.mapper;

import com.example.coursemanagesystem.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import java.util.List;

@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {
    @Select("SELECT * FROM classroom WHERE classroom_id = #{classroomId}")
    Classroom getClassroomById(String classroomId);

    @Select("SELECT * FROM classroom WHERE functions = #{functions}")
    Classroom getClassroomByFunction(String functions);

    @Update("UPDATE classroom SET max_students = #{maxStudents}, functions = #{functions} WHERE classroom_id = #{classroomId}")
    int updateClassroom(Classroom classroom);

    @Select("SELECT * FROM classroom")
    List<Classroom> getAllClassrooms();
}