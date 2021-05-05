package com.covermymeds.service;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.covermymeds.JDBC.JDBCUserDAO;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	 private JDBCUserDAO jdbcUserDAO;
	 
	 @Autowired
	 public JwtUserDetailsService(DataSource dataSource) {
		 this.jdbcUserDAO = new JDBCUserDAO(dataSource);
	 }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return jdbcUserDAO.get(username);
	}

}
