package com.covermymeds.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4645297097660812801L;

	public UsernameAlreadyExistsException() {
		super("Username already exists.");
	}
	
}
