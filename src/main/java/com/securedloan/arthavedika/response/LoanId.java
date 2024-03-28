package com.securedloan.arthavedika.response;

import java.util.List;

public class LoanId {
List<String> LoanId;
String message;
String status;
public List<String> getLoanId() {
	return LoanId;
}
public void setLoanId(List<String> loanId) {
	LoanId = loanId;
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
public LoanId(List<String> loanId, String message, String status) {
	super();
	LoanId = loanId;
	this.message = message;
	this.status = status;
}
}
