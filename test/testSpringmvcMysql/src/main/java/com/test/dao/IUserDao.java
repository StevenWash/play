package com.test.dao;

import com.test.model.User;

public interface IUserDao {
	/**
     * 添加新用户
     * @param user
     * @return
     */
    public int insertUser(User user);
}
