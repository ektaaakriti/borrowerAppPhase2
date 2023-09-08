package com.securedloan.arthavedika.controller;

import java.sql.PreparedStatement;
import com.securedloan.arthavedika.model.Applicant_approval_details;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import com.securedloan.arthavedika.payload.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.DocEkyc;
import com.securedloan.arthavedika.model.EKYC;
import com.securedloan.arthavedika.model.FileDB;
import com.securedloan.arthavedika.model.GroupData;
import com.securedloan.arthavedika.model.PsyAns;
import com.securedloan.arthavedika.model.PsyQstn;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.GetApplicantPayload;
import com.securedloan.arthavedika.payload.UserPayload;
import com.securedloan.arthavedika.repo.ApplicantApprovalDetailsRepo;
import com.securedloan.arthavedika.repo.ApplicantRepository;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.repo.PsyAnsRepo;
import com.securedloan.arthavedika.response.ApplicantInfo;
import com.securedloan.arthavedika.response.ApplicantInfo1;
import com.securedloan.arthavedika.response.ApplicantInfos;
import com.securedloan.arthavedika.response.ApprovedApplicantResponse;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.GroupResponse;
//import com.securedloan.arthavedika.response.Prediction;
import com.securedloan.arthavedika.response.Response;
import com.securedloan.arthavedika.response.ResponseMessage;
import com.securedloan.arthavedika.service.ApplicantService;
import com.securedloan.arthavedika.service.EkycFileStorageService;
import com.securedloan.arthavedika.service.FileStorageService;
import com.securedloan.arthavedika.service.GroupDataService;
//import com.securedloan.arthavedika.service.PredictService;

@CrossOrigin()
@RestController
@RequestMapping("applicant")
public class ApplicantController {
	private final Logger LOGGER = LoggerFactory.getLogger(ApplicantController.class);
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	@Autowired
	ApplicantApprovalDetailsRepo approvalRepo;
	@Autowired
	ApplicantRepository appRepo;
	@Autowired
	CompanyRepo companyRepo;
	@PersistenceContext
	private EntityManager entitymanager;
	@Autowired
	private EkycFileStorageService storageService;
	@Autowired
	private FileStorageService fileStorageService;
	@Autowired
	private ApplicantService applicantService;
	@Autowired
	private PsyAnsRepo psyRepo;
	@Autowired
	private GroupDataService grpDataService;
//	@Autowired
//	private PredictService pdService;

