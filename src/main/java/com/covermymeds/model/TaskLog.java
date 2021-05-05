package com.covermymeds.model;

import javax.validation.constraints.NotNull;

public class TaskLog {
	
	private int id;
	
	@NotNull(message="taskId cannot be null")
	private Integer taskId;
	
	private Integer userId;
	
	@NotNull(message="durationInMinutes cannot be null")
	private Integer durationInMinutes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(Integer durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}
	
	

}
