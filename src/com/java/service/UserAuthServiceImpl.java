package com.java.service;

import org.apache.log4j.Logger;

import com.java.dao.UserRepositoryImpl;
import com.java.dao.UserRepository;


import com.java.dto.User;
import com.java.exception.DatabaseException;


public class UserAuthServiceImpl implements UserAuthenticationService{
	
	static Logger logger = Logger.getLogger(UserAuthServiceImpl.class);
	static UserRepository userRepo = new UserRepositoryImpl();

	@Override
	public boolean registerNewUser(User user) throws DatabaseException {
		return userRepo.insertUser(user);
	}

	@Override
	public User authenticateUser(User user) throws DatabaseException {
		return userRepo.retrieveUser(user);
	}
}
