package com.pra.spring.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "e_commerce")
public class RegisterDTO implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String fname;
	private String lname;
	private String email;
	private String password;
	@Transient
	private String confimPassword;

	@Column(name = "invalid_login_count")
	private int invalidLoginCount;

	@Column(name = "account_status_locked")
	private boolean accountStatusLocked;

	public RegisterDTO() {

		System.out.println("Created..." + this.getClass().getSimpleName());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfimPassword() {
		return confimPassword;
	}

	public void setConfimPassword(String confimPassword) {
		this.confimPassword = confimPassword;
	}
	public int getInvalidLoginCount() {
		return invalidLoginCount;
	}

	public void setInvalidLoginCount(int invalidLoginCount) {
		this.invalidLoginCount = invalidLoginCount;
	}

	public boolean isAccountStatusLocked() {
		return accountStatusLocked;
	}

	public void setAccountStatusLocked(boolean accountStatusLocked) {
		this.accountStatusLocked = accountStatusLocked;
	}

	@Override
	public String toString() {
		return "RegisterDTO [fname=" + fname + ", lname=" + lname + ", email=" + email + ", password=" + password
				+ ", confimPassword=" + confimPassword + "]";
	}

}
