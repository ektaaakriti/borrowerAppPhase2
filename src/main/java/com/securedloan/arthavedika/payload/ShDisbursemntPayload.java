package com.securedloan.arthavedika.payload;

public class ShDisbursemntPayload {
String Loan_id;
String date_of_disbursemnet;
String amount;
String Transaction_id;
String remarks;
String disbursement_ifsc;
String disbursement_account_no;
public String getDisbursement_ifsc() {
	return disbursement_ifsc;
}
public void setDisbursement_ifsc(String disbursement_ifsc) {
	this.disbursement_ifsc = disbursement_ifsc;
}
public String getDisbursement_account_no() {
	return disbursement_account_no;
}
public void setDisbursement_account_no(String disbursement_account_no) {
	this.disbursement_account_no = disbursement_account_no;
}
public String getLoan_id() {
	return Loan_id;
}
public void setLoan_id(String loan_id) {
	Loan_id = loan_id;
}
public String getDate_of_disbursemnet() {
	return date_of_disbursemnet;
}
public void setDate_of_disbursemnet(String date_of_disbursemnet) {
	this.date_of_disbursemnet = date_of_disbursemnet;
}
public String getAmount() {
	return amount;
}
public void setAmount(String amount) {
	this.amount = amount;
}
public String getTransaction_id() {
	return Transaction_id;
}
public void setTransaction_id(String transaction_id) {
	Transaction_id = transaction_id;
}
public String getRemarks() {
	return remarks;
}
public void setRemarks(String remarks) {
	this.remarks = remarks;
}

}
