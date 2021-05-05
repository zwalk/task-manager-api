package com.covermymeds.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Project {
	
	private int id;
	
	@NotNull(message="customerId cannot be null")
	private Integer customerId;
	
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
