package com.example.coursemanagesystem.service.impl;

import com.example.coursemanagesystem.dto.LoginRequest;
import com.example.coursemanagesystem.dto.LoginResponse;
import com.example.coursemanagesystem.dto.StudentRegisterRequest;
import com.example.coursemanagesystem.dto.TeacherRegisterRequest;
import com.example.coursemanagesystem.entity.Student;
import com.example.coursemanagesystem.entity.Teacher;
import com.example.coursemanagesystem.entity.User;
import com.example.coursemanagesystem.mapper.StudentMapper;
import com.example.coursemanagesystem.mapper.TeacherMapper;
import com.example.coursemanagesystem.mapper.UserMapper;
import com.example.coursemanagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public LoginResponse login(LoginRequest req) {
        String account = req.getAccount();
        String password = req.getPassword();
        if (account == null || password == null) {
            return null;
        }
        User user = userMapper.getUserByAccount(account);
        if (user == null) {
            return null;
        }
        if (!password.equals(user.getPassword())) {
            return null;
        }
        LoginResponse resp = new LoginResponse();
        resp.setAccount(account);
        resp.setUserType(user.getUserType());
        if ("student".equals(user.getUserType())) {
            Student stu = studentMapper.getStudentById(account);
            if (stu != null) {
                resp.setStudentName(stu.getStudentName());
                resp.setMajor(stu.getMajor());
                resp.setClassName(stu.getClassName());
            }
        } else if ("teacher".equals(user.getUserType())) {
            Teacher tea = teacherMapper.getTeacherById(account);
            if (tea != null) {
                resp.setTeacherName(tea.getTeacherName());
                resp.setTeacherCourse(tea.getTeacherCourse());
                resp.setMaxHoursPerWeek(tea.getMaxHoursPerWeek());
            }
        }
        return resp;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerStudent(StudentRegisterRequest req) {
        String account = req.getAccount();
        if (account == null) {
            return false;
        }
        User exist = userMapper.getUserByAccount(account);
        if (exist != null) {
            return false;
        }
        // 插入 user
        User user = new User();
        user.setAccount(account);
        user.setPassword(req.getPassword());
        user.setUserType("student");
        int u = userMapper.insert(user);
        if (u <= 0) {
            return false;
        }
        // 插入 student
        Student stu = new Student();
        stu.setStudentId(account);
        stu.setStudentName(req.getStudentName());
        stu.setMajor(req.getMajor());
        stu.setClassName(req.getClassName());
        int s = studentMapper.insert(stu);
        if (s <= 0) {
            throw new RuntimeException("学生信息插入失败，触发回滚");
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerTeacher(TeacherRegisterRequest req) {
        String account = req.getAccount();
        if (account == null) {
            return false;
        }
        User exist = userMapper.getUserByAccount(account);
        if (exist != null) {
            return false;
        }
        // 插入 user
        User user = new User();
        user.setAccount(account);
        user.setPassword(req.getPassword());
        user.setUserType("teacher");
        int u = userMapper.insert(user);
        if (u <= 0) {
            return false;
        }
        // 插入 teacher
        Teacher tea = new Teacher();
        tea.setTeacherId(account);
        tea.setTeacherName(req.getTeacherName());
        tea.setTeacherCourse(req.getTeacherCourse());
        tea.setMaxHoursPerWeek(req.getMaxHoursPerWeek());
        int t = teacherMapper.insert(tea);
        if (t <= 0) {
            throw new RuntimeException("教师信息插入失败，触发回滚");
        }
        return true;
    }
}
