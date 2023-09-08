package com.securedloan.arthavedika.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
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
import com.securedloan.arthavedika.model.AdvanceRequest;
import com.securedloan.arthavedika.payload.AdvanceRequestPayload;
import com.securedloan.arthavedika.repo.LocationRepo;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.*;
import com.securedloan.arthavedika.model.*;

@CrossOrigin()
@RestController
@RequestMapping("/borrower")
public class GeneralController {
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(AdvanceTrigerController.class);
	@Autowired
	LocationRepo locationRepo;
	@SuppressWarnings("static-access")
	@RequestMapping(value={"/AllLocation"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<LocationListResponse> AllLocation( )
	{Log.info(" All location api is called");

		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	List<M_location> location=new ArrayList<M_location>();
	List<LocationResponse> lct=new ArrayList<LocationResponse>();
	try {
		location=locationRepo.getAllLocation();
		int i=0;
		for(M_location lctin:location) {
		LocationResponse locations=new LocationResponse();
		locations.setLocation_id(encdec.encryptnew(String.valueOf(lctin.getLocation_id())));
		locations.setLocation_name(encdec.encryptnew(lctin.getLocation_name()));
		lct.add(i, locations);
		i++;
		}
		response="Location retrieved successfully";
		status="True";
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="error in retrieving location detail"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new LocationListResponse(lct,encdec.encryptnew(response),encdec.encryptnew(status)));	
	}
}
