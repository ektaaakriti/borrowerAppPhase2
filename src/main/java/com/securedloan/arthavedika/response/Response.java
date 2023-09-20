package com.securedloan.arthavedika.response;

import java.util.List;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.DocEkyc;
import com.securedloan.arthavedika.model.EKYC;
import com.securedloan.arthavedika.model.GroupData;
import com.securedloan.arthavedika.model.PsyQstn;
import com.securedloan.arthavedika.model.User;

public class Response {

	private String message;
	private String status;
	private User user;
	private Boolean status1;
	public Response(String message, Boolean status1, List<PsyQstn> psyQstnList, Applicant applicant) {
		super();
		this.message = message;
		this.status1 = status1;
		PsyQstnList = psyQstnList;
		this.applicant = applicant;
	}



	public Response(String message, Boolean status1, Applicant applicant) {
		super();
		this.message = message;
		this.status1 = status1;
		this.applicant = applicant;
	}



	public Response(String message, Boolean status1, User user) {
		super();
		this.message = message;
		this.user = user;
		this.status1 = status1;
	}



	public Boolean getStatus1() {
		return status1;
	}



	public void setStatus1(Boolean status1) {
		this.status1 = status1;
	}



	public String getStatus() {
		return status;
	}



	public String getImageResultStatus() {
		return imageResultStatus;
	}

	private CompanyEnc company;
	private List<EKYC> EkycList;
	private List<PsyQstn> PsyQstnList;
	private GroupData grpData;
	
	

	

	public CompanyEnc getCompany() {
		return company;
	}



	public void setCompany(CompanyEnc company) {
		this.company = company;
	}



	public Response( User user, CompanyEnc company,String message, String status) {
		super();
		this.message = message;
		this.status = status;
		this.user = user;
		this.company = company;
	}



	public Response(String message, String status, GroupData grpData) {
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
	private String imageResultStatus;
	private Applicant applicant;

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	
	public Response(String message, String status, List<PsyQstn> psyQstnList, Applicant applicant) {
		super();
		this.message = message;
		this.status = status;
		PsyQstnList = psyQstnList;
		this.applicant = applicant;
	}

	public Response(String message, String status, Applicant applicant) {
		super();
		this.message = message;
		this.status = status;
		this.applicant = applicant;
	}

	public Response(String message, String status, String imageResultStatus) {
		super();
		this.message = message;
		this.status = status;
		this.imageResultStatus = imageResultStatus;
	}

	public String isImageResultStatus() {
		return imageResultStatus;
	}

	public void setImageResultStatus(String imageResultStatus) {
		this.imageResultStatus = imageResultStatus;
	}

	public Response( List<DocEkyc> docEkyc,String message, String status) {
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

	public Response(String message, String status, List<EKYC> ekycList) {
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

	public Response(String message, String status, User user) {
		super();
		this.message = message;
		this.status = status;
		this.user = user;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
