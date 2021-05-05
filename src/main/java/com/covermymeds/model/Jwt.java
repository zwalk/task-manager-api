package com.covermymeds.model;

import java.io.Serializable;

public class Jwt implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8671414990414441768L;
	
	private String jwt;
	
	public Jwt(String jwt) {
		this.jwt = jwt;
	}
	
	public String getJwt() {
		return this.jwt;
	}
 
}
