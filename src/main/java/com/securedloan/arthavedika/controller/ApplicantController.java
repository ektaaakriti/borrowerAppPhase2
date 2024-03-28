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
import com.securedloan.arthavedika.repo.ApplicantApprovalDetailsRepo;
import com.securedloan.arthavedika.repo.ApplicantRepository;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.repo.PsyAnsRepo;
import com.securedloan.arthavedika.response.ApplicantInfo;
import com.securedloan.arthavedika.response.ApplicantInfo1;
import com.securedloan.arthavedika.response.ApplicantInfos;
import com.securedloan.arthavedika.response.ApplicantPredictionListResponse;
import com.securedloan.arthavedika.response.ApplicatListPrediction;
import com.securedloan.arthavedika.response.ApprovedApplicantResponse;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.GroupResponse;
//import com.securedloan.arthavedika.response.Prediction;
import com.securedloan.arthavedika.response.Response;
import com.securedloan.arthavedika.response.ResponseMessage;
import com.securedloan.arthavedika.response.ResponseOld;
import com.securedloan.arthavedika.service.ApplicantService;
import com.securedloan.arthavedika.service.EkycFileStorageService;
import com.securedloan.arthavedika.service.FileStorageService;
import com.securedloan.arthavedika.service.GroupDataService;

//import com.securedloan.arthavedika.service.PredictService;
//@CrossOrigin()
//@CrossOrigin(origins = "http://4.236.144.236:4200")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("applicant")
public class ApplicantController {
	private final Logger LOGGER = LoggerFactory.getLogger(ApplicantController.class);
	EncryptionDecryptionClass encdec = new EncryptionDecryptionClass();
	@Autowired
	ApplicantApprovalDetailsRepo approvalRepo;

