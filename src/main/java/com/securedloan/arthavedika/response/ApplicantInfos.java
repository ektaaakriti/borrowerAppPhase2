package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Applicant;

public class ApplicantInfos {
	private String message;
	private boolean status;
	private Applicant applicant;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	public ApplicantInfos(String message, boolean status, Applicant applicant) {
		super();
		this.message = message;
		this.status = status;
		this.applicant = applicant;
	}
	
}
