package com.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "file:.env", ignoreResourceNotFound = true)
public class ErpEmployeeRegisterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErpEmployeeRegisterApplication.class, args);
	}

}
