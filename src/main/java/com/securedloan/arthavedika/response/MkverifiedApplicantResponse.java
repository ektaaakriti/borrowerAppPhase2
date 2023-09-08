package com.securedloan.arthavedika.response;

import java.util.List;

public class MkverifiedApplicantResponse {
List<MkVerrifiedList> applicant;
String message;
String status;
public List<MkVerrifiedList> getApplicant() {
	return applicant;
}
public void setApplicant(List<MkVerrifiedList> applicant) {
	this.applicant = applicant;
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
public MkverifiedApplicantResponse(List<MkVerrifiedList> applicant, String message, String status) {
	super();
	this.applicant = applicant;
	this.message = message;
	this.status = status;
}


}
