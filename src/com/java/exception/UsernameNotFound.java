package com.java.exception;

public class UsernameNotFound extends DatabaseException{

	private static final long serialVersionUID = 1L;

	public UsernameNotFound (String errorMessage) {
		super(errorMessage);
		System.out.println("The specified username was not found in the system.");
	}
}
