package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Company;

public class AllCompanyName {
List<Company> company;
String message;
Boolean status;
public List<Company> getCompany() {
	return company;
}
public void setCompany(List<Company> company) {
	this.company = company;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public Boolean getStatus() {
	return status;
}
public void setStatus(Boolean status) {
	this.status = status;
}
public AllCompanyName(List<Company> company, String message, Boolean status) {
	super();
	this.company = company;
	this.message = message;
	this.status = status;
}

}
