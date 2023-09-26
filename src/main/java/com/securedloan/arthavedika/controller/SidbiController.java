package com.securedloan.arthavedika.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.model.AdvanceRequest;
import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.payload.AdvanceRequestPayload;
import com.securedloan.arthavedika.payload.ApplicantPayload;
import com.securedloan.arthavedika.response.ApplicantInfo;
/*import com.securedloan.arthavedika.response.ApplicantInfos;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.GetPredictionResponse;

@CrossOrigin()
@RestController
@RequestMapping("/borrower/Sidbi")
public class SidbiController {
	private static final Boolean TRUE = null;
	private static final Boolean False = null;
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(SidbiController.class);
	@SuppressWarnings("static-access")
	@RequestMapping(value={"/GetPredictionByAppId"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<GetPredictionResponse> GetPrediction(@RequestBody ApplicantPayload  applicantPayload )
	{Log.info(" Get prediction by applicant id requst api is called");
List prediction=null;
		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	
	try {
		
		response="Prediction retrieved  succefuly for applicant id"+applicantPayload.getApplicant_id();
		status="True";
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="Error in retrieving prediction for applicant id"+applicantPayload.getApplicant_id()+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new GetPredictionResponse(prediction,(response),(status)));	
	}
	@RequestMapping(value={"/GetApplicantList"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<ApplicantInfo> GetApplicantList( )
	{Log.info(" Get applicant List api is called");
List<Applicant> applicant=null;
		HttpStatus httpstatus=null;
	String response="";
	Boolean status;
	
	try {
		
		response="All applicant list is retrieved  successfuly";
		status=TRUE;
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="Error in retrieving applicant list"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status=False;
		
		
	}
	return ResponseEntity.status(httpstatus).body(new ApplicantInfo((response),(status),applicant));	
	}
	@RequestMapping(value={"/ReadApplicants"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<GeneralResponse> ReadApplicants(@RequestParam("file") MultipartFile file )
	{Log.info(" Read applicant List api is called");

		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	
	try {
		
		response=" applicant list are uploaded   successfuly";
		status="TRUE";
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="Error while reading  applicant list"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new GeneralResponse((response),(status)));	
	}
	@RequestMapping(value={"/WritePrediction"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<GeneralResponse> writePrediction( )
	{Log.info(" write prediction api is called");

		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	
	try {
		
		response=" prediction is downloaded   successfuly";
		status="TRUE";
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="Error while downloading prediction "+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new GeneralResponse((response),(status)));	
	}
}*/

