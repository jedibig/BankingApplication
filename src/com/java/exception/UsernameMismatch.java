package com.java.exception;

public class UsernameMismatch extends DatabaseException {
	private static final long serialVersionUID = 1L;
	
	public UsernameMismatch(String e) {
		super(e);
	}
	
	public UsernameMismatch() {
		this("Username was incorrect");
	}
	
}
