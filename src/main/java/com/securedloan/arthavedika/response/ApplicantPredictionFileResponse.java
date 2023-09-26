package com.securedloan.arthavedika.response;

import java.io.File;

public class ApplicantPredictionFileResponse {
File ResponseFile;
String response;
String status;
public File getResponseFile() {
	return ResponseFile;
}
public void setResponseFile(File responseFile) {
	ResponseFile = responseFile;
}
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
public ApplicantPredictionFileResponse(File responseFile, String response, String status) {
	super();
	ResponseFile = responseFile;
	this.response = response;
	this.status = status;
}

}
