package com.example.coursemanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coursemanagesystem.dto.StudentUpdateRequest;
import com.example.coursemanagesystem.entity.Student;
import com.example.coursemanagesystem.mapper.StudentMapper;
import com.example.coursemanagesystem.mapper.UserMapper;
import com.example.coursemanagesystem.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentServiceImpl
        extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Autowired private UserMapper userMapper;
    @Autowired private StudentMapper studentMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStudent(StudentUpdateRequest req) {
        String oldId = req.getOldAccount();
        String newId = req.getStudentId();
        // 1. 如果 ID 改变，先更新 user 表
        if (!oldId.equals(newId)) {
            int u = userMapper.updateAccount(oldId, newId);
            if (u <= 0) {
                throw new RuntimeException("同步更新 user.account 失败");
            }
        }
        // 2. 更新 student 表（包括新的主键和其他字段）
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
}
