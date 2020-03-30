package com.java.service;

import com.java.dto.User;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameExistsException;
import com.java.exception.UsernameNotFound;

public interface UserAuthenticate {
	void registerNewUser(User user) throws UsernameExistsException;
	void authenticateUser(User user) throws PasswordMismatch, UsernameNotFound;
}