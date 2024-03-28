package com.securedloan.arthavedika.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.securedloan.arthavedika.model.Applicant;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.securedloan.arthavedika.response.ApplicantPredictionFileResponse;
import com.securedloan.arthavedika.response.ApplicantPredictionListResponse;
import com.securedloan.arthavedika.response.ApplicatListPrediction;
import java.time.LocalDate;
import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.Add_modifyUser;
import com.securedloan.arthavedika.payload.ApplicantPayload;
import com.securedloan.arthavedika.payload.ApprovedApplicantList;
import com.securedloan.arthavedika.payload.UpdateTruckersDetails;
import com.securedloan.arthavedika.repo.ApplicantRepository;
import com.securedloan.arthavedika.response.ApprovedApplicantResponse;
import com.securedloan.arthavedika.response.GeneralResponse;

@RestController
//@CrossOrigin()
@CrossOrigin(origins = "http://4.236.144.236:4200")
@RequestMapping("/borrower/SidbiApi")
public class SidbiApiController {}
	/*EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	private final Logger LOGGER = LoggerFactory.getLogger(SidbiApiController.class);
	@Autowired
	ApplicantRepository appRepo;
	@RequestMapping(value = { "/add_new_applicant" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> add_new_applicant(@RequestBody UpdateTruckersDetails appPayload) {
		LOGGER.info("add new applicant api has been called !!! Start Of Method add new applicant");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		User users=null;
		try {
			Applicant app=new Applicant();
			vehicle_no,company_name,applicant_firstname,applicant_date_of_birth,age,maritalstatus,nominee_name,nominee_dob,
			nominee_age,nominee_relation,spouse_name,applicant_father_firstname,religion,applicant_qualification,
			applicant_employment_type,applicant_address_line_1,applicant_city_name,applicant_pin,applicant_mobile_no,
			no_of_family_member,no_of_earning_member,house_type,	Ration_Card,medical_insurance,
			current_loan_outstanding_principal,current_loan_outstanding_interest,applicant_income,income_from_other_sources,
			food_expenses,houserent
			,house_renovation_expenses,total_monthly_bill_payment,applicant_expense_monthly,created_by,	createddate                                      
			 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			if(!appPayload.getVehicle_no().isEmpty()) {
				app.setVehicle_no(encdec.decryptnew(appPayload.getVehicle_no()));
			}
			if(!appPayload.getCompany_name().isEmpty()) {
				app.setCompany_name(encdec.decryptnew(appPayload.getCompany_name()));
			}
			if(!appPayload.getApplicant_firstname().isEmpty()) {
				app.setApplicant_firstname(encdec.decryptnew(appPayload.getApplicant_firstname()));
			}
			if(!appPayload.getApplicant_date_of_birth().isEmpty()) {
				String applicant=encdec.decryptnew(appPayload.getApplicant_date_of_birth()) ;
				 app.setApplicant_date_of_birth(sdf1.parse(applicant));
			}
			
			if(!appPayload.getAge().isEmpty()) {
				 app.setAge(Integer.parseInt(encdec.decryptnew(appPayload.getAge())));}
			if(!appPayload.getMaritalstatus().isEmpty()) {
				 app.setMaritalstatus(encdec.decryptnew(appPayload.getMaritalstatus()));}
			
			if(!appPayload.getNominee_name().isEmpty())
				{ app.setNominee_name(encdec.decryptnew(appPayload.getNominee_name()));}
			if(!appPayload.getNominee_dob().isEmpty())
				{String dob=encdec.decryptnew(appPayload.getNominee_dob());
				 app.setNominee_dob(sdf1.parse(dob));
				}
			if(!appPayload.getNominee_age().isEmpty())
			{ app.setNominee_age(Integer.parseInt(encdec.decryptnew(appPayload.getNominee_age())));}				
				if(!appPayload.getNominee_relation().isEmpty() ) {
					app.setNominee_relation(encdec.decryptnew(appPayload.getNominee_relation()));
				}
				if(!appPayload.getSpouse_name().isEmpty())
				{app.setSpouse_name(encdec.decryptnew(appPayload.getSpouse_name()));}
				if(!appPayload.getApplicant_father_firstname().isEmpty())
					app.setApplicant_father_firstname(encdec.decryptnew(appPayload.getApplicant_father_firstname()));
				if(!appPayload.getReligion().isEmpty())
				{app.setReligion(encdec.decryptnew(appPayload.getReligion()));}
			if(!appPayload.getApplicant_qualification().isEmpty()) {
				app.setApplicant_qualification(encdec.decryptnew(appPayload.getApplicant_qualification()));}
			if(!appPayload.getApplicant_employment_type().isEmpty())	{
			app.setApplicant_employment_type(encdec.decryptnew(appPayload.getApplicant_employment_type()));}
			if(!appPayload.getApplicant_address_line_1().isEmpty()) {
			 app.setApplicant_address_line_1(encdec.decryptnew(appPayload.getApplicant_address_line_1()));}
			if(!appPayload.getApplicant_city_name().isEmpty()) {
				 app.setApplicant_city_name(encdec.decryptnew(appPayload.getApplicant_city_name()));}
			if(!appPayload.getApplicant_pin().isEmpty()) {
				 app.setApplicant_PIN(Integer.parseInt(encdec.decryptnew(appPayload.getApplicant_pin())));}
			if(!appPayload.getApplicant_mobile_no().isEmpty()) {
				 app.setApplicant_mobile_no((encdec.decryptnew(appPayload.getApplicant_mobile_no())));}
			if(!appPayload.getNo_of_family_member().isEmpty()) {
				 app.setNo_of_family_member(Integer.parseInt(encdec.decryptnew(appPayload.getNo_of_family_member())));}
			if(!appPayload.getNo_of_earning_member().isEmpty()) {
				app.setNo_of_earning_member(Integer.parseInt(encdec.decryptnew(appPayload.getNo_of_earning_member())));}
			if(!appPayload.getHouse_type().isEmpty()) {
				app.setHouse_type(encdec.decryptnew(appPayload.getHouse_type()));}
			if(!appPayload.getRation_Card().isEmpty()) {	
			app.setRation_card(encdec.decryptnew(appPayload.getRation_Card()));}
			if(!appPayload.getMedical_insurance().isEmpty()) {
			app.setMedical_insurance(encdec.decryptnew(appPayload.getMedical_insurance()));}
			if(!appPayload.getCurrent_loan_outstanding_principal().isEmpty()) {
				 app.setCurrent_loan_outstanding_principal(Float.parseFloat(encdec.decryptnew(appPayload.getCurrent_loan_outstanding_principal())));}
			if(!appPayload.getCurrent_loan_outstanding_Stringerest().isEmpty()) {	
			 app.setCurrent_loan_outstanding_interest(Float.parseFloat(encdec.decryptnew(appPayload.getCurrent_loan_outstanding_Stringerest())));}
			if(!appPayload.getApplicant_income().isEmpty())	{
			app.setApplicant_income((encdec.decryptnew(appPayload.getApplicant_income())));}
			if(!appPayload.getIncome_from_other_sources().isEmpty()) {
				app.setIncome_from_other_sources(Float.parseFloat(encdec.decryptnew(appPayload.getIncome_from_other_sources())));}
			if(!appPayload.getFood_expenses().isEmpty())	{
			app.setFood_expenses(Float.parseFloat(encdec.decryptnew(appPayload.getFood_expenses())));}
			if(!appPayload.getHouserent().isEmpty()) {
				 app.setHouserent((encdec.decryptnew(appPayload.getHouserent())));}
			if(!appPayload.getHouse_renovation_expenses().isEmpty()) {
			 app.setHouse_renovation_expenses(Float.parseFloat(encdec.decryptnew(appPayload.getHouse_renovation_expenses())));}
			if(!appPayload.getTotal_monthly_bill_payment().isEmpty()) {
				 app.setTotal_monthly_bill_payment(Float.parseFloat(encdec.decryptnew(appPayload.getTotal_monthly_bill_payment())));}
			if(!appPayload.getApplicant_expense_monthly().isEmpty()) {
			app.setApplicant_expense_monthly((encdec.decryptnew(appPayload.getApplicant_expense_monthly())));}
			
			if(!appPayload.getCreated_by().isEmpty()) {
				app.setCreated_by(encdec.decryptnew(appPayload.getCreated_by()));
			}
			LocalDate moddt=LocalDate.now();
			app.setDataentdt(moddt);
			appRepo.save(app);
			response="Applicant  is added successfully.";
				System.out.println(response);
				
			
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While adding applicant" + e.getMessage());
			response="Error While adding applicant" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	@RequestMapping(value = { "/GetApplicantPrediction" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.ApplicantPredictionListResponse> GetApplicantPrediction(@RequestBody ApplicantPayload appPayload) {
		LOGGER.info("get  applicant prediction  by id api has been called !!! Start Of Method get applicant by id");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		
		List<ApplicatListPrediction> applicant= new ArrayList<ApplicatListPrediction>();
		
		try {
		 List<Applicant> applicantDetails=appRepo.findByApplicant_id(Long.parseLong(encdec.decryptnew(appPayload.getApplicant_id())));
		System.out.println("details retrived successfully");
			if (applicantDetails==null) {
				
			response=" applicant details not available"	;
			}
			else
			{ 
				System.out.println("encyption of list");
				int i=0;
				for(i=0;i<applicantDetails.size();i++) {
					System.out.println("for loop");
						ApplicatListPrediction app= new ApplicatListPrediction();
						
							 SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
								if(!((Applicant) applicantDetails).getVehicle_no().isEmpty()) {
									app.setVehicle_no(encdec.encryptnew(((Applicant) applicantDetails).getVehicle_no()));
								}
								if(!((Applicant) applicantDetails).getCompany_name().isEmpty()) {
									app.setCompany_name(encdec.encryptnew(((Applicant) applicantDetails).getCompany_name()));
								}
								if(!((Applicant) applicantDetails).getApplicant_firstname().isEmpty()) {
									app.setApplicant_firstname(encdec.encryptnew(((Applicant) applicantDetails).getApplicant_firstname()));
								}
								if(!((CharSequence) ((Applicant) applicantDetails).getApplicant_date_of_birth()).isEmpty()) {
									String dob=sdf1.format(((Applicant) applicantDetails).getApplicant_date_of_birth()) ;
									 app.setApplicant_Date_of_birth(encdec.encryptnew(dob));
								}
								
								if(!(((Applicant) applicantDetails).getAge()==0)) {
									String age= (String.valueOf(applicantDetails.get(i).getAge()));
									 app.setAge(encdec.encryptnew(age));}
								if(!applicantDetails.get(i).getMaritalstatus().isEmpty()) {
									 app.setMaritalstatus(encdec.encryptnew(applicantDetails.get(i).getMaritalstatus()));}
								
								if(!applicantDetails.get(i).getNominee_name().isEmpty())
									{ app.setNominee_name(encdec.encryptnew(applicantDetails.get(i).getNominee_name()));}
								if(applicantDetails.get(i).getNominee_dob() != null)
									{String dob=(encdec.encryptnew(sdf1.format(applicantDetails.get(i).getNominee_dob())));
									 app.setNominee_dob((dob));
									}
								if(!(applicantDetails.get(i).getNominee_age()==0))
								{ app.setNominee_age(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getNominee_age())));}				
									if(!applicantDetails.get(i).getNominee_relation().isEmpty() ) {
										app.setNominee_relation(encdec.encryptnew(applicantDetails.get(i).getNominee_relation()));
									}
									if(!applicantDetails.get(i).getSpouse_name().isEmpty())
									{app.setSpouse_name(encdec.encryptnew(applicantDetails.get(i).getSpouse_name()));}
									if(!applicantDetails.get(i).getApplicant_father_firstname().isEmpty())
										app.setApplicant_father_firstname(encdec.encryptnew(applicantDetails.get(i).getApplicant_father_firstname()));
									if(!applicantDetails.get(i).getReligion().isEmpty())
									{app.setReligion(encdec.encryptnew(applicantDetails.get(i).getReligion()));}
								if(!applicantDetails.get(i).getApplicant_qualification().isEmpty()) {
									app.setApplicant_qualification(encdec.encryptnew(applicantDetails.get(i).getApplicant_qualification()));}
								if(!applicantDetails.get(i).getApplicant_employment_type().isEmpty())	{
								app.setApplicant_employment_type(encdec.encryptnew(applicantDetails.get(i).getApplicant_employment_type()));}
								if(!applicantDetails.get(i).getApplicant_address_line_1().isEmpty()) {
								 app.setApplicant_address_line_1(encdec.encryptnew(applicantDetails.get(i).getApplicant_address_line_1()));}
								if(!applicantDetails.get(i).getApplicant_city_name().isEmpty()) {
									 app.setApplicant_city_name(encdec.encryptnew(applicantDetails.get(i).getApplicant_city_name()));}
								if(!(applicantDetails.get(i).getApplicant_PIN()==0)) {
									 app.setApplicant_pin(
											 (encdec.encryptnew(String.valueOf(applicantDetails.get(i).getApplicant_PIN()))));}
								if(!applicantDetails.get(i).getApplicant_mobile_no().isEmpty()) {
									 app.setApplicant_mobile_no((encdec.encryptnew(applicantDetails.get(i).getApplicant_mobile_no())));}
								if(!(applicantDetails.get(i).getNo_of_family_member()==0)) {
									 app.setNo_of_family_member(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getNo_of_family_member())));}
								if(!(applicantDetails.get(i).getNo_of_earning_member()==0) ){
									app.setNo_of_earning_member(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getNo_of_earning_member())));}
								if(!applicantDetails.get(i).getHouse_type().isEmpty()) {
									app.setHouse_type(encdec.encryptnew(applicantDetails.get(i).getHouse_type()));}
								if(!applicantDetails.get(i).getRation_card().isEmpty()) {	
								app.setRation_Card(encdec.encryptnew(applicantDetails.get(i).getRation_card()));}
								if(!applicantDetails.get(i).getMedical_insurance().isEmpty()) {
								app.setMedical_insurance(encdec.encryptnew(applicantDetails.get(i).getMedical_insurance()));}
								if(!(applicantDetails.get(i).getCurrent_loan_outstanding_principal()==0)) {
									 app.setCurrent_loan_outstanding_principal(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getCurrent_loan_outstanding_principal())));}
								if(!(applicantDetails.get(i).getCurrent_loan_outstanding_interest()==0)) {	
								 app.setCurrent_loan_outstanding_Stringerest(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getCurrent_loan_outstanding_interest())));}
								if(!applicantDetails.get(i).getApplicant_income().isEmpty())	{
								app.setApplicant_income((encdec.encryptnew(applicantDetails.get(i).getApplicant_income())));}
								if(!(applicantDetails.get(i).getIncome_from_other_sources()==0)) {
									app.setIncome_from_other_sources(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getIncome_from_other_sources())));}
								if(!(applicantDetails.get(i).getFood_expenses()==0))	{
								app.setFood_expenses(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getFood_expenses())));}
								if(!applicantDetails.get(i).getHouserent().isEmpty()) {
									 app.setHouserent((encdec.encryptnew(applicantDetails.get(i).getHouserent())));}
								if(!(applicantDetails.get(i).getHouse_renovation_expenses()==0)) {
								 app.setHouse_renovation_expenses(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getHouse_renovation_expenses())));}
								if(!(applicantDetails.get(i).getTotal_monthly_bill_payment()==0)) {
									 app.setTotal_monthly_bill_payment(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getTotal_monthly_bill_payment())));}
								if(!applicantDetails.get(i).getApplicant_expense_monthly().isEmpty()) {
								app.setApplicant_expense_monthly((encdec.encryptnew(applicantDetails.get(i).getApplicant_expense_monthly())));}
								applicant.add(i, app);
								
				}
				//System.out.println("dummy2");
				response=" Applicant detail is retrieved successfully";
				
			}
			System.out.println("dummy3");
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While retreiving user" + e.getMessage());
			response="Error While retreiving  user" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new ApplicantPredictionListResponse
				(applicant,(response),(status)));
	}
	@RequestMapping(value = { "/transfer_applicant_db" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> transfer_applicant_db (@RequestBody UpdateTruckersDetails appPayload) {
		LOGGER.info("add new applicant api has been called !!! Start Of Method add new applicant");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
	
		try {
			
			response="Applicant data transfered successfully.";
				System.out.println(response);
				
			
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While transfering applicant data" + e.getMessage());
			response="Error While transfering applicant data" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	} 
	@PostMapping("/downloadApplicantResposne")
	public ResponseEntity<ApplicantPredictionFileResponse> uploadFile(@RequestParam("file") MultipartFile file) {
	  String response = "";
	  String status=null;
	  HttpStatus httpstatus=null;
	  File Applicantreponse=null;
	  try {
		  response="File generated successfully";
		  httpstatus=HttpStatus.OK;
		  status="true";
	  }
		  catch (Exception e) {
				LOGGER.error("Error While downloading applicant  reponse data" + e.getMessage());
				response="Error While downloading applicant response data" + e.getMessage();
				status="false";
				httpstatus=HttpStatus.BAD_REQUEST;
			}
			return ResponseEntity.status(httpstatus).body(new ApplicantPredictionFileResponse(Applicantreponse,encdec.encryptnew(response),encdec.encryptnew(status)));
		} 
}
*/