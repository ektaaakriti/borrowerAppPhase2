package com.securedloan.arthavedika.payload;

import java.util.List;

public class AuthorizeApplicantPayload {
Long Applicant_id;
String company_code;
String approval_status;
public Long getApplicant_id() {
	return Applicant_id;
}
public void setApplicant_id(Long applicant_id) {
	Applicant_id = applicant_id;
}
public String getCompany_code() {
	return company_code;
}
public void setCompany_code(String company_code) {
	this.company_code = company_code;
}
public String getApproval_status() {
	return approval_status;
}
public void setApproval_status(String approval_status) {
	this.approval_status = approval_status;
}

}
