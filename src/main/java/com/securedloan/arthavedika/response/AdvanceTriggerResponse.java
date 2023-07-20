package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.payload.AdvanceRequestPayload;

public class AdvanceTriggerResponse {
	List<AdvanceRequestPayload> list;
	String message;
	String status;
	public List<AdvanceRequestPayload> getList() {
		return list;
	}
	public void setList(List<AdvanceRequestPayload> list) {
		this.list = list;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public AdvanceTriggerResponse(List<AdvanceRequestPayload> list, String message, String status) {
		super();
		this.list = list;
		this.message = message;
		this.status = status;
	}
	
	
}
