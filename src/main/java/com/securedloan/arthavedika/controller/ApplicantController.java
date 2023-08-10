package com.securedloan.arthavedika.controller;

import java.time.LocalDate;
import com.securedloan.arthavedika.payload.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.securedloan.arthavedika.repo.ApplicantRepository;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.repo.PsyAnsRepo;
import com.securedloan.arthavedika.response.ApplicantInfo;
import com.securedloan.arthavedika.response.ApplicantInfo1;
import com.securedloan.arthavedika.response.ApplicantInfos;
import com.securedloan.arthavedika.response.ApprovedApplicantResponse;
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
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		try {
			 document=fileStorageService.documentById(newApplicant.getApplicant_id());
			applicant = applicantService.findById(newApplicant.getApplicant_id());
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
						.body(new ApplicantInfo(response, status, applicant,document));

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
		public ResponseEntity<com.securedloan.arthavedika.response.ApprovedApplicantResponse> GetApprovedApplicant(@RequestBody UserPayload usrPayload) {
			LOGGER.info("get  user  by id api has been called !!! Start Of Method get  user by id");
			
			HttpStatus httpstatus=null;
			String response="";
			String status=null;
			
			List<ApprovedApplicantList> applicant= new ArrayList<ApprovedApplicantList>();
			
			try {
			 List<Applicant> app=appRepo.AllApprovedAppplicat(encdec.decryptnew(usrPayload.getUser_id()));
			
				if (app==null) {
					
				response="Approved applicant details not available"	;
				}
				else
				{
					int i=0;
					for( Applicant apps:app) {
							ApprovedApplicantList userss= new ApprovedApplicantList();
							if(app.get(i).getApplicant_firstname()!=null) {
							
						userss.setApplicant_firstname(encdec.encryptnew( app.get(i).getApplicant_firstname()));}
							if(((Applicant) app).getApplicant_lastname()!=null) {
								
						userss.setApplicant_lastname(encdec.encryptnew( app.get(i).getApplicant_lastname()));}
							if( app.get(i).getApplicant_email_id()!=null) {
								userss.setApplicant_email_id(encdec.encryptnew("NA"));
							}else {
						userss.setApplicant_email_id(encdec.encryptnew(app.get(i).getApplicant_email_id()));}
							if(((Applicant) app).getApplicant_mobile_no()!=null) {
								
						userss.setApplicant_mobile_no(encdec.encryptnew( app.get(i).getApplicant_mobile_no()));}
							String applicant_id=String.valueOf(app.get(i).getApplicant_id());
							userss.setApplicant_id(encdec.encryptnew(applicant_id));
					applicant.add(i, userss);
					i++;
					}
					response="Approved Applicant detail is retrieved successfully";
					
				}
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
					(applicant,encdec.encryptnew(response),encdec.encryptnew(status)));
		}

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

