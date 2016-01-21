package com.toulezu.test.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/hello")
public class HelloController {
	
	@RequestMapping(value="/sayHello", method = RequestMethod.GET)
	public String sayHello(@RequestParam String name) {
		return "你好，" + name;
	}
}
