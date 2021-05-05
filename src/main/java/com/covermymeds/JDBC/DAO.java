package com.covermymeds.JDBC;

import java.util.List;

public interface DAO<T> {
	
	public List<T> getAll();
	
	public T get(int id);
	
	public void add(T entity);
	
	public void update(T entity);
	
	public void delete(int id);
	
}
