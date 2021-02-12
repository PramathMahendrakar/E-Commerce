package com.pra.spring.dto;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResetPasswordDTO implements Serializable {

	private final static Logger logger = LoggerFactory.getLogger(ResetPasswordDTO.class);

	private String password;
	private String newPassword;
	private String cPassword;

	public ResetPasswordDTO() {
		logger.info("Created..." + this.getClass().getSimpleName());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getcPassword() {
		return cPassword;
	}

	public void setcPassword(String cPassword) {
		this.cPassword = cPassword;
	}

}
