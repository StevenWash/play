package com.test.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.test.dao.IUserDao;
import com.test.model.User;
import com.test.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Resource
	private IUserDao userDao;

	@Override
	public int insertUser(User user) {
		return userDao.insertUser(user);
	}

}
