package com.securedloan.arthavedika.controller;

import java.io.UnsupportedEncodingException;
import com.securedloan.arthavedika.EncryptionDecryptionClass;
import java.util.Date;
import java.util.List;
import java.util.Random;
import com.securedloan.arthavedika.response.CompanyEnc;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
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

import com.securedloan.arthavedika.exception.UserNotFoundException;
import com.securedloan.arthavedika.model.Company;
import com.securedloan.arthavedika.model.LoginDetail;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.Add_modifyUser;
import com.securedloan.arthavedika.payload.ChangePassword;
import com.securedloan.arthavedika.payload.ForgetPassword;
import com.securedloan.arthavedika.payload.LoginUserPost;
import com.securedloan.arthavedika.payload.UserPayload;
import com.securedloan.arthavedika.repo.CompanyRepo;
import com.securedloan.arthavedika.repo.UserRepository;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.Response;
import com.securedloan.arthavedika.response.ResponseOld;
import com.securedloan.arthavedika.response.Result;
import com.securedloan.arthavedika.service.Mail;
import com.securedloan.arthavedika.service.ResetPassword;
import com.securedloan.arthavedika.service.UserService;
import com.securedloan.arthavedika.utility.Utility;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("user")
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	@Value("${error_mob_no}")
	private String mobErrorMsg;

	@Value("${error_email_id}")
	private String emailErrorMsg;

	@Value("${verify-email}")
	private String emailVerify;

	@Value("${login-success}")
	private String loginSuccess;

	@Value("${login-failed}")
	private String loginFailed;

	@Value("${accNtVerified}")
	private String accountNtVerified;

	@Value("${verificationSuccess}")
	private String success;

	@Value("${verificationFailed}")
	private String failed;

	@Value("${checkMail}")
	private String chkMail;

	@Value("${otpInvalid}")
	private String otpInvalid;

	@Value("${passwordChangeSuccess}")
	private String passChngSuccess;

	@Value("${logout-success}")
	private String logoutSuccess;

	@Value("${logout-failed}")
	private String logoutFailed;

	@Autowired
	private Mail sendEmailService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private ResetPassword resetPassService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private CompanyRepo companyrepo;
	@Autowired
	private Mail mail;
	

	@RequestMapping(value = { "/signIn/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Result> registerUser(@Valid @RequestBody User newUser) {
		LOGGER.info("SignIn api has been called !!! Start Of Method registerUser");
		try {

			List<User> users = userService.findAllUser();
			for (User user : users) {
				if (user.equalsMobile(newUser))
					// return new Result("Mobile number Already Exist", Boolean.FALSE);
					return ResponseEntity.status(HttpStatus.OK).body(new Result(encdec.encryptnew(mobErrorMsg), encdec.encryptnew("FALSE")));

				else if (user.equalsEmail(newUser))
					// return new Result("Email Already Exist", Boolean.FALSE);
					return ResponseEntity.status(HttpStatus.OK).body(new Result(encdec.encryptnew(emailErrorMsg),encdec.encryptnew("FALSE")));
			}

			Random random = new Random();
			String randomCode = String.format("%04d", random.nextInt(10000));
			newUser.setOtp(randomCode);
			userService.saveUser(newUser);
			sendEmailService.sendEmail(newUser, randomCode);
			// return new Result("Please Verify Your Email", Boolean.TRUE);
			LOGGER.info("End Of Method registerUser");
			return ResponseEntity.status(HttpStatus.OK).body(new Result(encdec.encryptnew(emailVerify), encdec.encryptnew("TRUE")));
		} catch (Exception e) {
			LOGGER.error("Error While SignIn" + e.getMessage());
			return ResponseEntity.badRequest().body(new Result(e.getMessage(), encdec.encryptnew("FALSE")));
		}
	}
	
	@RequestMapping(value = { "/loginPost/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> loginUserPost(@RequestBody LoginUserPost loginUserPayload) {
		LOGGER.info("LognIn api has been called !!! Start Of Method loginUser");
		CompanyEnc comp=new CompanyEnc();
		try {
			List<User> users = (List<User>) userService.findUsers(encdec.decryptnew(loginUserPayload.getMobile_no()),encdec.decryptnew( loginUserPayload.getPassword()));
		
			if (users.size() > 0) {
				if(users.get(0).getIs_first_login().equals("Y"))
				{
					userRepo.updateIsfirstLogin(users.get(0).getUser_id());
				}
				Company com=companyrepo.company_details(users.get(0).getCompany_code());
				if(com.getCompany_id()!=null) {
					comp.setCompany_id(encdec.encryptnew(com.getCompany_id()));
					}
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
			
					List<LoginDetail> login = userService.findUserByUserNative(users.get(0).getUser_id());
					if (login.size() > 0) {

						LoginDetail currentLoginDetail = login.get(0);
						currentLoginDetail.setLogged_in_date(new Date());
						currentLoginDetail.setLogin_ip(request.getRemoteHost());
						userService.saveLogin(currentLoginDetail); // will update the login date time
					} else {
						LoginDetail loginDetail = new LoginDetail(request.getRemoteHost(), new Date(), null,
								users.get(0));
						userService.saveLogin(loginDetail);// new login

					}
					User userss=new User();
					if(users.get(0).getFirstname()!=null) {
						
						userss.setFirstname(encdec.encryptnew(users.get(0).getFirstname()));}
							if(users.get(0).getLastname()!=null) {
								
						userss.setLastname(encdec.encryptnew(users.get(0).getLastname()));}
							if(users.get(0).getEmail_id()!=null) {
								userss.setEmail_id(encdec.encryptnew("NA"));
							}else {
						userss.setEmail_id(encdec.encryptnew(users.get(0).getEmail_id()));}
							if(users.get(0).getMobile_no()!=null) {
								
						userss.setMobile_no(encdec.encryptnew(users.get(0).getMobile_no()));}
							if(users.get(0).getCompany_code()!=null) {
						userss.setCompany_code(encdec.encryptnew(users.get(0).getCompany_code()));}
							if(users.get(0).getCompanyName()!=null) {
						userss.setCompanyName(encdec.encryptnew(users.get(0).getCompanyName()));}
							if(users.get(0).getUser_id()!=null) {
						userss.setUser_id(encdec.encryptnew(users.get(0).getUser_id()));}
							if(users.get(0).getRole()!=null) {
								userss.setRole(encdec.encryptnew(users.get(0).getRole()));
							}
						
					
					LOGGER.info("End Of Method loginUser");
					users.get(0).setLoggedIn(Boolean.TRUE);
					// return new Response("Login Success", Boolean.TRUE, users.get(0));
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Response( userss,comp,encdec.encryptnew("login done successfully"),encdec.encryptnew( "TRUE")));

				
			} else {
				// return new Response("Login Failed !!!", Boolean.FALSE, users.get(0));
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response((encdec.encryptnew(loginFailed)),encdec.encryptnew("FALSE"),new User()));

			}
		} catch (Exception e) {
			LOGGER.error("Error While Login" + e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(),encdec.encryptnew( "FALSE"), new User()));
		}

	}

	@RequestMapping(value = { "/login/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ResponseOld> loginUser(@RequestParam("mobile_no") String mobile_no,
			@RequestParam("password") String password, @Valid User user) {
		LOGGER.info("LognIn api has been called !!! Start Of Method loginUser");
		try {
			List<User> users = (List<User>) userService.findUsers(user.getMobile_no(), user.getPassword());
			if (users.size() > 0) {
				if (users.get(0).isVerified() == Boolean.TRUE) {
					List<LoginDetail> login = userService.findUserByUserNative(users.get(0).getUser_id());
					if (login.size() > 0) {

						LoginDetail currentLoginDetail = login.get(0);
						currentLoginDetail.setLogged_in_date(new Date());
						currentLoginDetail.setLogin_ip(request.getRemoteHost());
						userService.saveLogin(currentLoginDetail); // will update the login date time
					} else {
						LoginDetail loginDetail = new LoginDetail(request.getRemoteHost(), new Date(), null,
								users.get(0));
						userService.saveLogin(loginDetail);// new login

					}
					LOGGER.info("End Of Method loginUser");
					users.get(0).setLoggedIn(Boolean.TRUE);
					// return new Response("Login Success", Boolean.TRUE, users.get(0));
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseOld(loginSuccess, Boolean.TRUE, users.get(0)));

				} else {
					// return new Response("Account not verified!!!", Boolean.FALSE, users.get(0));
					return ResponseEntity.status(HttpStatus.OK)
							.body(new ResponseOld(accountNtVerified, Boolean.FALSE, new User()));

				}
			} else {
				// return new Response("Login Failed !!!", Boolean.FALSE, users.get(0));
				return ResponseEntity.status(HttpStatus.OK)
						.body(new ResponseOld(loginFailed, Boolean.FALSE,new User()));

			}
		} catch (Exception e) {
			LOGGER.error("Error While Login" + e.getMessage());
			return ResponseEntity.badRequest().body(new ResponseOld(e.getMessage(), Boolean.FALSE, new User()));
		}

	}

	@RequestMapping(value = { "/verify/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Result> verifyAccount(@RequestParam("otp") String otp, User user) {
		LOGGER.info("Verify api has been called !!! Start Of Method verifyAccount");
		try {
			boolean verified = sendEmailService.verify(otp);
			String accountVerified = verified ? success : failed;
			LOGGER.info("End Of Method verifyAccount");
			// return new Result(accountVerified, Boolean.TRUE);
			return ResponseEntity.status(HttpStatus.OK).body(new Result(accountVerified, Boolean.TRUE));

		} catch (Exception e) {
			LOGGER.error("Error While Verify :" + e.getMessage());
			return ResponseEntity.badRequest().body(new Result(e.getMessage(), Boolean.FALSE));
		}
	}

	@RequestMapping(value = { "/forgot_password/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Result> processForgotPassword(@Valid @RequestBody User user) {
		LOGGER.info("Forgot Password api has been called !!! Start Of Method processForgotPassword");
		String email_id = user.getEmail_id();
		Random random = new Random();
		String token = String.format("%04d", random.nextInt(10000));
		user.setOtp(token);

		try {
			resetPassService.updateResetPasswordToken(token, email_id);
			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
			resetPassService.sendEmail(user, resetPasswordLink);
			LOGGER.info("End Of Method processForgotPassword");
			return ResponseEntity.status(HttpStatus.OK).body(new Result(chkMail, Boolean.TRUE));

		} catch (Exception e) {
			LOGGER.error("Error While Sending Mail" + e.getMessage());
			return ResponseEntity.badRequest().body(new Result(e.getMessage(), Boolean.FALSE));
		}
//		catch (UserNotFoundException ex) {
//			System.out.println(ex.getMessage());
//		} catch (UnsupportedEncodingException | MessagingException e) {
//			System.out.println("Error while sending email");
//		}

//		return new Result("Check Email", Boolean.TRUE);
	}

	// @PostMapping("/reset_password/v1")
	@RequestMapping(value = { "/reset_password/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Result> processResetPassword(@Valid @RequestBody User user) {
		LOGGER.info("Reset Password api has been called !!! Start Of Method processResetPassword");
		try {
			String otp = user.getOtp();
			System.out.println(otp);
			String password = user.getPassword();
			user = resetPassService.getByResetPasswordToken(otp,user.getEmail_id());
			if (user == null) {
				// System.out.println("Invalid OTP");
				// return new Result("Invalid OTP", Boolean.FALSE);
				return ResponseEntity.status(HttpStatus.OK).body(new Result(otpInvalid, Boolean.FALSE));
			} else {
				resetPassService.updatePassword(user, password);
				// System.out.println("You have successfully changed your password.");
			}
			// return new Result("Password Changed Successfully !!", Boolean.TRUE);
			LOGGER.info("End Of Method processResetPassword");
			return ResponseEntity.status(HttpStatus.OK).body(new Result(passChngSuccess, Boolean.TRUE));
		} catch (Exception e) {
			LOGGER.error("Error While Reset the Password" + e.getMessage());
			return ResponseEntity.badRequest().body(new Result(e.getMessage(), Boolean.FALSE));
		}
	}

	// @GetMapping("/logout/v1")
	@RequestMapping(value = { "/logout/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Result> logUserOut(@Valid @RequestBody User user) {
		LOGGER.info("Log Out api has been called !!! Start Of Method logUserOut");
		try {
			List<User> users = (List<User>) userService.findUsers(encdec.decryptnew(user.getMobile_no()),encdec.decryptnew( user.getPassword()));
			for (User other : users) {
				if (other.equals(user)) {
					user.setLoggedIn(false);
					LoginDetail loginDetail = new LoginDetail(request.getRemoteHost(), null, new Date(), users.get(0));
					userService.saveLogin(loginDetail);
					LOGGER.info("End Of Method logUserOut");
					// return new Result("Logged Out SuccessFull", Boolean.TRUE);
					return ResponseEntity.status(HttpStatus.OK).body(new Result(encdec.encryptnew(logoutSuccess), "TRUE"));
				}
			}

			// return new Result("Logged Out Failed", Boolean.FALSE);
			return ResponseEntity.status(HttpStatus.OK).body(new Result(logoutFailed, "TRUE"));
		} catch (Exception e) {
			LOGGER.error("Error While Reset the Password" + e.getMessage());
			return ResponseEntity.badRequest().body(new Result(e.getMessage(), "FALSE"));
		}
	}
	@RequestMapping(value = { "/GetAllUsers" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.GetAllUsers> GetAllUsers() {
		LOGGER.info("get all users api has been called !!! Start Of Method get all users");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		List<User> userList= new ArrayList<User>();
		List<User> userListEnc= new ArrayList<User>();
		
		try {
		 userList =  userRepo.getAllUsers();
			if (userList==null) {
				
			response="User list is empty"	;
			}
			else
			{
				int i=0;
			for(User users:userList) {
					final User userss=new User();
					if(users.getFirstname()!=null) {
					
				userss.setFirstname(encdec.encryptnew(users.getFirstname()));}
					if(users.getLastname()!=null) {
						
				userss.setLastname(encdec.encryptnew(users.getLastname()));}
					if(users.getEmail_id()!=null) {
						userss.setEmail_id(encdec.encryptnew(users.getEmail_id()));
					}else {
				userss.setEmail_id(encdec.encryptnew("NA"));}
					if(users.getMobile_no()!=null) {
						
				userss.setMobile_no(encdec.encryptnew(users.getMobile_no()));}
					if(users.getCompany_code()!=null) {
				userss.setCompany_code(encdec.encryptnew(users.getCompany_code()));}
					if(users.getCompanyName()!=null) {
				userss.setCompanyName(encdec.encryptnew(users.getCompanyName()));}
					if(users.getUser_id()!=null) {
				userss.setUser_id(encdec.encryptnew(users.getUser_id()));}
				userListEnc.add(i, userss);
				i++;
				
				}
				
				response="Users list is retrieved successfully";
				
			}
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While retreiving all user" + e.getMessage());
			response="Error While retreiving all user" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new com.securedloan.arthavedika.response.GetAllUsers(userListEnc,encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	@RequestMapping(value = { "/GetAllCompany_name" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.AllCompanyName> GetAllCompany_name() {
		LOGGER.info("get all company name api has been called !!! Start Of Method get all company name");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		List<Company> company=null;
		List<CompanyEnc> companyList=new ArrayList<CompanyEnc>();
		
		try {
		 company =  companyrepo.company_name();
			if (company==null) {
				
			response="company list is empty"	;
			}
			else
			{
				int i=0;
				for(Company com:company) {
					CompanyEnc comp=new CompanyEnc();
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
				companyList.add(i, comp);
				i++;
					}
				
				response="company list is retrieved successfully";
				
			}
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While retreiving all company name" + e.getMessage());
			response="Error While retreiving all company name" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new com.securedloan.arthavedika.response.AllCompanyName(companyList,encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	@RequestMapping(value = { "/GetUserById" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.GetUserById> GetUserById(@RequestBody UserPayload userPayload) {
		LOGGER.info("get  user  by id api has been called !!! Start Of Method get  user by id");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		User users=null;
		 User userss=new User();
		
		try {
		 users =  userRepo.findUserByUser_Id(encdec.decryptnew(userPayload.getUser_id()));
			if (users==null) {
				
			response="User details not available"	;
			}
			else
			{
						
						if(users.getFirstname()!=null) {
						
					userss.setFirstname(encdec.encryptnew(users.getFirstname()));}
						if(users.getLastname()!=null) {
							
					userss.setLastname(encdec.encryptnew(users.getLastname()));}
						if(users.getEmail_id()!=null) {
							userss.setEmail_id(encdec.encryptnew("NA"));
						}else {
					userss.setEmail_id(encdec.encryptnew(users.getEmail_id()));}
						if(users.getMobile_no()!=null) {
							
					userss.setMobile_no(encdec.encryptnew(users.getMobile_no()));}
						if(users.getCompany_code()!=null) {
					userss.setCompany_code(encdec.encryptnew(users.getCompany_code()));}
						if(users.getCompanyName()!=null) {
					userss.setCompanyName(encdec.encryptnew(users.getCompanyName()));}
						if(users.getUser_id()!=null) {
					userss.setUser_id(encdec.encryptnew(users.getUser_id()));}
						if(users.getRole()!=null) {
							userss.setRole(encdec.encryptnew(users.getRole()));
						}
					
				
				response="User detail is retrieved successfully";
				
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
		return ResponseEntity.status(httpstatus).body(new com.securedloan.arthavedika.response.GetUserById(userss,
				encdec.encryptnew(response),encdec.encryptnew(status)));
	}

	@RequestMapping(value = { "/add_modify_user" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> add_modify_user(@RequestBody Add_modifyUser addmodifyUserPayload) {
		LOGGER.info("add/modify api has been called !!! Start Of Method add/modify User");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		User users=null;
		try {
			if(!addmodifyUserPayload.getUser_id().isEmpty())
			{ users =  userRepo.findUserByUser_Id(encdec.decryptnew(addmodifyUserPayload.getUser_id()));}
			if (users==null) {
				User usr=new User();
				usr.setFirstname(encdec.decryptnew(addmodifyUserPayload.getFirstname()));
				System.out.println(usr.getFirstname());
				usr.setLastname(encdec.decryptnew(addmodifyUserPayload.getLastname()));
				System.out.println(usr.getFirstname());
				usr.setEmail_id(encdec.decryptnew(addmodifyUserPayload.getEmail_id()));
				System.out.println(usr.getFirstname());
				usr.setMobile_no(encdec.decryptnew(addmodifyUserPayload.getMobile_no()));
				System.out.println(usr.getFirstname());
				//usr.setCompanyName(addmodifyUserPayload.getCompany_name());
				//usr.setUser_id(addmodifyUserPayload.getUser_id());
				usr.setRole(encdec.decryptnew(addmodifyUserPayload.getRole()));
				System.out.println(usr.getRole());
				Random random = new Random();
				String token = String.format("%04d", random.nextInt(10000));
				usr.setPassword(token);
				usr.setIs_first_login("Y");
				usr.setDelete_status("N");
				System.out.println("1");
				usr.setCompany_code(encdec.decryptnew(addmodifyUserPayload.getCompany_code()));
				List<Company>company=companyrepo.company_name();
				for(int i=0;i<company.size();i++) {
					System.out.println("if statement");
System.out.print(encdec.decryptnew(addmodifyUserPayload.getCompany_code()));
System.out.print((company.get(i).getCompany_code()));
					if (encdec.decryptnew(addmodifyUserPayload.getCompany_code()).equals(company.get(i).getCompany_code())) {
						System.out.println("company name is"+company.get(i).getCompanyName());
						usr.setCompanyName(company.get(i).getCompanyName());
					}
				}
				System.out.print("before saving");
				userRepo.save(usr);
				System.out.print("after saving");
				User user_details=userRepo.findByEmailNMobile(encdec.decryptnew(addmodifyUserPayload.getEmail_id()),(encdec.decryptnew(addmodifyUserPayload.getMobile_no())));
				System.out.println("password is"+user_details.getPassword());
				mail.sendEmailForPassword(user_details);
			response="User is added successfully. Please check your mail for further details"	;
			}
			else
			{
								List<Company>company=companyrepo.company_name();
								String Company_name = null;
System.out.println("modify user");
				for(int i=0;i<company.size();i++) {
					System.out.println("in for loop");
					System.out.println("payload company code"+encdec.decryptnew(addmodifyUserPayload.getCompany_code()));
					System.out.println("froim db company code"+(company.get(i).getCompany_code()));
					if (encdec.decryptnew(addmodifyUserPayload.getCompany_code()).equals(company.get(i).getCompany_code())) {
						System.out.println("company name is"+company.get(i).getCompanyName());
					 Company_name=(company.get(i).getCompanyName());
					}
				}
				System.out.println("email"+encdec.decryptnew(addmodifyUserPayload.getEmail_id()));
				userRepo.updateUser(encdec.decryptnew(addmodifyUserPayload.getMobile_no()), (encdec.decryptnew(addmodifyUserPayload.getFirstname())), (encdec.decryptnew(addmodifyUserPayload.getLastname())),
						(encdec.decryptnew(addmodifyUserPayload.getEmail_id())),(encdec.decryptnew( addmodifyUserPayload.getCompany_code())), (encdec.decryptnew(addmodifyUserPayload.getRole())),
								Company_name,(encdec.decryptnew(addmodifyUserPayload.getUser_id())));
				//userRepo.updateUser(addmodifyUserPayload.getMobile_no(), addmodifyUserPayload.getFirstname(), addmodifyUserPayload.getLastname(),
				//		addmodifyUserPayload.getEmail_id(), addmodifyUserPayload.getCompany_code(), addmodifyUserPayload.getRole(),
				//		company,addmodifyUserPayload.getUser_id());
				
				response="User modified successfully";
				System.out.println(response);
				
			}
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While adding or modifying user" + e.getMessage());
			response="Error While adding or modifying user" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}

	@RequestMapping(value = { "/delete_user" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> delete_user(@RequestBody UserPayload UserPayload) {
		LOGGER.info("Delete user api has been called !!! Start Of Method deleteUser");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		
		try {
				userRepo.deleteUser(encdec.decryptnew(UserPayload.getUser_id()));
				
				response="User deleted successfully";
				
			
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While deleting user" + e.getMessage());
			response="Error While deleting user" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	@RequestMapping(value = { "/change_password" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> change_password(@RequestBody ChangePassword changepassword) {
		LOGGER.info("Change password for user api has been called !!! Start Of Method change password for User");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		
		try {
			 String pass=userRepo.getpassword(encdec.decryptnew(changepassword.getUser_id()));
			 if(pass.equals(encdec.decryptnew(changepassword.getOld_password()))) {
				 userRepo.changePassword(encdec.decryptnew(changepassword.getNew_password()),(encdec.decryptnew(changepassword.getUser_id())));
				 response="password changed successfully";
			 }
			 else {
				 response="please enter valid password";
			 }
			
				
			
			status="true";
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While changing password for user" + e.getMessage());
			response="Error While changing passowrd for user" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	
	@RequestMapping(value = { "/forgot_password1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> forgot_password(@RequestBody ForgetPassword changepassword) {
		LOGGER.info("forgot password for user api has been called !!! Start Of Method forgot password for User");
		
		HttpStatus httpstatus=null;
		String response="";
		String status=null;
		
		try {
			 User user=userRepo.findUserByMobile_no(encdec.decryptnew(changepassword.getMobile_no()));
			 String email_id = user.getEmail_id();
			 if(changepassword.getOtp()==null &&changepassword.getNew_password()==null) {
				Random random = new Random();
				String token = String.format("%04d", random.nextInt(10000));
				user.setOtp(token);
				resetPassService.updateResetPasswordToken(token, email_id);
				String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
				resetPassService.sendEmail(user, resetPasswordLink);
				response=chkMail;
				status="true";
				}
			 else if ((encdec.decryptnew(changepassword.getOtp()))!=null &&(encdec.decryptnew(changepassword.getNew_password()))==null) {
				User usr=resetPassService.getByResetPasswordToken(encdec.decryptnew(changepassword.getOtp()),email_id) ;
					if(usr==null) {
						response="please enter valid otp";
						status="false";
					}
					else {
						response="please enter new password";
						status="true";
					}
			 }
			 else if(encdec.decryptnew(changepassword.getOtp())!=null &&(encdec.decryptnew(changepassword.getNew_password()))!=(null)) {
				 userRepo.changePassword(encdec.decryptnew(changepassword.getNew_password()),user.getUser_id());
				 response="password changed successfully"; 
				 status="true";
			 }
				LOGGER.info("End Of Method processForgotPassword");
				
			
		  
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While changing password for user" + e.getMessage());
			response="Error While changing passowrd for user" + e.getMessage();
			status="false";
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(encdec.encryptnew(response),encdec.encryptnew(status)));
	}
	

//	@CrossOrigin()
//	@PostMapping("/signIn/v1")
//	public Result registerUser(@Valid @RequestBody User newUser)
//			throws UnsupportedEncodingException, MessagingException {
//		List<User> users = userRepository.findAll();
//		Random random = new Random();
//		String randomCode = String.format("%04d", random.nextInt(10000));
////		String randomCode = RandomString.make(64);
//		newUser.setOtp(randomCode);
	
//
//		// System.out.println(name);
//
//		for (User user : users) {
//			if (user.equalsMobile(newUser))
//				return new Result("Mobile number Already Exist", Boolean.FALSE);
//			else if (user.equalsEmail(newUser))
//				return new Result("Email Already Exist", Boolean.FALSE);
//		}
//		userRepository.save(newUser);
//		sendEmailService.sendEmail(newUser, randomCode);
//		return new Result("Please Verify Your Email", Boolean.TRUE);
//	}

//	@CrossOrigin()
//	@GetMapping("/login/v1")
//	public Response loginUser(@RequestParam("mobile_no") String mobile_no, @RequestParam("password") String password,
//			@Valid User user) throws UserNotFoundException {
//		List<User> users = (List<User>) userRepository.findUsers(user.getMobile_no(), user.getPassword());
//		if (users.size() > 0) {
//			if (users.get(0).isVerified() == Boolean.TRUE) {
//				List<LoginDetail> login = loginRepository.findUserByUserNative(users.get(0).getUser_id());
	
//				if (login.size() > 0) {
//
//					LoginDetail currentLoginDetail = login.get(0);
//					currentLoginDetail.setLogged_in_date(new Date());
//					currentLoginDetail.setLogin_ip(request.getRemoteHost());
//					loginRepository.save(currentLoginDetail); // will update the login date time
//				} else {
//
//					LoginDetail loginDetail = new LoginDetail(request.getRemoteHost(), new Date(), null, users.get(0));
//					loginRepository.save(loginDetail);// new login
//
//				}
//				users.get(0).setLoggedIn(Boolean.TRUE);
//				return new Response("Login Success", Boolean.TRUE, users.get(0));
//			} else {
//				return new Response("Account not verified!!!", Boolean.FALSE, users.get(0));
//			}
//		} else {
//			return new Response("Login Failed !!!", Boolean.FALSE, users.get(0));
//		}
//	}
//
//	@CrossOrigin()
//	@GetMapping("/verify/v1")
//	// public String verifyAccount(@Param("code") String code) {
//	public Result verifyAccount(@RequestParam("otp") String otp, User user) {
//		boolean verified = mail.verify(otp);
//		String accountVerified = verified ? "Verification Success !!" : "Verification Failed !!";
//		return new Result(accountVerified, Boolean.TRUE);
//
//	}

//	@CrossOrigin()
//	@PostMapping("/forgot_password/v1")
//	public Result processForgotPassword(@Valid @RequestBody User user) throws UserNotFoundException {
//		// public String processForgotPassword(HttpServletRequest request, Model model,
//		// User user) {
//		String email_id = user.getEmail_id();// request.getParameter("email");
//		Random random = new Random();
//		String token = String.format("%04d", random.nextInt(10000));
//		user.setOtp(token);
//		// String token = RandomString.make(30);
//
//		try {
//			resetPassword.updateResetPasswordToken(token, email_id);
//			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?token=" + token;
//			resetPassword.sendEmail(user, resetPasswordLink);
//			// model.addAttribute("message", "We have sent a reset password link to your
//			// email. Please check.");
//
//		} catch (UserNotFoundException ex) {
//			System.out.println(ex.getMessage());
//		} catch (UnsupportedEncodingException | MessagingException e) {
//			System.out.println("Error while sending email");
//		}
//
//		return new Result("Check Email", Boolean.TRUE);
//	}

//	@CrossOrigin()
//	@PostMapping("/reset_password/v1")
//	public Result processResetPassword(@Valid @RequestBody User user) {
//		String otp = user.getOtp();// request.getParameter("token");
//		String password = user.getPassword();
//
//		user = resetPassword.getByResetPasswordToken(otp);
//
//		if (user == null) {
//			System.out.println("Invalid OTP");
//			return new Result("Invalid OTP", Boolean.FALSE);
//		} else {
//			resetPassword.updatePassword(user, password);
//
//			System.out.println("You have successfully changed your password.");
//		}
//
//		return new Result("Password Changed Successfully !!", Boolean.TRUE);
//	}

//	@PostMapping("/reset_password")
//	public String processResetPassword(@Valid @RequestBody User user) {
//		String token = user.getResetPasswordToken();//request.getParameter("token");
//		String password = user.getPassword();
//
//		user = resetPassword.getByResetPasswordToken(token);
//
//		if (user == null) {
//			System.out.println("Invalid Token");
//			return "Invalid Token";
//		} else {
//			resetPassword.updatePassword(user, password);
//
//			System.out.println("You have successfully changed your password.");
//		}
//
//		return "Password Changed Successfully !!";
//	}

//	@CrossOrigin()
//	@GetMapping("/logout/v1")
//	public Result logUserOut(@Valid @RequestBody User user) {
//		List<User> users = (List<User>) userRepository.findUsers(user.getMobile_no(), user.getPassword());
//		// List<User> users = userRepository.findAll();
//		for (User other : users) {
//			if (other.equals(user)) {
//				user.setLoggedIn(false);
//				// userRepository.save(user);
//				LoginDetail loginDetail = new LoginDetail(request.getRemoteHost(), null, new Date(), users.get(0));
//				loginRepository.save(loginDetail);
//				return new Result("Logged Out SuccessFull", Boolean.TRUE);
//			}
//		}
//
//		return new Result("Logged Out Failed", Boolean.FALSE);
//	}

}
