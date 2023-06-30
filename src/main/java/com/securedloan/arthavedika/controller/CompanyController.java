package com.securedloan.arthavedika.controller;

import java.util.List;
import java.util.Random;

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

import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.Add_modifyCompany;
import com.securedloan.arthavedika.payload.Add_modifyUser;
import com.securedloan.arthavedika.payload.CompanyPayload;
import com.securedloan.arthavedika.payload.UserPayload;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.response.GeneralResponse;

@CrossOrigin()
@RestController
@RequestMapping("applicant/borrower/company")
public class CompanyController {
	@Autowired
	private CompanyRepo companyRepo;
	private final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
	@RequestMapping(value = { "/add_modify_company" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> add_modify_user(@RequestBody Add_modifyCompany addmodifyCompanyPayload) {
		LOGGER.info("add/modify api has been called !!! Start Of Method add/modify User");
		
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		
		try {
			Company comp=companyRepo.getcompanyById(addmodifyCompanyPayload.getCompany_id());
			if (comp==null) {
				Company company=new Company();
				company.setCompany_code(addmodifyCompanyPayload.getCompany_code());
				company.setCompanyName(addmodifyCompanyPayload.getCompany_name());
				company.setCompany_address(addmodifyCompanyPayload.getCompany_address());
				company.setAllowed_amount(addmodifyCompanyPayload.getAllowed_amount());
				company.setCurrent_amount(addmodifyCompanyPayload.getAllowed_amount());
				company.setDelete_status("N");
				companyRepo.save(company);
			response="Company is added successfully."	;
			}
			else
			{
				companyRepo.updateCompany(addmodifyCompanyPayload.getCompany_code(),addmodifyCompanyPayload.getCompany_name(),
						addmodifyCompanyPayload.getCompany_address(),addmodifyCompanyPayload.getAllowed_amount(),addmodifyCompanyPayload.getCompany_id());
				
				response="Company modified successfully";
				
			}
			status=true;
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While adding or modifying company" + e.getMessage());
			response="Error While adding or modifying company" + e.getMessage();
			status=false;
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(response,status));
	}
	@RequestMapping(value = { "/delete_company" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> delete_company(@RequestBody CompanyPayload companyPayload) {
		LOGGER.info("Delete user api has been called !!! Start Of Method deleteUser");
		
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		
		try {
				companyRepo.deleteCompany(companyPayload.getCompany_id());		
				response="Company deleted successfully";
				
			
			status=true;
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While deletting company" + e.getMessage());
			response="Error While deleting company" + e.getMessage();
			status=false;
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(response,status));
	}

}
