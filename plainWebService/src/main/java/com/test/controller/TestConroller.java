package com.test.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestConroller {
	
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String sayHello() {
		return "hello"; // TODO 返回中文会乱码
	}
	
	@RequestMapping(value = "/map", method = RequestMethod.GET)
	public Map<String, String> getMap(@RequestParam String key, @RequestParam String value) {
		Map<String, String> data = new HashMap<String, String>();
		data.put(key, value);
		data.put("b", "b");
		data.put("c", "水");
		return data; // TODO 返回中文会乱码
	}
}
