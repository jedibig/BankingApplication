package com.java.exception;

public class UsernameExistsException extends DatabaseException{
	public UsernameExistsException(String errorMessage) {
		super(errorMessage);
		System.out.println("The specified username already exists in our system.");
	}

}
