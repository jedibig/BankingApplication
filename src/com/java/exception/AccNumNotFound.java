package com.java.exception;

public class AccNumNotFound extends DatabaseException{

	private static final long serialVersionUID = 1L;

	public AccNumNotFound() {
		super("Corresponding account from specified account number could not be found.");
	}
}
