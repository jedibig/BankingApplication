package com.java.dao;

import com.java.dto.User;

public interface UserRepository {
	public boolean insertUser(User user);
	public boolean retrieveUser(User user);
}
