package com.securedloan.arthavedika.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.securedloan.arthavedika.model.Applicant_approval_details;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.FileDB;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.AuthorizeApplicantPayload;
import com.securedloan.arthavedika.payload.Dashboard;
import com.securedloan.arthavedika.payload.DashboardRequest;
import com.securedloan.arthavedika.payload.FindAllApplicantPagination;
import com.securedloan.arthavedika.payload.FindAllApplicantPayload;
import com.securedloan.arthavedika.repo.ApplicantApprovalDetailsRepo;
import com.securedloan.arthavedika.repo.ApplicantPaginationRepo;
import com.securedloan.arthavedika.repo.ApplicantRepository;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.response.ApplicantInfo;
import com.securedloan.arthavedika.response.ApplicantResponse;
import com.securedloan.arthavedika.response.BorrowerResponse;
import com.securedloan.arthavedika.response.DashboardResponse;
import com.securedloan.arthavedika.response.FindAllApplicant;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.Result;
import com.securedloan.arthavedika.service.ApplicantService;
import com.securedloan.arthavedika.service.UserService;
//@CrossOrigin()
@CrossOrigin(origins = "*", allowedHeaders = "*")
//@CrossOrigin(origins = "http://4.236.144.236:4200")
@RestController
@RequestMapping("applicant/borrower")
public class BorrowerAppController {
	@Autowired
	ApplicantRepository appRepo;
	@Autowired
	ApplicantPaginationRepo applicantRepo;
	@Autowired
	CompanyRepo companyRepo;
	@Autowired
	ApplicantApprovalDetailsRepo approvalRepo;
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	@Autowired
	private ApplicantService applicantService;
	@RequestMapping(value = { "/findAllApplicant/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<FindAllApplicant> findAllApplicant( ) {
		LOGGER.info("Find All applicant api is called");
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		List<Applicant> applicant=null;
		List<ApplicantResponse> appl=new ArrayList<ApplicantResponse>();
		//int status_code=0;
		try {		
			 applicant = applicantService.findAllAppicant();
			 int i=0;
			 for(Applicant app:applicant) {
				ApplicantResponse appr=new ApplicantResponse(); 
				appr.setApplicant_id(encdec.encryptnew(Long.toString(app.getApplicant_id())));
				if(app.getApplicant_firstname()!=null) {
				appr.setApplicant_firstname(encdec.encryptnew(app.getApplicant_firstname()));}
				if(app.getApplicant_lastname()!=null) {
				appr.setApplicant_lastname(encdec.encryptnew(app.getApplicant_lastname()));}
				if(app.getApplicant_email_id()!=null) {
				appr.setApplicant_email_id(encdec.encryptnew(app.getApplicant_email_id()));}
				if(app.getApplicant_mobile_no()!=null) {
				appr.setApplicant_mobile_no(encdec.encryptnew(app.getApplicant_mobile_no()));}
				if(app.getAV_approval()!=null) {
				appr.setAV_approval(encdec.encryptnew(app.getAV_approval()));}
				if(app.getMK_approval()!=null) {
				appr.setMK_approval(encdec.encryptnew(app.getMK_approval()));}
				if(app.getSH_approval()!=null) {
				appr.setSH_approval(encdec.encryptnew(app.getSH_approval()));}
				if(app.getCompany_name()!=null) {
				appr.setCompany_name(encdec.encryptnew(app.getCompany_name()));}
				appl.add(i, appr);
				i++;
			 }
			 response="list extracted succesfully";
			 status="true";
			 httpstatus=HttpStatus.OK;

				//	return ResponseEntity.status(HttpStatus.OK).body(new FindAllApplicant(applicant,"list extracted succesfully", Boolean.TRUE));
			

		
		} catch (Exception e) {
			response="Error While retreiving all applicant list" + e.getMessage();
			 status="false";
			 httpstatus=HttpStatus.BAD_REQUEST;
			LOGGER.error("Error While retreiving all applicant list" + e.getMessage());
			//return ResponseEntity.badRequest().body(new FindAllApplicant(applicant,e.getMessage(), Boolean.FALSE));
		}
		return ResponseEntity.status(httpstatus).body(new FindAllApplicant(appl,encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	@RequestMapping(value = { "/authorizeApplicant/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<BorrowerResponse> authorizeApplicant(@RequestBody AuthorizeApplicantPayload authorizeApplicantPayload ) {
		LOGGER.info("Authorise applicant api is called");
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		try {
			httpstatus=HttpStatus.OK;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			Date date = sdf.parse(sdf.format(new Date()));
			
			 status="true";
			LOGGER.info("company_code is"+authorizeApplicantPayload.getCompany_code());
			System.out.println("company code"+encdec.decryptnew(authorizeApplicantPayload.getCompany_code()));
			Long applicant_id=Long.parseLong(encdec.decryptnew(authorizeApplicantPayload.getApplicant_id()));
			LOGGER.info(" applicant id is"+applicant_id);
			switch(encdec.decryptnew(authorizeApplicantPayload.getCompany_code())) {
			case "AV":
			{System.out.println("av approval before");
				LOGGER.info("status of av_approval is"+encdec.decryptnew(authorizeApplicantPayload.getApproval_status()));	
				//Long applicant_id=Long.parseLong(encdec.decryptnew(authorizeApplicantPayload.getApplicant_id()));
				System.out.println("av approval before1");
				//appRepo.AVauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()),date, applicant_id);
				System.out.println("av approval before12");
				if(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()).contains("Y"))
				{
					appRepo.AVauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()),date,1, applicant_id);
					response="Applicant is verified";
				}
				else {
					appRepo.AVauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()),date,4, applicant_id);
				
					response="Applicant is rejected";
				}
				
				/*System.out.println("applicant company code is"+encdec.decryptnew(authorizeApplicantPayload.getApplicant_company_code()));
				Company company=companyRepo.company_details(encdec.decryptnew(authorizeApplicantPayload.getApplicant_company_code()));
				System.out.println("Applicant company"+company);
				System.out.println("av approval before2");
				if(company!=null)
				{Float current_amount=company.getCurrent_amount()-Float.parseFloat(encdec.decryptnew(authorizeApplicantPayload.getLoan_amount()));
				companyRepo.updateCurrentAmount(current_amount, encdec.decryptnew(authorizeApplicantPayload.getCompany_code()));}*/
				Applicant_approval_details approvaldtls=new Applicant_approval_details();
				System.out.println("av approval before123");
				approvaldtls.setApplicant_id(applicant_id);
				approvaldtls.setAv_authorisation_by(encdec.decryptnew(authorizeApplicantPayload.getApproval_by()));
				approvaldtls.setAv_comment(encdec.decryptnew(authorizeApplicantPayload.getComment()));
				approvaldtls.setAv_authorisation_status(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()));
				approvaldtls.setAv_authorisation_datetime(date);
				approvalRepo.save(approvaldtls);
				System.out.println("av approval before1234");

				break;	
				
			}
			case"MK":
			{
				LOGGER.info("status of MK_approval is"+encdec.decryptnew(authorizeApplicantPayload.getApproval_status()));	
			//appRepo.MKauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()), date,applicant_id);
		/*	Company company=companyRepo.company_details(encdec.decryptnew(authorizeApplicantPayload.getApplicant_company_code()));
			if(company!=null)
			{	Float current_amount=company.getCurrent_amount()-Float.parseFloat(encdec.decryptnew(authorizeApplicantPayload.getLoan_amount()));
			System.out.println("Mk approval before1");
			companyRepo.updateCurrentAmount(current_amount, encdec.decryptnew(authorizeApplicantPayload.getCompany_code()));}*/	
			System.out.println("Mk approval before2");
			approvalRepo.updateMkverifyDetails(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()),
					encdec.decryptnew(authorizeApplicantPayload.getApproval_by()),encdec.decryptnew(authorizeApplicantPayload.getComment()) 
			, date, applicant_id);
			System.out.println("Mk approval after");
			if(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()).contains("Y"))
			{appRepo.MKauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()), date,2,applicant_id);
				response="Applicant is authorized";
			}
			else {
				appRepo.MKauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()), date,4,applicant_id);
				response="Applicant is rejected";
			}
			break;
			
			}
			case"SH":
			{LOGGER.info("status of SH_approval is"+encdec.decryptnew(authorizeApplicantPayload.getApproval_status()));	
			//appRepo.SHauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()), date, applicant_id);
			System.out.println("sh approval before");
		/*	Company company=companyRepo.company_details(authorizeApplicantPayload.getApplicant_company_code());
			if(company!=null)
			{System.out.println("sh approval before12");
				Float current_amount=company.getCurrent_amount()-Float.parseFloat(encdec.decryptnew(authorizeApplicantPayload.getLoan_amount()));
			companyRepo.updateCurrentAmount(current_amount, encdec.decryptnew(authorizeApplicantPayload.getCompany_code()));}*/
			System.out.println("sh approval before2");
			approvalRepo.updateshapprovalDetails(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()),
					encdec.decryptnew(authorizeApplicantPayload.getApproval_by()),encdec.decryptnew(authorizeApplicantPayload.getComment()) 
			, date, applicant_id);	
			System.out.println("sh approval after");
			if(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()).contains("Y"))
			{
				System.out.println("loan_limit "+encdec.decryptnew(authorizeApplicantPayload.getEligible_loan_amount()));
				Float loan_limit=Float.parseFloat(encdec.decryptnew(authorizeApplicantPayload.getEligible_loan_amount()));
				System.out.println(loan_limit);
				appRepo.SHauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()), date,3,loan_limit, applicant_id);
				response="Applicant is approved";
			}
			else {
				Float loan_limit=(float) 0;
				appRepo.SHauthoriseApplicant(encdec.decryptnew(authorizeApplicantPayload.getApproval_status()), date,4,loan_limit, applicant_id);
				response="Applicant is rejected";
			}
			break;
			}
			}
			
