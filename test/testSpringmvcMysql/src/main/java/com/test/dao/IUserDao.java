package com.test.dao;

import java.util.List;

import com.test.model.User;

public interface IUserDao {
	/**
     * 插入用户
     * @param user
     * @return
     */
    public int insertUser(User user);
    
    public List<User> getAll();
}
