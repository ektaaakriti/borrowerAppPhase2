package com.securedloan.arthavedika.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetaDataId implements Serializable {
Long applicant_id;
Date datetime_of_request;

public Long getApplicant_id() {
	return applicant_id;
}
public void setApplicant_id(Long applicant_id) {
	this.applicant_id = applicant_id;
}
public Date getDatetime_of_request() {
	return datetime_of_request;
}
public void setDatetime_of_request(Date datetime_of_request) {
	this.datetime_of_request = datetime_of_request;
}

}
