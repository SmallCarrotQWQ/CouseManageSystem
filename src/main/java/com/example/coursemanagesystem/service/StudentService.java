package com.example.coursemanagesystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example. coursemanagesystem.dto.StudentUpdateRequest;
import com.example.coursemanagesystem.entity.Student;

public interface StudentService extends IService<Student> {

    boolean updateStudent(StudentUpdateRequest req);

    boolean backupStudentCourses(String studentId);

    boolean deleteBackupCourses(String studentId);

}
