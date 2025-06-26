package com.example.coursemanagesystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.coursemanagesystem.entity.Course;
import com.example.coursemanagesystem.mapper.CourseMapper;
import com.example.coursemanagesystem.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}