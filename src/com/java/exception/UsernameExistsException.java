package com.java.exception;

public class UsernameExistsException extends DatabaseException{

	private static final long serialVersionUID = 1L;

	public UsernameExistsException(String errorMessage) {
		super(errorMessage);
//		System.out.println("The specified username already exists in our system.");
	}

}
