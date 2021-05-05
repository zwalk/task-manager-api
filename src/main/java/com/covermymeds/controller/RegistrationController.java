package com.covermymeds.controller;

import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.covermymeds.JDBC.DAO;
import com.covermymeds.JDBC.JDBCUserDAO;
import com.covermymeds.model.User;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegistrationController {
	
	private DAO<User> jdbcUserDAO;
	
	@Autowired
	public RegistrationController(DataSource dataSource) {
		this.jdbcUserDAO = new JDBCUserDAO(dataSource);
	}
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@Valid @RequestBody User user) {
		jdbcUserDAO.add(user);
	}
}
