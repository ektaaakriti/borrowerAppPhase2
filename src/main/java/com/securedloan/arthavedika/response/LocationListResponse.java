package com.securedloan.arthavedika.response;

import java.util.List;

public class LocationListResponse {
List<LocationResponse> list;
String message;
String status;
public LocationListResponse(List<LocationResponse> list, String message, String status) {
	super();
	this.list = list;
	this.message = message;
	this.status = status;
}
public List<LocationResponse> getList() {
	return list;
}
public void setList(List<LocationResponse> list) {
	this.list = list;
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
