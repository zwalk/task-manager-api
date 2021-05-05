package com.covermymeds.model;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Customer {
	
	private int id;
	
	@NotNull(message="name cannot be null")
	@NotEmpty(message="name cannot be empty")
	@NotBlank(message="name cannot be blank")
	private String name;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
