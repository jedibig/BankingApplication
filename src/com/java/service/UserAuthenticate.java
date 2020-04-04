package com.java.service;

import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameNotFound;

public interface UserAuthenticate {
	boolean registerNewUser(User user) throws DatabaseException;
	User authenticateUser(User user) throws PasswordMismatch, UsernameNotFound, DatabaseException;
}