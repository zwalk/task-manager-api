package com.covermymeds.JDBC;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.covermymeds.config.JwtTokenUtil;
import com.covermymeds.exception.EntityNotFoundException;
import com.covermymeds.exception.UnauthorizedUserException;
import com.covermymeds.model.TaskLog;
import com.covermymeds.validation.ForeignKeyValidator;

@Repository
public class JDBCTaskLogDAO implements DAO<TaskLog> {
	
	private JdbcTemplate jdbcTemplate;
	private ForeignKeyValidator foreignKeyValidator;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	public JDBCTaskLogDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.foreignKeyValidator = new ForeignKeyValidator(dataSource);
	}

	@Override
	public List<TaskLog> getAll() {
		List<TaskLog> allTaskLogs = new ArrayList<TaskLog>();
		String sql = "SELECT * FROM task_logs";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		while (results.next()) {
			allTaskLogs.add(mapTaskLog(results));
		}
		return allTaskLogs;
	}

	@Override
	public TaskLog get(int id) {
		String sql = "SELECT * FROM task_logs WHERE id = ?";
		SqlRowSet result = jdbcTemplate.queryForRowSet(sql, id);
		TaskLog taskLog = null;
		if (result.next()) {
			taskLog = mapTaskLog(result);
		} else {
			throw new EntityNotFoundException("TaskLog", id);
		}
		return taskLog;
	}

	@Override
	public void add(TaskLog taskLog) {
		String sql = "INSERT INTO task_logs (task_id, user_id, start_time, end_time) VALUES (?,?,?,?)";
		foreignKeyValidator.validateTaskIdExists(taskLog.getTaskId());
		foreignKeyValidator.validateUserIdExists(taskLog.getUserId());
		if (taskLog.getStartTime() == null) {
			taskLog.setStartTime(LocalDateTime.now());
		}
		jdbcTemplate.update(sql, taskLog.getTaskId(), taskLog.getUserId(), taskLog.getStartTime()
				, taskLog.getEndTime());		
	}
	
	public void add(TaskLog taskLog, String token) {
		int userId = jwtTokenUtil.getUserIdFromToken(token);
		taskLog.setUserId(userId);
		add(taskLog);
	}

	@Override
	public void update(TaskLog taskLog) {
		String sql = "UPDATE task_logs SET task_id = ?, user_id = ?, start_time = ?, end_time = ? WHERE id = ?";
		foreignKeyValidator.validateTaskIdExists(taskLog.getTaskId());
		foreignKeyValidator.validateUserIdExists(taskLog.getUserId());
		int numberOfRowsAffected = 0;
		if (taskLog.getEndTime() == null) {
			taskLog.setEndTime(LocalDateTime.now());
		}
		if (taskLog.getStartTime() == null) {
			sql = "UPDATE task_logs SET task_id = ?, user_id = ?, end_time = ? WHERE id = ?";
			numberOfRowsAffected = jdbcTemplate.update(sql, taskLog.getTaskId(), taskLog.getUserId(), 
					taskLog.getEndTime(), taskLog.getId());
		} else {
			numberOfRowsAffected = jdbcTemplate.update(sql, taskLog.getTaskId(), taskLog.getUserId(), 
					taskLog.getStartTime(), taskLog.getEndTime(), taskLog.getId());
		}

		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("TaskLog", taskLog.getId());
		}
	}
	
	public void update(TaskLog taskLog, String token) {
		validateMatchingUserId(taskLog.getUserId(), token);
		update(taskLog);
	}

	@Override
	public void delete(int id) {
		String sql = "DELETE FROM task_logs WHERE id=?";
		int numberOfRowsAffected = jdbcTemplate.update(sql, id);
		if (numberOfRowsAffected == 0) {
			throw new EntityNotFoundException("TaskLog", id);
		}
	}
	
	public void delete(int id, String token) {
		TaskLog taskLog = get(id);
		validateMatchingUserId(taskLog.getUserId(), token);
		delete(id);
	}
	
	private TaskLog mapTaskLog(SqlRowSet result) {
		TaskLog taskLog = new TaskLog();
		taskLog.setId(result.getInt("id"));
		taskLog.setTaskId(result.getInt("task_id"));
		taskLog.setUserId(result.getInt("user_id"));
		taskLog.setStartTime(result.getTimestamp("start_time").toLocalDateTime());
		if (result.getTimestamp("end_time") != null) {
			taskLog.setEndTime(result.getTimestamp("end_time").toLocalDateTime());
			taskLog.setDurationInSeconds();
		}
		return taskLog;
	}
	
	private void validateMatchingUserId(int idFromRequest, String token) {
		int userIdFromJwt = jwtTokenUtil.getUserIdFromToken(token);
		if (userIdFromJwt != idFromRequest) {
			throw new UnauthorizedUserException();
		}
	}

}
