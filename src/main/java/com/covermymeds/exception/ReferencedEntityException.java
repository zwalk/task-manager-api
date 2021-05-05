package com.covermymeds.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReferencedEntityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReferencedEntityException(String entityName, int id) {
		super(entityName + " " + id + " is used as a reference. Resources that reference it must first be deleted.");
	}

}
