package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "securedloan_users")
public class User {
	private @Id @NotNull String user_id;
	private @NotNull String firstname;
	private @NotNull String lastname;
	private @NotNull String email_id;
	private @NotNull String mobile_no;
	//@JsonIgnore
	private @NotNull String password;
	private @NotNull String company_code;
	
	private String role;
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	@JsonIgnore
	private @NotNull boolean loggedIn;
	@JsonIgnore
	private @NotNull boolean verified;
	private @NotNull boolean admin;
	private @NotNull boolean bc_agent;
	private @NotNull String companyName;
	private @NotNull String delete_status;
	public String getDelete_status() {
		return delete_status;
	}

	public void setDelete_status(String delete_status) {
		this.delete_status = delete_status;
	}

	// private static final long OTP_VALID_DURATION = 5 * 60 *1000 ;
	//@JsonIgnore
	@Column(name = "one_time_password")
	private String otp;
	@JsonIgnore
	@Column(name = "otp_requested_time")
	private Date otpRequestedTime;

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public Date getOtpRequestedTime() {
		return otpRequestedTime;
	}

//	@Column(name = "reset_password_token")
//	private String resetPasswordToken;
//
//	public String getResetPasswordToken() {
//		return resetPasswordToken;
//	}
//
//	public void setResetPasswordToken(String resetPasswordToken) {
//		this.resetPasswordToken = resetPasswordToken;
//	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	public String getMobile_no() {
		return mobile_no;
	}

	public void setMobile_no(String mobile_no) {
		this.mobile_no = mobile_no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

		
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isBc_agent() {
		return bc_agent;
	}

	public void setBc_agent(boolean bc_agent) {
		this.bc_agent = bc_agent;
	}



	public User(@NotNull String user_id, @NotNull String firstname, @NotNull String lastname, @NotNull String email_id,
			@NotNull String mobile_no, @NotNull String password, @NotNull String company_code, 
			String role, @NotNull boolean loggedIn, @NotNull boolean verified, @NotNull boolean admin,
			@NotNull boolean bc_agent, @NotNull String companyName, String otp, Date otpRequestedTime,String delete_status) {
		super();
		this.user_id = user_id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email_id = email_id;
		this.mobile_no = mobile_no;
		this.password = password;
		this.company_code = company_code;
		this.role = role;
		this.loggedIn = loggedIn;
		this.verified = verified;
		this.admin = admin;
		this.bc_agent = bc_agent;
		this.companyName = companyName;
		this.otp = otp;
		this.otpRequestedTime = otpRequestedTime;
		this.delete_status=delete_status;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(@NotNull String user_id) {
		super();
		this.user_id = user_id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email_id == null) ? 0 : email_id.hashCode());
		result = prime * result + ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((lastname == null) ? 0 : lastname.hashCode());
		result = prime * result + (loggedIn ? 1231 : 1237);
		result = prime * result + ((mobile_no == null) ? 0 : mobile_no.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((user_id == null) ? 0 : user_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email_id == null) {
			if (other.email_id != null)
				return false;
		} else if (!email_id.equals(other.email_id))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		if (loggedIn != other.loggedIn)
			return false;
		if (mobile_no == null) {
			if (other.mobile_no != null)
				return false;
		} else if (!mobile_no.equals(other.mobile_no))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (user_id == null) {
			if (other.user_id != null)
				return false;
		} else if (!user_id.equals(other.user_id))
			return false;
		return true;
	}

	public boolean equalsMobile(User obj) {

		if (obj.getMobile_no().equals(this.mobile_no)) {
			return true;
		} else {
			return false;
		}

	}

	public boolean equalsEmail(User obj) {

		if (obj.getEmail_id().equals(this.email_id)) {
			return true;
		} else {
			return false;
		}

	}

	public void setOtpRequestedTime(java.util.Date date) {
		// TODO Auto-generated method stub

	}

}
