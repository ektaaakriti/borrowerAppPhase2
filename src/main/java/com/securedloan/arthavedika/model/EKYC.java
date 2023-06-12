package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Lob;

public class EKYC {

	private String applicant_name;
	private String applicant_middle_name;
	private String applicant_lastname;
	private String photo;
	private String docName;
	private String doc;
	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}
	@Lob
	private byte[] photos;
	public byte[] getPhotos() {
		return photos;
	}

	public void setPhotos(byte[] photos) {
		this.photos = photos;
	}
	private Date dob;
	private String email;
	private String mobile;
	private String fathersName;
	private String fathersMiddleName;
	private String fathersLastName;
	public String getApplicant_middle_name() {
		return applicant_middle_name;
	}

	public void setApplicant_middle_name(String applicant_middle_name) {
		this.applicant_middle_name = applicant_middle_name;
	}

	public String getApplicant_lastname() {
		return applicant_lastname;
	}

	public void setApplicant_lastname(String applicant_lastname) {
		this.applicant_lastname = applicant_lastname;
	}

	public String getFathersMiddleName() {
		return fathersMiddleName;
	}

	public void setFathersMiddleName(String fathersMiddleName) {
		this.fathersMiddleName = fathersMiddleName;
	}

	public String getFathersLastName() {
		return fathersLastName;
	}

	public void setFathersLastName(String fathersLastName) {
		this.fathersLastName = fathersLastName;
	}

	private String gender;
	private long applicant_id;

	public long getApplicant_id() {
		return applicant_id;
	}

	public void setApplicant_id(long applicant_id) {
		this.applicant_id = applicant_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFathersName() {
		return fathersName;
	}

	public void setFathersName(String fathersName) {
		this.fathersName = fathersName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getApplicant_name() {
		return applicant_name;
	}

	public void setApplicant_name(String applicant_name) {
		this.applicant_name = applicant_name;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String string) {
		this.photo = string;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}




}
