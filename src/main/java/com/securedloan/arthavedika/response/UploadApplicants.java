package com.securedloan.arthavedika.response;

public class UploadApplicants {
String date;
String error;
String filename;
String row_no;
String File_id;

public String getFile_id() {
	return File_id;
}
public void setFile_id(String file_id) {
	File_id = file_id;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getError() {
	return error;
}
public void setError(String error) {
	this.error = error;
}
public String getFilename() {
	return filename;
}
public void setFilename(String filename) {
	this.filename = filename;
}
public String getRow_no() {
	return row_no;
}
public void setRow_no(String row_no) {
	this.row_no = row_no;
}
}
