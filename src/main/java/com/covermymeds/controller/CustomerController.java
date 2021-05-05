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
import com.covermymeds.JDBC.JDBCCustomerDAO;
import com.covermymeds.model.Customer;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class CustomerController {

	DAO<Customer> customerDAO;
	
	@Autowired
	public CustomerController(DataSource dataSource) {
		customerDAO = new JDBCCustomerDAO(dataSource);
	}
	
	@RequestMapping(path="/customers", method=RequestMethod.GET)
	public List<Customer> getAll() {
		return customerDAO.getAll();
	}
	
	@RequestMapping(path="/customers/{id}", method=RequestMethod.GET)
	public Customer get(@PathVariable("id") int id) {
		return customerDAO.get(id);
	}
	
	@PostMapping("/customers")
	@ResponseStatus(HttpStatus.CREATED)
	public void add(@Valid @RequestBody Customer customer) {
		customerDAO.add(customer);
	}
	
	@PutMapping("/customers/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable("id") int id, @Valid @RequestBody Customer customer) {
		customer.setId(id);
		customerDAO.update(customer);
	}
	
	@DeleteMapping("/customers/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") int id) {
		customerDAO.delete(id);
	}
	
}
