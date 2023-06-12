package com.securedloan.arthavedika.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import com.securedloan.arthavedika.model.LoginDetail;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.payload.LoginUserPost;
import com.securedloan.arthavedika.payload.UserPayload;
import com.securedloan.arthavedika.repo.UserRepository;
import com.securedloan.arthavedika.response.GeneralResponse;
import com.securedloan.arthavedika.response.Response;
import com.securedloan.arthavedika.response.Result;
import com.securedloan.arthavedika.service.Mail;
import com.securedloan.arthavedika.service.ResetPassword;
import com.securedloan.arthavedika.service.UserService;
import com.securedloan.arthavedika.utility.Utility;

@CrossOrigin()
@RestController
@RequestMapping("user")
public class UserController {

	private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
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
					return ResponseEntity.status(HttpStatus.OK).body(new Result(mobErrorMsg, Boolean.FALSE));

				else if (user.equalsEmail(newUser))
					// return new Result("Email Already Exist", Boolean.FALSE);
					return ResponseEntity.status(HttpStatus.OK).body(new Result(emailErrorMsg, Boolean.FALSE));
			}

			Random random = new Random();
			String randomCode = String.format("%04d", random.nextInt(10000));
			newUser.setOtp(randomCode);
			userService.saveUser(newUser);
			sendEmailService.sendEmail(newUser, randomCode);
			// return new Result("Please Verify Your Email", Boolean.TRUE);
			LOGGER.info("End Of Method registerUser");
			return ResponseEntity.status(HttpStatus.OK).body(new Result(emailVerify, Boolean.TRUE));
		} catch (Exception e) {
			LOGGER.error("Error While SignIn" + e.getMessage());
			return ResponseEntity.badRequest().body(new Result(e.getMessage(), Boolean.FALSE));
		}
	}

	@RequestMapping(value = { "/loginPost/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> loginUserPost(@RequestBody LoginUserPost loginUserPayload) {
		LOGGER.info("LognIn api has been called !!! Start Of Method loginUser");
		try {
			List<User> users = (List<User>) userService.findUsers(loginUserPayload.getMobile_no(), loginUserPayload.getPassword());
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
							.body(new Response(loginSuccess, Boolean.TRUE, users.get(0)));

				} else {
					// return new Response("Account not verified!!!", Boolean.FALSE, users.get(0));
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Response(accountNtVerified, Boolean.FALSE, new User()));

				}
			} else {
				// return new Response("Login Failed !!!", Boolean.FALSE, users.get(0));
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response(loginFailed, Boolean.FALSE,new User()));

			}
		} catch (Exception e) {
			LOGGER.error("Error While Login" + e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new User()));
		}

	}

	@RequestMapping(value = { "/login/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Response> loginUser(@RequestParam("mobile_no") String mobile_no,
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
							.body(new Response(loginSuccess, Boolean.TRUE, users.get(0)));

				} else {
					// return new Response("Account not verified!!!", Boolean.FALSE, users.get(0));
					return ResponseEntity.status(HttpStatus.OK)
							.body(new Response(accountNtVerified, Boolean.FALSE, new User()));

				}
			} else {
				// return new Response("Login Failed !!!", Boolean.FALSE, users.get(0));
				return ResponseEntity.status(HttpStatus.OK)
						.body(new Response(loginFailed, Boolean.FALSE,new User()));

			}
		} catch (Exception e) {
			LOGGER.error("Error While Login" + e.getMessage());
			return ResponseEntity.badRequest().body(new Response(e.getMessage(), Boolean.FALSE, new User()));
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
			user = resetPassService.getByResetPasswordToken(otp);
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
			List<User> users = (List<User>) userService.findUsers(user.getMobile_no(), user.getPassword());
			for (User other : users) {
				if (other.equals(user)) {
					user.setLoggedIn(false);
					LoginDetail loginDetail = new LoginDetail(request.getRemoteHost(), null, new Date(), users.get(0));
					userService.saveLogin(loginDetail);
					LOGGER.info("End Of Method logUserOut");
					// return new Result("Logged Out SuccessFull", Boolean.TRUE);
					return ResponseEntity.status(HttpStatus.OK).body(new Result(logoutSuccess, Boolean.TRUE));
				}
			}

			// return new Result("Logged Out Failed", Boolean.FALSE);
			return ResponseEntity.status(HttpStatus.OK).body(new Result(logoutFailed, Boolean.TRUE));
		} catch (Exception e) {
			LOGGER.error("Error While Reset the Password" + e.getMessage());
			return ResponseEntity.badRequest().body(new Result(e.getMessage(), Boolean.FALSE));
		}
	}
	@RequestMapping(value = { "/GetAllUsers" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.GetAllUsers> GetAllUsers() {
		LOGGER.info("get all users api has been called !!! Start Of Method get all users");
		
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		List<User> users=null;
		
		try {
		 users =  userRepo.getAllUsers();
			if (users==null) {
				
			response="User list is empty"	;
			}
			else
			{
				
				
				response="Users list is retrieved successfully";
				
			}
			status=true;
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While retreiving all user" + e.getMessage());
			response="Error While retreiving all user" + e.getMessage();
			status=false;
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new com.securedloan.arthavedika.response.GetAllUsers(users,response,status));
	}
	@RequestMapping(value = { "/GetUserById" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<com.securedloan.arthavedika.response.GetUserById> GetUserById(@RequestBody UserPayload userPayload) {
		LOGGER.info("get  user  by id api has been called !!! Start Of Method get  user by id");
		
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		User users=null;
		
		try {
		 users =  userRepo.findUserByUser_Id(userPayload.getUser_id());
			if (users==null) {
				
			response="User details not available"	;
			}
			else
			{
				
				
				response="User detail is retrieved successfully";
				
			}
			status=true;
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While retreiving user" + e.getMessage());
			response="Error While retreiving  user" + e.getMessage();
			status=false;
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new com.securedloan.arthavedika.response.GetUserById(users,response,status));
	}

	@RequestMapping(value = { "/add_modify_user" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> add_modify_user(@RequestBody User addmodifyUserPayload) {
		LOGGER.info("LognIn api has been called !!! Start Of Method loginUser");
		
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		
		try {
		User users =  userRepo.findUserByUser_Id(addmodifyUserPayload.getUser_id());
			if (users==null) {
				userRepo.save(addmodifyUserPayload);
			response="User is added successfully"	;
			}
			else
			{
				userRepo.updateUser(addmodifyUserPayload.getMobile_no(), addmodifyUserPayload.getFirstname(), addmodifyUserPayload.getLastname(),
						addmodifyUserPayload.getEmail_id(), addmodifyUserPayload.getCompanyName(), addmodifyUserPayload.getRole(),
						addmodifyUserPayload.getUser_id());
				
				response="User modified successfully";
				
			}
			status=true;
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While adding or modifying user" + e.getMessage());
			response="Error While adding or modifying user" + e.getMessage();
			status=false;
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(response,status));
	}

	@RequestMapping(value = { "/delete_user" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<GeneralResponse> delete_user(@RequestBody UserPayload UserPayload) {
		LOGGER.info("Delete user api has been called !!! Start Of Method deleteUser");
		
		HttpStatus httpstatus=null;
		String response="";
		Boolean status=null;
		
		try {
				userRepo.deleteUser(UserPayload.getUser_id());
				
				response="User deleted successfully";
				
			
			status=true;
			httpstatus=HttpStatus.OK;
			}
					
		catch (Exception e) {
			LOGGER.error("Error While deleting user" + e.getMessage());
			response="Error While deleting user" + e.getMessage();
			status=false;
			httpstatus=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(httpstatus).body(new GeneralResponse(response,status));
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
