package com.pkest.web.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.pkest")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
