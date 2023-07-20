package com.securedloan.arthavedika.response;

import javax.validation.constraints.NotNull;

public class ApplicantResponse {
String applicant_id; 
String applicant_firstname;
 String applicant_middle_name;
 String applicant_lastname;
 String applicant_email_id;
 String AV_approval;
 String MK_approval;
String SH_approval;
 String company_name;
 String applicant_mobile_no;
	
public String getApplicant_mobile_no() {
	return applicant_mobile_no;
}
public void setApplicant_mobile_no(String applicant_mobile_no) {
	this.applicant_mobile_no = applicant_mobile_no;
}
public String getApplicant_id() {
	return applicant_id;
}
public void setApplicant_id(String applicant_id) {
	this.applicant_id = applicant_id;
}
public String getApplicant_firstname() {
	return applicant_firstname;
}
public void setApplicant_firstname(String applicant_firstname) {
	this.applicant_firstname = applicant_firstname;
}
public String getApplicant_middle_name() {
	return applicant_middle_name;
}
public void setApplicant_middle_name(String applicant_middle_name) {
	this.applicant_middle_name = applicant_middle_name;
}
public String getApplicant_lastname() {
	return applicant_lastname;
}
public void setApplicant_lastname(String applicant_lastname) {
	this.applicant_lastname = applicant_lastname;
}
public String getApplicant_email_id() {
	return applicant_email_id;
}
public void setApplicant_email_id(String applicant_email_id) {
	this.applicant_email_id = applicant_email_id;
}
public String getAV_approval() {
	return AV_approval;
}
public void setAV_approval(String aV_approval) {
	AV_approval = aV_approval;
}
public String getMK_approval() {
	return MK_approval;
}
public void setMK_approval(String mK_approval) {
	MK_approval = mK_approval;
}
public String getSH_approval() {
	return SH_approval;
}
public void setSH_approval(String sH_approval) {
	SH_approval = sH_approval;
}
public String getCompany_name() {
	return company_name;
}
public void setCompany_name(String company_name) {
	this.company_name = company_name;
}
}
