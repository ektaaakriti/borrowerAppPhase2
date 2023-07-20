package com.securedloan.arthavedika.controller;
import com.securedloan.arthavedika.model.*;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

import org.jfree.util.Log;
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
import org.springframework.web.bind.annotation.RestController;

import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.payload.AdvanceRequestPayload;
import com.securedloan.arthavedika.repo.AdvanceRequestRepo;
import com.securedloan.arthavedika.response.AdvanceTriggerResponse;
import com.securedloan.arthavedika.response.FindAllApplicant;
import com.securedloan.arthavedika.response.GeneralResponse;

@CrossOrigin()
@RestController
@RequestMapping("/borrower")
public class AdvanceTrigerController {
	@Autowired
	AdvanceRequestRepo advanceRepo;
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(AdvanceTrigerController.class);
	@SuppressWarnings("static-access")
	@RequestMapping(value={"/SaveadvanceTrigger"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<GeneralResponse> saveAdvanceTriger(@RequestBody AdvanceRequestPayload advancePayload )
	{HttpStatus httpstatus=null;
	String response="";
	String status=null;
	try {
		AdvanceRequest ad=new AdvanceRequest();
		ad.setApplicant_id(encdec.decryptnew(advancePayload.getApplicant_id()));
		ad.setAccount_no(encdec.decryptnew(advancePayload.getAccount_no()));
		ad.setAdvance_amount(Float.parseFloat(encdec.decryptnew(advancePayload.getAdvance_amount())));
		ad.setApproved_amount((Float.parseFloat(encdec.decryptnew(advancePayload.getApproved_amount()))));
		ad.setApproved_user_id(encdec.decryptnew(advancePayload.getApproved_user_id()));
		ad.setRaised_user_id(encdec.decryptnew(advancePayload.getRaised_user_id()));
		ad.setComment_by_mk(encdec.decryptnew(advancePayload.getComment_by_mk()));
		ad.setCommenyt_by_sh(encdec.decryptnew(advancePayload.getCommenyt_by_sh()));
		ad.setTruck_number(encdec.decryptnew(advancePayload.getTruck_number()));
		ad.setFrom_location(encdec.decryptnew(advancePayload.getFrom_location()));
		ad.setTo_location(encdec.decryptnew(advancePayload.getTo_location()));
		ad.setEnd_date_journey_expected(new SimpleDateFormat("dd/MM/yyyy").parse(encdec.decryptnew(advancePayload.getEnd_date_journey_expected())));
		ad.setIfsc_code(encdec.decryptnew(advancePayload.getIfsc_code()));
		ad.setApproval_status(encdec.decryptnew(advancePayload.getApproval_status()));
		ad.setReturn_date_amount_expected(new SimpleDateFormat("dd/MM/yyyy").parse(encdec.decryptnew(advancePayload.getReturn_date_amount_expected())));
		ad.setStart_date_journey(new SimpleDateFormat("dd/MM/yyyy").parse(encdec.decryptnew(advancePayload.getStart_date_journey())));
		advanceRepo.save(ad);
		response="Details Saved succefuly";
		status="True";
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="error in saving adnace trigger detail"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));	
	}
	@RequestMapping(value={"/getAlladvanceTrigger"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<AdvanceTriggerResponse> GetAllAdvanceTriger( )
	{HttpStatus httpstatus=null;
	String response="";
	String status=null;
	List<AdvanceRequest> list=new ArrayList<AdvanceRequest>();
	List<AdvanceRequestPayload> listenc=new ArrayList<AdvanceRequestPayload>();
	try {
		list=advanceRepo.getAllAdvanceRequest();
		int i=0;
		for(AdvanceRequest advancePayload:list) {
		AdvanceRequestPayload ad=new AdvanceRequestPayload();
		ad.setApplicant_id(encdec.encryptnew(advancePayload.getApplicant_id()));
		ad.setAccount_no(encdec.encryptnew(advancePayload.getAccount_no()));
		ad.setAdvance_amount(encdec.encryptnew(Float.toString(advancePayload.getAdvance_amount())));
		ad.setApproved_amount((encdec.encryptnew(Float.toString(advancePayload.getApproved_amount()))));
		ad.setApproved_user_id(encdec.encryptnew(advancePayload.getApproved_user_id()));
		ad.setRaised_user_id(encdec.encryptnew(advancePayload.getRaised_user_id()));
		ad.setComment_by_mk(encdec.encryptnew(advancePayload.getComment_by_mk()));
		ad.setCommenyt_by_sh(encdec.encryptnew(advancePayload.getCommenyt_by_sh()));
		ad.setTruck_number(encdec.encryptnew(advancePayload.getTruck_number()));
		ad.setFrom_location(encdec.encryptnew(advancePayload.getFrom_location()));
		ad.setTo_location(encdec.encryptnew(advancePayload.getTo_location()));
		ad.setEnd_date_journey_expected(encdec.encryptnew((new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getEnd_date_journey_expected()))));
		ad.setIfsc_code(encdec.encryptnew(advancePayload.getIfsc_code()));
		ad.setApproval_status(encdec.encryptnew(advancePayload.getApproval_status()));
		ad.setReturn_date_amount_expected(encdec.encryptnew(new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getReturn_date_amount_expected())));
		ad.setStart_date_journey(encdec.encryptnew((new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getStart_date_journey()))));
		listenc.add(i,ad);
		i++;
		}
		
		response="Details retrieved succefuly";
		status="True";
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="error in retrieving advace trigger detail"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new AdvanceTriggerResponse(listenc,encdec.encryptnew(response),encdec.encryptnew(status)));	
	}
	
}

