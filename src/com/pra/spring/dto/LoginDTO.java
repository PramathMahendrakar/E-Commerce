package com.pra.spring.dto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginDTO implements Serializable {

	private final static Logger logger = LoggerFactory.getLogger(LoginDTO.class);

	private String email;
	private String password;

	public LoginDTO() {
		logger.info("Created " + getClass().getSimpleName());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
