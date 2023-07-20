package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Company;

public class AllCompanyName {
List<CompanyEnc> company;
String message;
String status;
public List<CompanyEnc> getCompany() {
	return company;
}
public void setCompany(List<CompanyEnc> company) {
	this.company = company;
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
public AllCompanyName(List<CompanyEnc> company, String message, String status) {
	super();
	this.company = company;
	this.message = message;
	this.status = status;
}

}
