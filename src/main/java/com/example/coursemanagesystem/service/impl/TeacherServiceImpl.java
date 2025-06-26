package com.example.coursemanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coursemanagesystem.dto.TeacherUpdateRequest;
import com.example.coursemanagesystem.entity.Teacher;
import com.example.coursemanagesystem.mapper.TeacherMapper;
import com.example.coursemanagesystem.mapper.UserMapper;
import com.example.coursemanagesystem.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherServiceImpl
        extends ServiceImpl<TeacherMapper, Teacher>
        implements TeacherService {

    @Autowired private UserMapper userMapper;
    @Autowired private TeacherMapper teacherMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateTeacher(TeacherUpdateRequest req) {
        String oldId = req.getOldAccount();
        String newId = req.getTeacherId();
        if (!oldId.equals(newId)) {
            int u = userMapper.updateAccount(oldId, newId);
            if (u <= 0) {
                throw new RuntimeException("同步更新 user.account 失败");
            }
        }
        // 同理：删除旧记录再插入新
        teacherMapper.deleteById(oldId);
        Teacher t = new Teacher();
        t.setTeacherId(newId);
        t.setTeacherName(req.getTeacherName());
        t.setTeacherCourse(req.getTeacherCourse());
        t.setMaxHoursPerWeek(req.getMaxHoursPerWeek());
        int insert = teacherMapper.insert(t);
        if (insert <= 0) {
            throw new RuntimeException("更新 teacher 表失败");
        }
        return true;
    }
}
