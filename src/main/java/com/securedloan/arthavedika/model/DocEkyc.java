package com.securedloan.arthavedika.model;

public class DocEkyc {
	private long applicant_id;
	
	public long getApplicant_id() {
		return applicant_id;
	}
	public void setApplicant_id(long applicant_id) {
		this.applicant_id = applicant_id;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	private byte[] photo;
	private String photoString;

	public String getPhotoString() {
		return photoString;
	}
	public void setPhotoString(String photoString) {
		this.photoString = photoString;
	}
}
