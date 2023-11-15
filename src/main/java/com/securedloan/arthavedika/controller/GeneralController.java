package com.securedloan.arthavedika.controller;
import java.nio.file.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	@PostMapping("/uploadApplicantData")
	public ResponseEntity<GeneralResponse> uploadData(@RequestParam("file") MultipartFile file) throws IOException {
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		try{
		//	List<LicenseDetails> list1=new ArrayList<>();
		
		InputStream inputstream=file.getInputStream();
		String name=file.getOriginalFilename();
		System.out.println("inputstream size is"+inputstream.toString());
		System.out.println("name of file is"+name);
		String target=writeToFile(inputstream,name);
		response="Applicant File uploaded successfully to path"+target;
		status="True";
		 httpstatus=HttpStatus.OK;
		 System.out.println(response);
	  
	}
	 catch(Exception e) {
	  System.out.println("error in uploading applicant details"+e);
	  response="error in uploading applicant detail"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
	 }
		 System.out.println(response);
		 System.out.println(status);
	  return ResponseEntity.status(httpstatus)
					.body(new GeneralResponse(encdec.encryptnew(response), encdec.encryptnew(status)));
	 
	}
	public String writeToFile(InputStream stream,String name) {
		String target="";
		try {
			    File targetFile = new File("C://upload batch file//UploadFiles//"+name);

			    FileUtils.copyInputStreamToFile(stream, targetFile);
		target=targetFile.getPath();
		
	}
		catch (Exception e) {
			System.out.println(e);
		}
	    return target;
	}
}
