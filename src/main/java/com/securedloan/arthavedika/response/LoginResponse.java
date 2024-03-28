package com.securedloan.arthavedika.response;

import com.securedloan.arthavedika.model.User;

public class LoginResponse {
	private String message;
	private User user;
	private Boolean status;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public LoginResponse(String message, Boolean status, User user) {
		super();
		this.message = message;
		this.user = user;
		this.status = status;
	}
	
}
