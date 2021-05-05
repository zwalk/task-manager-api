package com.covermymeds.validation;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.covermymeds.exception.EntityNotFoundException;
import com.covermymeds.exception.ReferencedEntityException;

public class ForeignKeyValidator {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public ForeignKeyValidator(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public void validateCustomerIdExists(int customerId) {
		String sql = "SELECT * FROM customers WHERE id = ?";
		handleEntityExistsValidation(sql, customerId, "Customer");
	}
	
	public void validateProjectIdExists(int projectId) {
		String sql = "SELECT * FROM projects WHERE id = ?";
		handleEntityExistsValidation(sql, projectId, "Project");
	}
	
	public void validateTaskIdExists(int taskId) {
		String sql = "SELECT * FROM tasks WHERE id = ?";
		handleEntityExistsValidation(sql, taskId, "Task");
	}
	
	public void validateUserIdExists(int userId) {
		String sql = "SELECT username FROM users WHERE id = ?";
		handleEntityExistsValidation(sql, userId, "User");
	}
	
	public void validateCustomerReferences(int customerId) {
		String sql = "SELECT name FROM projects WHERE customer_id = ?";
		handleReferencedEntityValidation(sql, customerId, "Customer");
	}
	
	public void validateProjectReferences(int projectId) {
		String sql = "SELECT id FROM tasks WHERE project_id = ?";
		handleReferencedEntityValidation(sql, projectId, "Project");
	}
	
	public void validateTaskReferences(int taskId) {
		String sql = "SELECT id FROM task_logs WHERE task_id = ?";
		handleReferencedEntityValidation(sql, taskId, "Task");
	}
	
	public void validateUserReferences(int userId) {
		String sql = "SELECT id FROM task_logs WHERE user_id = ?";
		handleReferencedEntityValidation(sql, userId, "User");
	}
	
	private void handleEntityExistsValidation(String sql, int id, String entityName) {
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		if (!result.next()) {
			throw new EntityNotFoundException(entityName, id);
		}
	}
	
	private void handleReferencedEntityValidation(String sql, int id, String entityName) {
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		if (result.next()) {
			throw new ReferencedEntityException(entityName, id);
		}
	}

}
