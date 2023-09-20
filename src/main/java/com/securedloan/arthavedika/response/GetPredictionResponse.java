package com.securedloan.arthavedika.response;

import java.util.List;

public class GetPredictionResponse {
List Prediction;
String status;
String Response;
public GetPredictionResponse(List prediction, String status, String response) {
	super();
	Prediction = prediction;
	this.status = status;
	Response = response;
}
public List getPrediction() {
	return Prediction;
}
public void setPrediction(List prediction) {
	Prediction = prediction;
}
public String getStatus() {
	return status;
}
public void setStatus(String status) {
	this.status = status;
}
public String getResponse() {
	return Response;
}
public void setResponse(String response) {
	Response = response;
}
}
