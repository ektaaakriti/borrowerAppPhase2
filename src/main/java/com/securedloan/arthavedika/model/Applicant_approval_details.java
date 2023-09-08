package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "applicant_approval_details")
public class Applicant_approval_details {
	@Id
	Long applicant_id;
	String	av_authorisation_status;
	String av_authorisation_by;
	String av_comment;
	Date av_authorisation_datetime;
	String mk_verified_status;
	String mk_verified_by;
	Date mk_verify_datetime;
	String mk_comment;
	String sh_approval_status;
	String sh_approval_by;
	Date sh_approval_datetime;
	String  sh_comment;
	public Long getApplicant_id() {
		return applicant_id;
	}
	public void setApplicant_id(Long applicant_id) {
		this.applicant_id = applicant_id;
	}
	public String getAv_authorisation_status() {
		return av_authorisation_status;
	}
	public void setAv_authorisation_status(String av_authorisation_status) {
		this.av_authorisation_status = av_authorisation_status;
	}
	public String getAv_authorisation_by() {
		return av_authorisation_by;
	}
	public void setAv_authorisation_by(String av_authorisation_by) {
		this.av_authorisation_by = av_authorisation_by;
	}
	public String getAv_comment() {
		return av_comment;
	}
	public void setAv_comment(String av_comment) {
		this.av_comment = av_comment;
	}
	public Date getAv_authorisation_datetime() {
		return av_authorisation_datetime;
	}
	public void setAv_authorisation_datetime(Date av_authorisation_datetime) {
		this.av_authorisation_datetime = av_authorisation_datetime;
	}
	public String getMk_verified_status() {
		return mk_verified_status;
	}
	public void setMk_verified_status(String mk_verified_status) {
		this.mk_verified_status = mk_verified_status;
	}
	public String getMk_verified_by() {
		return mk_verified_by;
	}
	public void setMk_verified_by(String mk_verified_by) {
		this.mk_verified_by = mk_verified_by;
	}
	public Date getMk_verify_datetime() {
		return mk_verify_datetime;
	}
	public void setMk_verify_datetime(Date mk_verify_datetime) {
		this.mk_verify_datetime = mk_verify_datetime;
	}
	public String getMk_comment() {
		return mk_comment;
	}
	public void setMk_comment(String mk_comment) {
		this.mk_comment = mk_comment;
	}
	public String getSh_approval_status() {
		return sh_approval_status;
	}
	public void setSh_approval_status(String sh_approval_status) {
		this.sh_approval_status = sh_approval_status;
	}
	public String getSh_approval_by() {
		return sh_approval_by;
	}
	public void setSh_approval_by(String sh_approval_by) {
		this.sh_approval_by = sh_approval_by;
	}
	public Date getSh_approval_datetime() {
		return sh_approval_datetime;
	}
	public void setSh_approval_datetime(Date sh_approval_datetime) {
		this.sh_approval_datetime = sh_approval_datetime;
	}
	public String getSh_comment() {
		return sh_comment;
	}
	public void setSh_comment(String sh_comment) {
		this.sh_comment = sh_comment;
	}
	public Applicant_approval_details(Long applicant_id, String av_authorisation_status, String av_authorisation_by,
			String av_comment, Date av_authorisation_datetime, String mk_verified_status, String mk_verified_by,
			Date mk_verify_datetime, String mk_comment, String sh_approval_status, String sh_approval_by,
			Date sh_approval_datetime, String sh_comment) {
		super();
		this.applicant_id = applicant_id;
		this.av_authorisation_status = av_authorisation_status;
		this.av_authorisation_by = av_authorisation_by;
		this.av_comment = av_comment;
		this.av_authorisation_datetime = av_authorisation_datetime;
		this.mk_verified_status = mk_verified_status;
		this.mk_verified_by = mk_verified_by;
		this.mk_verify_datetime = mk_verify_datetime;
		this.mk_comment = mk_comment;
		this.sh_approval_status = sh_approval_status;
		this.sh_approval_by = sh_approval_by;
		this.sh_approval_datetime = sh_approval_datetime;
		this.sh_comment = sh_comment;
	}
	public Applicant_approval_details() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
