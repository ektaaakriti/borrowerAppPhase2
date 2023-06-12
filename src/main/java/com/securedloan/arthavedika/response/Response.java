package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.DocEkyc;
import com.securedloan.arthavedika.model.EKYC;
import com.securedloan.arthavedika.model.GroupData;
import com.securedloan.arthavedika.model.PsyQstn;
import com.securedloan.arthavedika.model.User;

public class Response {

	private String message;
	private boolean status;
	private User user;
	private List<EKYC> EkycList;
	private List<PsyQstn> PsyQstnList;
	private GroupData grpData;
	
	

	

	public Response(String message, boolean status, GroupData grpData) {
		super();
		this.message = message;
		this.status = status;
		this.grpData = grpData;
	}

	public GroupData getGrpData() {
		return grpData;
	}

	public void setGrpData(GroupData grpData) {
		this.grpData = grpData;
	}

	public List<PsyQstn> getPsyQstnList() {
		return PsyQstnList;
	}

	public void setPsyQstnList(List<PsyQstn> psyQstnList) {
		PsyQstnList = psyQstnList;
	}

	private List<DocEkyc> docEkyc;
	private boolean imageResultStatus;
	private Applicant applicant;

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	
	public Response(String message, boolean status, List<PsyQstn> psyQstnList, Applicant applicant) {
		super();
		this.message = message;
		this.status = status;
		PsyQstnList = psyQstnList;
		this.applicant = applicant;
	}

	public Response(String message, boolean status, Applicant applicant) {
		super();
		this.message = message;
		this.status = status;
		this.applicant = applicant;
	}

	public Response(String message, boolean status, boolean imageResultStatus) {
		super();
		this.message = message;
		this.status = status;
		this.imageResultStatus = imageResultStatus;
	}

	public boolean isImageResultStatus() {
		return imageResultStatus;
	}

	public void setImageResultStatus(boolean imageResultStatus) {
		this.imageResultStatus = imageResultStatus;
	}

	public Response( List<DocEkyc> docEkyc,String message, boolean status) {
		super();
		this.docEkyc = docEkyc;
		this.message = message;
		this.status = status;
		
	}

	public List<DocEkyc> getDocEkyc() {
		return docEkyc;
	}

	public void setDocEkyc(List<DocEkyc> docEkyc) {
		this.docEkyc = docEkyc;
	}

	public Response(String message, boolean status, List<EKYC> ekycList) {
		super();
		this.message = message;
		this.status = status;
		EkycList = ekycList;
	}

	public List<EKYC> getEkycList() {
		return EkycList;
	}

	public void setEkycList(List<EKYC> ekycList) {
		EkycList = ekycList;
	}

	public String getMessage() {
		return message;
	}

	public Response() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Response(String message, boolean status, User user) {
		super();
		this.message = message;
		this.status = status;
		this.user = user;
	}

	public Response(String registerSuccess, Boolean true1, List<Applicant> applicants) {
		// TODO Auto-generated constructor stub
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
