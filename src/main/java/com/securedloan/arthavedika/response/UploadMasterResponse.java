package com.securedloan.arthavedika.response;

import java.util.Date;

public class UploadMasterResponse {
String File_name;
String Log;
String datetime;
String File_id;

public String getFile_id() {
	return File_id;
}
public void setFile_id(String file_id) {
	File_id = file_id;
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
public String getDatetime() {
	return datetime;
}
public void setDatetime(String datetime) {
	this.datetime = datetime;
}
}
