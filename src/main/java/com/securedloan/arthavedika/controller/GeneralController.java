package com.securedloan.arthavedika.controller;
import java.nio.file.Files;
import java.sql.Date;
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
import com.securedloan.arthavedika.payload.AdvanceRequestPayload;
import com.securedloan.arthavedika.payload.uploadApplicantspayload;
import com.securedloan.arthavedika.repo.LocationRepo;
import com.securedloan.arthavedika.repo.UploadApplicantsRepo;
import com.securedloan.arthavedika.repo.UploadLogMasterRepo;
import com.securedloan.arthavedika.response.*;
import com.securedloan.arthavedika.model.*;
//@CrossOrigin()
//@CrossOrigin(origins = "http://4.236.144.236:4200")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/borrower")
public class GeneralController {
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(AdvanceTrigerController.class);
	@Autowired
	LocationRepo locationRepo;
	@Autowired
	UploadApplicantsRepo upRepo;
	@Autowired
	UploadLogMasterRepo masterRepo;
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
		response="Upload applicants process is initiated.Please wait for few minutes to get it uploaded in database.";
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
			    File targetFile = new File("C://uploadbatchfile//UploadFiles//"+name);

			    FileUtils.copyInputStreamToFile(stream, targetFile);
		target=targetFile.getPath();
		
	}
		catch (Exception e) {
			System.out.println(e);
		}
	    return target;
	}
	@RequestMapping(value={"/UploadApplicantsLogMaster"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UploadMasterLogResponse> UpdateDocumentLogMaster( )
	{Log.info("upload applicants log  master api is called");

		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	List<UploadLogMaster> list=new ArrayList<UploadLogMaster>();
	List<UploadMasterResponse> enclist=new ArrayList<UploadMasterResponse>();
	try {
		System.out.println("dummy11");
		list=masterRepo.findAll();
		System.out.println("list retrieved");
		int i=0;
		for(UploadLogMaster log:list) {
		UploadMasterResponse logs=new UploadMasterResponse();
		System.out.println("dummy1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("dummy2");
		String date=sdf.format(list.get(i).getDatetime());
		System.out.println("dummy3"+date);
		logs.setDatetime(encdec.encryptnew(date));
		System.out.println("dummy4");
		logs.setLog(encdec.encryptnew(list.get(i).getLog()));
		logs.setFile_name(encdec.encryptnew(list.get(i).getFile_name()));
		logs.setFile_id(encdec.encryptnew(String.valueOf(list.get(i).getFile_id())));
		//logs.setRow_no(encdec.encryptnew(list.get(i).getRow_no()));
		enclist.add(i, logs);
		i++;
		}
		response="Logs of uploads applicants master retreived successfully";
		status="True";
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="error in retrieving uploads applicants master  detail"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new UploadMasterLogResponse(enclist,encdec.encryptnew(response),encdec.encryptnew(status)));	
	}
	@RequestMapping(value={"/UploadApplicantsLog"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UploadApplicantsLogResponse> UpdateDocumentLog(@RequestBody uploadApplicantspayload uploadPayload )
	{Log.info("upload applicants log api is called");
System.out.println("start of method upload applicants lop api");
		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	List<Uploadapplicants> list=new ArrayList<Uploadapplicants>();
	List<UploadApplicants> enclist=new ArrayList<UploadApplicants>();
	System.out.println("dummy");
	try {
		System.out.println("Filename enc"+uploadPayload.getFilename());
		String filename=encdec.decryptnew(uploadPayload.getFilename());
		System.out.println("Filename"+filename);
		Integer File_id=(Integer.parseInt(encdec.decryptnew(uploadPayload.getFile_id())));
		System.out.println("File id "+File_id);
		list=upRepo.findLog(filename,File_id);
		System.out.println("list retrieved"+list);
		int i=0;
		for(Uploadapplicants log:list) {
		UploadApplicants logs=new UploadApplicants();
		System.out.println("dummy1");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		System.out.println("dummy2");
		String date=sdf.format(list.get(i).getDateTime());
		System.out.println("dummy3");
		logs.setDate(encdec.encryptnew(date));
		System.out.println("dummy4");
		logs.setError(encdec.encryptnew(list.get(i).getError()));
		logs.setFilename(encdec.encryptnew(list.get(i).getFilename()));
		logs.setRow_no(encdec.encryptnew(list.get(i).getRow_no()));
		logs.setFile_id(uploadPayload.getFile_id());
		enclist.add(i, logs);
		i++;
		}
		response="Logs of uploads applicants retreived successfully";
		status="True";
		System.out.println(response);
		 httpstatus=HttpStatus.OK;
		
				
	}
	catch(Exception e){
		response="error in retrieving upload applicants  details"+e;
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	System.out.println(response);
	return ResponseEntity.status(httpstatus).body(new UploadApplicantsLogResponse(enclist,encdec.encryptnew(response),encdec.encryptnew(status)));	
	}
}
