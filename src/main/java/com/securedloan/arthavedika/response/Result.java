package com.securedloan.arthavedika.response;

public class Result {

	private String message;
	private String status;
	private Boolean statuss;

	public Result(String message, Boolean statuss) {
		super();
		this.message = message;
		this.statuss = statuss;
	}

	public Boolean getStatuss() {
		return statuss;
	}

	public void setStatuss(Boolean statuss) {
		this.statuss = statuss;
	}

	public String getStatus() {
		return status;
	}

	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Result(String message, String status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String isStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
