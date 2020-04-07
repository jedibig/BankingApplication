package com.java.exception;

public class UsernameNotFound extends DatabaseException{

	private static final long serialVersionUID = 1L;

	public UsernameNotFound (String errorMessage) {
		super(errorMessage);
	}
	
	public UsernameNotFound () {
		this("Corresponding username could not be found.");
	}

}
