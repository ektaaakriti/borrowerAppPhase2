package com.securedloan.arthavedika.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "psycho_ans")
public class PsyAns {
	private @GeneratedValue(strategy = GenerationType.AUTO, generator = "port_gen") @Id @NotNull long id;

	private Long applicant_id;
	private String  queId;
	private String ans;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Long getApplicant_id() {
		return applicant_id;
	}
	public void setApplicant_id(Long applicant_id) {
		this.applicant_id = applicant_id;
	}
	public String getQueId() {
		return queId;
	}
	public void setQueId(String queId) {
		this.queId = queId;
	}
	public String getAns() {
		return ans;
	}
	public void setAns(String ans) {
		this.ans = ans;
	}
	public PsyAns(Long applicant_id, String queId, String ans) {
		super();
		this.applicant_id = applicant_id;
		this.queId = queId;
		this.ans = ans;
	}

	
	
}
