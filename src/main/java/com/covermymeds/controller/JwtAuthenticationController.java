package com.covermymeds.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.covermymeds.config.JwtTokenUtil;
import com.covermymeds.model.User;
import com.covermymeds.service.JwtUserDetailsService;

@RestController
@CrossOrigin(origins = "localhost:4200")
public class JwtAuthenticationController {

	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	@PostMapping("/authenticate")
	public User createAuthenticationToken(@Valid @RequestBody User user) throws Exception {
		authenticate(user.getUsername(), user.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		((User)userDetails).setToken(jwtTokenUtil.generateToken(userDetails));
		((User)userDetails).setPassword(null);
		return (User)userDetails;
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("User is disabled.", e);
		} catch (BadCredentialsException e) {
			throw new Exception("Invalid credentials provided.", e);
		}
	}
}
