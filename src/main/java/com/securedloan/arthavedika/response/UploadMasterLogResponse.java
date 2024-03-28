package com.securedloan.arthavedika.response;

import java.util.List;

public class UploadMasterLogResponse {
List<UploadMasterResponse> UploadResponse;
String message;
String status;
public List<UploadMasterResponse> getUploadResponse() {
	return UploadResponse;
}
public void setUploadResponse(List<UploadMasterResponse> uploadResponse) {
	UploadResponse = uploadResponse;
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
public UploadMasterLogResponse(List<UploadMasterResponse> uploadResponse, String message, String status) {
	super();
	UploadResponse = uploadResponse;
	this.message = message;
	this.status = status;
}

}
