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

import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.Add_modifyCompany;
import com.securedloan.arthavedika.payload.Add_modifyUser;
import com.securedloan.arthavedika.payload.CompanyPayload;
import com.securedloan.arthavedika.payload.UserPayload;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.response.CompanyEnc;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.GetCompanyByIdResponse;

@CrossOrigin()
@RestController
@RequestMapping("applicant/borrower/company")
public class CompanyController {
	@Autowired
	private CompanyRepo companyRepo;
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
	@RequestMapping(value = { "/get_companyById" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GetCompanyByIdResponse > get_companybyId(@RequestBody CompanyPayload CompanyPayload) {
		LOGGER.info("get company by id api has been called !!! Start Of Method get company by id");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		Company com=new Company();
		CompanyEnc comp=new CompanyEnc();
		try {
			 com=companyRepo.getcompanyById(encdec.decryptnew(CompanyPayload.getCompany_id()));
			
					
				
			if (com==null) {
				response="Company does not exist"	;
			}
			else
			{
				
				if(com.getCompany_id()!=null) {
				comp.setCompany_id(encdec.encryptnew(com.getCompany_id()));}
				if(com.getCompanyName()!=null) {
				comp.setCompany_name(encdec.encryptnew(com.getCompanyName()));
			}
				if(com.getCompany_code()!=null) {
					comp.setCompany_code(encdec.encryptnew(com.getCompany_code()));
				}
				if(com.getCompany_address()!=null) {
					comp.setCompnay_address(encdec.encryptnew(com.getCompany_address()));
				}
				
					String amount=Float.toString(com.getAllowed_amount());
					comp.setAllowed_amount(encdec.encryptnew(amount));
				String currentamount=Float.toString(com.getCurrent_amount());
				comp.setCurrent_amount(encdec.encryptnew(currentamount));
				
				response="Company details returned successfully";
				
			}
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While getting company" + e.getMessage());
			response="Error While getting company" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GetCompanyByIdResponse (comp,encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	@RequestMapping(value = { "/add_modify_company" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> add_modify_user(@RequestBody Add_modifyCompany addmodifyCompanyPayload) {
		LOGGER.info("add/modify api has been called !!! Start Of Method add/modify company");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		
		try {
			Company comp=companyRepo.getcompanyById(encdec.decryptnew(addmodifyCompanyPayload.getCompany_id()));
			float amount=Float.parseFloat(encdec.decryptnew(addmodifyCompanyPayload.getAllowed_amount()));
			if (comp==null) {
				Company company=new Company();
				company.setCompany_code(encdec.decryptnew(addmodifyCompanyPayload.getCompany_code()));
				String company_name=encdec.decryptnew(addmodifyCompanyPayload.getCompany_name());
				company_name=company_name.replace(" ", "_");
				company.setCompanyName(company_name);
				
				company.setCompany_address(encdec.decryptnew(addmodifyCompanyPayload.getCompany_address()));
				company.setAllowed_amount(amount);
				
				company.setCurrent_amount(amount);
				company.setDelete_status("N");
				companyRepo.save(company);
			response="Company is added successfully.";}
			
			else
			{
				String company_name=encdec.decryptnew(addmodifyCompanyPayload.getCompany_name());
				company_name=company_name.replace(" ", "_");
				companyRepo.updateCompany(encdec.decryptnew(addmodifyCompanyPayload.getCompany_code()),company_name,
						encdec.decryptnew(addmodifyCompanyPayload.getCompany_address()),amount,encdec.decryptnew(addmodifyCompanyPayload.getCompany_id()));
				
				response="Company modified successfully";
				
			}
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While adding or modifying company" + e.getMessage());
			response="Error While adding or modifying company" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	@RequestMapping(value = { "/delete_company" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> delete_company(@RequestBody CompanyPayload companyPayload) {
		LOGGER.info("Delete compsny api has been called !!! Start Of Method delete compsny");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		
		try {
				companyRepo.deleteCompany(encdec.decryptnew(companyPayload.getCompany_id()));		
				response="Company deleted successfully";
				
			
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While deletting company" + e.getMessage());
			response="Error While deleting company" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}

}
