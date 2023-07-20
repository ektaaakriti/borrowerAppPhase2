package com.securedloan.arthavedika.response;
import java.util.List;

import com.securedloan.arthavedika.model.Applicant;
public class FindAllApplicant {
List<ApplicantResponse> Applicant;
String Message;
String Status;
public List<ApplicantResponse> getApplicant() {
	return Applicant;
}
public void setApplicant(List<ApplicantResponse> applicant) {
	Applicant = applicant;
}
public String getMessage() {
	return Message;
}
public void setMessage(String message) {
	Message = message;
}
public String getStatus() {
	return Status;
}
public void setStatus(String status) {
	Status = status;
}
public FindAllApplicant(List<ApplicantResponse> applicant, String message, String status) {
	super();
	Applicant = applicant;
	Message = message;
	Status = status;
}



}
