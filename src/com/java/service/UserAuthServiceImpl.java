package com.java.service;

import org.apache.log4j.Logger;

import com.java.dao.UserRepositoryImpl;
import com.java.dao.UserRepository;


import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.PasswordMismatch;


public class UserAuthServiceImpl implements UserAuthenticationService{
	
	static Logger logger = Logger.getLogger(UserAuthServiceImpl.class);
	static UserRepository userRepo = new UserRepositoryImpl();

	@Override
	public boolean registerNewUser(User user) throws DatabaseException {
		return userRepo.insertUser(user);
	}

	@Override
	public User authenticateUser(User user) throws DatabaseException {
		User checkedUser = userRepo.retrieveUser(user);
		
		if(checkedUser != null) {
			//TODO move it to db layer

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
