package com.covermymeds.JDBC;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.covermymeds.exception.EntityNotFoundException;
import com.covermymeds.exception.UsernameAlreadyExistsException;
import com.covermymeds.model.User;

public class JDBCUserDAO implements DAO<User> {
	
	private JdbcTemplate jdbcTemplate;
	
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public JDBCUserDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.passwordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	public List<User> getAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public User get(int id) {
		throw new UnsupportedOperationException();
	}
	
	public User get(String username) {
		String sql = "SELECT * FROM users WHERE username = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
		User user = null;
		if (result.next()) {
			user = mapUser(result);
		} else {
			throw new EntityNotFoundException("User", username);
		}
		return user;
	}

	@Override
	public void add(User user) {
		checkIfUsernameExists(user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
		jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
	}

	@Override
	public void update(User entity) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException();
	}
	
	private User mapUser(SqlRowSet result) {
		User user = new User();
		user.setId(result.getInt("id"));
		user.setPassword(result.getString("password"));
		user.setUsername(result.getString("username"));
		return user;
	}
	
	private void checkIfUsernameExists(String username) {
		String sql = "SELECT username FROM users WHERE username = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
		if (result.next()) {
			throw new UsernameAlreadyExistsException();
		}
	}

}
