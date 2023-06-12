package com.securedloan.arthavedika.model;

import java.util.List;

public class Res {
	
	private double distance;
	private boolean result;
	private List<String> text;
	public Res() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public List<String> getText() {
		return text;
	}

	public void setText(List<String> text) {
		this.text = text;
	}

	public Res(double distance, boolean result, List<String> text) {
		super();
		this.distance = distance;
		this.result = result;
		this.text = text;
	}
	

}
