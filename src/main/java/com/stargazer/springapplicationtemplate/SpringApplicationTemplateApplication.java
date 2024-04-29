package com.stargazer.springapplicationtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringApplicationTemplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringApplicationTemplateApplication.class, args);
	}

}
