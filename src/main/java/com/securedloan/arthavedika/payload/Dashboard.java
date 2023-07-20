package com.securedloan.arthavedika.payload;

public class Dashboard {
String total_applicants;
String pending_for_verification;
String verified_today;
String total_verified;
String total_rejected;
public String getTotal_applicants() {
	return total_applicants;
}
public void setTotal_applicants(String total_applicants) {
	this.total_applicants = total_applicants;
}
public String getPending_for_verification() {
	return pending_for_verification;
}
public void setPending_for_verification(String pending_for_verification) {
	this.pending_for_verification = pending_for_verification;
}
public String getVerified_today() {
	return verified_today;
}
public void setVerified_today(String verified_today) {
	this.verified_today = verified_today;
}
public String getTotal_verified() {
	return total_verified;
}
public void setTotal_verified(String total_verified) {
	this.total_verified = total_verified;
}
public String getTotal_rejected() {
	return total_rejected;
}
public void setTotal_rejected(String total_rejected) {
	this.total_rejected = total_rejected;
}

}
