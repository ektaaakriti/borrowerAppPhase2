package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.FileDB;

public class ApplicantInfo1 {
	private String message;
	private boolean status;
	private Applicant applicant;
	private List<FileDB> document;
	public ApplicantInfo1(String message, boolean status, Applicant applicant, List<FileDB> document) {
		super();
		this.message = message;
		this.status = status;
		this.applicant = applicant;
		this.document = document;
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
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	public List<FileDB> getDocument() {
		return document;
	}
	public void setDocument(List<FileDB> document) {
		this.document = document;
	}
}
