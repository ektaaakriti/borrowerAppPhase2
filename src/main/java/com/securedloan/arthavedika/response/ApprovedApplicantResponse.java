package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.payload.ApprovedApplicantList;

public class ApprovedApplicantResponse {
List<ApprovedApplicantList> app;
String message;
String status;
public List<ApprovedApplicantList> getApp() {
	return app;
}
public void setApp(List<ApprovedApplicantList> app) {
	this.app = app;
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
public ApprovedApplicantResponse(List<ApprovedApplicantList> app, String message, String status) {
	super();
	this.app = app;
	this.message = message;
	this.status = status;
}

}
