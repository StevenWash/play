package com.test.run;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.test.run.Run;

@Configuration
@ComponentScan(basePackages = {"com.test"})
@EnableAutoConfiguration // 告知Boot要采用一种特定的方式来对应用进行配置。这种方法会将其他样板式的配置均假设为框架默认的约定，因此能够聚焦于如何尽快地使应用准备就绪以便运行起来
public class Run {
	public static void main(String[] args) {
		SpringApplication.run(Run.class, args);
	}
}