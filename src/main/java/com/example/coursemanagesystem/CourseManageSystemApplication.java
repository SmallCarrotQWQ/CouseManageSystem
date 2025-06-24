package com.example.coursemanagesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.coursemanagesystem.mapper")
public class CourseManageSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourseManageSystemApplication.class, args);
	}

}
