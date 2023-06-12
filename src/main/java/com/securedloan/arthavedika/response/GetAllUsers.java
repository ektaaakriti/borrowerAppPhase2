package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.User;

public class GetAllUsers {
	List<User> User;
	String message;
	Boolean status;
	public GetAllUsers(List<User> User, String message, Boolean status) {
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
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
}
