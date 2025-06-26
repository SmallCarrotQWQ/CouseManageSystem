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

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired private UserMapper userMapper;
    @Autowired private StudentMapper studentMapper;
    @Autowired private TeacherMapper teacherMapper;

    @Override
    public LoginResponse login(LoginRequest req) {
        // 登录逻辑同之前代码
        String account = req.getAccount();
        String password = req.getPassword();
        if (account == null || password == null) return null;
        User user = userMapper.getUserByAccount(account);
        if (user == null || !password.equals(user.getPassword())) return null;
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
        // 学生注册同之前逻辑
        String account = req.getAccount();
        if (account == null || userMapper.getUserByAccount(account) != null) return false;
        User u = new User();
        u.setAccount(account);
        u.setPassword(req.getPassword());
        u.setUserType("student");
        if (userMapper.insert(u) <= 0) return false;
        Student stu = new Student();
        stu.setStudentId(account);
        stu.setStudentName(req.getStudentName());
        stu.setMajor(req.getMajor());
        stu.setClassName(req.getClassName());
        if (studentMapper.insert(stu) <= 0) throw new RuntimeException("学生信息插入失败");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerTeacher(TeacherRegisterRequest req) {
        // 教师注册同之前逻辑
        String account = req.getAccount();
        if (account == null || userMapper.getUserByAccount(account) != null) return false;
        User u = new User();
        u.setAccount(account);
        u.setPassword(req.getPassword());
        u.setUserType("teacher");
        if (userMapper.insert(u) <= 0) return false;
        Teacher tea = new Teacher();
        tea.setTeacherId(account);
        tea.setTeacherName(req.getTeacherName());
        tea.setTeacherCourse(req.getTeacherCourse());
        tea.setMaxHoursPerWeek(req.getMaxHoursPerWeek());
        if (teacherMapper.insert(tea) <= 0) throw new RuntimeException("教师信息插入失败");
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int createUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(User user) {
        return userMapper.updateUser(user) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(String account) {
        return userMapper.deleteById(account) > 0;
    }

    @Override
    public User getUserByAccount(String account) {
        return userMapper.getUserByAccount(account);
    }

    @Override
    public List<User> listUsers() {
        return userMapper.getAllUsers();
    }
}