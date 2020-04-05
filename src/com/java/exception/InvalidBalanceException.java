package com.java.exception;

public class InvalidBalanceException extends DatabaseException{

	private static final long serialVersionUID = 1L;

	public InvalidBalanceException(String errorMessage) {
		super(errorMessage);
	}
}
