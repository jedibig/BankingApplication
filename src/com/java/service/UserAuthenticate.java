package com.java.service;

import com.java.dto.User;

public interface UserAuthenticate {
	boolean registerNewUser(User user);
	boolean authenticateUser(User user);
}
