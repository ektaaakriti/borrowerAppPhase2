package com.securedloan.arthavedika.payload;

import java.util.Date;

public class MetaPayload {
	String name;
	String clanguage;
	String accnum;
	String loanamout;
	String pendingamount;
	String EMIamount;
	String duedate;
	String pemi;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClanguage() {
		return clanguage;
	}
	public void setClanguage(String clanguage) {
		this.clanguage = clanguage;
	}
	public String getAccnum() {
		return accnum;
	}
	public void setAccnum(String accnum) {
		this.accnum = accnum;
	}
	public String getLoanamout() {
		return loanamout;
	}
	public void setLoanamout(String loanamout) {
		this.loanamout = loanamout;
	}
	public String getPendingamount() {
		return pendingamount;
	}
	public void setPendingamount(String pendingamount) {
		this.pendingamount = pendingamount;
	}
	public String getEMIamount() {
		return EMIamount;
	}
	public void setEMIamount(String eMIamount) {
		EMIamount = eMIamount;
	}
	public String getDuedate() {
		return duedate;
	}
	public void setDuedate(String duedate) {
		this.duedate = duedate;
	}
	public String getPemi() {
		return pemi;
	}
	public void setPemi(String pemi) {
		this.pemi = pemi;
	}
	
	
}
