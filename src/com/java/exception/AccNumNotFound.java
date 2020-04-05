package com.java.exception;

public class AccNumNotFound extends DatabaseException{

	private static final long serialVersionUID = 1L;

	public AccNumNotFound(String errorMessage) {
		super(errorMessage);
	}
}
