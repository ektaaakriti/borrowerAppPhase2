package com.securedloan.arthavedika.response;

import com.securedloan.arthavedika.model.User;

public class GetUserById {
User user;
String message;
String status;
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
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
public GetUserById(User user, String message, String status) {
	super();
	this.user = user;
	this.message = message;
	this.status = status;
}

}
