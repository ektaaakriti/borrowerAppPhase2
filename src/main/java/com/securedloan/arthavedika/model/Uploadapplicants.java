package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "Uploadapplicants")
public class Uploadapplicants {
	@Id
	Date DateTime;
	Integer File_id;

	public Integer getFile_id() {
		return File_id;
	}
	public void setFile_id(Integer file_id) {
		File_id = file_id;
	}
	String error;
	String Filename;
	String row_no;
	public Date getDateTime() {
		return DateTime;
	}
	public void setDateTime(Date dateTime) {
		DateTime = dateTime;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getFilename() {
		return Filename;
	}
	public void setFilename(String filename) {
		Filename = filename;
	}
	public String getRow_no() {
		return row_no;
	}
	public void setRow_no(String row_no) {
		this.row_no = row_no;
	}
	
}
