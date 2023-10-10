package com.securedloan.arthavedika.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.model.*;
import com.securedloan.arthavedika.payload.ApplicantPayload;
import com.securedloan.arthavedika.payload.CompanyPayload;
import com.securedloan.arthavedika.payload.MetaDataResponsePayload;
import com.securedloan.arthavedika.repo.MetadataResponseRepo;
import java.util.Date;
import com.securedloan.arthavedika.response.GetCompanyByIdResponse;
import com.securedloan.arthavedika.response.MetaResponse_Response;


@RestController
@RequestMapping("applicant/MetaData")
public class MetaDataController {
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(MetaDataController.class);
@Autowired
MetadataResponseRepo metaDataRepo;
@RequestMapping(value = { "/response" }, method = RequestMethod.POST, produces = {
		MediaType.APPLICATION_JSON_VALUE })
@ResponseStatus(value = HttpStatus.OK)
public ResponseEntity<MetaResponse_Response> save_metadataResponse(@RequestBody MetaDataResponsePayload metaPayload) {
	LOGGER.info("save MetaData Response api has been called !!! Start Of Method save metadata response");
	
	HttpStatus httpstatus=null;
	String response="";
	String status=null;
	
	try {
		 MetaDataResponse mt=new MetaDataResponse();
		mt.setApplicant_id(Long.parseLong(encdec.decryptnew(metaPayload.getApplicant_id())));
		mt.setApplicant_response(encdec.decryptnew(metaPayload.getResponse()));
		mt.setUser_pin(encdec.decryptnew(metaPayload.getUser_pin()));
		 Date date = new Date();
		
		mt.setRequest_type("response");
		
		mt.setDatetime_of_request(date);
		metaDataRepo.save(mt);
				
			response="response saved successfully for applicant id"+Long.parseLong(encdec.decryptnew(metaPayload.getApplicant_id()));
				status="true";
		httpstatus=HttpStatus.OK;
		}
				
	catch (Exception e) {
		
		response="Error While saving metadata response" + e.getMessage();
		LOGGER.error(response + e.getMessage());
		status="false";
		httpstatus=HttpStatus.BAD_REQUEST;
	}
	return ResponseEntity.status(httpstatus).body(new MetaResponse_Response (encdec.encryptnew(response),encdec.encryptnew(status)));
}
@RequestMapping(value = { "/request" }, method = RequestMethod.POST, produces = {
		MediaType.APPLICATION_JSON_VALUE })
@ResponseStatus(value = HttpStatus.OK)
public ResponseEntity<MetaResponse_Response> save_metadataRequest(@RequestBody  ApplicantPayload metaPayload) {
	LOGGER.info("save MetaData Request api has been called !!! Start Of Method save metadata request");
	
	HttpStatus httpstatus=null;
	String response="";
	String status=null;
	
	try {
		 MetaDataResponse mt=new MetaDataResponse();
		mt.setApplicant_id(Long.parseLong(encdec.decryptnew(metaPayload.getApplicant_id())));
		mt.setRequest_type("request");
		
		 Date date = new Date();
		
		mt.setDatetime_of_request(date);
		metaDataRepo.save(mt);
				
			response="resquest time saved successfully for applicant id"+Long.parseLong(encdec.decryptnew(metaPayload.getApplicant_id()));
				status="true";
		httpstatus=HttpStatus.OK;
		}
				
	catch (Exception e) {
		
		response="Error While saving metadata request" + e.getMessage();
		LOGGER.error(response + e.getMessage());
		status="false";
		httpstatus=HttpStatus.BAD_REQUEST;
	}
	return ResponseEntity.status(httpstatus).body(new MetaResponse_Response (encdec.encryptnew(response),encdec.encryptnew(status)));
}
}

