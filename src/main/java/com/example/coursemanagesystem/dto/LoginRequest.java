package com.example.coursemanagesystem.dto;

import lombok.Data;

/**
 * 登录请求：account + password
 */
@Data
public class LoginRequest {
    private String account;
    private String password;
}
