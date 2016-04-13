package com.toulezu.service.impl;

import org.springframework.stereotype.Service;

import com.toulezu.service.IUserService;

@Service
public class UserImpl implements IUserService {

	@Override
	public String getName(String name) {
		return "name is " + name;
	}
}
