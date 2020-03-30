package com.java.service;

import com.java.dao.ImplementUserRepository;
import com.java.dto.User;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameExistsException;
import com.java.exception.UsernameNotFound;


public class UserAuthenticateImpl implements UserAuthenticate{

	ImplementUserRepository newRepository = new ImplementUserRepository();

	@Override
	public void registerNewUser(User user) throws UsernameExistsException {
		newRepository.insertUser(user);
	}

	@Override
	public void authenticateUser(User user) throws PasswordMismatch, UsernameNotFound {
		newRepository.retrieveUser(user);
	}
	

}
