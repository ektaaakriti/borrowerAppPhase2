package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "uploadlogmaster")
public class UploadLogMaster {
	@Id
	int File_no;
	int File_id;
	public int getFile_id() {
		return File_id;
	}
	public void setFile_id(int file_id) {
		File_id = file_id;
	}
	String File_name;
	String Log;
	Date datetime;
	public int getFile_no() {
		return File_no;
	}
	public void setFile_no(int file_no) {
		File_no = file_no;
	}
	public String getFile_name() {
		return File_name;
	}
	public void setFile_name(String file_name) {
		File_name = file_name;
	}
	public String getLog() {
		return Log;
	}
	public void setLog(String log) {
		Log = log;
	}
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	
}
