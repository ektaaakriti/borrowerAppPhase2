package com.securedloan.arthavedika.payload;

import java.util.Date;

public class RepaymentAdvancePayload {
String Loan_id;
String laon_amount_returned;
String loan_amount_pending;
String next_date_loan_return;
public String getLoan_id() {
	return Loan_id;
}
public void setLoan_id(String Loan_id) {
	this.Loan_id = Loan_id;
}
public String getLaon_amount_returned() {
	return laon_amount_returned;
}
public void setLaon_amount_returned(String laon_amount_returned) {
	this.laon_amount_returned = laon_amount_returned;
}
public String getLoan_amount_pending() {
	return loan_amount_pending;
}
public void setLoan_amount_pending(String loan_amount_pending) {
	this.loan_amount_pending = loan_amount_pending;
}
public String getNext_date_loan_return() {
	return next_date_loan_return;
}
public void setNext_date_loan_return(String next_date_loan_return) {
	this.next_date_loan_return = next_date_loan_return;
}
}
