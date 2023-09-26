package com.securedloan.arthavedika.response;
import java.util.*;
import com.securedloan.arthavedika.response.ApplicatListPrediction;
public class ApplicantPredictionListResponse {
List<ApplicatListPrediction> applicant;
String Message;
String status;
public List<ApplicatListPrediction> getApplicant() {
	return applicant;
}
public void setApplicant(List<ApplicatListPrediction> applicant) {
	this.applicant = applicant;
}
public String getMessage() {
	return Message;
}
public void setMessage(String message) {
	Message = message;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public ApplicantPredictionListResponse(List<ApplicatListPrediction> applicant, String message, String status) {
	super();
	this.applicant = applicant;
	Message = message;
	this.status = status;
}
 

}
