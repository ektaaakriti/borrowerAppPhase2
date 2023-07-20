package com.securedloan.arthavedika.response;

import com.securedloan.arthavedika.model.Company;

public class GetCompanyByIdResponse {
CompanyEnc company;
String message;
String status;
public GetCompanyByIdResponse(CompanyEnc company, String message, String status) {
	super();
	this.company = company;
	this.message = message;
	this.status = status;
}
public CompanyEnc getCompany() {
	return company;
}
public void setCompany(CompanyEnc company) {
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

}
