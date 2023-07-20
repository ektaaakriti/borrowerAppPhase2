package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.User;

public class GetAllUsers {
	List<User> User;
	String message;
	String status;
	public GetAllUsers(List<User> User, String message, String status) {
		super();
		this.User = User;
		this.message = message;
		this.status = status;
	}
	public List<User> getUser() {
		return User;
	}
	public void setUser(List<User> User) {
		this.User = User;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
