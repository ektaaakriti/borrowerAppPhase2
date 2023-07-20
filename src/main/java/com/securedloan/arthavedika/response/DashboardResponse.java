package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.payload.Dashboard;

public class DashboardResponse {
	List<Dashboard> dashboard_details;
	String message;
	String status;
	public List<Dashboard> getDashboard_details() {
		return dashboard_details;
	}
	public void setDashboard_details(List<Dashboard> dashboard_details) {
		this.dashboard_details = dashboard_details;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public DashboardResponse(List<Dashboard> dashboard_details, String message, String status) {
		super();
		this.dashboard_details = dashboard_details;
		this.message = message;
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
