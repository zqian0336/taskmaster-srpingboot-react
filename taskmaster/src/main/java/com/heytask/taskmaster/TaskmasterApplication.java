package com.heytask.taskmaster;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@SpringBootApplication

public class TaskmasterApplication {


	//Spring IoC 'S Object
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskmasterApplication.class, args);
	}

//	@GetMapping("/hello")
//	public String hello(@RequestParam(value = "Spencer", defaultValue = "World") String name) {
//		return String.format("Hello %s!", name);
//	}
}