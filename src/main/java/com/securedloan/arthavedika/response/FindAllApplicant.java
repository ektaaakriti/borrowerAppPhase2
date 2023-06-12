package com.securedloan.arthavedika.response;
import java.util.List;

import com.securedloan.arthavedika.model.Applicant;
public class FindAllApplicant {
List<Applicant> Applicant;
String Message;
Boolean Status;
public List<Applicant> getApplicant() {
	return Applicant;
}
public void setApplicant(List<Applicant> applicant) {
	Applicant = applicant;
}
public String getMessage() {
	return Message;
}
public void setMessage(String message) {
	Message = message;
}
public Boolean getStatus() {
	return Status;
}
public void setStatus(Boolean status) {
	Status = status;
}
public FindAllApplicant(List<com.securedloan.arthavedika.model.Applicant> applicant, String message, Boolean status) {
	super();
	Applicant = applicant;
	Message = message;
	Status = status;
}



}
