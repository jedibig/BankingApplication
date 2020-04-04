package com.java.service;

import com.java.dto.User;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameExistsException;
import com.java.exception.UsernameNotFound;

public interface UserAuthenticate {
	User registerNewUser(User user) throws UsernameExistsException;
	boolean authenticateUser(User user) throws PasswordMismatch, UsernameNotFound;
}