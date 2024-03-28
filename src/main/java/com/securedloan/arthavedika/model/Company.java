package com.securedloan.arthavedika.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Table(name = "company")

public class Company {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "User_seq")
    @GenericGenerator(
        name = "User_seq", 
        strategy = "com.securedloan.arthavedika.model.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "10"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "Com_"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d") })
	@Id
	String company_id;
	
	@Column(name="company_code")
String company_code;
	@OneToMany(mappedBy="company_code")
	private Set<User>  user;
@Column(name="company_name")
String companyName;
Float allowed_amount;
Float current_amount;
String company_address;
String delete_status;
String contact_person1;
Long contact_person_mobile1; 
String contact_person_designation1 ;
String  contact_person_email1;
String contact_person2;
Long contact_person_mobile2;
String contact_person_designation2;
String contact_person_email2;
public Set<User> getUser() {
	return user;
}
public void setUser(Set<User> user) {
	this.user = user;
}
public String getContact_person1() {
	return contact_person1;
}
public void setContact_person1(String contact_person1) {
	this.contact_person1 = contact_person1;
}
public Long getContact_person_mobile1() {
	return contact_person_mobile1;
}
public void setContact_person_mobile1(Long contact_person_mobile1) {
	this.contact_person_mobile1 = contact_person_mobile1;
}
public String getContact_person_designation1() {
	return contact_person_designation1;
}
public void setContact_person_designation1(String contact_person_designation1) {
	this.contact_person_designation1 = contact_person_designation1;
}
public String getContact_person_email1() {
	return contact_person_email1;
}
public void setContact_person_email1(String contact_person_email1) {
	this.contact_person_email1 = contact_person_email1;
}
public String getContact_person2() {
	return contact_person2;
}
public void setContact_person2(String contact_person2) {
	this.contact_person2 = contact_person2;
}
public Long getContact_person_mobile2() {
	return contact_person_mobile2;
}
public void setContact_person_mobile2(Long contact_person_mobile2) {
	this.contact_person_mobile2 = contact_person_mobile2;
}
public String getContact_person_designation2() {
	return contact_person_designation2;
}
public void setContact_person_designation2(String contact_person_designation2) {
	this.contact_person_designation2 = contact_person_designation2;
}
public String getContact_person_email2() {
	return contact_person_email2;
}
public void setContact_person_email2(String contact_person_email2) {
	this.contact_person_email2 = contact_person_email2;
}
public String getDelete_status() {
	return delete_status;
}
public void setDelete_status(String delete_status) {
	this.delete_status = delete_status;
}

public Company(String company_id, String company_code, Set<User> user, String companyName, Float allowed_amount,
		Float current_amount, String company_address, String delete_status, String contact_person1,
		Long contact_person_mobile1, String contact_person_designation1, String contact_person_email1,
		String contact_person2, Long contact_person_mobile2, String contact_person_designation2,
		String contact_person_email2) {
	super();
	this.company_id = company_id;
	this.company_code = company_code;
	this.user = user;
	this.companyName = companyName;
	this.allowed_amount = allowed_amount;
	this.current_amount = current_amount;
	this.company_address = company_address;
	this.delete_status = delete_status;
	this.contact_person1 = contact_person1;
	this.contact_person_mobile1 = contact_person_mobile1;
	this.contact_person_designation1 = contact_person_designation1;
	this.contact_person_email1 = contact_person_email1;
	this.contact_person2 = contact_person2;
	this.contact_person_mobile2 = contact_person_mobile2;
	this.contact_person_designation2 = contact_person_designation2;
	this.contact_person_email2 = contact_person_email2;
}
public String getCompany_id() {
	return company_id;
}
public void setCompany_id(String company_id) {
	this.company_id = company_id;
}
public String getCompany_address() {
	return company_address;
}
public void setCompany_address(String company_address) {
	this.company_address = company_address;
}
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




}