//response="Applicant pre approval status is submitted succesfully";
System.out.print("response is"+response);
				//	return ResponseEntity.status(HttpStatu).body(new BorrowerResponse("Applicant authorisation status is submitted succesfully", Boolean.TRUE));
		
		} catch (Exception e) {
			response="Error While authorising applicant list" + e.getMessage();
			httpstatus=HttpStatus.BAD_REQUEST;
			 status="false";
			LOGGER.error("Error While authorising applicant list" + e.getMessage());
			//return ResponseEntity.badRequest().body(new BorrowerResponse(e.getMessage(), Boolean.FALSE));
		}
		return ResponseEntity.status(httpstatus).body(new BorrowerResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}
/*
	@RequestMapping(value = { "/findAllApplicantPagination/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<FindAllApplicant> findAllApplicantPagination(@RequestBody FindAllApplicantPagination findAllApplicantPayload) {
		LOGGER.info("Find All applicant api is called");
		List<Applicant> applicant=null;
		//int status_code=0;
		try {
			Pageable Page = PageRequest.of(findAllApplicantPayload.getPage_number(), findAllApplicantPayload.getNo_of_records());
				
				
			 applicant = applicantRepo.findAllApplicant(Page);
			 System.out.println("size of list"+applicant.size());

					return ResponseEntity.status(HttpStatus.OK).body(new FindAllApplicant(applicant,"list extracted succesfully", Boolean.TRUE));
			

		
		} catch (Exception e) {
			LOGGER.error("Error While retreiving all applicant list" + e.getMessage());
			return ResponseEntity.badRequest().body(new FindAllApplicant(applicant,e.getMessage(), Boolean.FALSE));
		}
	}
*/
	@RequestMapping(value = { "/Dashboard" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<DashboardResponse> dashboard(@RequestBody DashboardRequest dashboardPayload) {
		LOGGER.info("dashboard api is called");
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		//List<Dashboard> list=new ArrayList();
		Dashboard dash=new Dashboard();
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));

	        System.out.println("today date is"+date);
			Integer total=appRepo.total_applicant();
			dash.setTotal_applicants(encdec.encryptnew(total.toString()));
			System.out.println(dashboardPayload.getCompany_code());
			if(encdec.decryptnew(dashboardPayload.getCompany_code()).equals("AV"))
			{
			dash.setTotal_verified(encdec.encryptnew(String.valueOf(appRepo.av_approval())));
			System.out.println(String.valueOf(appRepo.av_approval()));
			dash.setTotal_rejected(encdec.encryptnew(String.valueOf(appRepo.av_rejection())));
			System.out.println(String.valueOf(appRepo.av_rejection()));
			dash.setPending_for_verification(encdec.encryptnew(String.valueOf(appRepo.av_pending())));
			System.out.println(String.valueOf(appRepo.av_pending()));
			dash.setVerified_today(encdec.encryptnew(String.valueOf(appRepo.today_av_approval(date))));
			}
			if(encdec.decryptnew(dashboardPayload.getCompany_code()).equals("MK")){
				dash.setTotal_verified(encdec.encryptnew(String.valueOf(appRepo.mk_approval())));
				dash.setTotal_rejected(encdec.encryptnew(String.valueOf(appRepo.MK_rejection())));
				dash.setPending_for_verification(encdec.encryptnew(String.valueOf(appRepo.MK_pending())));
				dash.setVerified_today(encdec.encryptnew(String.valueOf(appRepo.today_mk_approval(date))));
			}
			if(encdec.decryptnew(dashboardPayload.getCompany_code()).equals("SH")){
				dash.setTotal_verified(encdec.encryptnew(String.valueOf(appRepo.Sh_approval())));
				dash.setTotal_rejected(encdec.encryptnew(String.valueOf(appRepo.SH_rejection())));
				dash.setPending_for_verification(encdec.encryptnew(String.valueOf(appRepo.Sh_pending())));
				dash.setVerified_today(encdec.encryptnew(String.valueOf(appRepo.today_sh_approval(date))));
			}
			
				
			
			response="dashboard details fecthed successfully";		
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error in dashbaord api" + e.getMessage());
			response="Error in dashboard api" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		List<Dashboard> list=new ArrayList<>();
		list.add(dash);
		return ResponseEntity.status(httpstatus).body(new DashboardResponse(list,encdec.encryptnew(response),
				encdec.encryptnew(status)));
	}
}

