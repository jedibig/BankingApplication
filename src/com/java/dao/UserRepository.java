package com.java.dao;

import com.java.dto.User;
import com.java.exception.DatabaseException;
import com.java.exception.PasswordMismatch;
import com.java.exception.UsernameNotFound;

public interface UserRepository {
	public boolean insertUser(User user) throws DatabaseException;
	public void retrieveUser(User user) throws PasswordMismatch, UsernameNotFound;
}