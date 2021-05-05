package com.covermymeds.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public EntityNotFoundException(String nameOfEntity, int id) {
		super(nameOfEntity + " " + id +  " not found.");
	}
	
	public EntityNotFoundException(String nameOfEntity, String identifier) {
		super(nameOfEntity + " " + identifier + " not found.");
	}

}
