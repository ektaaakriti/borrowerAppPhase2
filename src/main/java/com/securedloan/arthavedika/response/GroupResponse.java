package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.GroupData;

public class GroupResponse {

	private List<GroupData> grpData;
	private String message;
	private boolean status;

	public List<GroupData> getGrpData() {
		return grpData;
	}

	public void setGrpData(List<GroupData> grpData) {
		this.grpData = grpData;
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

	public GroupResponse(List<GroupData> grpData, String message, boolean status) {
		super();
		this.grpData = grpData;
		this.message = message;
		this.status = status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
	
}
