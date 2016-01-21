package com.toulezu.test.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.toulezu.test"})
@EnableAutoConfiguration
public class Run {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Run.class);
		app.run(args);
	}
}
