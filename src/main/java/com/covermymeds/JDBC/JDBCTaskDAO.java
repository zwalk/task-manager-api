package com.covermymeds.JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.covermymeds.exception.EntityNotFoundException;
import com.covermymeds.model.Task;
import com.covermymeds.validation.ForeignKeyValidator;

public class JDBCTaskDAO implements DAO<Task> {
	
	private JdbcTemplate jdbcTemplate;
	private ForeignKeyValidator foreignKeyValidator;
	
	@Autowired
	public JDBCTaskDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.foreignKeyValidator = new ForeignKeyValidator(dataSource);
	}

	@Override
	public List<Task> getAll() {
		List<Task> allTasks = new ArrayList<Task>();
		String sql = "SELECT * FROM tasks";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			allTasks.add(mapTask(results));
		}
		return allTasks;
	}

	@Override
	public Task get(int id) {
		String sql = "SELECT * FROM tasks WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		Task task = null;
		if (result.next()) {
			task = mapTask(result);
		} else {
			throw new EntityNotFoundException("Task", id);
		}
		return task;
	}

	@Override
	public void add(Task task) {
		String sql = "INSERT INTO tasks (description, project_id) VALUES (?,?)";
		foreignKeyValidator.validateProjectIdExists(task.getProjectId());
		jdbcTemplate.update(sql, task.getDescription(), task.getProjectId());
	}

	@Override
	public void update(Task task) {
		String sql = "UPDATE tasks SET description = ?, project_id = ? WHERE id = ?";
		foreignKeyValidator.validateProjectIdExists(task.getProjectId());
		int numberOfRowsAffected = jdbcTemplate.update(sql, task.getDescription(), task.getProjectId(), task.getId());
		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("Task", task.getId());
		}
	}

	@Override
	public void delete(int id) {
		foreignKeyValidator.validateTaskReferences(id);
		String sql = "DELETE FROM tasks WHERE id=?";
		int numberOfRowsAffected = jdbcTemplate.update(sql, id);
		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("Task", id);
		}
	}
	
	private Task mapTask(SqlRowSet result) {
		Task task = new Task();
		task.setDescription(result.getString("description"));
		task.setId(result.getInt("id"));
		task.setProjectId(result.getInt("project_id"));
		return task;
	}

}
