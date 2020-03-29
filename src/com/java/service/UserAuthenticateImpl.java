package com.java.service;

import com.java.dao.ImplementUserRepository;
import com.java.dto.User;



public class UserAuthenticateImpl implements UserAuthenticate{

	ImplementUserRepository newRepository = new ImplementUserRepository();
	@Override
	public boolean registerNewUser(User user) {
		if(newRepository.insertUser(user) == true)
			return true;
		else
			return false;
	}

	@Override
	public boolean authenticateUser(User user) {
		if(newRepository.retrieveUser(user) == true)
			return true;
		else
			return false;
	}
	

}
