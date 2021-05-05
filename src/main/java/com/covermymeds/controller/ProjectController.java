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
import com.covermymeds.JDBC.JDBCProjectDAO;
import com.covermymeds.model.Project;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {
	
	DAO<Project> projectDAO;
	
	@Autowired
	public ProjectController(DataSource dataSource) {
		this.projectDAO = new JDBCProjectDAO(dataSource);
	}
	
	@RequestMapping(path="/projects", method=RequestMethod.GET)
	public List<Project> getAll() {
		return projectDAO.getAll();
	}
	
	@RequestMapping(path="/projects/{id}", method=RequestMethod.GET)
	public Project get(@PathVariable("id") int id) {
		return projectDAO.get(id);
	}
	
	@PostMapping("/projects")
	@ResponseStatus(HttpStatus.CREATED)
	public void add(@Valid @RequestBody Project project) {
		projectDAO.add(project);
	}
	
	@PutMapping("/projects/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") int id, @Valid @RequestBody Project project) {
		project.setId(id);
		projectDAO.update(project);
	}
	
	@DeleteMapping("/projects/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") int id) {
		projectDAO.delete(id);
	}
	
}
