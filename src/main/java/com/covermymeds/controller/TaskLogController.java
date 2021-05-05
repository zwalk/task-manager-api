package com.covermymeds.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.covermymeds.JDBC.JDBCTaskLogDAO;
import com.covermymeds.model.TaskLog;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TaskLogController {
	
	@Autowired
	JDBCTaskLogDAO taskLogDAO;
	
	@RequestMapping(path="/task-logs", method=RequestMethod.GET)
	public List<TaskLog> getAll() {
		return taskLogDAO.getAll();
	}
	
	@RequestMapping(path="/task-logs/{id}", method=RequestMethod.GET)
	public TaskLog get(@PathVariable("id") int id) {
		return taskLogDAO.get(id);
	}
	
	@PostMapping("/task-logs")
	@ResponseStatus(HttpStatus.CREATED)
	public void add(@Valid @RequestBody TaskLog taskLog, @RequestHeader(name = "Authorization") String token) {
		token = token.substring(7);
		taskLogDAO.add(taskLog, token);
	}
	
	@PutMapping("/task-logs/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") int id, @Valid @RequestBody TaskLog taskLog,
			@RequestHeader(name = "Authorization") String token) {
		token = token.substring(7);
		taskLog.setId(id);
		taskLogDAO.update(taskLog, token);
	}
	
	@DeleteMapping("/task-logs/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") int id, @RequestHeader(name = "Authorization") String token) {
		token = token.substring(7);
		taskLogDAO.delete(id, token);
	}

}
