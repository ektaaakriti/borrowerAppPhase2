package com.securedloan.arthavedika.controller;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.payload.AdvanceRequestPayload;
import com.securedloan.arthavedika.payload.ApplicantPayload;
import com.securedloan.arthavedika.payload.ApproveAdvancePayload;
import com.securedloan.arthavedika.payload.GetApplicantPayload;
import com.securedloan.arthavedika.payload.ekycPayload;
import com.securedloan.arthavedika.repo.AdvanceRequestRepo;
import com.securedloan.arthavedika.repo.ApplicantRepository;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.response.AdvanceTriggerResponse;
import com.securedloan.arthavedika.response.ApplicantInfos;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.MkVerrifiedList;
import com.securedloan.arthavedika.response.MkverifiedApplicantResponse;

import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.client.*;
import org.apache.http.client.methods.*;
@CrossOrigin()
@RestController
@RequestMapping("/borrower")
public class AdvanceTrigerController {
	@Autowired
	AdvanceRequestRepo advanceRepo;
	@Autowired
	ApplicantRepository applicantRepo;
	@Autowired
	CompanyRepo companyRepo;
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(AdvanceTrigerController.class);
	@SuppressWarnings("static-access")
	@RequestMapping(value={"/SaveadvanceTrigger"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<GeneralResponse> saveAdvanceTriger(@RequestBody AdvanceRequestPayload advancePayload )
	{Log.info(" save advance requst api is called");

		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	
	try {
		AdvanceRequest ad=new AdvanceRequest();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = sdf.parse(sdf.format(new Date()));
		ad.setRaised_date_time(date);
		if(advancePayload.getApplicant_id()!=null) {
		ad.setApplicant_id(Long.valueOf(encdec.decryptnew((advancePayload.getApplicant_id()))));}
		if(advancePayload.getAccount_no()!=null) {
		ad.setAccount_no(encdec.decryptnew(advancePayload.getAccount_no()));}
		if(advancePayload.getAdvance_amount()!=null) {
		ad.setAdvance_amount(Float.parseFloat(encdec.decryptnew(advancePayload.getAdvance_amount())));}
		if(advancePayload.getApproved_amount()!=null) {
		ad.setApproved_amount((Float.parseFloat(encdec.decryptnew(advancePayload.getApproved_amount()))));}
		if(advancePayload.getApproved_user_id()!=null) {
		ad.setApproved_user_id(encdec.decryptnew(advancePayload.getApproved_user_id()));}
		if(advancePayload.getRaised_user_id()!=null) {
		ad.setRaised_user_id(encdec.decryptnew(advancePayload.getRaised_user_id()));}
		if(advancePayload.getComment_by_mk()!=null) {
		ad.setComment_by_mk(encdec.decryptnew(advancePayload.getComment_by_mk()));}
		if(advancePayload.getCommenyt_by_sh()!=null) {
		ad.setCommenyt_by_sh(encdec.decryptnew(advancePayload.getCommenyt_by_sh()));}
		if(advancePayload.getTruck_number()!=null) {
		ad.setTruck_number(encdec.decryptnew(advancePayload.getTruck_number()));}
		if(advancePayload.getFrom_location()!=null) {
		ad.setFrom_location(encdec.decryptnew(advancePayload.getFrom_location()));}
		if(advancePayload.getTo_location()!=null) {
		ad.setTo_location(encdec.decryptnew(advancePayload.getTo_location()));}
		if(advancePayload.getEnd_date_journey_expected()!=null) {
		ad.setEnd_date_journey_expected(new SimpleDateFormat("yyyy-MM-dd").parse(encdec.decryptnew(advancePayload.getEnd_date_journey_expected())));}
		if(advancePayload.getIfsc_code()!=null) {
		ad.setIfsc_code(encdec.decryptnew(advancePayload.getIfsc_code()));}
		if(advancePayload.getApproval_status()!=null) {
		ad.setApproval_status(encdec.decryptnew(advancePayload.getApproval_status()));}
		if(advancePayload.getReturn_date_amount_expected()!=null) {
		
		//Date date=(new SimpleDateFormat("yyyy-MM-dd").parse(encdec.decryptnew(advancePayload.getReturn_date_amount_expected()));
		ad.setReturn_date_amount_expected(new SimpleDateFormat("yyyy-MM-dd").parse(encdec.decryptnew(advancePayload.getReturn_date_amount_expected())));
		}
		if(advancePayload.getStart_date_journey()!=null) {
		ad.setStart_date_journey(new SimpleDateFormat("yyyy-MM-dd").parse(encdec.decryptnew(advancePayload.getStart_date_journey())));
		}
		advanceRepo.save(ad);
		Company com=companyRepo.company_details("MK");
		Float limit=com.getCurrent_amount();
		System.out.println("limit"+limit);
		Float current_limit=limit-Float.parseFloat(encdec.decryptnew(advancePayload.getAdvance_amount()));
		System.out.println("limit after advance"+current_limit);
		companyRepo.updateCurrentAmount(current_limit,"MK");
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
	{
		Log.info("advance requst api is called");

		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	List<AdvanceRequest> list=new ArrayList<AdvanceRequest>();
	List<AdvanceRequestPayload> listenc=new ArrayList<AdvanceRequestPayload>();
	try {
		list=advanceRepo.getAllAdvanceRequest();
		if(list==null) {
			response="No details of advance";
			System.out.println(response);
		}
		else {
		int i=0;
		for(AdvanceRequest advancePayload:list) {
		AdvanceRequestPayload ad=new AdvanceRequestPayload();
		System.out.println("dummy1");
		if(!advancePayload.getApplicant_id().equals(null))
		{ad.setApplicant_id(encdec.encryptnew(String.valueOf(advancePayload.getApplicant_id())));}
		System.out.println("dummy2");
		if(!advancePayload.getAccount_no().isEmpty())
		{ad.setAccount_no(encdec.encryptnew(advancePayload.getAccount_no()));}
		System.out.println("dummy3");
		if(!advancePayload.getAdvance_amount().equals(null))
		{ad.setAdvance_amount(encdec.encryptnew(Float.toString(advancePayload.getAdvance_amount())));}
		System.out.println("dummy4");
		if(!advancePayload.getApproved_amount().equals(0))
		{ad.setApproved_amount((encdec.encryptnew(Float.toString(advancePayload.getApproved_amount()))));}
		System.out.println("Approved amount"+advancePayload.getApproved_amount());
		System.out.println("dummy5");
		if(!advancePayload.getApproved_user_id().isEmpty()) {
		ad.setApproved_user_id(encdec.encryptnew(advancePayload.getApproved_user_id()));}
		System.out.println("dummy6");
		if(!advancePayload.getRaised_user_id().isEmpty())
		{ad.setRaised_user_id(encdec.encryptnew(advancePayload.getRaised_user_id()));}
		if(!advancePayload.getComment_by_mk().isEmpty())
		{ad.setComment_by_mk(encdec.encryptnew(advancePayload.getComment_by_mk()));}
		if(!advancePayload.getComment_by_mk().isEmpty())
		{ad.setCommenyt_by_sh(encdec.encryptnew(advancePayload.getCommenyt_by_sh()));}
		if(!advancePayload.getTruck_number().isEmpty())
		{ad.setTruck_number(encdec.encryptnew(advancePayload.getTruck_number()));}
		if(!advancePayload.getFrom_location().isEmpty())
		{ad.setFrom_location(encdec.encryptnew(advancePayload.getFrom_location()));}
		if(!advancePayload.getTo_location().isEmpty())
		{ad.setTo_location(encdec.encryptnew(advancePayload.getTo_location()));}
		if(!advancePayload.getEnd_date_journey_expected().equals(null))
		{ad.setEnd_date_journey_expected(encdec.encryptnew((new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getEnd_date_journey_expected()))));
		}
		if(!advancePayload.getIfsc_code().isEmpty())
		{ad.setIfsc_code(encdec.encryptnew(advancePayload.getIfsc_code()));}
		if(!advancePayload.getApproval_status().isEmpty())
		{ad.setApproval_status(encdec.encryptnew(advancePayload.getApproval_status()));}
		if(!advancePayload.getReturn_date_amount_expected().equals(null))
		{ad.setReturn_date_amount_expected(encdec.encryptnew(new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getReturn_date_amount_expected())));
		}
		if(!advancePayload.getStart_date_journey().equals(null))
		{ad.setStart_date_journey(encdec.encryptnew((new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getStart_date_journey()))));
		}
		listenc.add(i,ad);
		i++;
		System.out.println(listenc);
		}
		System.out.println(response);
		response="Details retrieved succefuly";}
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
	@RequestMapping(value={"/GetMkverifiedApplicant"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<MkverifiedApplicantResponse> GetMkverifiedApplicant( )
	{
		Log.info("Get mk verified applicant api is called");
		System.out.print("get mk verified applicant api");
		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	List<MkVerrifiedList> applicant=new ArrayList<MkVerrifiedList>();
	
	try {
		List<Applicant> applicants=applicantRepo.getMKVerifiedApplicant();	
		int i=0;
		for(Applicant app:applicants) {
			MkVerrifiedList mk=new MkVerrifiedList();
			mk.setApplicant_id(encdec.encryptnew(String.valueOf(app.getApplicant_id())));
			mk.setApplicant_name(encdec.encryptnew(app.getApplicant_firstname()));
			applicant.add(i, mk);
			i++;
			
		}
		response="Details retrieved succefuly";
		status="True";
		 httpstatus=HttpStatus.OK;
		 System.out.print(response);
		
				
	}
	catch(Exception e){
		response="error in retrieving advace trigger detail"+e.getMessage();
		System.out.print(response);
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new MkverifiedApplicantResponse(applicant,encdec.encryptnew(response),encdec.encryptnew(status)));	
	}
	
	@RequestMapping(value={"/ApproveAdvanceLoan"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<GeneralResponse> ApproveAdvanceLoan(@RequestBody ApproveAdvancePayload advancePayload )
	{
		Log.info("approve advance of applicant api is called");
		System.out.print("approve advance of  applicant api");
		HttpStatus httpstatus=null;
	String response="";
	String status=null;
	
	
	try {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = sdf.parse(sdf.format(new Date()));
	advanceRepo.approveAdvance(encdec.decryptnew(advancePayload.getApproval_status()), Float.valueOf(encdec.decryptnew(advancePayload.getApproved_amount())),
			date, encdec.decryptnew(advancePayload.getApproved_username()),encdec.decryptnew(advancePayload.getComment_by_sh()), Long.valueOf(encdec.decryptnew(advancePayload.getApplicant_id())));
	if(encdec.decryptnew(advancePayload.getApproval_status()).contains("Y")) {	
	response="Advance Loan of Applicant is approved";}
	else {
	
		response="Advance Loan of the applicant is rejected ";
		Company com=companyRepo.company_details("MK");
		Float limit=com.getCurrent_amount();
		System.out.println("limit"+limit);
		Float current_limit=limit+Float.parseFloat(encdec.decryptnew(advancePayload.getApproved_amount()));
		System.out.println("limit after advance rejection"+current_limit);
		companyRepo.updateCurrentAmount(current_limit,"MK");
	}
		status="True";
		 httpstatus=HttpStatus.OK;
		 System.out.print(response);
		
				
	}
	catch(Exception e){
		response="error in approving advance of applicant by Share india"+e.getMessage();
		System.out.print(response);
		Log.error(response);
		 httpstatus=HttpStatus.BAD_REQUEST;
		 status="False";
		
		
	}
	return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));	
	}
	

@SuppressWarnings("static-access")
@RequestMapping(value={"/eKycfacematching"}, method = RequestMethod.POST, 
produces= {MediaType.APPLICATION_JSON_VALUE})
public ResponseEntity<GeneralResponse> eKyFaceMatching( @RequestBody ekycPayload ekycPaylaod){
	
HttpStatus httpstatus=null;
String response1="";
String status=null;
String url="10.2.0.4:5000";
	try {

		/*OkHttpClient client = new OkHttpClient();
		String url="10.2.0.4:5000";
		String respstr = "";
	
       okhttp3.RequestBody requestBody = new RequestBody.Builder()
               .add("query_1", ekycPaylaod.getPic1())
               .add("other_images",ekycPaylaod.getPic2())
                .add("method","Face_Matching")
                .build();
	
       Request request = new Request.Builder().url(url).post(formBody).
        		build();
       
        try (
        	
        		Response response = client.newCall(request).execute()) {
        	
        	respstr = response.body().string();
        	System.out.println("respstr"+respstr);
        	response1=respstr;
            
        } catch(Exception e) {
        	System.out.println("expcetion is "+e.getMessage());
        
        	response1="exception in face matching"+e.getMessage();
        	Log.info(response1);
        }*/
		//OkHttpClient client = new OkHttpClient();

		/*MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("method", "Face_matching", ContentType.TEXT_PLAIN);
		builder.addBinaryBody("query_1",ekycPaylaod.getPic1());
		builder.addBinaryBody("other_images", ekycPaylaod.getPic2());*/
		HttpEntity entity = MultipartEntityBuilder.create()
				.addBinaryBody("query_1",ekycPaylaod.getPic1())
				.addBinaryBody("other_images", ekycPaylaod.getPic2())
				.addTextBody("method", "Face_matching")
				.build();
		CloseableHttpClient httpclient = HttpClients.createDefault();
	HttpPost httpPost = new HttpPost(url);
	httpPost.setEntity(entity);
	HttpResponse response= httpclient.execute(httpPost);
		HttpEntity result = response.getEntity();
		response1=EntityUtils.toString(result);
		//respstr = response.body().string();
    	System.out.println("response is"+response1);
    
        }
	catch (Exception e) {
		Log.error("error in face matching");
	}
        
        return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response1),encdec.encryptnew(status)));	
	}
@RequestMapping(value={"/getadvanceTriggerByApplicant_id"}, method = RequestMethod.POST, 
produces= {MediaType.APPLICATION_JSON_VALUE})
public ResponseEntity<AdvanceTriggerResponse> GetAdvanceTrigerByApplicant_id(@RequestBody ApplicantPayload applicantPayload )
{
Log.info("advance requst details by applicant_id api is called");
System.out.println("advance request of applicant id");
HttpStatus httpstatus=null;
String response="";
String status=null;
List<AdvanceRequest> list=new ArrayList<AdvanceRequest>();
List<AdvanceRequestPayload> listenc=new ArrayList<AdvanceRequestPayload>();
try {
	System.out.println("Applicant id is"+Long.parseLong(encdec.decryptnew(applicantPayload.getApplicant_id())));
list=advanceRepo.getAdvanceRequestByApplicant_id(Long.parseLong(encdec.decryptnew(applicantPayload.getApplicant_id())));
System.out.println("user "+list.get(0).getRaised_user_id());
int i=0;
if(list==null) {
	response="Applicant details are not available";
	System.out.println(response);
}
else {
for(AdvanceRequest advancePayload:list) {
AdvanceRequestPayload ad=new AdvanceRequestPayload();
if(!advancePayload.getApplicant_id().equals(null))
{ad.setApplicant_id((encdec.encryptnew(String.valueOf(advancePayload.getApplicant_id()))));}
if(!advancePayload.getAccount_no().isEmpty())
{System.out.println("dummy1");
	ad.setAccount_no(encdec.encryptnew(advancePayload.getAccount_no()));}
if(!advancePayload.getAdvance_amount().equals(0))
{ad.setAdvance_amount(encdec.encryptnew(Float.toString(advancePayload.getAdvance_amount())));}
if(!advancePayload.getAdvance_amount().equals(0))
{System.out.println("dummy2");
	ad.setApproved_amount((encdec.encryptnew(Float.toString(advancePayload.getApproved_amount()))));}
if(!advancePayload.getApproved_user_id().isEmpty()) {
ad.setApproved_user_id(encdec.encryptnew(advancePayload.getApproved_user_id()));}
System.out.println("dummy3");
if(!advancePayload.getRaised_user_id().isEmpty())
{System.out.print("raised user"+advancePayload.getRaised_user_id());
	ad.setRaised_user_id(encdec.encryptnew(advancePayload.getRaised_user_id()));}
System.out.println("dummy4");
if(!advancePayload.getComment_by_mk().isEmpty())
{ad.setComment_by_mk(encdec.encryptnew(advancePayload.getComment_by_mk()));}
if(!advancePayload.getComment_by_mk().isEmpty())
{ad.setCommenyt_by_sh(encdec.encryptnew(advancePayload.getCommenyt_by_sh()));}
if(!advancePayload.getTruck_number().isEmpty())
{ad.setTruck_number(encdec.encryptnew(advancePayload.getTruck_number()));}
if(!advancePayload.getFrom_location().isEmpty())
{ad.setFrom_location(encdec.encryptnew(advancePayload.getFrom_location()));}
if(!advancePayload.getTo_location().isEmpty())
{ad.setTo_location(encdec.encryptnew(advancePayload.getTo_location()));}
if(!advancePayload.getEnd_date_journey_expected().equals(null))
{ad.setEnd_date_journey_expected(encdec.encryptnew((new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getEnd_date_journey_expected()))));
}
if(!advancePayload.getIfsc_code().isEmpty())
{ad.setIfsc_code(encdec.encryptnew(advancePayload.getIfsc_code()));}
if(!advancePayload.getApproval_status().isEmpty())
{ad.setApproval_status(encdec.encryptnew(advancePayload.getApproval_status()));}
if(!advancePayload.getReturn_date_amount_expected().equals(null))
{ad.setReturn_date_amount_expected(encdec.encryptnew(new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getReturn_date_amount_expected())));
}
if(!advancePayload.getStart_date_journey().equals(null))
{ad.setStart_date_journey(encdec.encryptnew((new SimpleDateFormat("dd/MM/yyyy").format(advancePayload.getStart_date_journey()))));
}
listenc.add(i,ad);
i++;
}

response="Details retrieved succefuly";}
status="True";
httpstatus=HttpStatus.OK;

	
}
catch(Exception e){
response="error in retrieving advace trigger detail"+e;
System.out.println(response);
Log.error(response);
httpstatus=HttpStatus.BAD_REQUEST;
status="False";


}
return ResponseEntity.status(httpstatus).body(new AdvanceTriggerResponse(listenc,encdec.encryptnew(response),encdec.encryptnew(status)));	
}
}