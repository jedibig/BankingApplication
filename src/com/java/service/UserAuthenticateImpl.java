package com.java.service;

import com.java.dao.ImplementUserRepository;
import com.java.dao.UserRepository;


import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameExistsException;
import com.java.exception.UsernameNotFound;


public class UserAuthenticateImpl implements UserAuthenticate{

	UserRepository userRepo = new ImplementUserRepository();

	@Override
	public boolean registerNewUser(User user) throws DatabaseException {
		return userRepo.insertUser(user);
	}

	@Override
	public User authenticateUser(User user) throws PasswordMismatch, UsernameNotFound {
		// TODO Auto-generated method stub
		return null;
	}

	

}
