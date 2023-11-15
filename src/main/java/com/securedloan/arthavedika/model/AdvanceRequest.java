package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;



@Entity
@Table(name = "advance_request")
public class AdvanceRequest {
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "Advance_seq")
    @GenericGenerator(
        name = "Advance_seq", 
        strategy = "com.securedloan.arthavedika.model.StringPrefixedSequenceIdGenerator", 
        parameters = {
            @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "10"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "Adv_"),
            @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d") })
	@Id
	String Loan_id;
public String getLoan_id() {
		return Loan_id;
	}
	public void setLoan_id(String loan_id) {
		Loan_id = loan_id;
	}
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
Long applicant_id;
String from_location ;
String to_location ;
Date start_date_journey;
Date end_date_journey_expected;
Float advance_amount;
Date return_date_amount_expected;
String truck_number;
String raised_user_id;
Date raised_date_time ;
String approved_user_id ;
Date approved_date_time;
String account_no;
String ifsc_code;
Float approved_amount;
String comment_by_mk;
String comment_by_sh;
String approval_status ;
String av_approval;
String comment_by_av;
Date date_of_disbursemnt;
String transaction_id;

public Date getDate_of_disbursemnt() {
	return date_of_disbursemnt;
}
public void setDate_of_disbursemnt(Date date_of_disbursemnt) {
	this.date_of_disbursemnt = date_of_disbursemnt;
}
public String getTransaction_id() {
	return transaction_id;
}
public void setTransaction_id(String transaction_id) {
	this.transaction_id = transaction_id;
}
public String getComment_by_sh() {
	return comment_by_sh;
}
public void setComment_by_sh(String comment_by_sh) {
	this.comment_by_sh = comment_by_sh;
}
public String getAv_approval() {
	return av_approval;
}
public void setAv_approval(String av_approval) {
	this.av_approval = av_approval;
}
public String getComment_by_av() {
	return comment_by_av;
}
public void setComment_by_av(String comment_by_av) {
	this.comment_by_av = comment_by_av;
}
public Long getApplicant_id() {
	return applicant_id;
}
public void setApplicant_id(Long applicant_id) {
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
public Date getStart_date_journey() {
	return start_date_journey;
}
public void setStart_date_journey(Date start_date_journey) {
	this.start_date_journey = start_date_journey;
}
public Date getEnd_date_journey_expected() {
	return end_date_journey_expected;
}
public void setEnd_date_journey_expected(Date end_date_journey_expected) {
	this.end_date_journey_expected = end_date_journey_expected;
}
public Float getAdvance_amount() {
	return advance_amount;
}
public void setAdvance_amount(Float advance_amount) {
	this.advance_amount = advance_amount;
}
public Date getReturn_date_amount_expected() {
	return return_date_amount_expected;
}
public void setReturn_date_amount_expected(Date return_date_amount_expected) {
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
public Date getRaised_date_time() {
	return raised_date_time;
}
public void setRaised_date_time(Date raised_date_time) {
	this.raised_date_time = raised_date_time;
}
public String getApproved_user_id() {
	return approved_user_id;
}
public void setApproved_user_id(String approved_user_id) {
	this.approved_user_id = approved_user_id;
}
public Date getApproved_date_time() {
	return approved_date_time;
}
public void setApproved_date_time(Date approved_date_time) {
	this.approved_date_time = approved_date_time;
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
public Float getApproved_amount() {
	return approved_amount;
}
public void setApproved_amount(Float approved_amount) {
	this.approved_amount = approved_amount;
}
public String getComment_by_mk() {
	return comment_by_mk;
}
public void setComment_by_mk(String comment_by_mk) {
	this.comment_by_mk = comment_by_mk;
}
public String getCommenyt_by_sh() {
	return comment_by_sh;
}
public void setCommenyt_by_sh(String comment_by_sh) {
	this.comment_by_sh = comment_by_sh;
}
public String getApproval_status() {
	return approval_status;
}
public void setApproval_status(String approval_status) {
	this.approval_status = approval_status;
}
}
