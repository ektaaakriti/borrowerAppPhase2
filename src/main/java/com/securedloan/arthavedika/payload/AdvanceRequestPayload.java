package com.securedloan.arthavedika.payload;

import java.util.Date;

public class AdvanceRequestPayload {
	String applicant_id;
	String from_location ;
	String to_location ;
	String start_date_journey;
	String end_date_journey_expected;
	String advance_amount;
	String return_date_amount_expected;
	String truck_number;
	String raised_user_id;
	//String approved_user_id ;
	String account_no;
	String ifsc_code;
	//String approved_amount;
	//String comment_by_mk;
	//String commenyt_by_sh;
	//String approval_status ;
	String Loan_id;
	//String av_approval;
	//String comment_by_av;
	public String getApplicant_id() {
		return applicant_id;
	}
	public void setApplicant_id(String applicant_id) {
		this.applicant_id = applicant_id;
	}
	public String getFrom_location() {
		return from_location;
	}
	public void setFrom_location(String from_location) {
		this.from_location = from_location;
	}
	public String getTo_location() {
		return to_location;
	}
	public void setTo_location(String to_location) {
		this.to_location = to_location;
	}
	public String getStart_date_journey() {
		return start_date_journey;
	}
	public void setStart_date_journey(String start_date_journey) {
		this.start_date_journey = start_date_journey;
	}
	public String getEnd_date_journey_expected() {
		return end_date_journey_expected;
	}
	public void setEnd_date_journey_expected(String end_date_journey_expected) {
		this.end_date_journey_expected = end_date_journey_expected;
	}
	public String getAdvance_amount() {
		return advance_amount;
	}
	public void setAdvance_amount(String advance_amount) {
		this.advance_amount = advance_amount;
	}
	public String getReturn_date_amount_expected() {
		return return_date_amount_expected;
	}
	public void setReturn_date_amount_expected(String return_date_amount_expected) {
		this.return_date_amount_expected = return_date_amount_expected;
	}
	public String getTruck_number() {
		return truck_number;
	}
	public void setTruck_number(String truck_number) {
		this.truck_number = truck_number;
	}
	public String getRaised_user_id() {
		return raised_user_id;
	}
	public void setRaised_user_id(String raised_user_id) {
		this.raised_user_id = raised_user_id;
	}
	public String getAccount_no() {
		return account_no;
	}
	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}
	public String getIfsc_code() {
		return ifsc_code;
	}
	public void setIfsc_code(String ifsc_code) {
		this.ifsc_code = ifsc_code;
	}
	public String getLoan_id() {
		return Loan_id;
	}
	public void setLoan_id(String loan_id) {
		Loan_id = loan_id;
	}
	
	}
