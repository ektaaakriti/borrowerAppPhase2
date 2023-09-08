package com.securedloan.arthavedika.response;

public class MetaResponse_Response {
String response;
String status;
public String getResponse() {
	return response;
}
public void setResponse(String response) {
	this.response = response;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public MetaResponse_Response(String response, String status) {
	super();
	this.response = response;
	this.status = status;
}

}
