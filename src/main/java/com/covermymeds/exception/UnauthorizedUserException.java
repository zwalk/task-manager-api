package com.covermymeds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6694986932934455451L;

	public UnauthorizedUserException() {
		super("The User is not authorized to edit or delete this entity.");
	}

}
