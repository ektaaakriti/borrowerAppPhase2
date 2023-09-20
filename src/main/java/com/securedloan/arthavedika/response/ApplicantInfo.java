package com.securedloan.arthavedika.response;

import java.util.List;
import java.util.Optional;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Applicant_approval_details;
import com.securedloan.arthavedika.model.FileDB;

public class ApplicantInfo {
	private String message;
	private boolean status;
	private List<Applicant> applicant;
	private List<FileDB> document;
	private Optional<Applicant_approval_details> approval_details;
	public Optional<Applicant_approval_details> getApproval_details() {
		return approval_details;
	}
	public void setApproval_details(Optional<Applicant_approval_details> approval_details) {
		this.approval_details = approval_details;
	}
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
	public List<Applicant> getApplicant() {
		return applicant;
	}
	public void setApplicant(List<Applicant> applicant) {
		this.applicant = applicant;
	}
	public List<FileDB> getDocument() {
		return document;
	}
	public void setDocument(List<FileDB> document) {
		this.document = document;
	}
	public ApplicantInfo(String message, boolean status, List<Applicant> applicant, List<FileDB> document,
			Optional<Applicant_approval_details> approval_details) {
		super();
		this.message = message;
		this.status = status;
		this.applicant = applicant;
		this.document = document;
		this.approval_details = approval_details;
	}
	public ApplicantInfo(String message, boolean status, List<Applicant> applicant) {
		super();
		this.message = message;
		this.status = status;
		this.applicant = applicant;
	}
	

	

}
