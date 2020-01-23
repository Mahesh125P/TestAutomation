package com.testautomation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.testautomation","com.testautomation.controller","com.testautomation.model","com.testautomation.service","com.testautomation.repositories"})
public class TestAutomationApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TestAutomationApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(TestAutomationApplication.class);
	}
 
}
