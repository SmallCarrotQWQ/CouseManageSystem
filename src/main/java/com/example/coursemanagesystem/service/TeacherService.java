package com.example.coursemanagesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.coursemanagesystem.dto.TeacherUpdateRequest;
import com.example.coursemanagesystem.entity.Teacher;

public interface TeacherService extends IService<Teacher> {
    boolean updateTeacher(TeacherUpdateRequest req);
}
