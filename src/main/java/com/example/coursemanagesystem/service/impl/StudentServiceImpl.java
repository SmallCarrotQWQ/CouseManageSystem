package com.example.coursemanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coursemanagesystem.dto.StudentUpdateRequest;
import com.example.coursemanagesystem.entity.Course;
import com.example.coursemanagesystem.entity.Student;
import com.example.coursemanagesystem.mapper.StudentMapper;
import com.example.coursemanagesystem.mapper.UserMapper;
import com.example.coursemanagesystem.mapper.CourseMapper;
import com.example.coursemanagesystem.mapper.DynamicTableMapper;
import com.example.coursemanagesystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl
        extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Autowired private UserMapper userMapper;
    @Autowired private StudentMapper studentMapper;
    @Autowired private CourseMapper courseMapper;
    @Autowired private DynamicTableMapper dynamicTableMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudent(StudentUpdateRequest req) {
        String oldId = req.getOldAccount();
        String newId = req.getStudentId();
        //如果 ID 改变，先更新 user 表
        if (!oldId.equals(newId)) {
            int u = userMapper.updateAccount(oldId, newId);
            if (u <= 0) {
                throw new RuntimeException("同步更新 user.account 失败");
            }
        }
        //更新 student 表
        Student s = new Student();
        s.setStudentId(newId);
        s.setStudentName(req.getStudentName());
        s.setMajor(req.getMajor());
        s.setClassName(req.getClassName());

        studentMapper.deleteById(oldId);
        int insert = studentMapper.insert(s);
        if (insert <= 0) {
            throw new RuntimeException("更新 student 表失败");
        }
        return true;
    }

    @Override
    public boolean backupStudentCourses(String studentId) {
        // 查询学生
        Student student = studentMapper.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("学生不存在");
        }

        // 查询课程
        List<Course> courses = courseMapper.getCoursesByClassName(student.getClassName());
        if (courses == null || courses.isEmpty()) {
            throw new RuntimeException("该学生所在班级无课程信息");
        }

        // 创建表
        dynamicTableMapper.createBackupTable(studentId);

        // 插入数据
        for (Course course : courses) {
            dynamicTableMapper.insertCourseIntoBackupTable(studentId, course);
        }

        return true;
    }

    @Override
    public boolean deleteBackupCourses(String studentId) {
        Student student = studentMapper.getStudentById(studentId);
        if (student == null) {
            throw new RuntimeException("学生不存在");
        }

        dynamicTableMapper.dropBackupTable(studentId);
        return true;
    }

}