	@Value("${mandatory}")
	private String mandatoryField;
	@Value("${alreadyRegister}")
	private String Alreadyregistered;
	@Value("${registerSuccess}")
	private String registerSuccess;
	@Value("${success}")
	private String Success;
	@RequestMapping(value = { "/getapplicantList/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ApplicantInfo> getApplicant(@RequestParam("applicant_id") Applicant newApplicant) {
		LOGGER.info("getApplicant api has been called !!! Start Of Method getApplicant");
		List<Applicant> applicant=null;
		List<FileDB> document=null;
		Optional<Applicant_approval_details> approval_details=null;
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		try {
			LOGGER.info("getApplicant api has been called !!! Start Of Method getApplicant");
			 document=fileStorageService.documentById(newApplicant.getApplicant_id());
			applicant = applicantService.findById(newApplicant.getApplicant_id());
			approval_details=approvalRepo.findById(newApplicant.getApplicant_id());
			
			 httpstatus=HttpStatus.OK;
			 status=true;
			if (applicant != null) {
				 response="Retrive Success";
				
				//return ResponseEntity.status(HttpStatus.OK)
				//		.body(new ApplicantInfo("Retrive Success", Boolean.TRUE, applicant,document));
			} else {
				response="No data with such applicant id";
				//return ResponseEntity.status(HttpStatus.OK)
				//		.body(new ApplicantInfo("No data with such applicant id", Boolean.FALSE, applicant,document));
			}
		} catch (Exception e) {
			response="Error While retrive the Applicant"+e.getMessage();
			LOGGER.error(response);
			httpstatus=HttpStatus.BAD_REQUEST;
			status=false;
			//return ResponseEntity.status(HttpStatus.OK).body(new ApplicantInfo(e.getMessage(), Boolean.FALSE, applicant,document));

		}
		return ResponseEntity.status(httpstatus)
						.body(new ApplicantInfo(response, status, applicant,document,approval_details));

	}
	@RequestMapping(value = { "/getapplicant/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ApplicantInfos> getApplicantId(@RequestParam("applicant_id") Applicant newApplicant) {
		LOGGER.info("getApplicant api has been called !!! Start Of Method getApplicant");
		
		try {
			Applicant applicants = applicantService.findByIds(newApplicant.getApplicant_id());
			if (applicants != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApplicantInfos("Retrive Success", Boolean.TRUE, applicants));
			} else {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ApplicantInfos("No data with such applicant id", Boolean.FALSE, applicants));
			}
		} catch (Exception e) {
			LOGGER.error("Error While retrive the Applicant" + e.getMessage());
			return ResponseEntity.badRequest().body(new ApplicantInfos(e.getMessage(), Boolean.FALSE,new Applicant()));

		}}
		@RequestMapping(value = { "/GetApprovedApplicant" }, method = RequestMethod.POST, produces = {
				MediaType.APPLICATION_JSON_VALUE })
		@ResponseStatus(value = HttpStatus.OK)
		public ResponseEntity<com.securedloan.arthavedika.response.ApprovedApplicantResponse> GetApprovedApplicant() {
			LOGGER.info("get  user  by id api has been called !!! Start Of Method get  user by id");
			
			HttpStatus httpstatus=null;
			String response="";
			String status=null;
			
			List<ApprovedApplicantList> applicant= new ArrayList<ApprovedApplicantList>();
			
			try {
			 List<Applicant> app=appRepo.AllApprovedAppplicat();
			System.out.println("details retrived successfully");
				if (app==null) {
					
				response="Approved applicant details not available"	;
				}
				else
				{
					System.out.println("encyption of list");
					int i=0;
					for(i=0;i<app.size();i++) {
						System.out.println("for loop");
							ApprovedApplicantList userss= new ApprovedApplicantList();
							if(app.get(i).getApplicant_firstname()!=null) {
								System.out.println("dummy21");
						//userss.setApplicant_firstname(encdec.encryptnew( app.get(i).getApplicant_firstname()));
								userss.setApplicant_firstname(app.get(i).getApplicant_firstname());
						System.out.println("dummy22");}
							if(app.get(i).getApplicant_lastname()!=null) {
								System.out.println("dummy25");	
						//userss.setApplicant_lastname(encdec.encryptnew( app.get(i).getApplicant_lastname()));
								userss.setApplicant_lastname(app.get(i).getApplicant_lastname());
						System.out.println("dummy26");}
							if( app.get(i).getApplicant_email_id()!=null) {
								System.out.println("dummy27");
							
								System.out.println("dummy28");
							
						//userss.setApplicant_email_id(encdec.encryptnew(app.get(i).getApplicant_email_id()));
								userss.setApplicant_email_id(app.get(i).getApplicant_email_id());
						System.out.println("dummy29");}
							if(app.get(i).getApplicant_mobile_no()!=null) {
								System.out.println("dummy210");	
						//userss.setApplicant_mobile_no(encdec.encryptnew( app.get(i).getApplicant_mobile_no()));
								userss.setApplicant_mobile_no(app.get(i).getApplicant_mobile_no());
						System.out.println("dummy211");}
							String applicant_id=String.valueOf(app.get(i).getApplicant_id());
							System.out.println("dummy212");
							//userss.setApplicant_id(encdec.encryptnew(applicant_id));
							userss.setApplicant_id(applicant_id);
							System.out.println("dummy213");
							System.out.println("dummy24"+userss.getApplicant_email_id());
							applicant.add(i, userss);
					//applicant.add(i, userss);
					System.out.println("dummy1");
					//i++;
					}
					System.out.println("dummy2");
					response="Approved Applicant detail is retrieved successfully";
					
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
			return ResponseEntity.status(httpstatus).body(new ApprovedApplicantResponse
					(applicant,(response),(status)));
		}

	
/*
	@RequestMapping(value = { "/register/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> registerApplicant(@RequestBody Applicant applicantInput) {
		LOGGER.info("Register api has been called with post !!! Start Of Method registerApplicant");
		System.out.println("monthlin income is");
		try {

			if (applicantInput.getUserIdStr() == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response(mandatoryField, Boolean.FALSE, new Applicant()));
			}
			Applicant applicants = applicantService.findByApplicant_mobile_no(applicantInput.getApplicant_mobile_no());
            System.out.println("applicants is"+applicants); 
			if (applicants == null) {
				User user = new User();
				user.setUser_id(applicantInput.getUserIdStr());

				applicantInput.setUser(user);
				applicantInput.setDataentdt(LocalDate.now());
				applicantInput.setDatamoddt(null);
				//applicantInput.(false);
				System.out.println("applicants gng to save"); 
				System.out.println("applicants father name is"+applicantInput.getApplicant_father_firstname());
				System.out.println("applicants mother name is"+applicantInput.getMother_name());
				System.out.println("applicants spouse name is"+applicantInput.getSpouse_name());
				System.out.println("applicants aadhar nois"+applicantInput.getAadhar_no());
				System.out.println("applicants pan is"+applicantInput.getPan_no());
				System.out.println("applicants account is"+applicantInput.getAccount_no());
				String company_name = null;
				List<Company>company=companyRepo.company_name();
				for(int i=0;i<company.size();i++) {
					if (applicantInput.getCompany_code().equals(company.get(i).getCompany_code())) {
						System.out.println("company name is"+company.get(i).getCompanyName());
					 company_name=(company.get(i).getCompanyName());
					}}
				applicantInput.setCompany_name(company_name);
				if(applicantInput.getCompany_code().equals("MK")) {
					applicantInput.setAV_approval("Y");
					applicantInput.setAuthorisation_status(1);
				}
				Applicant applicant = applicantService.saveApplicant(applicantInput);
				LOGGER.info("End Of Method registerApplicant");
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response(registerSuccess, Boolean.TRUE, applicant));
			} else if (applicants != null) {
				if (applicants.getApplicant_id() == applicantInput.getApplicant_id()) {
					List<PsyQstn> res = new ArrayList<>();
					applicantInput.setDataentdt(LocalDate.now());
					System.out.println("applicants aadhar nois"+applicantInput.getAadhar_no());
					System.out.println("applicants pan is"+applicantInput.getPan_no());
					System.out.println("applicants account is"+applicantInput.getAccount_no());
					List<Company>company=companyRepo.company_name();
					String company_name="";
					for(int i=0;i<company.size();i++) {
						if (applicantInput.getCompany_code().equals(company.get(i).getCompany_code())) {
							System.out.println("company name is"+company.get(i).getCompanyName());
						 company_name=(company.get(i).getCompanyName());
						}}
					applicantInput.setCompany_name(company_name);
					if(applicantInput.getCompany_code().equals("MK")) {
						applicantInput.setAV_approval("Y");
						applicantInput.setAuthorisation_status(1);
					}
					Applicant applicant = applicantService.saveApplicant(applicantInput);
					System.out.println("applicantInput.isPsycho_page()"+applicantInput.isPsycho_page());
					if (applicantInput.isPsycho_page() == Boolean.TRUE )
					{
						List<Object[]> doc = (List<Object[]>) entitymanager.createNativeQuery(
								"SELECT q.qstn,q.optn_a,q.optn_b,q.optn_c,q.optn_d,q.optn_e,q.id from kosh_db_v1.psycho_qstn q ORDER BY RAND() limit 29")
								.getResultList();
						System.out.println("applicantInput.doc_size"+doc.size());
						if (doc.size() != 0) {
							for (Object d : doc) {
								
								PsyQstn pq = new PsyQstn();
								Object[] obj = (Object[]) d;
								System.out.println("question is 0 "+obj[0] +"  ==1== "+obj[1]+"==2=="+obj[2]+"==3=="+obj[3]);
								pq.setQstn((String) obj[0]);
								pq.setOpt_a((String) obj[1]);
								pq.setOpt_b((String) obj[2]);
								pq.setOpt_c((String) obj[3]);
								pq.setOpt_d((String) obj[4]);
								pq.setOpt_e((String) obj[5]);
								pq.setId((int) obj[6]);
								res.add(pq);
							}
							
							System.out.println("pg is"+res.get(0).getOpt_a());        
						}
					}
					else if(applicantInput.isPsycho_page() == Boolean.TRUE ) {
						List<Object[]> doc = (List<Object[]>) entitymanager.createNativeQuery(
								"SELECT q.qstn,q.optn_a,q.optn_b,q.optn_c,q.optn_d,q.optn_e,q.id from kosh_db_v1.psycho_qstn_hnd q ORDER BY RAND() limit 29")
								.getResultList();

						if (doc.size() != 0) {
							for (Object d : doc) {
								PsyQstn pq = new PsyQstn();
								Object[] obj = (Object[]) d;
								pq.setQstn((String) obj[0]);
								pq.setOpt_a((String) obj[1]);
								pq.setOpt_b((String) obj[2]);
								pq.setOpt_c((String) obj[3]);
								pq.setOpt_d((String) obj[4]);
								pq.setOpt_e((String) obj[5]);
								pq.setId((int) obj[6]);
								res.add(pq);
							}

						}
					}
					if (applicantInput.getPsyAns().size() > 0) {
						String psyAnsQuery = null;
						for (PsyAns eachAns : applicantInput.getPsyAns()) {
							eachAns.setApplicant_id(applicantInput.getApplicant_id());
						}
					
						company=companyRepo.company_name();
						for(int i=0;i<company.size();i++) {
							if (applicantInput.getCompany_code().equals(company.get(i).getCompany_code())) {
								System.out.println("company name is"+company.get(i).getCompanyName());
							 company_name=(company.get(i).getCompanyName());
							}}
						applicantInput.setCompany_name(company_name);
						if(applicantInput.getCompany_code().equals("MK")) {
							applicantInput.setAV_approval("Y");
							applicantInput.setAuthorisation_status(1);
						}
						psyRepo.saveAll(applicantInput.getPsyAns());

					}
					

					

					LOGGER.info("End Of Method registerApplicant");
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Response("Applicant Registered Successfully", Boolean.TRUE, res,applicant));
				}
			}
			LOGGER.info("End Of Method registerApplicant");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Already Registered", Boolean.FALSE, new Applicant()));

		} catch (Exception e) {
			LOGGER.error("Error While Registering the Applicant" + e.getMessage());
			System.out.println("in error"+e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new Applicant()));
		}
	}*/

	//commented  by rajeev to make similar as kosh
//	public ResponseEntity<Response> registerApplicant(@RequestBody Applicant applicantInput) {
//		LOGGER.info("Register api has been called !!! Start Of Method registerApplicant");
//		try {
//
//			if (applicantInput.getUserIdStr() == null) {
//				return ResponseEntity.status(HttpStatus.OK)
//						.body(new Response(mandatoryField, Boolean.FALSE, new Applicant()));
//			}
//			Applicant applicants = applicantService.findByApplicant_mobile_no(applicantInput.getApplicant_mobile_no());
//
//			if (applicants == null) {
//				User user = new User();
//				user.setUser_id(applicantInput.getUserIdStr());
//
//				applicantInput.setUser(user);
//				applicantInput.setDataentdt(LocalDate.now());
//				applicantInput.setDatamoddt(null);
//
//				Applicant applicant = applicantService.saveApplicant(applicantInput);
//				LOGGER.info("End Of Method registerApplicant");
//				return ResponseEntity.status(HttpStatus.OK)
//						.body(new Response(registerSuccess, Boolean.TRUE, applicant));
//			} else if (applicants != null) {
//				if (applicants.getApplicant_id() == applicantInput.getApplicant_id()) {
//					List<PsyQstn> res = new ArrayList<>();
//					applicantInput.setDataentdt(LocalDate.now());
//					Applicant applicant = applicantService.saveApplicant(applicantInput);
//					if (applicantInput.isPsycho_page() == Boolean.TRUE) {
//						List<Object[]> doc = (List<Object[]>) entitymanager.createNativeQuery(
//								"SELECT q.qstn,q.optn_a,q.optn_b,q.id from kosh_db_v1.psycho_qstn q ORDER BY RAND() limit 29")
//								.getResultList();
//
//						if (doc.size() != 0) {
//							for (Object d : doc) {
//								PsyQstn pq = new PsyQstn();
//								Object[] obj = (Object[]) d;
//								pq.setQstn((String) obj[0]);
//								pq.setOpt_a((String) obj[1]);
//								pq.setOpt_b((String) obj[2]);
//								pq.setId((int) obj[3]);
//								res.add(pq);
//							}
//
//						}
//					}
//					if (applicantInput.getPsyAns().size() > 0) {
//						String psyAnsQuery = null;
//						for (PsyAns eachAns : applicantInput.getPsyAns()) {
//							eachAns.setApplicant_id(applicantInput.getApplicant_id());
//						}
//						psyRepo.saveAll(applicantInput.getPsyAns());
//
//					}
//
//					//System.out.println(res);
//
//					LOGGER.info("End Of Method registerApplicant");
//					return ResponseEntity.status(HttpStatus.OK)
//							.body(new Response("Applicant Registered Successfully", Boolean.TRUE, res, applicant));
//				}
//			}
//			LOGGER.info("End Of Method registerApplicant");
//			return ResponseEntity.status(HttpStatus.OK)
//					.body(new Response("Already Registered", Boolean.FALSE, new Applicant()));
//
//		} catch (Exception e) {
//			LOGGER.error("Error While Registering the Applicant" + e.getMessage());
//			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new Applicant()));
//		}
//	}
//comeent for encdec
/*	@RequestMapping(value = { "/register/v1" }, method = RequestMethod.PUT, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> updateApplicant(@RequestBody Applicant updateApplicant) {
		LOGGER.info("Register api has been called with put method !!! Start Of Method registerApplicant");
		try {
			Applicant applicants = applicantService.findByIds(updateApplicant.getApplicant_id());
			if (applicants != null) {
				updateApplicant.setDatamoddt(LocalDate.now());
				String company_name = null;
				List<Company>company=companyRepo.company_name();
				for(int i=0;i<company.size();i++) {
					if (updateApplicant.getCompany_code().equals(company.get(i).getCompany_code())) {
						System.out.println("company name is"+company.get(i).getCompanyName());
					 company_name=(company.get(i).getCompanyName());
					}}
				updateApplicant.setCompany_name(company_name);
				if(updateApplicant.getCompany_code().equals("MK")) {
					updateApplicant.setAV_approval("Y");
					updateApplicant.setAuthorisation_status(1);
				}
				Applicant applicant = applicantService.saveApplicant(updateApplicant);
				LOGGER.info("End Of Method updateApplicant");
				return ResponseEntity.status(HttpStatus.OK).body(new Response("Data Saved", Boolean.TRUE, applicant));
			}
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("UNABLE TO UPDATE", Boolean.FALSE, new Applicant()));
		} catch (Exception e) {
			LOGGER.error("Error While Updating the Applicant" + e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new Applicant()));

		}
	}

	@RequestMapping(value = { "/getapplicantList1/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ApplicantInfo1> getApplicant1(@RequestParam("applicant_id") Applicant newApplicant) {
		LOGGER.info("getApplicant api has been called !!! Start Of Method getApplicant");
		Applicant applicant=null;
		List<FileDB> document=null;
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		try {
			 document=fileStorageService.documentById(newApplicant.getApplicant_id());
			applicant = applicantService.findByIds(newApplicant.getApplicant_id());
			 
			 status=true;
			if (applicant != null) {
				 response="Retrive Success";
				
				//return ResponseEntity.status(HttpStatus.OK)
				//		.body(new ApplicantInfo("Retrive Success", Boolean.TRUE, applicant,document));
			} else {
				response="No data with such applicant id";
				//return ResponseEntity.status(HttpStatus.OK)
				//		.body(new ApplicantInfo("No data with such applicant id", Boolean.FALSE, applicant,document));
			}
			httpstatus=HttpStatus.OK;
		} catch (Exception e) {
			response="Error While retrive the Applicant"+e.getMessage();
			LOGGER.error(response);
			httpstatus=HttpStatus.BAD_REQUEST;
			status=false;
			//return ResponseEntity.status(HttpStatus.OK).body(new ApplicantInfo(e.getMessage(), Boolean.FALSE, applicant,document));

		}
		return ResponseEntity.status(httpstatus)
						.body(new ApplicantInfo1(response, status, applicant,document));

	}

	//comment for encdec
	/*
	@RequestMapping(value = { "/psychometric/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> psychometricQstn(@RequestBody Applicant applicantInput) {
		LOGGER.info("Psychometric api has been called !!! Start Of Method registerApplicant");
		try {
			Applicant applicants = applicantService.findByIds(applicantInput.getApplicant_id());
			System.out.println("applicants value"+applicants);
			System.out.println("is having cell value"+applicantInput.isIs_having_cell());
			 if (applicants != null) {
//				if (applicants.getApplicant_id() == applicantInput.getApplicant_id()) {
					List<PsyQstn> res = new ArrayList<>();
//					Applicant applicant = applicantService.saveApplicant(applicantInput);
					System.out.println("applicants.is subitted"+applicantInput.isSubmited());
						if ( applicantInput.isSubmited() == Boolean.FALSE) {
							List<Object[]> doc = (List<Object[]>) entitymanager.createNativeQuery(
									"SELECT q.qstn,q.optn_a,q.optn_b,q.optn_c,q.optn_d,q.optn_e,q.id from kosh_db_v1.psycho_qstn q ORDER BY RAND() limit 29")
									.getResultList();

							if (doc.size() != 0) {
								for (Object d : doc) {
									PsyQstn pq = new PsyQstn();
									Object[] obj = (Object[]) d;
									pq.setQstn((String) obj[0]);
									pq.setOpt_a((String) obj[1]);
									pq.setOpt_b((String) obj[2]);
									pq.setOpt_c((String) obj[3]);
									pq.setOpt_d((String) obj[4]);
									pq.setOpt_e((String) obj[5]);
									pq.setId((int) obj[6]);;
									res.add(pq);
								}

							}
						}
						else if(applicantInput.isSubmited() ==Boolean.FALSE ) {
							List<Object[]> doc = (List<Object[]>) entitymanager.createNativeQuery(
									"SELECT q.qstn,q.optn_a,q.optn_b,q.id from kosh_db_v1.psycho_qstn_hnd q ORDER BY RAND() limit 29")
									.getResultList();

							if (doc.size() != 0) {
								for (Object d : doc) {
									PsyQstn pq = new PsyQstn();
									Object[] obj = (Object[]) d;
									pq.setQstn((String) obj[0]);
									pq.setOpt_a((String) obj[1]);
									pq.setOpt_b((String) obj[2]);
									pq.setId((int) obj[3]);
									res.add(pq);
								}

							}
						}
						else {
							LOGGER.info("End Of Method psychometric");
							return ResponseEntity.status(HttpStatus.OK)
									.body(new Response("Psychometric Answer already Submitted", Boolean.FALSE, res,null));
						}
						if (applicantInput.getPsyAns().size() > 0) {
							String psyAnsQuery = null;
							for (PsyAns eachAns : applicantInput.getPsyAns()) {
								eachAns.setApplicant_id(applicantInput.getApplicant_id());
							}
							psyRepo.saveAll(applicantInput.getPsyAns());

						}
						LOGGER.info("End Of Method psychometric");
						return ResponseEntity.status(HttpStatus.OK)
								.body(new Response("Response saved Successfully", Boolean.TRUE, res,null));
					}
					LOGGER.info("End Of Method registerApplicant");
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Response("Enter a valid applicant Id", Boolean.FALSE, null,new Applicant()));
			 
		}
	catch(Exception e) {

		LOGGER.error("Error While Registering the Applicant" + e.getMessage());
		return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new Applicant()));
		
	}
	}

	@RequestMapping(value = { "/eKYC/v1" }, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> eKYC(@RequestParam("applicant_id") Applicant newApplicant) {
		LOGGER.info("Ekyc api has been called !!! Start Of Method eKYC");

		List<EKYC> response = new ArrayList<>();
		try {
			List<Object[]> docs = entitymanager.createNativeQuery(
					"SELECT f.data,f.doc_name,f.document,a.applicant_date_of_BIRTH as dob,a.APPLICANT_ID,a.APPLICANT_EMAIL_ID as email,a.APPLICANT_MOBILE_NO as mobile,a.APPLICANT_FIRSTNAME as firstname,a.APPLICANT_FATHER_FIRSTNAME as fathersfirstname,a.GENDER, a.APPLICANT_FATHER_MIDDLE_NAME as fathersmiddlename,a.APPLICANT_FATHER_LASTNAME as fatherslastname,a.APPLICANT_MIDDLE_NAME as middlename,a.APPLICANT_LASTNAME as lastname  from  kosh_db_v1.applicant_table a join kosh_db_v1.files f on a.applicant_id = f.applicant_id where a.applicant_id="
							+ newApplicant.getApplicant_id())
					.getResultList();
			if (docs.size() != 0) {
				for (Object d : docs) {
					EKYC ekyc = new EKYC();
					Object[] obj = (Object[]) d;
					String base64Image = Base64.getEncoder().encodeToString((byte[]) obj[0]);
					ekyc.setPhoto("data:image/jpeg;base64," + base64Image);
					ekyc.setPhotos((byte[]) obj[0]);
					ekyc.setDocName((String) obj[1]);
					ekyc.setDoc((String) obj[2]);
					ekyc.setDob((Date) obj[3]);

					long app_id = Long.parseLong(obj[4].toString());
					ekyc.setApplicant_id(app_id);
					// ekyc.setApplicant_id((long) obj[3]);
					ekyc.setEmail((String) obj[5]);
					ekyc.setMobile((String) obj[6]);
					ekyc.setApplicant_name((String) obj[7]);
					ekyc.setFathersName((String) obj[8]);
					ekyc.setGender((String) obj[9]);
					ekyc.setFathersMiddleName((String) obj[10]);
					ekyc.setFathersLastName((String) obj[11]);
					ekyc.setApplicant_middle_name((String) obj[12]);
					ekyc.setApplicant_lastname((String) obj[13]);
					response.add(ekyc);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error While fetching :" + e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new ArrayList<>()));
		}
		// return response;
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Success, Boolean.TRUE, response));
	}

	// @PostMapping("/upload/ekyc/v1")
	@RequestMapping(value = { "/upload/ekyc/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("applicant_id") Applicant applicant) {
		LOGGER.info("Upload ekyc api has been called !!! Start Of Method uploadFile");
		String message = "";
		try {
			String company_name = null;
			List<Company>company=companyRepo.company_name();
			for(int i=0;i<company.size();i++) {
				if (applicant.getCompany_code().equals(company.get(i).getCompany_code())) {
					System.out.println("company name is"+company.get(i).getCompanyName());
				 company_name=(company.get(i).getCompanyName());
				}}
			applicant.setCompany_name(company_name);
			if(applicant.getCompany_code().equals("MK")) {
				applicant.setAV_approval("Y");
				applicant.setAuthorisation_status(1);
			}
			storageService.store(file, applicant);
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			LOGGER.info("End Of Method uploadFile");
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			LOGGER.error("Error While Uploading the File" + e.getMessage());
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@RequestMapping(value = { "/eKYC/retrieve/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> eKYCRetrieve(@RequestParam("applicant_id") Applicant newApplicant) {
		LOGGER.info("Retrieve ekyc api has been called !!! Start Of Method EkycRetrieve");
		Boolean Status = false;
		List<DocEkyc> DocResponse = new ArrayList<>();
		try {
			List<Object[]> docs = entitymanager.createNativeQuery(
					"SELECT f.data,f.applicant_id from kosh_db_v1.ekycfile f where f.applicant_id= "
							+ newApplicant.getApplicant_id())
					.getResultList();

			List<String> images = new ArrayList<>();

			if (docs.size() != 0) {
				System.out.println(docs.size());
				for (Object d : docs) {
					DocEkyc ekyc = new DocEkyc();
					Object[] obj = (Object[]) d;

					String base64Image = Base64.getEncoder().encodeToString((byte[]) obj[0]);

					ekyc.setPhotoString("data:image/jpeg;base64," + base64Image);
					images.add("data:image/jpeg;base64," + base64Image);
					long a_id = Long.parseLong(obj[1].toString());
					ekyc.setApplicant_id(a_id);
					DocResponse.add(ekyc);
					LOGGER.info("End Of Method EkycRetrieve");
				}
				Status = applicantService.authImage(images);
				System.out.println(images);

			}
		} catch (Exception e) {
			LOGGER.error("Error While Retrieve" + e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, Status));
		}
		return ResponseEntity.status(HttpStatus.OK).body(new Response(Success, Boolean.TRUE, Status));
	}

	
	@RequestMapping(value = { "/groupRegistration/v1" }, method = RequestMethod.POST, produces = {
				MediaType.APPLICATION_JSON_VALUE })
		@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> groupDataRegister(@RequestBody GroupData groupdata) {
		LOGGER.info("groupRegistration api has been called !!! Start Of Method registerApplicant");
		try {
			GroupData groupd = grpDataService.save(groupdata);
			LOGGER.info("End Of Method ");
			return ResponseEntity.status(HttpStatus.OK).body(new Response("group-Added successfully",Boolean.TRUE,groupd));
		} catch (Exception e) {
			LOGGER.error("Error While Registering the Applicant" + e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new GroupData()));
		}
	
	}
	@RequestMapping(value = { "/getGroupName/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GroupResponse> getGroup() {
		LOGGER.info("getApplicant api has been called !!! Start Of Method getApplicant");
		try {
			List<GroupData> groupdata = grpDataService.findAll();
			if (groupdata != null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new GroupResponse(groupdata,"Retrive Success", Boolean.TRUE));
			} else {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new GroupResponse((List<GroupData>) new GroupData(),"No data",Boolean.FALSE));
			}
		} catch (Exception e) {
			LOGGER.error("Error While retrive the Applicant" + e.getMessage());
			return ResponseEntity.badRequest().body(new GroupResponse((List<GroupData>) new GroupData(),"No data",Boolean.FALSE));

		}

	}*/
@RequestMapping(value = { "/modifyTruckersDetails/v1" }, method = RequestMethod.POST, produces = {
		MediaType.APPLICATION_JSON_VALUE })
@ResponseStatus(value = HttpStatus.OK)
public ResponseEntity<GeneralResponse> modifyTruckersDetail(@RequestBody UpdateTruckersDetails updateTruckersDetails) {
	LOGGER.info("Modify Applicant api has been called !!! Start Of Method Modify Applicant");
	HttpStatus httpstatus=null;
	String response="";
	String status=null;
	try {String vehicle_no=null;String company_name=null; String applicant_firstname=null;
	
	Date applicant_date_of_birth =null; int age=0; 
	
		String maritalstatus=null; String nominee_name=null; Date nominee_dob=null; int nominee_age=0;
		String nominee_relation=null; String spouse_name=null; 
		String applicant_father_firstname=null; String religion=null; String applicant_qualification=null; String applicant_employment_type=null; 
		String applicant_address_line_1=null; String applicant_city_name=null; int applicant_pin=0;
		Long applicant_mobile_no=null; 
		int no_of_family_member=0;
		int no_of_earning_member=0; String house_type=null; String Ration_Card=null; 
		String medical_insurance=null; Float current_loan_outstanding_principal=null;
		Float current_loan_outstanding_interest=null; 
		Float applicant_income=null; Float income_from_other_sources=null; Float food_expenses=null; Float houserent=null; 
		Float house_renovation_expenses=null; Float total_monthly_bill_payment=null; Float applicant_expense_monthly=null; 
		Long applicant_id=null;
		String updated_by=null;
		Date datamoddt=new Date();
		  SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
		if(!updateTruckersDetails.getVehicle_no().isEmpty()) {
	 vehicle_no=encdec.decryptnew(updateTruckersDetails.getVehicle_no());}
		
		if(!updateTruckersDetails.getCompany_name().isEmpty())
		{ company_name=encdec.decryptnew(updateTruckersDetails.getCompany_name());}
		
		if(!updateTruckersDetails.getApplicant_firstname().isEmpty())
		{ applicant_firstname=encdec.decryptnew(updateTruckersDetails.getApplicant_firstname());}
	
	if(!updateTruckersDetails.getApplicant_date_of_birth().isEmpty()) {
		String applicant=encdec.decryptnew(updateTruckersDetails.getApplicant_date_of_birth()) ;
		 applicant_date_of_birth = sdf1.parse(applicant);
	}
	
	if(!updateTruckersDetails.getAge().isEmpty()) {
		 age=Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getAge()));}
	if(!updateTruckersDetails.getMaritalstatus().isEmpty()) {
		 maritalstatus=encdec.decryptnew(updateTruckersDetails.getMaritalstatus());}
	
	if(!updateTruckersDetails.getNominee_name().isEmpty())
		{ nominee_name=encdec.decryptnew(updateTruckersDetails.getNominee_name());}
	if(!updateTruckersDetails.getNominee_dob().isEmpty())
		{String dob=encdec.decryptnew(updateTruckersDetails.getNominee_dob());
		 nominee_dob=sdf1.parse(dob);
		}
	if(!updateTruckersDetails.getNominee_age().isEmpty())
	{ nominee_age=Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getNominee_age()));}				
		if(!updateTruckersDetails.getNominee_relation().isEmpty() ) {
			nominee_relation=encdec.decryptnew(updateTruckersDetails.getNominee_relation());
		}
		if(!updateTruckersDetails.getSpouse_name().isEmpty())
		{spouse_name=encdec.decryptnew(updateTruckersDetails.getSpouse_name());}
		if(!updateTruckersDetails.getApplicant_father_firstname().isEmpty())
			applicant_father_firstname=encdec.decryptnew(updateTruckersDetails.getApplicant_father_firstname());
		if(!updateTruckersDetails.getReligion().isEmpty())
		{religion=encdec.decryptnew(updateTruckersDetails.getReligion());}
	if(!updateTruckersDetails.getApplicant_qualification().isEmpty()) {
		applicant_qualification=encdec.decryptnew(updateTruckersDetails.getApplicant_qualification());}
	if(!updateTruckersDetails.getApplicant_employment_type().isEmpty())	{
	applicant_employment_type=encdec.decryptnew(updateTruckersDetails.getApplicant_employment_type());}
	if(!updateTruckersDetails.getApplicant_address_line_1().isEmpty()) {
	 applicant_address_line_1=encdec.decryptnew(updateTruckersDetails.getApplicant_address_line_1());}
	if(!updateTruckersDetails.getApplicant_city_name().isEmpty()) {
		 applicant_city_name=encdec.decryptnew(updateTruckersDetails.getApplicant_city_name());}
	if(!updateTruckersDetails.getApplicant_pin().isEmpty()) {
		 applicant_pin=Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getApplicant_pin()));}
	if(!updateTruckersDetails.getApplicant_mobile_no().isEmpty()) {
		 applicant_mobile_no=Long.parseLong(encdec.decryptnew(updateTruckersDetails.getApplicant_mobile_no()));}
	if(!updateTruckersDetails.getNo_of_family_member().isEmpty()) {
		 no_of_family_member=Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getNo_of_family_member()));}
	if(!updateTruckersDetails.getNo_of_earning_member().isEmpty()) {
		no_of_earning_member=Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getNo_of_earning_member()));}
	if(!updateTruckersDetails.getHouse_type().isEmpty()) {
		house_type=encdec.decryptnew(updateTruckersDetails.getHouse_type());}
	if(!updateTruckersDetails.getRation_Card().isEmpty()) {	
	Ration_Card=encdec.decryptnew(updateTruckersDetails.getRation_Card());}
	if(!updateTruckersDetails.getMedical_insurance().isEmpty()) {
	medical_insurance=encdec.decryptnew(updateTruckersDetails.getMedical_insurance());}
	if(!updateTruckersDetails.getCurrent_loan_outstanding_principal().isEmpty()) {
		 current_loan_outstanding_principal=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_principal()));}
	if(!updateTruckersDetails.getCurrent_loan_outstanding_Stringerest().isEmpty()) {	
	 current_loan_outstanding_interest=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_Stringerest()));}
	if(!updateTruckersDetails.getApplicant_income().isEmpty())	{
	applicant_income=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getApplicant_income()));}
	if(!updateTruckersDetails.getIncome_from_other_sources().isEmpty()) {
		income_from_other_sources=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getIncome_from_other_sources()));}
	if(!updateTruckersDetails.getFood_expenses().isEmpty())	{
	food_expenses=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getFood_expenses()));}
	if(!updateTruckersDetails.getHouserent().isEmpty()) {
		 houserent=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getHouserent()));}
	if(!updateTruckersDetails.getHouse_renovation_expenses().isEmpty()) {
	 house_renovation_expenses=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getHouse_renovation_expenses()));}
	if(!updateTruckersDetails.getTotal_monthly_bill_payment().isEmpty()) {
		 total_monthly_bill_payment=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getTotal_monthly_bill_payment()));}
	if(!updateTruckersDetails.getApplicant_expense_monthly().isEmpty()) {
		 applicant_expense_monthly=Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getApplicant_expense_monthly()));}
	if(!updateTruckersDetails.getApplicant_id().isEmpty()) {
	 applicant_id=Long.parseLong(encdec.decryptnew(updateTruckersDetails.getApplicant_id()));
	 }
	if(!updateTruckersDetails.getUpdated_by().isEmpty()) {
		updated_by=encdec.decryptnew(updateTruckersDetails.getUpdated_by());
	}
	 appRepo.updateTruckersDetails(vehicle_no,company_name,applicant_firstname,applicant_date_of_birth,age,maritalstatus,nominee_name,nominee_dob,nominee_age,nominee_relation,spouse_name,applicant_father_firstname,religion,applicant_qualification,applicant_employment_type,applicant_address_line_1,applicant_city_name,applicant_pin,applicant_mobile_no,no_of_family_member,no_of_earning_member,house_type,	Ration_Card,medical_insurance,current_loan_outstanding_principal,current_loan_outstanding_interest,applicant_income,income_from_other_sources,food_expenses,houserent,house_renovation_expenses,total_monthly_bill_payment,applicant_expense_monthly,updated_by,datamoddt,applicant_id);    
	  httpstatus=HttpStatus.OK;
		 response="updated successfully";
		 status="true";	
	} catch (Exception e) {
		 httpstatus=HttpStatus.BAD_REQUEST;
		 response="error in updation";
		 status="false";	
		LOGGER.error("Error While updating the truckers Applicant" + e.getMessage());
	

	}
	return ResponseEntity.status(httpstatus)
			.body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
}
@GetMapping(path = "/downloadTruckerscsv")
public void getAllEmployeesInCsv(HttpServletResponse servletResponse)  {
    try{servletResponse.setContentType("text/csv");
    //Agent_msg_dtls msg=new Agent_msg_dtls();
    servletResponse.addHeader("Content-Disposition","attachment; filename=\"TruckersDetails.csv\"");
   List<Applicant> lst= appRepo.findAll();
   ICsvBeanWriter csvwriter=new CsvBeanWriter(servletResponse.getWriter(),CsvPreference.STANDARD_PREFERENCE);
   String[] csvHeader =  {"Applicant_id","VEHICLE NO","COMPANY NAME","TRUCK_DRIVER_NAME","DATE_OF_BIRTH (DD/MM/YY)","AGE","MARRIED/UN MARRIED/WIDOW/WIDOWER/DIVORCED","NOMINEE_NAME","NOMINEE_DOB (DD/MM/YYYY)","NOMINEE_AGE","NOMINEE_RELATION","SPOUSE NAME","FATHER NAME","RELIGION","Education 1. Upto Class 10, Class 12. Graduate and avove","Permanent/Contract Job","ADDRESS","VILLAGE_NAME/ CITY","PINCODE","Contact N os Phone / Mobile","Nos Of Family Members","Nos of working Members",	"House Own/ Rented",	"Ration Card Y/N",	"Mrdical Insurance Y/N",	"CURRENT A|LAON OUTSTANDING_PRINCIPAL (IF ANY)","CURRENT LOAN OUTSTANDING_INTEREST",	"TRUCK_INCOME",	"INCOME FROM OTHER SOURCES",	"Monthly Food Expenditure","Rent",	"House Repair",	"Total Monthly Bill Payment (Elec, Water etyc.)",	"Total Monthly Expenses","created_by","Date of entry"};
		   
  // String[] nameMapping = {"Scan_Date","System_Make","System_form_Factor","system_model_no","system_serial_no","Product_Type",	"system_ip","System_Hostname","System_OS_type","OS_License_details","OS_Version","OS_Key","Total_RAM","RAM_Available","RAM_Used","HD_Make","HD_Model","HD_Serial_Number","HD_Capacity","proccessor","MBD_Make","MBD_Model","mbd_serial_no","Type_of_Chipset","Monitor_Make","Monitor_Model","Monitor_Serial_Number","Monitor_Screen_Size","Assets_Status","Retired_Date","Software_list_with_version_and_installed_Date","Procured_Date","Procument_ID","Warranty_AMC","Warranty_AMC_Vendor_Name","Warrenty_AMC_From","Warrenty_AMC_To","username","Department_Name","Site_Name","Sub_Department_Name","Aforesight_Agent_ID","MS_Office_2010", "MS_Office_2013", "MS_Office_2016","Adobe_Reader", "Java8", "Symantec_Antivirus", "Mcafee_Antivirus", "Trend_Micro_Antivirus", "Microsoft_Teams", "MS_Office_2007", "Anydesk", "OneDrive","zip7","Mozilla_Firefox", "Google_Chrome","Team_Viewer","Zoom","Webex","AutoCad","Winrar"};
  String[] nameMapping= {"Applicant_id","vehicle_no","company_name","applicant_firstname","applicant_date_of_birth","age","maritalstatus","nominee_name","nominee_dob","nominee_age","nominee_relation","spouse_name","applicant_father_firstname","religion","applicant_qualification","applicant_employment_type","applicant_address_line_1","applicant_city_name","applicant_pin","applicant_mobile_no","no_of_family_member","no_of_earning_member","house_type",	"Ration_Card","medical_insurance","current_loan_outstanding_principal","current_loan_outstanding_interest","applicant_income","income_from_other_sources","food_expenses","houserent","house_renovation_expenses","total_monthly_bill_payment","applicant_expense_monthly","created_by","dataentdt"};
   csvwriter.writeHeader(csvHeader);
for (Applicant msg:lst) {
	
	 
	 csvwriter.write(msg, nameMapping);
}
csvwriter.close();

}
catch (Exception e) {
	 LOGGER.error("error while downloading truckers data"+e.getMessage());
}}
}


