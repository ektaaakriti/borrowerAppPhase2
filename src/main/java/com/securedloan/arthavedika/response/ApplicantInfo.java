package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.FileDB;

public class ApplicantInfo {
	private String message;
	private boolean status;
	private List<Applicant> applicant;
	private List<FileDB> document;
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
	public ApplicantInfo(String message, boolean status, List<Applicant> applicant, List<FileDB> document) {
		super();
		this.message = message;
		this.status = status;
		this.applicant = applicant;
		this.document = document;
	}

	

}
