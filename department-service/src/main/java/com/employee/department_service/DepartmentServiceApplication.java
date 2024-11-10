package com.employee.department_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DepartmentServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(DepartmentServiceApplication.class, args);

	}

}
