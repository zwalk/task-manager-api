package com.covermymeds.model;

import java.time.Duration;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class TaskLog {
	
	private int id;
	
	@NotNull(message="taskId cannot be null")
	private Integer taskId;
	
	private Integer userId;

	private LocalDateTime startTime;
	
	private LocalDateTime endTime;
	
	private Long durationInSeconds;

	public Long getDurationInSeconds() {
		return durationInSeconds;
	}
	
	public void setDurationInSeconds() {
		Long duration = null;
		if (startTime != null && endTime != null) {
			duration = Duration.between(startTime, endTime).toSeconds();
			
		}
		this.durationInSeconds = duration;
	}

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

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	

}
