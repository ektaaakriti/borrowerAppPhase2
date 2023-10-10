package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;



@Entity
@Table(name = "metadata_response")
@IdClass(MetaDataId.class)
public class MetaDataResponse {
	@Id
Long applicant_id;
String applicant_response;

String user_pin;
String request_type;
@Id
Date datetime_of_request;
public String getRequest_type() {
	return request_type;
}
public void setRequest_type(String request_type) {
	this.request_type = request_type;
}
public Date getDatetime_of_request() {
	return datetime_of_request;
}
public void setDatetime_of_request(Date datetime_of_request) {
	this.datetime_of_request = datetime_of_request;
}
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

public String getUser_pin() {
	return user_pin;
}
public void setUser_pin(String user_pin) {
	this.user_pin = user_pin;
}



}
