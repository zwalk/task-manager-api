package com.covermymeds.JDBC;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.covermymeds.exception.EntityNotFoundException;
import com.covermymeds.model.Customer;
import com.covermymeds.validation.ForeignKeyValidator;

import javax.sql.DataSource;

public class JDBCCustomerDAO implements DAO<Customer> {
	
	private JdbcTemplate jdbcTemplate;
	private ForeignKeyValidator foreignKeyValidator;
	
	@Autowired
	public JDBCCustomerDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.foreignKeyValidator = new ForeignKeyValidator(dataSource);
	}

	@Override
	public List<Customer> getAll() {
		List<Customer> allCustomers = new ArrayList<Customer>();
		String sql = "SELECT * FROM customers";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			allCustomers.add(mapCustomer(results));
		}
		return allCustomers;
	}

	@Override
	public Customer get(int id) {
		String sql = "SELECT * FROM customers WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		Customer customer = null;
		if (result.next()) {
			customer = mapCustomer(result);
		} else {
			throw new EntityNotFoundException("Customer", id);
		}
		return customer;
	}

	@Override
	public void add(Customer customer) {
		String sql = "INSERT INTO customers (name) VALUES (?)";
		jdbcTemplate.update(sql, customer.getName());
	}

	@Override
	public void update(Customer customer) {
		String sql = "UPDATE customers SET name = ? WHERE id = ?";
		int numberOfRowsAffected = jdbcTemplate.update(sql, customer.getName(), customer.getId());
		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("Customer", customer.getId());
		}
	}

	@Override
	public void delete(int id) {
		foreignKeyValidator.validateCustomerReferences(id);
		String sql = "DELETE FROM customers WHERE id=?";
		int numberOfRowsAffected = jdbcTemplate.update(sql, id);
		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("Customer", id);
		}
	}
	
	private Customer mapCustomer(SqlRowSet results) {
		Customer customer = new Customer();
		customer.setId(results.getInt("id"));
		customer.setName(results.getString("name"));
		return customer;
	}

}
