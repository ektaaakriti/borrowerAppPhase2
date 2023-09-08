package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "metadata_response")
public class MetaDataResponse {
Long applicant_id;
String applicant_response;
@Id
Date datetime_of_response;
String user_pin;
public Long getApplicant_id() {
	return applicant_id;
}
public void setApplicant_id(Long applicant_id) {
	this.applicant_id = applicant_id;
}
public String getApplicant_response() {
	return applicant_response;
}
public void setApplicant_response(String applicant_response) {
	this.applicant_response = applicant_response;
}
public Date getDatetime_of_response() {
	return datetime_of_response;
}
public void setDatetime_of_response(Date datetime_of_response) {
	this.datetime_of_response = datetime_of_response;
}
public String getUser_pin() {
	return user_pin;
}
public void setUser_pin(String user_pin) {
	this.user_pin = user_pin;
}



}
