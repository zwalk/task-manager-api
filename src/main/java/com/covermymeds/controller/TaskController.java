package com.covermymeds.controller;

import java.util.List;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.covermymeds.JDBC.DAO;
import com.covermymeds.JDBC.JDBCTaskDAO;
import com.covermymeds.model.Task;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TaskController {
	
	DAO<Task> taskDAO;
	
	@Autowired
	public TaskController(DataSource dataSource) {
		this.taskDAO = new JDBCTaskDAO(dataSource);
	}
	
	@RequestMapping(path="/tasks", method=RequestMethod.GET)
	public List<Task> getAll() {
		return taskDAO.getAll();
	}
	
	@RequestMapping(path="/tasks/{id}", method=RequestMethod.GET)
	public Task get(@PathVariable("id") int id) {
		return taskDAO.get(id);
	}
	
	@PostMapping("/tasks")
	@ResponseStatus(HttpStatus.CREATED)
	public void add(@Valid @RequestBody Task task) {
		taskDAO.add(task);
	}
	
	@PutMapping("/tasks/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") int id, @Valid @RequestBody Task task) {
		task.setId(id);
		taskDAO.update(task);
	}
	
	@DeleteMapping("/tasks/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") int id) {
		taskDAO.delete(id);
	}
}
