package com.securedloan.arthavedika.payload;

public class ApproveAdvancePayload {
	String applicant_id;
String approval_status;
String approved_amount;
String approved_username;
String comment_by_sh;
String return_date_amount_expected;
public String getReturn_date_amount_expected() {
	return return_date_amount_expected;
}
public void setReturn_date_amount_expected(String return_date_amount_expected) {
	this.return_date_amount_expected = return_date_amount_expected;
}
public String getApplicant_id() {
	return applicant_id;
}
public void setApplicant_id(String applicant_id) {
	this.applicant_id = applicant_id;
}

public String getApproval_status() {
	return approval_status;
}
public void setApproval_status(String approval_status) {
	this.approval_status = approval_status;
}
public String getApproved_amount() {
	return approved_amount;
}
public void setApproved_amount(String approved_amount) {
	this.approved_amount = approved_amount;
}
public String getApproved_username() {
	return approved_username;
}
public void setApproved_username(String approved_username) {
	this.approved_username = approved_username;
}
public String getComment_by_sh() {
	return comment_by_sh;
}
public void setComment_by_sh(String comment_by_sh) {
	this.comment_by_sh = comment_by_sh;
}


}