	@Autowired
	private EkycFileStorageService storageService;
	@Autowired
	ApplicantRepository appRepo;
	@Autowired
	CompanyRepo companyRepo;
	@PersistenceContext
	private EntityManager entitymanager;

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
		List<Applicant> applicant = null;
		List<FileDB> document = null;
		Optional<Applicant_approval_details> approval_details = null;
		HttpStatus httpstatus = null;
		String response = "";
		Boolean status = null;
		try {
			LOGGER.info("getApplicant api has been called !!! Start Of Method getApplicant");
			document = fileStorageService.documentById(newApplicant.getApplicant_id());
			applicant = applicantService.findById(newApplicant.getApplicant_id());
			approval_details = approvalRepo.findById(newApplicant.getApplicant_id());

			httpstatus = HttpStatus.OK;
			status = true;
			if (applicant != null) {
				response = "Retrive Success";

				// return ResponseEntity.status(HttpStatus.OK)
				// .body(new ApplicantInfo("Retrive Success", Boolean.TRUE,
				// applicant,document));
			} else {
				response = "No data with such applicant id";
				// return ResponseEntity.status(HttpStatus.OK)
				// .body(new ApplicantInfo("No data with such applicant id", Boolean.FALSE,
				// applicant,document));
			}
		} catch (Exception e) {
			response = "Error While retrive the Applicant" + e.getMessage();
			LOGGER.error(response);
			httpstatus = HttpStatus.BAD_REQUEST;
			status = false;
			// return ResponseEntity.status(HttpStatus.OK).body(new
			// ApplicantInfo(e.getMessage(), Boolean.FALSE, applicant,document));

		}
		LOGGER.info("response of get applicant is"+response);
		return ResponseEntity.status(httpstatus)
				.body(new ApplicantInfo(response, status, applicant, document, approval_details));

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
			return ResponseEntity.badRequest().body(new ApplicantInfos(e.getMessage(), Boolean.FALSE, new Applicant()));

		}
	}

	@RequestMapping(value = { "/GetApprovedApplicant" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.ApprovedApplicantResponse> GetApprovedApplicant() {
		LOGGER.info("get  user  by id api has been called !!! Start Of Method get  user by id");

		HttpStatus httpstatus = null;
		String response = "";
		String status = null;

		List<ApprovedApplicantList> applicant = new ArrayList<ApprovedApplicantList>();

		try {
			List<Applicant> app = appRepo.AllApprovedAppplicat();
			System.out.println("details retrived successfully");
			if (app == null) {

				response = "Approved applicant details not available";
			} else {
				System.out.println("encyption of list");
				int i = 0;
				for (i = 0; i < app.size(); i++) {
					System.out.println("for loop");
					ApprovedApplicantList userss = new ApprovedApplicantList();
					if (app.get(i).getApplicant_firstname() != null) {
						System.out.println("dummy21");
						// userss.setApplicant_firstname(encdec.encryptnew(
						// app.get(i).getApplicant_firstname()));
						userss.setApplicant_firstname(app.get(i).getApplicant_firstname());
						System.out.println("dummy22");
					}
					if (app.get(i).getApplicant_lastname() != null) {
						System.out.println("dummy25");
						// userss.setApplicant_lastname(encdec.encryptnew(
						// app.get(i).getApplicant_lastname()));
						userss.setApplicant_lastname(app.get(i).getApplicant_lastname());
						System.out.println("dummy26");
					}
					if (app.get(i).getApplicant_email_id() != null) {
						System.out.println("dummy27");

						System.out.println("dummy28");

						// userss.setApplicant_email_id(encdec.encryptnew(app.get(i).getApplicant_email_id()));
						userss.setApplicant_email_id(app.get(i).getApplicant_email_id());
						System.out.println("dummy29");
					}
					if (app.get(i).getApplicant_mobile_no() != null) {
						System.out.println("dummy210");
						// userss.setApplicant_mobile_no(encdec.encryptnew(
						// app.get(i).getApplicant_mobile_no()));
						userss.setApplicant_mobile_no(app.get(i).getApplicant_mobile_no());
						System.out.println("dummy211");
					}
					String applicant_id = String.valueOf(app.get(i).getApplicant_id());
					System.out.println("dummy212");
					// userss.setApplicant_id(encdec.encryptnew(applicant_id));
					userss.setApplicant_id(applicant_id);
					System.out.println("dummy213");
					System.out.println("dummy24" + userss.getApplicant_email_id());
					applicant.add(i, userss);
					// applicant.add(i, userss);
					System.out.println("dummy1");
					// i++;
				}
				System.out.println("dummy2");
				response = "Approved Applicant detail is retrieved successfully";

			}
			System.out.println("dummy3");
			status = "true";
			httpstatus = HttpStatus.OK;
		}

		catch (Exception e) {
			LOGGER.error("Error While retreiving user" + e.getMessage());
			response = "Error While retreiving  user" + e.getMessage();
			status = "false";
			httpstatus = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new ApprovedApplicantResponse(applicant, (response), (status)));
	}
	@RequestMapping(value = { "/register/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ResponseOld> registerApplicant(@RequestBody Applicant applicantInput) {
		LOGGER.info("Register api has been called !!! Start Of Method registerApplicant");
		System.out.println("monthlin income is");
		try {

			if (applicantInput.getUserIdStr() == null) {
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseOld(mandatoryField, Boolean.FALSE, new Applicant()));
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
				String company_name = null; List<Company>company=companyRepo.company_name();
				System.out.println("company is"+company.get(0));
				  for(int i=0;i<company.size();i++) { if
				  (applicantInput.getCompany_code().equals(company.get(i).getCompany_code())) {
				 System.out.println("company name is"+company.get(i).getCompanyName());
				  company_name=(company.get(i).getCompanyName()); }}
				  applicantInput.setCompany_name(company_name);
				  if(applicantInput.getCompany_code().equals("MK")) {
				 applicantInput.setAV_approval("Y");
				 applicantInput.setAuthorisation_status(1); }
				
				  Applicant applicant = applicantService.saveApplicant(applicantInput);
				  System.out.println("before save");
				
				 // appRepo.save(applicantInput);
				  System.out.println("after save");
				LOGGER.info("End Of Method registerApplicant");
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseOld(registerSuccess, Boolean.TRUE, applicant));
			} else if (applicants != null) {
				if (applicants.getApplicant_id() == applicantInput.getApplicant_id()) {
					List<PsyQstn> res = new ArrayList<>();
					applicantInput.setDataentdt(LocalDate.now());
					System.out.println("applicants aadhar nois"+applicantInput.getAadhar_no());
					System.out.println("applicants pan is"+applicantInput.getPan_no());
					System.out.println("applicants account is"+applicantInput.getAccount_no());
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
						psyRepo.saveAll(applicantInput.getPsyAns());

					}
					

					

					LOGGER.info("End Of Method registerApplicant");
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseOld("Applicant Registered Successfully", Boolean.TRUE, res,applicant));
				}
			}
			LOGGER.info("End Of Method registerApplicant");
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseOld("Already Registered", Boolean.FALSE, new Applicant()));

		} catch (Exception e) {
			LOGGER.error("Error While Registering the Applicant" + e.getMessage());
			System.out.println("in error"+e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseOld(e.getMessage(), Boolean.FALSE, new Applicant()));
		}
	}

	/*
	 * @RequestMapping(value = { "/register/v1" }, method = RequestMethod.POST,
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<ResponseOld>
	 * registerApplicant(@RequestBody Applicant applicantInput) { LOGGER.
	 * info("Register api has been called with post !!! Start Of Method registerApplicant"
	 * ); System.out.println("monthlin income is"); try {
	 * 
	 * if (applicantInput.getUserIdStr() == null) { return
	 * ResponseEntity.status(HttpStatus.OK) .body(new ResponseOld(mandatoryField,
	 * Boolean.FALSE, new Applicant())); } Applicant applicants =
	 * applicantService.findByApplicant_mobile_no(applicantInput.
	 * getApplicant_mobile_no()); System.out.println("applicants is"+applicants); if
	 * (applicants == null) { User user = new User();
	 * user.setUser_id(applicantInput.getUserIdStr());
	 * 
	 * applicantInput.setUser(user); applicantInput.setDataentdt(LocalDate.now());
	 * applicantInput.setDatamoddt(null); //applicantInput.(false);
	 * System.out.println("applicants gng to save");
	 * System.out.println("applicants father name is"+applicantInput.
	 * getApplicant_father_firstname());
	 * System.out.println("applicants mother name is"+applicantInput.getMother_name(
	 * ));
	 * System.out.println("applicants spouse name is"+applicantInput.getSpouse_name(
	 * ));
	 * System.out.println("applicants aadhar nois1"+applicantInput.getAadhar_no());
	 * System.out.println("applicants pan is1"+applicantInput.getPan_no());
	 * System.out.println("applicants account is1"+applicantInput.getAccount_no());
	 * String company_name = null; List<Company>company=companyRepo.company_name();
	 * for(int i=0;i<company.size();i++) { if
	 * (applicantInput.getCompany_code().equals(company.get(i).getCompany_code())) {
	 * System.out.println("company name is"+company.get(i).getCompanyName());
	 * company_name=(company.get(i).getCompanyName()); }}
	 * applicantInput.setCompany_name(company_name);
	 * if(applicantInput.getCompany_code().equals("MK")) {
	 * applicantInput.setAV_approval("Y");
	 * applicantInput.setAuthorisation_status(1); }
	 * 
	 * Applicant applicant = applicantService.saveApplicant(applicantInput);
	 * LOGGER.info("End Of Method registerApplicant"); return
	 * ResponseEntity.status(HttpStatus.OK) .body(new ResponseOld(registerSuccess,
	 * Boolean.TRUE, applicant)); } else if (applicants != null) { if
	 * (applicants.getApplicant_id() == applicantInput.getApplicant_id()) {
	 * List<PsyQstn> res = new ArrayList<>();
	 * applicantInput.setDataentdt(LocalDate.now());
	 * System.out.println("applicants aadhar nois"+applicantInput.getAadhar_no());
	 * System.out.println("applicants pan is"+applicantInput.getPan_no());
	 * System.out.println("applicants account is"+applicantInput.getAccount_no());
	 * List<Company>company=companyRepo.company_name();
	 * System.out.println("company is found"+company); String company_name="";
	 * for(int i=0;i<company.size();i++) { if
	 * (applicantInput.getCompany_code().equals(company.get(i).getCompany_code())) {
	 * System.out.println("company name is"+company.get(i).getCompanyName());
	 * company_name=(company.get(i).getCompanyName()); }}
	 * applicantInput.setCompany_name(company_name);
	 * if(applicantInput.getCompany_code().equals("MK")) {
	 * applicantInput.setAV_approval("Y");
	 * applicantInput.setAuthorisation_status(1); } Applicant applicant =
	 * applicantService.saveApplicant(applicantInput);
	 * System.out.println("applicantInput.isPsycho_page()"+applicantInput.
	 * isPsycho_page()); if (applicantInput.isPsycho_page() == Boolean.TRUE ) {
	 * List<Object[]> doc = (List<Object[]>) entitymanager.createNativeQuery(
	 * "SELECT q.qstn,q.optn_a,q.optn_b,q.optn_c,q.optn_d,q.optn_e,q.id from kosh_db_v1.psycho_qstn q ORDER BY RAND() limit 29"
	 * ) .getResultList(); System.out.println("applicantInput.doc_size"+doc.size());
	 * if (doc.size() != 0) { for (Object d : doc) {
	 * 
	 * PsyQstn pq = new PsyQstn(); Object[] obj = (Object[]) d;
	 * System.out.println("question is 0 "+obj[0]
	 * +"  ==1== "+obj[1]+"==2=="+obj[2]+"==3=="+obj[3]); pq.setQstn((String)
	 * obj[0]); pq.setOpt_a((String) obj[1]); pq.setOpt_b((String) obj[2]);
	 * pq.setOpt_c((String) obj[3]); pq.setOpt_d((String) obj[4]);
	 * pq.setOpt_e((String) obj[5]); pq.setId((int) obj[6]); res.add(pq); }
	 * 
	 * System.out.println("pg is"+res.get(0).getOpt_a()); } } else
	 * if(applicantInput.isPsycho_page() == Boolean.TRUE ) { List<Object[]> doc =
	 * (List<Object[]>) entitymanager.createNativeQuery(
	 * "SELECT q.qstn,q.optn_a,q.optn_b,q.optn_c,q.optn_d,q.optn_e,q.id from kosh_db_v1.psycho_qstn_hnd q ORDER BY RAND() limit 29"
	 * ) .getResultList();
	 * 
	 * if (doc.size() != 0) { for (Object d : doc) { PsyQstn pq = new PsyQstn();
	 * Object[] obj = (Object[]) d; pq.setQstn((String) obj[0]);
	 * pq.setOpt_a((String) obj[1]); pq.setOpt_b((String) obj[2]);
	 * pq.setOpt_c((String) obj[3]); pq.setOpt_d((String) obj[4]);
	 * pq.setOpt_e((String) obj[5]); pq.setId((int) obj[6]); res.add(pq); }
	 * 
	 * } } if (applicantInput.getPsyAns().size() > 0) { String psyAnsQuery = null;
	 * for (PsyAns eachAns : applicantInput.getPsyAns()) {
	 * eachAns.setApplicant_id(applicantInput.getApplicant_id()); }
	 * 
	 * company=companyRepo.company_name(); for(int i=0;i<company.size();i++) { if
	 * (applicantInput.getCompany_code().equals(company.get(i).getCompany_code())) {
	 * System.out.println("company name is"+company.get(i).getCompanyName());
	 * company_name=(company.get(i).getCompanyName()); }}
	 * applicantInput.setCompany_name(company_name);
	 * if(applicantInput.getCompany_code().equals("MK")) {
	 * applicantInput.setAV_approval("Y");
	 * applicantInput.setAuthorisation_status(1); }
	 * psyRepo.saveAll(applicantInput.getPsyAns());
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * LOGGER.info("End Of Method registerApplicant"); return
	 * ResponseEntity.status(HttpStatus.OK) .body(new
	 * ResponseOld("Applicant Registered Successfully", Boolean.TRUE,
	 * res,applicant)); } } LOGGER.info("End Of Method registerApplicant"); return
	 * ResponseEntity.status(HttpStatus.OK) .body(new
	 * ResponseOld("Already Registered", Boolean.FALSE, new Applicant()));
	 * 
	 * } catch (Exception e) { LOGGER.error("Error While Registering the Applicant"
	 * + e.getMessage()); System.out.println("in error"+e.getMessage()); return
	 * ResponseEntity.badRequest().body(new ResponseOld(e.getMessage(),
	 * Boolean.FALSE, new Applicant())); } }
	 */

	// commented by rajeev to make similar as kosh
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
	/*
	 * @RequestMapping(value = { "/register/v1" }, method = RequestMethod.PUT,
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<Response>
	 * updateApplicant(@RequestBody Applicant updateApplicant) { LOGGER.
	 * info("Register api has been called with put method !!! Start Of Method registerApplicant"
	 * ); try { Applicant applicants =
	 * applicantService.findByIds(updateApplicant.getApplicant_id()); if (applicants
	 * != null) { updateApplicant.setDatamoddt(LocalDate.now()); String company_name
	 * = null; List<Company>company=companyRepo.company_name(); for(int
	 * i=0;i<company.size();i++) { if
	 * (updateApplicant.getCompany_code().equals(company.get(i).getCompany_code()))
	 * { System.out.println("company name is"+company.get(i).getCompanyName());
	 * company_name=(company.get(i).getCompanyName()); }}
	 * updateApplicant.setCompany_name(company_name);
	 * if(updateApplicant.getCompany_code().equals("MK")) {
	 * updateApplicant.setAV_approval("Y");
	 * updateApplicant.setAuthorisation_status(1); } Applicant applicant =
	 * applicantService.saveApplicant(updateApplicant);
	 * LOGGER.info("End Of Method updateApplicant"); return
	 * ResponseEntity.status(HttpStatus.OK).body(new Response("Data Saved",
	 * Boolean.TRUE, applicant)); } return ResponseEntity.status(HttpStatus.OK)
	 * .body(new Response("UNABLE TO UPDATE", Boolean.FALSE, new Applicant())); }
	 * catch (Exception e) { LOGGER.error("Error While Updating the Applicant" +
	 * e.getMessage()); return ResponseEntity.badRequest().body(new
	 * Response(e.getMessage(), Boolean.FALSE, new Applicant()));
	 * 
	 * } }
	 * 
	 * @RequestMapping(value = { "/getapplicantList1/v1" }, method =
	 * RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<ApplicantInfo1>
	 * getApplicant1(@RequestParam("applicant_id") Applicant newApplicant) { LOGGER.
	 * info("getApplicant api has been called !!! Start Of Method getApplicant");
	 * Applicant applicant=null; List<FileDB> document=null; HttpStatus
	 * httpstatus=null; String response=""; Boolean status=null; try {
	 * document=fileStorageService.documentById(newApplicant.getApplicant_id());
	 * applicant = applicantService.findByIds(newApplicant.getApplicant_id());
	 * 
	 * status=true; if (applicant != null) { response="Retrive Success";
	 * 
	 * //return ResponseEntity.status(HttpStatus.OK) // .body(new
	 * ApplicantInfo("Retrive Success", Boolean.TRUE, applicant,document)); } else {
	 * response="No data with such applicant id"; //return
	 * ResponseEntity.status(HttpStatus.OK) // .body(new
	 * ApplicantInfo("No data with such applicant id", Boolean.FALSE,
	 * applicant,document)); } httpstatus=HttpStatus.OK; } catch (Exception e) {
	 * response="Error While retrive the Applicant"+e.getMessage();
	 * LOGGER.error(response); httpstatus=HttpStatus.BAD_REQUEST; status=false;
	 * //return ResponseEntity.status(HttpStatus.OK).body(new
	 * ApplicantInfo(e.getMessage(), Boolean.FALSE, applicant,document));
	 * 
	 * } return ResponseEntity.status(httpstatus) .body(new ApplicantInfo1(response,
	 * status, applicant,document));
	 * 
	 * }
	 * 
	 * //comment for encdec /*
	 * 
	 * @RequestMapping(value = { "/psychometric/v1" }, method = RequestMethod.POST,
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<Response>
	 * psychometricQstn(@RequestBody Applicant applicantInput) { LOGGER.
	 * info("Psychometric api has been called !!! Start Of Method registerApplicant"
	 * ); try { Applicant applicants =
	 * applicantService.findByIds(applicantInput.getApplicant_id());
	 * System.out.println("applicants value"+applicants);
	 * System.out.println("is having cell value"+applicantInput.isIs_having_cell());
	 * if (applicants != null) { // if (applicants.getApplicant_id() ==
	 * applicantInput.getApplicant_id()) { List<PsyQstn> res = new ArrayList<>(); //
	 * Applicant applicant = applicantService.saveApplicant(applicantInput);
	 * System.out.println("applicants.is subitted"+applicantInput.isSubmited()); if
	 * ( applicantInput.isSubmited() == Boolean.FALSE) { List<Object[]> doc =
	 * (List<Object[]>) entitymanager.createNativeQuery(
	 * "SELECT q.qstn,q.optn_a,q.optn_b,q.optn_c,q.optn_d,q.optn_e,q.id from kosh_db_v1.psycho_qstn q ORDER BY RAND() limit 29"
	 * ) .getResultList();
	 * 
	 * if (doc.size() != 0) { for (Object d : doc) { PsyQstn pq = new PsyQstn();
	 * Object[] obj = (Object[]) d; pq.setQstn((String) obj[0]);
	 * pq.setOpt_a((String) obj[1]); pq.setOpt_b((String) obj[2]);
	 * pq.setOpt_c((String) obj[3]); pq.setOpt_d((String) obj[4]);
	 * pq.setOpt_e((String) obj[5]); pq.setId((int) obj[6]);; res.add(pq); }
	 * 
	 * } } else if(applicantInput.isSubmited() ==Boolean.FALSE ) { List<Object[]>
	 * doc = (List<Object[]>) entitymanager.createNativeQuery(
	 * "SELECT q.qstn,q.optn_a,q.optn_b,q.id from kosh_db_v1.psycho_qstn_hnd q ORDER BY RAND() limit 29"
	 * ) .getResultList();
	 * 
	 * if (doc.size() != 0) { for (Object d : doc) { PsyQstn pq = new PsyQstn();
	 * Object[] obj = (Object[]) d; pq.setQstn((String) obj[0]);
	 * pq.setOpt_a((String) obj[1]); pq.setOpt_b((String) obj[2]); pq.setId((int)
	 * obj[3]); res.add(pq); }
	 * 
	 * } } else { LOGGER.info("End Of Method psychometric"); return
	 * ResponseEntity.status(HttpStatus.OK) .body(new
	 * Response("Psychometric Answer already Submitted", Boolean.FALSE, res,null));
	 * } if (applicantInput.getPsyAns().size() > 0) { String psyAnsQuery = null; for
	 * (PsyAns eachAns : applicantInput.getPsyAns()) {
	 * eachAns.setApplicant_id(applicantInput.getApplicant_id()); }
	 * psyRepo.saveAll(applicantInput.getPsyAns());
	 * 
	 * } LOGGER.info("End Of Method psychometric"); return
	 * ResponseEntity.status(HttpStatus.OK) .body(new
	 * Response("Response saved Successfully", Boolean.TRUE, res,null)); }
	 * LOGGER.info("End Of Method registerApplicant"); return
	 * ResponseEntity.status(HttpStatus.OK) .body(new
	 * Response("Enter a valid applicant Id", Boolean.FALSE, null,new Applicant()));
	 * 
	 * } catch(Exception e) {
	 * 
	 * LOGGER.error("Error While Registering the Applicant" + e.getMessage());
	 * return ResponseEntity.badRequest().body(new Response(e.getMessage(),
	 * Boolean.FALSE, new Applicant()));
	 * 
	 * } }
	 * 
	  @RequestMapping(value = { "/eKYC/v1" }, method = RequestMethod.GET, produces
	  = { MediaType.APPLICATION_JSON_VALUE })
	  
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<Response>
	 * eKYC(@RequestParam("applicant_id") Applicant newApplicant) {
	 * LOGGER.info("Ekyc api has been called !!! Start Of Method eKYC");
	 * 
	 * List<EKYC> response = new ArrayList<>(); try { List<Object[]> docs =
	 * entitymanager.createNativeQuery(
	 * "SELECT f.data,f.doc_name,f.document,a.applicant_date_of_BIRTH as dob,a.APPLICANT_ID,a.APPLICANT_EMAIL_ID as email,a.APPLICANT_MOBILE_NO as mobile,a.APPLICANT_FIRSTNAME as firstname,a.APPLICANT_FATHER_FIRSTNAME as fathersfirstname,a.GENDER, a.APPLICANT_FATHER_MIDDLE_NAME as fathersmiddlename,a.APPLICANT_FATHER_LASTNAME as fatherslastname,a.APPLICANT_MIDDLE_NAME as middlename,a.APPLICANT_LASTNAME as lastname  from  kosh_db_v1.applicant_table a join kosh_db_v1.files f on a.applicant_id = f.applicant_id where a.applicant_id="
	 * + newApplicant.getApplicant_id()) .getResultList(); if (docs.size() != 0) {
	 * for (Object d : docs) { EKYC ekyc = new EKYC(); Object[] obj = (Object[]) d;
	 * String base64Image = Base64.getEncoder().encodeToString((byte[]) obj[0]);
	 * ekyc.setPhoto("data:image/jpeg;base64," + base64Image);
	 * ekyc.setPhotos((byte[]) obj[0]); ekyc.setDocName((String) obj[1]);
	 * ekyc.setDoc((String) obj[2]); ekyc.setDob((Date) obj[3]);
	 * 
	 * long app_id = Long.parseLong(obj[4].toString());
	 * ekyc.setApplicant_id(app_id); // ekyc.setApplicant_id((long) obj[3]);
	 * ekyc.setEmail((String) obj[5]); ekyc.setMobile((String) obj[6]);
	 * ekyc.setApplicant_name((String) obj[7]); ekyc.setFathersName((String)
	 * obj[8]); ekyc.setGender((String) obj[9]); ekyc.setFathersMiddleName((String)
	 * obj[10]); ekyc.setFathersLastName((String) obj[11]);
	 * ekyc.setApplicant_middle_name((String) obj[12]);
	 * ekyc.setApplicant_lastname((String) obj[13]); response.add(ekyc); } } } catch
	 * (Exception e) { LOGGER.error("Error While fetching :" + e.getMessage());
	 * return ResponseEntity.badRequest().body(new Response(e.getMessage(),
	 * Boolean.FALSE, new ArrayList<>())); } // return response; return
	 * ResponseEntity.status(HttpStatus.OK).body(new Response(Success, Boolean.TRUE,
	 * response)); }
	 * 
	 * // @PostMapping("/upload/ekyc/v1")
	 * 
	 * @RequestMapping(value = { "/upload/ekyc/v1" }, method = RequestMethod.POST,
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<ResponseMessage>
	 * uploadFile(@RequestParam("file") MultipartFile file,
	 * 
	 * @RequestParam("applicant_id") Applicant applicant) {
	 * LOGGER.info("Upload ekyc api has been called !!! Start Of Method uploadFile"
	 * ); String message = ""; try { String company_name = null;
	 * List<Company>company=companyRepo.company_name(); for(int
	 * i=0;i<company.size();i++) { if
	 * (applicant.getCompany_code().equals(company.get(i).getCompany_code())) {
	 * System.out.println("company name is"+company.get(i).getCompanyName());
	 * company_name=(company.get(i).getCompanyName()); }}
	 * applicant.setCompany_name(company_name);
	 * if(applicant.getCompany_code().equals("MK")) { applicant.setAV_approval("Y");
	 * applicant.setAuthorisation_status(1); } storageService.store(file,
	 * applicant); message = "Uploaded the file successfully: " +
	 * file.getOriginalFilename(); LOGGER.info("End Of Method uploadFile"); return
	 * ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message)); }
	 * catch (Exception e) { message = "Could not upload the file: " +
	 * file.getOriginalFilename() + "!";
	 * LOGGER.error("Error While Uploading the File" + e.getMessage()); return
	 * ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
	 * ResponseMessage(message)); } }
	 * 
	 * @RequestMapping(value = { "/eKYC/retrieve/v1" }, method = RequestMethod.GET,
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<Response>
	 * eKYCRetrieve(@RequestParam("applicant_id") Applicant newApplicant) { LOGGER.
	 * info("Retrieve ekyc api has been called !!! Start Of Method EkycRetrieve");
	 * Boolean Status = false; List<DocEkyc> DocResponse = new ArrayList<>(); try {
	 * List<Object[]> docs = entitymanager.createNativeQuery(
	 * "SELECT f.data,f.applicant_id from kosh_db_v1.ekycfile f where f.applicant_id= "
	 * + newApplicant.getApplicant_id()) .getResultList();
	 * 
	 * List<String> images = new ArrayList<>();
	 * 
	 * if (docs.size() != 0) { System.out.println(docs.size()); for (Object d :
	 * docs) { DocEkyc ekyc = new DocEkyc(); Object[] obj = (Object[]) d;
	 * 
	 * String base64Image = Base64.getEncoder().encodeToString((byte[]) obj[0]);
	 * 
	 * ekyc.setPhotoString("data:image/jpeg;base64," + base64Image);
	 * images.add("data:image/jpeg;base64," + base64Image); long a_id =
	 * Long.parseLong(obj[1].toString()); ekyc.setApplicant_id(a_id);
	 * DocResponse.add(ekyc); LOGGER.info("End Of Method EkycRetrieve"); } Status =
	 * applicantService.authImage(images); System.out.println(images);
	 * 
	 * } } catch (Exception e) { LOGGER.error("Error While Retrieve" +
	 * e.getMessage()); return ResponseEntity.badRequest().body(new
	 * Response(e.getMessage(), Boolean.FALSE, Status)); } return
	 * ResponseEntity.status(HttpStatus.OK).body(new Response(Success, Boolean.TRUE,
	 * Status)); }
	 * 
	 * 
	 * @RequestMapping(value = { "/groupRegistration/v1" }, method =
	 * RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<Response>
	 * groupDataRegister(@RequestBody GroupData groupdata) { LOGGER.
	 * info("groupRegistration api has been called !!! Start Of Method registerApplicant"
	 * ); try { GroupData groupd = grpDataService.save(groupdata);
	 * LOGGER.info("End Of Method "); return
	 * ResponseEntity.status(HttpStatus.OK).body(new
	 * Response("group-Added successfully",Boolean.TRUE,groupd)); } catch (Exception
	 * e) { LOGGER.error("Error While Registering the Applicant" + e.getMessage());
	 * return ResponseEntity.badRequest().body(new Response(e.getMessage(),
	 * Boolean.FALSE, new GroupData())); }
	 * 
	 * }
	 * 
	 * @RequestMapping(value = { "/getGroupName/v1" }, method = RequestMethod.GET,
	 * produces = { MediaType.APPLICATION_JSON_VALUE })
	 * 
	 * @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<GroupResponse>
	 * getGroup() { LOGGER.
	 * info("getApplicant api has been called !!! Start Of Method getApplicant");
	 * try { List<GroupData> groupdata = grpDataService.findAll(); if (groupdata !=
	 * null) { return ResponseEntity.status(HttpStatus.OK) .body(new
	 * GroupResponse(groupdata,"Retrive Success", Boolean.TRUE)); } else { return
	 * ResponseEntity.status(HttpStatus.OK) .body(new
	 * GroupResponse((List<GroupData>) new GroupData(),"No data",Boolean.FALSE)); }
	 * } catch (Exception e) { LOGGER.error("Error While retrive the Applicant" +
	 * e.getMessage()); return ResponseEntity.badRequest().body(new
	 * GroupResponse((List<GroupData>) new GroupData(),"No data",Boolean.FALSE));
	 * 
	 * }
	 * 
	 * }
	 */
	
	
	
	
	
	
	
	
	@RequestMapping(value = { "/modifyTruckersDetails/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> modifyTruckersDetail(
			@RequestBody UpdateTruckersDetails updateTruckersDetails) {
		LOGGER.info("Modify Applicant api has been called !!! Start Of Method Modify Applicant");
		HttpStatus httpstatus = null;
		String response = "";
		String status = null;
		try {
			String vehicle_no = null;
			String company_name = null;
			String applicant_firstname = null;

			Date applicant_date_of_birth = null;
			int age = 0;

			String maritalstatus = null;
			String nominee_name = null;
			Date nominee_dob = null;
			int nominee_age = 0;
			String nominee_relation = null;
			String spouse_name = null;
			String applicant_father_firstname = null;
			String religion = null;
			String applicant_qualification = null;
			String applicant_employment_type = null;
			String applicant_address_line_1 = null;
			String applicant_city_name = null;
			int applicant_pin = 0;
			long applicant_pin1 = 0;
			String applicant_mobile_no = null;
			int no_of_family_member = 0;
			int no_of_earning_member = 0;
			String house_type = null;
			String Ration_Card = null;
			String medical_insurance = null;
			Float current_loan_outstanding_principal = (float) 0;
			Float current_loan_outstanding_interest = (float) 0;
			String applicant_income = null;
			Float income_from_other_sources = (float) 0;
			Float food_expenses = (float) 0;
			String houserent = null;
			Float house_renovation_expenses = (float) 0;
			Float total_monthly_bill_payment = (float) 0;
			String applicant_expense_monthly = "";
			Long applicant_id = null;
			String updated_by = null;
			LocalDate datamoddt = LocalDate.now();
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			System.out.print("dummy1");
			if (!updateTruckersDetails.getVehicle_no().isEmpty()) {
				vehicle_no = encdec.decryptnew(updateTruckersDetails.getVehicle_no());
			}
			System.out.print("dummy12");
			if (!updateTruckersDetails.getCompany_name().isEmpty()) {
				company_name = encdec.decryptnew(updateTruckersDetails.getCompany_name());
			}
			System.out.print("dummy13");
			if (!updateTruckersDetails.getApplicant_firstname().isEmpty()) {
				applicant_firstname = encdec.decryptnew(updateTruckersDetails.getApplicant_firstname());
			}
			System.out.print("dummy4");
			/*
			 * if(!updateTruckersDetails.getApplicant_date_of_birth().isEmpty()) { String
			 * applicantdob=encdec.decryptnew(updateTruckersDetails.
			 * getApplicant_date_of_birth()) ; System.out.print(applicantdob);
			 * applicant_date_of_birth = sdf1.parse(applicantdob);
			 * System.out.print(applicant_date_of_birth); }
			 */
			System.out.print("dummy5");

			if (!encdec.decryptnew(updateTruckersDetails.getAge()).isEmpty()) {
				age = Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getAge()));
			}
			System.out.print("dummy16");
			if (!encdec.decryptnew(updateTruckersDetails.getMaritalstatus()).isEmpty()) {
				maritalstatus = encdec.decryptnew(updateTruckersDetails.getMaritalstatus());
			}
			System.out.print("dummy17");
			if (!encdec.decryptnew(updateTruckersDetails.getNominee_name()).isBlank()) {
				nominee_name = encdec.decryptnew(updateTruckersDetails.getNominee_name());
			}
			System.out.println("dummy18");
			/*
			 * if(!updateTruckersDetails.getNominee_dob().contains("")) {String
			 * dob=encdec.decryptnew(updateTruckersDetails.getNominee_dob());
			 * System.out.print(dob); nominee_dob=sdf1.parse(dob);
			 * System.out.print(nominee_dob); }
			 */
			System.out.println("dummy19");
			if (!encdec.decryptnew(updateTruckersDetails.getNominee_age()).isEmpty()) {
				System.out.println("nominee" + updateTruckersDetails.getNominee_age());
				System.out.println("nominee" + (encdec.decryptnew(updateTruckersDetails.getNominee_age())));
				nominee_age = Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getNominee_age()));
				System.out.println("nominee" + nominee_age);
			}
			System.out.println("dummy10");
			if (!encdec.decryptnew(updateTruckersDetails.getNominee_relation()).isEmpty()) {
				nominee_relation = encdec.decryptnew(updateTruckersDetails.getNominee_relation());
			}
			System.out.print("dummy111");
			if (!encdec.decryptnew(updateTruckersDetails.getSpouse_name()).isEmpty()) {
				spouse_name = encdec.decryptnew(updateTruckersDetails.getSpouse_name());
			}
			System.out.print("dummy112");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_father_firstname()).isEmpty()) {
				applicant_father_firstname = encdec.decryptnew(updateTruckersDetails.getApplicant_father_firstname());
			}
			System.out.print("dummy113");
			if (!encdec.decryptnew(updateTruckersDetails.getReligion()).isEmpty()) {
				religion = encdec.decryptnew(updateTruckersDetails.getReligion());
			}
			System.out.print("dummy114");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_qualification()).isEmpty()) {
				applicant_qualification = encdec.decryptnew(updateTruckersDetails.getApplicant_qualification());
			}
			System.out.print("dummy115");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_employment_type()).isEmpty()) {
				applicant_employment_type = encdec.decryptnew(updateTruckersDetails.getApplicant_employment_type());
			}
			System.out.print("dummy116");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_address_line_1()).isEmpty()) {
				applicant_address_line_1 = encdec.decryptnew(updateTruckersDetails.getApplicant_address_line_1());
			}
			System.out.print("dummy117");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_city_name()).isEmpty()) {
				applicant_city_name = encdec.decryptnew(updateTruckersDetails.getApplicant_city_name());
			}
			System.out.println("dummy118");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_pin()).isEmpty()) {
				// applicant_pin=Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getApplicant_pin()));
				applicant_pin1 = Long.parseLong(encdec.decryptnew(updateTruckersDetails.getApplicant_pin()));
			}
			System.out.println("dummy119");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_mobile_no()).isEmpty()) {
				applicant_mobile_no = (encdec.decryptnew(updateTruckersDetails.getApplicant_mobile_no()));
			}
			System.out.println("dummy120");
			if (!encdec.decryptnew(updateTruckersDetails.getNo_of_family_member()).isEmpty()) {
				no_of_family_member = Integer
						.parseInt(encdec.decryptnew(updateTruckersDetails.getNo_of_family_member()));
			}
			System.out.println("dummy121");
			if (!encdec.decryptnew(updateTruckersDetails.getNo_of_earning_member()).isEmpty()) {
				no_of_earning_member = Integer
						.parseInt(encdec.decryptnew(updateTruckersDetails.getNo_of_earning_member()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getHouse_type()).isEmpty()) {
				house_type = encdec.decryptnew(updateTruckersDetails.getHouse_type());
			}
			System.out.println("dummy122");
			if (!encdec.decryptnew(updateTruckersDetails.getRation_Card()).isEmpty()) {
				Ration_Card = encdec.decryptnew(updateTruckersDetails.getRation_Card());
			}
			System.out.print("dummy123");
			if (!encdec.decryptnew(updateTruckersDetails.getMedical_insurance()).isEmpty()) {
				medical_insurance = encdec.decryptnew(updateTruckersDetails.getMedical_insurance());
			}
			System.out.print("dummy124");
			if (!encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_principal()).isEmpty()) {
				current_loan_outstanding_principal = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_principal()));
			}
			System.out.print("dummy125");
			if (!encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_Stringerest()).isEmpty()) {
				current_loan_outstanding_interest = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_Stringerest()));
			}
			System.out.print("dummy126");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_income()).isEmpty()) {
				applicant_income = (encdec.decryptnew(updateTruckersDetails.getApplicant_income()));
			}
			System.out.print("dummy127");
			if (!encdec.decryptnew(updateTruckersDetails.getIncome_from_other_sources()).isEmpty()) {
				income_from_other_sources = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getIncome_from_other_sources()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getFood_expenses()).isEmpty()) {
				food_expenses = Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getFood_expenses()));
			}
			System.out.print("dummy128");
			if (!encdec.decryptnew(updateTruckersDetails.getHouserent()).isEmpty()) {
				houserent = (encdec.decryptnew(updateTruckersDetails.getHouserent()));
			}
			System.out.print("dummy129");
			if (!encdec.decryptnew(updateTruckersDetails.getHouse_renovation_expenses()).isEmpty()) {
				house_renovation_expenses = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getHouse_renovation_expenses()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getTotal_monthly_bill_payment()).isEmpty()) {
				System.out.println("total monthly bill"
						+ encdec.decryptnew(updateTruckersDetails.getTotal_monthly_bill_payment()));
				total_monthly_bill_payment = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getTotal_monthly_bill_payment()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_expense_monthly()).isEmpty()) {
				applicant_expense_monthly = (encdec.decryptnew(updateTruckersDetails.getApplicant_expense_monthly()));
			}
			System.out.print("dummy130");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_id()).isEmpty()) {
				applicant_id = Long.parseLong(encdec.decryptnew(updateTruckersDetails.getApplicant_id()));
			}
			System.out.print("dummy131");
			if (!encdec.decryptnew(updateTruckersDetails.getUpdated_by()).isEmpty()) {
				updated_by = encdec.decryptnew(updateTruckersDetails.getUpdated_by());
			}
			System.out.print("dummy132");
			appRepo.updateTruckersDetails(vehicle_no, company_name, applicant_firstname, applicant_date_of_birth, age,
					maritalstatus, nominee_name, nominee_dob, nominee_age, nominee_relation, spouse_name,
					applicant_father_firstname, religion, applicant_qualification, applicant_employment_type,
					applicant_address_line_1, applicant_city_name, applicant_pin1, applicant_mobile_no,
					no_of_family_member, no_of_earning_member, house_type, Ration_Card, medical_insurance,
					current_loan_outstanding_principal, current_loan_outstanding_interest, applicant_income,
					income_from_other_sources, food_expenses, houserent, house_renovation_expenses,
					total_monthly_bill_payment, applicant_expense_monthly, updated_by, datamoddt, applicant_id);
			httpstatus = HttpStatus.OK;

			response = "updated successfully";
			status = "true";
		} catch (Exception e) {
			httpstatus = HttpStatus.BAD_REQUEST;
			response = "error in updation" + e;
			status = "false";
			LOGGER.error("Error While updating the truckers Applicant" + e.getMessage());

		}
		return ResponseEntity.status(httpstatus)
				.body(new GeneralResponse(encdec.encryptnew(response), encdec.encryptnew(status)));
	}

	@RequestMapping(value = { "/modifyTruckersDetailsDocument/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> modifyTruckersDetailDocument(
			@RequestBody UpdateTruckersDetails updateTruckersDetails) {
		LOGGER.info("Modify Applicant api has been called !!! Start Of Method Modify Applicant");
		HttpStatus httpstatus = null;
		String response = "";
		String status = null;
		try {
			String vehicle_no = null;
			String company_name = null;
			String applicant_firstname = null;

			Date applicant_date_of_birth = null;
			int age = 0;

			String maritalstatus = null;
			String nominee_name = null;
			Date nominee_dob = null;
			int nominee_age = 0;
			String nominee_relation = null;
			String spouse_name = null;
			String applicant_father_firstname = null;
			String religion = null;
			String applicant_qualification = null;
			String applicant_employment_type = null;
			String applicant_address_line_1 = null;
			String applicant_city_name = null;
			int applicant_pin = 0;
			long applicant_pin1 = 0;
			String applicant_mobile_no = null;
			int no_of_family_member = 0;
			int no_of_earning_member = 0;
			String house_type = null;
			String Ration_Card = null;
			String medical_insurance = null;
			Float current_loan_outstanding_principal = (float) 0;
			Float current_loan_outstanding_interest = (float) 0;
			String applicant_income = null;
			Float income_from_other_sources = (float) 0;
			Float food_expenses = (float) 0;
			String houserent = null;
			Float house_renovation_expenses = (float) 0;
			Float total_monthly_bill_payment = (float) 0;
			String applicant_expense_monthly = "";
			Long applicant_id = null;
			String updated_by = null;
			LocalDate datamoddt = LocalDate.now();
			SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
			System.out.print("dummy1");
			if (!updateTruckersDetails.getVehicle_no().isEmpty()) {
				vehicle_no = encdec.decryptnew(updateTruckersDetails.getVehicle_no());
			}
			System.out.print("dummy12");
			if (!updateTruckersDetails.getCompany_name().isEmpty()) {
				company_name = encdec.decryptnew(updateTruckersDetails.getCompany_name());
			}
			System.out.print("dummy13");
			if (!updateTruckersDetails.getApplicant_firstname().isEmpty()) {
				applicant_firstname = encdec.decryptnew(updateTruckersDetails.getApplicant_firstname());
			}
			System.out.print("dummy4");
			/*
			 * if(!updateTruckersDetails.getApplicant_date_of_birth().isEmpty()) { String
			 * applicantdob=encdec.decryptnew(updateTruckersDetails.
			 * getApplicant_date_of_birth()) ; System.out.print(applicantdob);
			 * applicant_date_of_birth = sdf1.parse(applicantdob);
			 * System.out.print(applicant_date_of_birth); }
			 */
			System.out.print("dummy5");

			if (!encdec.decryptnew(updateTruckersDetails.getAge()).isEmpty()) {
				age = Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getAge()));
			}
			System.out.print("dummy16");
			if (!encdec.decryptnew(updateTruckersDetails.getMaritalstatus()).isEmpty()) {
				maritalstatus = encdec.decryptnew(updateTruckersDetails.getMaritalstatus());
			}
			System.out.print("dummy17");
			if (!encdec.decryptnew(updateTruckersDetails.getNominee_name()).isBlank()) {
				nominee_name = encdec.decryptnew(updateTruckersDetails.getNominee_name());
			}
			System.out.println("dummy18");
			/*
			 * if(!updateTruckersDetails.getNominee_dob().contains("")) {String
			 * dob=encdec.decryptnew(updateTruckersDetails.getNominee_dob());
			 * System.out.print(dob); nominee_dob=sdf1.parse(dob);
			 * System.out.print(nominee_dob); }
			 */
			System.out.println("dummy19");
			if (!encdec.decryptnew(updateTruckersDetails.getNominee_age()).isEmpty()) {
				System.out.println("nominee" + updateTruckersDetails.getNominee_age());
				System.out.println("nominee" + (encdec.decryptnew(updateTruckersDetails.getNominee_age())));
				nominee_age = Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getNominee_age()));
				System.out.println("nominee" + nominee_age);
			}
			System.out.println("dummy10");
			if (!encdec.decryptnew(updateTruckersDetails.getNominee_relation()).isEmpty()) {
				nominee_relation = encdec.decryptnew(updateTruckersDetails.getNominee_relation());
			}
			System.out.print("dummy111");
			if (!encdec.decryptnew(updateTruckersDetails.getSpouse_name()).isEmpty()) {
				spouse_name = encdec.decryptnew(updateTruckersDetails.getSpouse_name());
			}
			System.out.print("dummy112");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_father_firstname()).isEmpty()) {
				applicant_father_firstname = encdec.decryptnew(updateTruckersDetails.getApplicant_father_firstname());
			}
			System.out.print("dummy113");
			if (!encdec.decryptnew(updateTruckersDetails.getReligion()).isEmpty()) {
				religion = encdec.decryptnew(updateTruckersDetails.getReligion());
			}
			System.out.print("dummy114");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_qualification()).isEmpty()) {
				applicant_qualification = encdec.decryptnew(updateTruckersDetails.getApplicant_qualification());
			}
			System.out.print("dummy115");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_employment_type()).isEmpty()) {
				applicant_employment_type = encdec.decryptnew(updateTruckersDetails.getApplicant_employment_type());
			}
			System.out.print("dummy116");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_address_line_1()).isEmpty()) {
				applicant_address_line_1 = encdec.decryptnew(updateTruckersDetails.getApplicant_address_line_1());
			}
			System.out.print("dummy117");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_city_name()).isEmpty()) {
				applicant_city_name = encdec.decryptnew(updateTruckersDetails.getApplicant_city_name());
			}
			System.out.println("dummy118");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_pin()).isEmpty()) {
				// applicant_pin=Integer.parseInt(encdec.decryptnew(updateTruckersDetails.getApplicant_pin()));
				applicant_pin1 = Long.parseLong(encdec.decryptnew(updateTruckersDetails.getApplicant_pin()));
			}
			System.out.println("dummy119");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_mobile_no()).isEmpty()) {
				applicant_mobile_no = (encdec.decryptnew(updateTruckersDetails.getApplicant_mobile_no()));
			}
			System.out.println("dummy120");
			if (!encdec.decryptnew(updateTruckersDetails.getNo_of_family_member()).isEmpty()) {
				no_of_family_member = Integer
						.parseInt(encdec.decryptnew(updateTruckersDetails.getNo_of_family_member()));
			}
			System.out.println("dummy121");
			if (!encdec.decryptnew(updateTruckersDetails.getNo_of_earning_member()).isEmpty()) {
				no_of_earning_member = Integer
						.parseInt(encdec.decryptnew(updateTruckersDetails.getNo_of_earning_member()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getHouse_type()).isEmpty()) {
				house_type = encdec.decryptnew(updateTruckersDetails.getHouse_type());
			}
			System.out.println("dummy122");
			if (!encdec.decryptnew(updateTruckersDetails.getRation_Card()).isEmpty()) {
				Ration_Card = encdec.decryptnew(updateTruckersDetails.getRation_Card());
			}
			System.out.print("dummy123");
			if (!encdec.decryptnew(updateTruckersDetails.getMedical_insurance()).isEmpty()) {
				medical_insurance = encdec.decryptnew(updateTruckersDetails.getMedical_insurance());
			}
			System.out.print("dummy124");
			if (!encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_principal()).isEmpty()) {
				current_loan_outstanding_principal = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_principal()));
			}
			System.out.print("dummy125");
			if (!encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_Stringerest()).isEmpty()) {
				current_loan_outstanding_interest = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getCurrent_loan_outstanding_Stringerest()));
			}
			System.out.print("dummy126");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_income()).isEmpty()) {
				applicant_income = (encdec.decryptnew(updateTruckersDetails.getApplicant_income()));
			}
			System.out.print("dummy127");
			if (!encdec.decryptnew(updateTruckersDetails.getIncome_from_other_sources()).isEmpty()) {
				income_from_other_sources = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getIncome_from_other_sources()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getFood_expenses()).isEmpty()) {
				food_expenses = Float.parseFloat(encdec.decryptnew(updateTruckersDetails.getFood_expenses()));
			}
			System.out.print("dummy128");
			if (!encdec.decryptnew(updateTruckersDetails.getHouserent()).isEmpty()) {
				houserent = (encdec.decryptnew(updateTruckersDetails.getHouserent()));
			}
			System.out.print("dummy129");
			if (!encdec.decryptnew(updateTruckersDetails.getHouse_renovation_expenses()).isEmpty()) {
				house_renovation_expenses = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getHouse_renovation_expenses()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getTotal_monthly_bill_payment()).isEmpty()) {
				System.out.println("total monthly bill"
						+ encdec.decryptnew(updateTruckersDetails.getTotal_monthly_bill_payment()));
				total_monthly_bill_payment = Float
						.parseFloat(encdec.decryptnew(updateTruckersDetails.getTotal_monthly_bill_payment()));
			}
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_expense_monthly()).isEmpty()) {
				applicant_expense_monthly = (encdec.decryptnew(updateTruckersDetails.getApplicant_expense_monthly()));
			}
			System.out.print("dummy130");
			if (!encdec.decryptnew(updateTruckersDetails.getApplicant_id()).isEmpty()) {
				applicant_id = Long.parseLong(encdec.decryptnew(updateTruckersDetails.getApplicant_id()));
			}
			System.out.print("dummy131");
			if (!encdec.decryptnew(updateTruckersDetails.getUpdated_by()).isEmpty()) {
				updated_by = encdec.decryptnew(updateTruckersDetails.getUpdated_by());
			}
			System.out.print("dummy132");
			appRepo.updateTruckersDetails(vehicle_no, company_name, applicant_firstname, applicant_date_of_birth, age,
					maritalstatus, nominee_name, nominee_dob, nominee_age, nominee_relation, spouse_name,
					applicant_father_firstname, religion, applicant_qualification, applicant_employment_type,
					applicant_address_line_1, applicant_city_name, applicant_pin1, applicant_mobile_no,
					no_of_family_member, no_of_earning_member, house_type, Ration_Card, medical_insurance,
					current_loan_outstanding_principal, current_loan_outstanding_interest, applicant_income,
					income_from_other_sources, food_expenses, houserent, house_renovation_expenses,
					total_monthly_bill_payment, applicant_expense_monthly, updated_by, datamoddt, applicant_id);
			// storageService.storeTruckers, docName,doc );
			/*
			 * if(!(updateTruckersDetails.getPOI()==null))
			 * {storageService.storeTruckers(updateTruckersDetails.getPOI().get(0).getFile()
			 * , applicant_id,(updateTruckersDetails.getPOI().get(0).getDocument()),
			 * updateTruckersDetails.getPOI().get(0).getDocName());
			 * System.out.println(" Uploaded the POI file successfully: " +
			 * updateTruckersDetails.getPOI().get(0).getFile().getOriginalFilename()); }
			 * if(!(updateTruckersDetails.getPOA()==null))
			 * {storageService.storeTruckers(updateTruckersDetails.getPOA().get(0).getFile()
			 * , applicant_id,updateTruckersDetails.getPOA().get(0).getDocument(),
			 * updateTruckersDetails.getPOA().get(0).getDocName());
			 * System.out.println(" Uploaded the POA file successfully: " +
			 * updateTruckersDetails.getPOA().get(0).getFile().getOriginalFilename()); }
			 * if(!(updateTruckersDetails.getApplicantPhoto()==null))
			 * {storageService.storeTruckers(updateTruckersDetails.getApplicantPhoto().get(0
			 * ).getFile(),
			 * applicant_id,updateTruckersDetails.getApplicantPhoto().get(0).getDocument(),
			 * updateTruckersDetails.getApplicantPhoto().get(0).getDocName());
			 * System.out.println(" Uploaded the ApplicantPhoto file successfully: " +
			 * updateTruckersDetails.getApplicantPhoto().get(0).getFile().
			 * getOriginalFilename()); }
			 */
			LOGGER.info("End Of Method uploadFile !!!");
			httpstatus = HttpStatus.OK;

			response = "updated successfully";
			status = "true";
		} catch (Exception e) {
			httpstatus = HttpStatus.BAD_REQUEST;
			response = "error in updation" + e;
			status = "false";
			LOGGER.error("Error While updating the truckers Applicant" + e.getMessage());

		}
		return ResponseEntity.status(httpstatus)
				.body(new GeneralResponse(encdec.encryptnew(response), encdec.encryptnew(status)));
	}

	@GetMapping(path = "/downloadTruckerscsv")
	public void getAllEmployeesInCsv(HttpServletResponse servletResponse,@RequestParam("applicant_id_List") List list1,@RequestParam("company_id")String company_id) {
		try {
			servletResponse.setContentType("text/csv");
			// Agent_msg_dtls msg=new Agent_msg_dtls();
			List applicant=new ArrayList<>();
			servletResponse.addHeader("Content-Disposition", "attachment; filename=\"TruckersDetails.csv\"");
			for(int i=0;i<list1.size();i++) {
				String app1=(list1.get(i).toString());
				System.out.println(app1);
				//String user=encdec.decryptnew(list1.get(i).toString());
			String app=(list1.get(i).toString());
			System.out.println("app in String"+app);
			Long ap=Long.parseLong(app);
				System.out.println("app in long"+app);
				applicant.add(i, ap);
			}
			System.out.println(applicant);
			System.out.println(company_id);
			List<Applicant> lst = appRepo.findMultipleApplicant(applicant);
			ICsvBeanWriter csvwriter = new CsvBeanWriter(servletResponse.getWriter(),
					CsvPreference.STANDARD_PREFERENCE);
			String company=(company_id);
			if(company.contains("SH")) {
				String[] csvHeader = { "Applicant_id", "VEHICLE NO", "COMPANY NAME", "TRUCK_DRIVER_NAME",
						"DATE_OF_BIRTH (DD/MM/YY)", "AGE", "MARRIED/UN MARRIED/WIDOW/WIDOWER/DIVORCED", "NOMINEE_NAME",
						"NOMINEE_DOB (DD/MM/YYYY)", "NOMINEE_AGE", "NOMINEE_RELATION", "SPOUSE NAME", "FATHER NAME",
						"RELIGION", "Education 1. Upto Class 10, Class 12. Graduate and avove", "Permanent/Contract Job",
						"ADDRESS", "VILLAGE_NAME/ CITY", "PINCODE", "Contact N os Phone / Mobile", "Nos Of Family Members",
						"Nos of working Members", "House Own/ Rented", "Ration Card Y/N", "Mrdical Insurance Y/N",
						"CURRENT A|LAON OUTSTANDING_PRINCIPAL (IF ANY)", "CURRENT LOAN OUTSTANDING_INTEREST",
						"TRUCK_INCOME", "INCOME FROM OTHER SOURCES", "Monthly Food Expenditure", "Rent", "House Repair",
						"Total Monthly Bill Payment (Elec, Water etyc.)", "Total Monthly Expenses", "created_by",
						"Date of entry","predicted_score","estimated_income" };

				// String[] nameMapping =
				// {"Scan_Date","System_Make","System_form_Factor","system_model_no","system_serial_no","Product_Type",
				// "system_ip","System_Hostname","System_OS_type","OS_License_details","OS_Version","OS_Key","Total_RAM","RAM_Available","RAM_Used","HD_Make","HD_Model","HD_Serial_Number","HD_Capacity","proccessor","MBD_Make","MBD_Model","mbd_serial_no","Type_of_Chipset","Monitor_Make","Monitor_Model","Monitor_Serial_Number","Monitor_Screen_Size","Assets_Status","Retired_Date","Software_list_with_version_and_installed_Date","Procured_Date","Procument_ID","Warranty_AMC","Warranty_AMC_Vendor_Name","Warrenty_AMC_From","Warrenty_AMC_To","username","Department_Name","Site_Name","Sub_Department_Name","Aforesight_Agent_ID","MS_Office_2010",
				// "MS_Office_2013", "MS_Office_2016","Adobe_Reader", "Java8",
				// "Symantec_Antivirus", "Mcafee_Antivirus", "Trend_Micro_Antivirus",
				// "Microsoft_Teams", "MS_Office_2007", "Anydesk",
				// "OneDrive","zip7","Mozilla_Firefox",
				// "Google_Chrome","Team_Viewer","Zoom","Webex","AutoCad","Winrar"};
				String[] nameMapping = { "Applicant_id", "vehicle_no", "company_name", "applicant_firstname",
						"applicant_date_of_birth", "age", "maritalstatus", "nominee_name", "nominee_dob", "nominee_age",
						"nominee_relation", "spouse_name", "applicant_father_firstname", "religion",
						"applicant_qualification", "applicant_employment_type", "applicant_address_line_1",
						"applicant_city_name", "applicant_pin", "applicant_mobile_no", "no_of_family_member",
						"no_of_earning_member", "house_type", "Ration_Card", "medical_insurance",
						"current_loan_outstanding_principal", "current_loan_outstanding_interest", "applicant_income",
						"income_from_other_sources", "food_expenses", "houserent", "house_renovation_expenses",
						"total_monthly_bill_payment", "applicant_expense_monthly", "created_by", "dataentdt","predicted_score","estimated_income" };
				csvwriter.writeHeader(csvHeader);
				for (Applicant msg : lst) {

					csvwriter.write(msg, nameMapping);
				}
			}
			else {
			String[] csvHeader = { "Applicant_id", "VEHICLE NO", "COMPANY NAME", "TRUCK_DRIVER_NAME",
					"DATE_OF_BIRTH (DD/MM/YY)", "AGE", "MARRIED/UN MARRIED/WIDOW/WIDOWER/DIVORCED", "NOMINEE_NAME",
					"NOMINEE_DOB (DD/MM/YYYY)", "NOMINEE_AGE", "NOMINEE_RELATION", "SPOUSE NAME", "FATHER NAME",
					"RELIGION", "Education 1. Upto Class 10, Class 12. Graduate and avove", "Permanent/Contract Job",
					"ADDRESS", "VILLAGE_NAME/ CITY", "PINCODE", "Contact N os Phone / Mobile", "Nos Of Family Members",
					"Nos of working Members", "House Own/ Rented", "Ration Card Y/N", "Mrdical Insurance Y/N",
					"CURRENT A|LAON OUTSTANDING_PRINCIPAL (IF ANY)", "CURRENT LOAN OUTSTANDING_INTEREST",
					"TRUCK_INCOME", "INCOME FROM OTHER SOURCES", "Monthly Food Expenditure", "Rent", "House Repair",
					"Total Monthly Bill Payment (Elec, Water etyc.)", "Total Monthly Expenses", "created_by",
					"Date of entry" };

			// String[] nameMapping =
			// {"Scan_Date","System_Make","System_form_Factor","system_model_no","system_serial_no","Product_Type",
			// "system_ip","System_Hostname","System_OS_type","OS_License_details","OS_Version","OS_Key","Total_RAM","RAM_Available","RAM_Used","HD_Make","HD_Model","HD_Serial_Number","HD_Capacity","proccessor","MBD_Make","MBD_Model","mbd_serial_no","Type_of_Chipset","Monitor_Make","Monitor_Model","Monitor_Serial_Number","Monitor_Screen_Size","Assets_Status","Retired_Date","Software_list_with_version_and_installed_Date","Procured_Date","Procument_ID","Warranty_AMC","Warranty_AMC_Vendor_Name","Warrenty_AMC_From","Warrenty_AMC_To","username","Department_Name","Site_Name","Sub_Department_Name","Aforesight_Agent_ID","MS_Office_2010",
			// "MS_Office_2013", "MS_Office_2016","Adobe_Reader", "Java8",
			// "Symantec_Antivirus", "Mcafee_Antivirus", "Trend_Micro_Antivirus",
			// "Microsoft_Teams", "MS_Office_2007", "Anydesk",
			// "OneDrive","zip7","Mozilla_Firefox",
			// "Google_Chrome","Team_Viewer","Zoom","Webex","AutoCad","Winrar"};
			String[] nameMapping = { "Applicant_id", "vehicle_no", "company_name", "applicant_firstname",
					"applicant_date_of_birth", "age", "maritalstatus", "nominee_name", "nominee_dob", "nominee_age",
					"nominee_relation", "spouse_name", "applicant_father_firstname", "religion",
					"applicant_qualification", "applicant_employment_type", "applicant_address_line_1",
					"applicant_city_name", "applicant_pin", "applicant_mobile_no", "no_of_family_member",
					"no_of_earning_member", "house_type", "Ration_Card", "medical_insurance",
					"current_loan_outstanding_principal", "current_loan_outstanding_interest", "applicant_income",
					"income_from_other_sources", "food_expenses", "houserent", "house_renovation_expenses",
					"total_monthly_bill_payment", "applicant_expense_monthly", "created_by", "dataentdt" };
			csvwriter.writeHeader(csvHeader);
			for (Applicant msg : lst) {

				csvwriter.write(msg, nameMapping);
			}}
			csvwriter.close();

		} catch (Exception e) {
			LOGGER.error("error while downloading truckers data" + e.getMessage());
		}
	}

	@RequestMapping(value = { "/GetApplicantListenc" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.ApplicantPredictionListResponse> GetApplicantPrediction(
			@RequestBody ApplicantPayload appPayload) {
		LOGGER.info("get  applicant   by id api has been called !!! Start Of Method get applicant by id");

		HttpStatus httpstatus = null;
		String response = "";
		String status = null;

		List<ApplicatListPrediction> applicant = new ArrayList<ApplicatListPrediction>();
		System.out.println("details retrived before");
		try {
			System.out.println("details retrived successfully1");
			List<Applicant> applicantDetails = appRepo
					.findByApplicant_id(Long.parseLong(encdec.decryptnew(appPayload.getApplicant_id())));
			System.out.println("details retrived successfully");
			if (applicantDetails == null) {

				response = " applicant details not available";
			} else {
				System.out.println("encyption of list");
				int i = 0;
				for (i = 0; i < applicantDetails.size(); i++) {
					System.out.println("for loop");
					ApplicatListPrediction app = new ApplicatListPrediction();
					System.out.println("encyption of list1");
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
					if (!(applicantDetails).get(i).getVehicle_no().isEmpty()) {
						app.setVehicle_no(encdec.encryptnew((applicantDetails).get(i).getVehicle_no()));
					}
					System.out.println("encyption of list2");
					if (!(applicantDetails.get(i).getCompany_name().isEmpty())) {
						app.setCompany_name(encdec.encryptnew((applicantDetails).get(i).getCompany_name()));
					}
					System.out.println("encyption of list3");
					if (!(applicantDetails).get(i).getApplicant_firstname().isEmpty()) {
						app.setApplicant_firstname(
								encdec.encryptnew((applicantDetails).get(i).getApplicant_firstname()));
					}
					System.out.println("encyption of list4");
					/*
					 * if(!( applicantDetails.get(i).getApplicant_date_of_birth()).isEmpty()) {
					 * String dob=sdf1.format((
					 * applicantDetails).get(i).getApplicant_date_of_birth()) ;
					 * app.setApplicant_Date_of_birth(encdec.encryptnew(dob)); }
					 */
					System.out.println("encyption of list5");
					if (!((applicantDetails).get(i).getAge() == 0)) {
						String age = (String.valueOf(applicantDetails.get(i).getAge()));
						app.setAge(encdec.encryptnew(age));
					}
					if (!applicantDetails.get(i).getMaritalstatus().isEmpty()) {
						app.setMaritalstatus(encdec.encryptnew(applicantDetails.get(i).getMaritalstatus()));
					}

					if (!applicantDetails.get(i).getNominee_name().isEmpty()) {
						app.setNominee_name(encdec.encryptnew(applicantDetails.get(i).getNominee_name()));
					}
					/*
					 * if(applicantDetails.get(i).getNominee_dob() != null) {String
					 * dob=(encdec.encryptnew(sdf1.format(applicantDetails.get(i).getNominee_dob()))
					 * ); app.setNominee_dob((dob)); }
					 */
					System.out.println("encyption of list6");
					if (!(applicantDetails.get(i).getNominee_age() == 0)) {
						app.setNominee_age(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getNominee_age())));
					}
					if (!applicantDetails.get(i).getNominee_relation().isEmpty()) {
						app.setNominee_relation(encdec.encryptnew(applicantDetails.get(i).getNominee_relation()));
					}
					System.out.println("encyption of list7");
					if (!applicantDetails.get(i).getSpouse_name().isEmpty()) {
						app.setSpouse_name(encdec.encryptnew(applicantDetails.get(i).getSpouse_name()));
					}
					if (!applicantDetails.get(i).getApplicant_father_firstname().isEmpty())
						app.setApplicant_father_firstname(
								encdec.encryptnew(applicantDetails.get(i).getApplicant_father_firstname()));
					if (!applicantDetails.get(i).getReligion().isEmpty()) {
						app.setReligion(encdec.encryptnew(applicantDetails.get(i).getReligion()));
					}
					System.out.println("encyption of list8");
					if (!applicantDetails.get(i).getApplicant_qualification().isEmpty()) {
						app.setApplicant_qualification(
								encdec.encryptnew(applicantDetails.get(i).getApplicant_qualification()));
					}
					if (!applicantDetails.get(i).getApplicant_employment_type().isEmpty()) {
						app.setApplicant_employment_type(
								encdec.encryptnew(applicantDetails.get(i).getApplicant_employment_type()));
					}
					if (!applicantDetails.get(i).getApplicant_address_line_1().isEmpty()) {
						app.setApplicant_address_line_1(
								encdec.encryptnew(applicantDetails.get(i).getApplicant_address_line_1()));
					}
					if (!applicantDetails.get(i).getApplicant_city_name().isEmpty()) {
						app.setApplicant_city_name(encdec.encryptnew(applicantDetails.get(i).getApplicant_city_name()));
					}
					if (!(applicantDetails.get(i).getApplicant_PIN() == null)) {
						app.setApplicant_pin(
								(encdec.encryptnew(String.valueOf(applicantDetails.get(i).getApplicant_PIN()))));
					}
					System.out.println("encyption of list9");
					if (!applicantDetails.get(i).getApplicant_mobile_no().isEmpty()) {
						app.setApplicant_mobile_no(
								(encdec.encryptnew(applicantDetails.get(i).getApplicant_mobile_no())));
					}
					if (!(applicantDetails.get(i).getNo_of_family_member() == 0)) {
						app.setNo_of_family_member(
								encdec.encryptnew(String.valueOf(applicantDetails.get(i).getNo_of_family_member())));
					}
					System.out.println("encyption of list10");
					if (!(applicantDetails.get(i).getNo_of_earning_member() == 0)) {
						app.setNo_of_earning_member(
								encdec.encryptnew(String.valueOf(applicantDetails.get(i).getNo_of_earning_member())));
					}
					if (!applicantDetails.get(i).getHouse_type().isEmpty()) {
						app.setHouse_type(encdec.encryptnew(applicantDetails.get(i).getHouse_type()));
					}
					if (!applicantDetails.get(i).getRation_card().isEmpty()) {
						app.setRation_Card(encdec.encryptnew(applicantDetails.get(i).getRation_card()));
					}
					if (!applicantDetails.get(i).getMedical_insurance().isEmpty()) {
						app.setMedical_insurance(encdec.encryptnew(applicantDetails.get(i).getMedical_insurance()));
					}
					if (!(applicantDetails.get(i).getCurrent_loan_outstanding_principal() == null)) {
						app.setCurrent_loan_outstanding_principal(encdec.encryptnew(
								String.valueOf(applicantDetails.get(i).getCurrent_loan_outstanding_principal())));
					}
					if (!(applicantDetails.get(i).getCurrent_loan_outstanding_interest() == null)) {
						app.setCurrent_loan_outstanding_Stringerest(encdec.encryptnew(
								String.valueOf(applicantDetails.get(i).getCurrent_loan_outstanding_interest())));
					}
					if (!applicantDetails.get(i).getApplicant_income().isEmpty()) {
						app.setApplicant_income((encdec.encryptnew(applicantDetails.get(i).getApplicant_income())));
					}
					if (!(applicantDetails.get(i).getIncome_from_other_sources() == null)) {
						app.setIncome_from_other_sources(encdec
								.encryptnew(String.valueOf(applicantDetails.get(i).getIncome_from_other_sources())));
					}
					System.out.println("encyption of list11");
					if (!(applicantDetails.get(i).getFood_expenses()==0)) {
						app.setFood_expenses(
								encdec.encryptnew(String.valueOf(applicantDetails.get(i).getFood_expenses())));
					}
					if (!applicantDetails.get(i).getHouserent().isEmpty()) {
						app.setHouserent((encdec.encryptnew(applicantDetails.get(i).getHouserent())));
					}
					System.out.println("encyption of list12");
					if (!(applicantDetails.get(i).getHouse_renovation_expenses() == 0)) {
						app.setHouse_renovation_expenses(encdec
								.encryptnew(String.valueOf(applicantDetails.get(i).getHouse_renovation_expenses())));
					}
					System.out.println("encyption of list13");
					if (!(applicantDetails.get(i).getTotal_monthly_bill_payment() == null)) {
						app.setTotal_monthly_bill_payment(encdec
								.encryptnew(String.valueOf(applicantDetails.get(i).getTotal_monthly_bill_payment())));
					}
					if (!applicantDetails.get(i).getApplicant_expense_monthly().isEmpty()) {
						System.out.println("encyption of list14");
						app.setApplicant_expense_monthly(
								(encdec.encryptnew(applicantDetails.get(i).getApplicant_expense_monthly())));
					}
					System.out.println("encyption of list15");
					applicant.add(i, app);

				}
				// System.out.println("dummy2");
				response = " Applicant detail is retrieved successfully";

			}
			System.out.println("dummy3");
			status = "true";
			httpstatus = HttpStatus.OK;
		}

		catch (Exception e) {
			LOGGER.error("Error While retreiving user" + e.getMessage());
			response = "Error While retreiving  user" + e.getMessage();
			status = "false";
			httpstatus = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus)
				.body(new ApplicantPredictionListResponse(applicant, (response), (status)));
	}

	@RequestMapping(value = { "/GetApplicantList" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.ApplicantPredictionListResponse> GetApplicant(
			@RequestBody ApplicantPayload appPayload) {
		LOGGER.info("get  applicant   by id api has been called !!! Start Of Method get applicant by id");

		HttpStatus httpstatus = null;
		String response = "";
		String status = null;

		List<ApplicatListPrediction> applicant = new ArrayList<ApplicatListPrediction>();
		System.out.println("details retrived before");
		try {
			System.out.println("details retrived successfully1");
			List<Applicant> applicantDetails = appRepo
					.findByApplicant_id(Long.parseLong((appPayload.getApplicant_id())));
			System.out.println("details retrived successfully");
			if (applicantDetails == null) {

				response = " applicant details not available";
			} else {
				System.out.println("encyption of list");
				int i = 0;
				for (i = 0; i < applicantDetails.size(); i++) {
					System.out.println("for loop");
					ApplicatListPrediction app = new ApplicatListPrediction();
					System.out.println("encyption of list1");
					SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
					if (!((applicantDetails).get(i).getVehicle_no() == null)) {
						app.setVehicle_no(((applicantDetails).get(i).getVehicle_no()));
					}
					System.out.println("encyption of list2");
					if (!(applicantDetails.get(i).getCompany_name() == null)) {
						app.setCompany_name(((applicantDetails).get(i).getCompany_name()));
					}
					System.out.println("encyption of list3");
					if (!((applicantDetails).get(i).getApplicant_firstname() == null)) {
						app.setApplicant_firstname(((applicantDetails).get(i).getApplicant_firstname()));
					}
					System.out.println("encyption of list4");
					/*
					 * if(!( applicantDetails.get(i).getApplicant_date_of_birth())==null) { String
					 * dob=sdf1.format(( applicantDetails).get(i).getApplicant_date_of_birth()) ;
					 * app.setApplicant_Date_of_birth(encdec.encryptnew(dob)); }
					 */
					System.out.println("encyption of list5");
					if (!((applicantDetails).get(i).getAge() == 0)) {
						String age = (String.valueOf(applicantDetails.get(i).getAge()));
						app.setAge((age));
					}
					if (!(applicantDetails.get(i).getMaritalstatus() == null)) {
						app.setMaritalstatus((applicantDetails.get(i).getMaritalstatus()));
					}

					if (!(applicantDetails.get(i).getNominee_name() == null)) {
						app.setNominee_name((applicantDetails.get(i).getNominee_name()));
					}
					/*
					 * if(applicantDetails.get(i).getNominee_dob() != null) {String
					 * dob=(encdec.encryptnew(sdf1.format(applicantDetails.get(i).getNominee_dob()))
					 * ); app.setNominee_dob((dob)); }
					 */
					System.out.println("encyption of list6");
					if (!(applicantDetails.get(i).getNominee_age() == 0)) {
						app.setNominee_age((String.valueOf(applicantDetails.get(i).getNominee_age())));
					}
					if (!(applicantDetails.get(i).getNominee_relation() == null)) {
						app.setNominee_relation((applicantDetails.get(i).getNominee_relation()));
					}
					System.out.println("encyption of list7");
					if (!(applicantDetails.get(i).getSpouse_name() == null)) {
						app.setSpouse_name((applicantDetails.get(i).getSpouse_name()));
					}
					if (!(applicantDetails.get(i).getApplicant_father_firstname() == null))
						app.setApplicant_father_firstname((applicantDetails.get(i).getApplicant_father_firstname()));
					if (!(applicantDetails.get(i).getReligion() == null)) {
						app.setReligion((applicantDetails.get(i).getReligion()));
					}
					System.out.println("encyption of list8");
					if (!(applicantDetails.get(i).getApplicant_qualification() == null)) {
						app.setApplicant_qualification((applicantDetails.get(i).getApplicant_qualification()));
					}
					if (!(applicantDetails.get(i).getApplicant_employment_type() == null)) {
						app.setApplicant_employment_type((applicantDetails.get(i).getApplicant_employment_type()));
					}
					if (!(applicantDetails.get(i).getApplicant_address_line_1() == null)) {
						app.setApplicant_address_line_1((applicantDetails.get(i).getApplicant_address_line_1()));
					}
					if (!(applicantDetails.get(i).getApplicant_city_name() == null)) {
						app.setApplicant_city_name((applicantDetails.get(i).getApplicant_city_name()));
					}
					if (!(applicantDetails.get(i).getApplicant_PIN() == null)) {
						app.setApplicant_pin(((String.valueOf(applicantDetails.get(i).getApplicant_PIN()))));
					}
					System.out.println("encyption of list9");
					if (!(applicantDetails.get(i).getApplicant_mobile_no() == null)) {
						app.setApplicant_mobile_no(((applicantDetails.get(i).getApplicant_mobile_no())));
					}
					if (!(applicantDetails.get(i).getNo_of_family_member() == 0)) {
						app.setNo_of_family_member((String.valueOf(applicantDetails.get(i).getNo_of_family_member())));
					}
					System.out.println("encyption of list10");
					if (!(applicantDetails.get(i).getNo_of_earning_member() == 0)) {
						app.setNo_of_earning_member(
								(String.valueOf(applicantDetails.get(i).getNo_of_earning_member())));
					}
					if (!(applicantDetails.get(i).getHouse_type() == null)) {
						app.setHouse_type((applicantDetails.get(i).getHouse_type()));
					}
					if (!(applicantDetails.get(i).getRation_card() == null)) {
						app.setRation_Card((applicantDetails.get(i).getRation_card()));
					}
					if (!(applicantDetails.get(i).getMedical_insurance() == null)) {
						app.setMedical_insurance(applicantDetails.get(i).getMedical_insurance());
					}
					if (!(applicantDetails.get(i).getCurrent_loan_outstanding_principal() == null)) {
						app.setCurrent_loan_outstanding_principal(
								(String.valueOf(applicantDetails.get(i).getCurrent_loan_outstanding_principal())));
					}
					if (!(applicantDetails.get(i).getCurrent_loan_outstanding_interest() == null)) {
						app.setCurrent_loan_outstanding_Stringerest(
								(String.valueOf(applicantDetails.get(i).getCurrent_loan_outstanding_interest())));
					}
					if (!(applicantDetails.get(i).getApplicant_income() == null)) {
						app.setApplicant_income(((applicantDetails.get(i).getApplicant_income())));
					}
					if (!(applicantDetails.get(i).getIncome_from_other_sources() == null)) {
						app.setIncome_from_other_sources(
								(String.valueOf(applicantDetails.get(i).getIncome_from_other_sources())));
					}
					System.out.println("encyption of list11");
					if (!(applicantDetails.get(i).getFood_expenses() == 0)) {
						app.setFood_expenses((String.valueOf(applicantDetails.get(i).getFood_expenses())));
					}
					if (!(applicantDetails.get(i).getHouserent() == null)) {
						app.setHouserent(((applicantDetails.get(i).getHouserent())));
					}
					System.out.println("encyption of list12");
					if (!(applicantDetails.get(i).getHouse_renovation_expenses() == 0)) {
						app.setHouse_renovation_expenses(
								(String.valueOf(applicantDetails.get(i).getHouse_renovation_expenses())));
					}
					System.out.println("encyption of list13");
					if (!((applicantDetails.get(i).getTotal_monthly_bill_payment() == null))) {
						app.setTotal_monthly_bill_payment(
								(String.valueOf(applicantDetails.get(i).getTotal_monthly_bill_payment())));
					}
					if (!(applicantDetails.get(i).getApplicant_expense_monthly() == null)) {
						System.out.println("encyption of list14");
						app.setApplicant_expense_monthly(((applicantDetails.get(i).getApplicant_expense_monthly())));
					}
					System.out.println("encyption of list15");
					applicant.add(i, app);

				}
				// System.out.println("dummy2");
				response = " Applicant detail is retrieved successfully";

			}
			System.out.println("dummy3");
			status = "true";
			httpstatus = HttpStatus.OK;
		}

		catch (Exception e) {
			LOGGER.error("Error While retreiving user" + e.getMessage());
			response = "Error While retreiving  user" + e.getMessage();
			status = "false";
			httpstatus = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus)
				.body(new ApplicantPredictionListResponse(applicant, (response), (status)));
	}
	@RequestMapping(value = { "/eKYC/retrieve/v1" }, method = RequestMethod.GET,
			  produces = { MediaType.APPLICATION_JSON_VALUE })
			 
			  @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<ResponseOld>
			  eKYCRetrieve(@RequestParam("applicant_id") Applicant newApplicant) { LOGGER.
			  info("Retrieve ekyc api has been called !!! Start Of Method EkycRetrieve");
			  Boolean Status = false; List<DocEkyc> DocResponse = new ArrayList<>(); try {
			  List<Object[]> docs = entitymanager.createNativeQuery(
			  "SELECT f.data,f.applicant_id from kosh_db_v1.ekycfile f where f.applicant_id= "
			  + newApplicant.getApplicant_id()) .getResultList();
			  
			  List<String> images = new ArrayList<>();
			  
			  if (docs.size() != 0) { System.out.println(docs.size()); for (Object d :
			  docs) { DocEkyc ekyc = new DocEkyc(); Object[] obj = (Object[]) d;
			  
			  String base64Image = Base64.getEncoder().encodeToString((byte[]) obj[0]);
			  
			  ekyc.setPhotoString("data:image/jpeg;base64," + base64Image);
			  images.add("data:image/jpeg;base64," + base64Image); long a_id =
			  Long.parseLong(obj[1].toString()); ekyc.setApplicant_id(a_id);
			 DocResponse.add(ekyc); LOGGER.info("End Of Method EkycRetrieve"); } Status =
			  applicantService.authImage(images); System.out.println(images);
			  
			  } } catch (Exception e) { 
				  LOGGER.error("Error While Retrieve" +e.getMessage()); 
				  return ResponseEntity.badRequest().body(new
			  ResponseOld(e.getMessage(), Boolean.FALSE, Status)); } return
			  ResponseEntity.status(HttpStatus.OK).body(new ResponseOld(Success, Boolean.TRUE,
			  Status));
			  }
	@RequestMapping(value = { "/eKYC/v1" }, method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ResponseOld> eKYC(@RequestParam("applicant_id") Applicant newApplicant) {
		LOGGER.info("Ekyc api has been called !!! Start Of Method eKYC");

		List<EKYC> response = new ArrayList<>();
		try {
			List<Object[]> docs = entitymanager.createNativeQuery(
					"SELECT f.data,f.doc_name,f.document,a.applicant_date_of_BIRTH as dob,a.APPLICANT_ID,a.APPLICANT_EMAIL_ID as email,a.APPLICANT_MOBILE_NO as mobile,a.APPLICANT_FIRSTNAME as firstname,a.APPLICANT_FATHER_FIRSTNAME as fathersfirstname,a.GENDER, a.APPLICANT_FATHER_MIDDLE_NAME as fathersmiddlename,a.APPLICANT_FATHER_LASTNAME as fatherslastname,a.APPLICANT_MIDDLE_NAME as middlename,a.APPLICANT_LASTNAME as lastname  from  borrowrenewdb.applicant_table a  join borrowrenewdb.files f on a.applicant_id = f.applicant_id where a.applicant_id="
							+ newApplicant.getApplicant_id())
					.getResultList();
			System.out.println("doc size is"+docs.size());
			if (docs.size() != 0) {
				for (Object d : docs) {
					EKYC ekyc = new EKYC();
					Object[] obj = (Object[]) d;
					System.out.println("debug1");
					String base64Image = Base64.getEncoder().encodeToString((byte[]) obj[0]);
					ekyc.setPhoto("data:image/jpeg;base64," + base64Image);
					System.out.println("debug2");
					ekyc.setPhotos((byte[]) obj[0]);
					ekyc.setDocName((String) obj[1]);
					System.out.println("debug3");
					System.out.println("debug4");
					ekyc.setDoc((String) obj[2]);
					ekyc.setDob((Date) obj[3]);
					System.out.println("debug5");
					long app_id = Long.parseLong(obj[4].toString());
					System.out.println("debug6");
					ekyc.setApplicant_id(app_id);
					// ekyc.setApplicant_id((long) obj[3]);
					ekyc.setEmail((String) obj[5]);
					System.out.println("debug7");
					ekyc.setMobile((String) obj[6]);
					ekyc.setApplicant_name((String) obj[7]);
					System.out.println("debug8");
					ekyc.setFathersName((String) obj[8]);
					ekyc.setGender((String) obj[9]);
					System.out.println("debug9");
					ekyc.setFathersMiddleName((String) obj[10]);
					System.out.println("debug10");
					ekyc.setFathersLastName((String) obj[11]);
					ekyc.setApplicant_middle_name((String) obj[12]);
					System.out.println("debug11");
					ekyc.setApplicant_lastname((String) obj[13]);
					response.add(ekyc);
					System.out.println("debug12");
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error While fetching :" + e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseOld(e.getMessage(), Boolean.FALSE, new ArrayList<>()));
		}
		// return response;
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseOld(Success, Boolean.TRUE, response));
	}
			   
			   // @PostMapping("/upload/ekyc/v1")
			   
			   @RequestMapping(value = { "/upload/ekyc/v1" }, method = RequestMethod.POST,
			   produces = { MediaType.APPLICATION_JSON_VALUE })
			   
			   @ResponseStatus(value = HttpStatus.OK) public ResponseEntity<ResponseMessage>
			   uploadFile(@RequestParam("file") MultipartFile file,
			   
			   @RequestParam("applicant_id") Applicant applicant) {
			   LOGGER.info("Upload ekyc api has been called !!! Start Of Method uploadFile");
			    String message = ""; 
			   try {  
			   storageService.store(file,applicant);
			   message = "Uploaded the file successfully: " +
			   file.getOriginalFilename(); LOGGER.info("End Of Method uploadFile"); return
			   ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message)); }
			   catch (Exception e) { message = "Could not upload the file: " +
			   file.getOriginalFilename() + "!";
			   LOGGER.error("Error While Uploading the File" + e.getMessage()); return
			   ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new
			   ResponseMessage(message)); } }
}
