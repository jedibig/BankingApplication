package com.java.service;

import org.apache.log4j.Logger;

import com.java.dao.ImplementUserRepository;
import com.java.dao.UserRepository;


import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.PasswordMismatch;


public class UserAuthenticateImpl implements UserAuthenticate{
	
	static Logger logger = Logger.getLogger(UserAuthenticateImpl.class);
	static UserRepository userRepo = new ImplementUserRepository();

	@Override
	public boolean registerNewUser(User user) throws DatabaseException {
		return userRepo.insertUser(user);
	}

	@Override
	public User authenticateUser(User user) throws DatabaseException {
		User checkedUser = userRepo.retrieveUser(user);
		
		if(checkedUser != null) {
			String userInput = user.getPassword();
			String dbInput = checkedUser.getPassword();
			
			if(!userInput.equals(dbInput)) {
				logger.info("Password did not match client input");
				throw new PasswordMismatch("The password was incorrect, please try again.");
			}
			checkedUser.setPassword(null);
		}
		
		return checkedUser;
	}
}
