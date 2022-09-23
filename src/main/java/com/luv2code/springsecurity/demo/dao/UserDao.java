package com.luv2code.springsecurity.demo.dao;

import com.luv2code.springsecurity.demo.entity.User;

public interface UserDao {
	User findUserByName(String theUserName);
	void save(User user);
}
