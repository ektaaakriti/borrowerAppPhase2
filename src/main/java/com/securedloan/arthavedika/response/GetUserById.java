package com.securedloan.arthavedika.response;

import com.securedloan.arthavedika.model.User;

public class GetUserById {
User user;
String message;
Boolean status;
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
public Boolean getStatus() {
	return status;
}
public void setStatus(Boolean status) {
	this.status = status;
}
public GetUserById(User user, String message, Boolean status) {
	super();
	this.user = user;
	this.message = message;
	this.status = status;
}

}
