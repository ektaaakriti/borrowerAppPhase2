package com.securedloan.arthavedika.response;

public class GeneralResponse {
String message;
Boolean status;
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
public GeneralResponse(String message, Boolean status) {
	super();
	this.message = message;
	this.status = status;
}

}
