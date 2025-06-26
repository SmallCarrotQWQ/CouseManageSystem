package com.example.coursemanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coursemanagesystem.entity.Classroom;
import com.example.coursemanagesystem.mapper.ClassroomMapper;
import com.example.coursemanagesystem.service.ClassroomService;
import org.springframework.stereotype.Service;

@Service
public class ClassroomServiceImpl extends ServiceImpl<ClassroomMapper, Classroom> implements ClassroomService {
}