package com.covermymeds.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Task {
	
	private int id;
	
	@NotNull(message="projectId cannot be null")
	private Integer projectId;
	
	@NotNull(message="description cannot be null")
	@NotEmpty(message="description cannot be empty")
	@NotBlank(message="description cannot be blank")
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
