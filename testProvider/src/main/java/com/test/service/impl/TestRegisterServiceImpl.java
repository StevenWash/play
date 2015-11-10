package com.test.service.impl;

import org.springframework.stereotype.Service;

import com.test.service.TestRegisterService;

@Service("testRegisterService")
public class TestRegisterServiceImpl implements TestRegisterService {

	public String sayHello(String name) {
		return "hello, " + name;
	}
}
