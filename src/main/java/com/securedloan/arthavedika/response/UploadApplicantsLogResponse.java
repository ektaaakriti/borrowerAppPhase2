package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Uploadapplicants;

public class UploadApplicantsLogResponse {
	List<UploadApplicants> Log;
	String Message;
	String status;
	public List<UploadApplicants> getLog() {
		return Log;
	}
	public void setLog(List<UploadApplicants> log) {
		Log = log;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public UploadApplicantsLogResponse(List<UploadApplicants> log, String message, String status) {
		super();
		Log = log;
		Message = message;
		this.status = status;
	}

}
