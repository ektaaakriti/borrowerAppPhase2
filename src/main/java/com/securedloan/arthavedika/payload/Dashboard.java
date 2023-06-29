package com.securedloan.arthavedika.payload;

public class Dashboard {
int total_applicants;
int pending_for_verification;
int verified_today;
int total_verified;
int total_rejected;
public int getTotal_applicants() {
	return total_applicants;
}
public void setTotal_applicants(int total_applicants) {
	this.total_applicants = total_applicants;
}
public int getPending_for_verification() {
	return pending_for_verification;
}
public void setPending_for_verification(int pending_for_verification) {
	this.pending_for_verification = pending_for_verification;
}
public int getVerified_today() {
	return verified_today;
}
public void setVerified_today(int verified_today) {
	this.verified_today = verified_today;
}
public int getTotal_verified() {
	return total_verified;
}
public void setTotal_verified(int total_verified) {
	this.total_verified = total_verified;
}
public int getTotal_rejected() {
	return total_rejected;
}
public void setTotal_rejected(int total_rejected) {
	this.total_rejected = total_rejected;
}

}
