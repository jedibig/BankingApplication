package com.java.exception;

public class UsernameExistsException extends DatabaseException{

	private static final long serialVersionUID = 1L;

	public UsernameExistsException(String errorMessage) {
		super(errorMessage);
	}
	
	public UsernameExistsException() {
		this("Username already exists");
	}

}
