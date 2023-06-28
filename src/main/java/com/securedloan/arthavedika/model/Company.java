package com.securedloan.arthavedika.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Table(name = "company")

public class Company {
	@Id
	@Column(name="company_code")
String company_code;
	@OneToMany(mappedBy="company_code")
	private Set<User>  user;
@Column(name="company_name")
String companyName;
Float allowed_amount;
Float current_amount;
public Float getAllowed_amount() {
	return allowed_amount;
}
public void setAllowed_amount(Float allowed_amount) {
	this.allowed_amount = allowed_amount;
}
public Float getCurrent_amount() {
	return current_amount;
}
public void setCurrent_amount(Float current_amount) {
	this.current_amount = current_amount;
}
public String getCompany_code() {
	return company_code;
}
public Company() {
	super();
	// TODO Auto-generated constructor stub
}
public void setCompany_code(String company_code) {
	this.company_code = company_code;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public Company(String company_code, String companyName, Float allowed_amount, Float current_amount) {
	super();
	this.company_code = company_code;
	this.companyName = companyName;
	this.allowed_amount = allowed_amount;
	this.current_amount = current_amount;
}



}
