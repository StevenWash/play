package com.test.dao;

import com.test.model.User;

public interface IUserDao {
	/**
     * ������û�
     * @param user
     * @return
     */
    public int insertUser(User user);
}
