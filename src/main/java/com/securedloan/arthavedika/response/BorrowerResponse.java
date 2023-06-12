package com.securedloan.arthavedika.response;

public class BorrowerResponse {
String message;
Boolean Status;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Boolean getStatus() {
	return Status;
}
public void setStatus(Boolean status) {
	Status = status;
}
public BorrowerResponse(String message, Boolean status) {
	super();
	this.message = message;
	Status = status;
}

}
