package com.securedloan.arthavedika.response;

public class BorrowerResponse {
String message;
String Status;
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public String getStatus() {
	return Status;
}
public void setStatus(String status) {
	Status = status;
}
public BorrowerResponse(String message, String string) {
	super();
	this.message = message;
	Status = string;
}

}
