package com.securedloan.arthavedika.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.FileDB;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.AuthorizeApplicantPayload;
import com.securedloan.arthavedika.payload.Dashboard;
import com.securedloan.arthavedika.payload.DashboardRequest;
import com.securedloan.arthavedika.payload.FindAllApplicantPagination;
import com.securedloan.arthavedika.payload.FindAllApplicantPayload;
import com.securedloan.arthavedika.repo.ApplicantPaginationRepo;
import com.securedloan.arthavedika.repo.ApplicantRepository;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.response.ApplicantInfo;
import com.securedloan.arthavedika.response.BorrowerResponse;
import com.securedloan.arthavedika.response.DashboardResponse;
import com.securedloan.arthavedika.response.FindAllApplicant;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.Result;
import com.securedloan.arthavedika.service.ApplicantService;
import com.securedloan.arthavedika.service.UserService;

@CrossOrigin()
@RestController
@RequestMapping("applicant/borrower")
public class BorrowerAppController {
	@Autowired
	ApplicantRepository appRepo;
	@Autowired
	ApplicantPaginationRepo applicantRepo;
	@Autowired
	CompanyRepo companyRepo;
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
		Boolean status=null;
		List<Applicant> applicant=null;
		//int status_code=0;
		try {		
			 applicant = applicantService.findAllAppicant();
			 response="list extracted succesfully";
			 status=true;
			 httpstatus=HttpStatus.OK;

				//	return ResponseEntity.status(HttpStatus.OK).body(new FindAllApplicant(applicant,"list extracted succesfully", Boolean.TRUE));
			

		
		} catch (Exception e) {
			response="Error While retreiving all applicant list" + e.getMessage();
			 status=false;
			 httpstatus=HttpStatus.BAD_REQUEST;
			LOGGER.error("Error While retreiving all applicant list" + e.getMessage());
			//return ResponseEntity.badRequest().body(new FindAllApplicant(applicant,e.getMessage(), Boolean.FALSE));
		}
		return ResponseEntity.status(httpstatus).body(new FindAllApplicant(applicant,response,status));
	}
	@RequestMapping(value = { "/authorizeApplicant/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<BorrowerResponse> authorizeApplicant(@RequestBody AuthorizeApplicantPayload authorizeApplicantPayload ) {
		LOGGER.info("Authorise applicant api is called");
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		try {
			httpstatus=HttpStatus.OK;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));
			response="Applicant authorisation status is submitted succesfully";
			 status=true;
			LOGGER.info("company_code is"+authorizeApplicantPayload.getCompany_code());

			LOGGER.info(" applicant id is"+authorizeApplicantPayload.getApplicant_id());
			switch(authorizeApplicantPayload.getCompany_code()) {
			case "AV":
			{
				LOGGER.info("status of av_approval is"+authorizeApplicantPayload.getApproval_status());	
				appRepo.AVauthoriseApplicant(authorizeApplicantPayload.getApproval_status(),date, authorizeApplicantPayload.getApplicant_id());
				Company company=companyRepo.company_details(authorizeApplicantPayload.getApplicant_company_code());
				System.out.println("Applicant company"+company);
				if(company!=null)
				{Float current_amount=company.getCurrent_amount()-authorizeApplicantPayload.getLoan_amount();
				companyRepo.updateCurrentAmount(current_amount, authorizeApplicantPayload.getCompany_code());}
				break;	
			}
			case"MK":
			{
				LOGGER.info("status of MK_approval is"+authorizeApplicantPayload.getApproval_status());	
			appRepo.MKauthoriseApplicant(authorizeApplicantPayload.getApproval_status(), date,authorizeApplicantPayload.getApplicant_id());
			Company company=companyRepo.company_details(authorizeApplicantPayload.getApplicant_company_code());
			if(company!=null)
			{	Float current_amount=company.getCurrent_amount()-authorizeApplicantPayload.getLoan_amount();
			companyRepo.updateCurrentAmount(current_amount, authorizeApplicantPayload.getCompany_code());}	
			break;
			}
			case"SH":
			{LOGGER.info("status of SH_approval is"+authorizeApplicantPayload.getApproval_status());	
			appRepo.SHauthoriseApplicant(authorizeApplicantPayload.getApproval_status(), date, authorizeApplicantPayload.getApplicant_id());
			Company company=companyRepo.company_details(authorizeApplicantPayload.getApplicant_company_code());
			if(company!=null)
			{Float current_amount=company.getCurrent_amount()-authorizeApplicantPayload.getLoan_amount();
			companyRepo.updateCurrentAmount(current_amount, authorizeApplicantPayload.getCompany_code());}
				break;
			}
			}
			

				//	return ResponseEntity.status(HttpStatu).body(new BorrowerResponse("Applicant authorisation status is submitted succesfully", Boolean.TRUE));
		
		} catch (Exception e) {
			response="Error While authorising applicant list" + e.getMessage();
			httpstatus=HttpStatus.BAD_REQUEST;
			 status=false;
			LOGGER.error("Error While authorising applicant list" + e.getMessage());
			//return ResponseEntity.badRequest().body(new BorrowerResponse(e.getMessage(), Boolean.FALSE));
		}
		return ResponseEntity.status(httpstatus).body(new BorrowerResponse(response, Boolean.TRUE));
	}

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

	@RequestMapping(value = { "/Dashboard" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<DashboardResponse> dashboard(@RequestBody DashboardRequest dashboardPayload) {
		LOGGER.info("dashboard api is called");
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		//List<Dashboard> list=new ArrayList();
		Dashboard dash=new Dashboard();
		
		try {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(sdf.format(new Date()));

	        System.out.println("today date is"+date);
			int total=appRepo.total_applicant();
			dash.setTotal_applicants(total);
			if(dashboardPayload.getCompany_code().equals("AV"))
			{
			dash.setTotal_verified(appRepo.av_approval());
			dash.setTotal_rejected(appRepo.av_rejection());
			dash.setPending_for_verification(appRepo.av_pending());
			dash.setVerified_today(appRepo.today_av_approval(date));
			}
			if(dashboardPayload.getCompany_code().equals("MK")){
				dash.setTotal_verified(appRepo.mk_approval());
				dash.setTotal_rejected(appRepo.MK_rejection());
				dash.setPending_for_verification(appRepo.MK_pending());
				dash.setVerified_today(appRepo.today_mk_approval(date));
			}
			if(dashboardPayload.getCompany_code().equals("SH")){
				dash.setTotal_verified(appRepo.Sh_approval());
				dash.setTotal_rejected(appRepo.SH_rejection());
				dash.setPending_for_verification(appRepo.Sh_pending());
				dash.setVerified_today(appRepo.today_sh_approval(date));
			}
			
				
			
			response="dashboard details fecthed successfully";		
			status=true;
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error in dashbaord api" + e.getMessage());
			response="Error in dashboard api" + e.getMessage();
			status=false;
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		List<Dashboard> list=new ArrayList<>();
		list.add(dash);
		return ResponseEntity.status(httpstatus).body(new DashboardResponse(list,response,status));
	}
}

