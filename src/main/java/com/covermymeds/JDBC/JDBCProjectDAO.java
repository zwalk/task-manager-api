package com.covermymeds.JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.covermymeds.exception.EntityNotFoundException;
import com.covermymeds.model.Project;
import com.covermymeds.validation.ForeignKeyValidator;

public class JDBCProjectDAO implements DAO<Project> {
	
	private JdbcTemplate jdbcTemplate;
	private ForeignKeyValidator foreignKeyValidator;
	
	@Autowired
	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.foreignKeyValidator = new ForeignKeyValidator(dataSource);
	}

	@Override
	public List<Project> getAll() {
		List<Project> allProjects = new ArrayList<Project>();
		String sql = "SELECT * FROM projects";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			allProjects.add(mapProject(results));
		}
		return allProjects;
	}

	@Override
	public Project get(int id) {
		String sql = "SELECT * FROM projects WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		Project project = null;
		if (result.next()) {
			project = mapProject(result);
		} else {
			throw new EntityNotFoundException("Project", id);
		}
		return project;
	}

	@Override
	public void add(Project project) {
		String sql = "INSERT INTO projects (name, customer_id) VALUES (?,?)";
		foreignKeyValidator.validateCustomerIdExists(project.getCustomerId());
		jdbcTemplate.update(sql, project.getName(), project.getCustomerId());
	}

	@Override
	public void update(Project project) {
		String sql = "UPDATE projects SET name = ?, customer_id = ? WHERE id = ?";
		foreignKeyValidator.validateCustomerIdExists(project.getCustomerId());
		int numberOfRowsAffected = jdbcTemplate.update(sql, project.getName(), project.getCustomerId(), project.getId());
		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("Project", project.getId());
		}
	}

	@Override
	public void delete(int id) {
		foreignKeyValidator.validateProjectReferences(id);
		String sql = "DELETE FROM projects WHERE id=?";
		int numberOfRowsAffected = jdbcTemplate.update(sql, id);
		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("Project", id);
		}
	}
	
	private Project mapProject(SqlRowSet result) {
		Project project = new Project();
		project.setCustomerId(result.getInt("customer_id"));
		project.setId(result.getInt("id"));
		project.setName(result.getString("name"));
		return project;
	}

}
