package com.securedloan.arthavedika.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.securedloan.arthavedika.exception.UserNotFoundException;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.repo.UserRepository;

@Service
@Transactional
public class ResetPassword {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JavaMailSender mailSender;

	public void updateResetPasswordToken(String otp, String email_id) throws Exception {
		User user = userRepository.findByEmail(email_id);
		if (user != null) {
			user.setOtp(otp);
			userRepository.save(user);
		} else {
			throw new Exception("Could not find any customer with the email " + email_id);
		}
	}

	public User getByResetPasswordToken(String otp) {
		System.out.println(otp);
		return userRepository.findByVerificationCode(otp);
	}

	public void updatePassword(User user, String newPassword) {
		String updatedPassword=newPassword;
		user.setPassword(updatedPassword);
		user.setOtp(null);
		userRepository.save(user);
	}

	public void sendEmail(User user, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom("test1@Arthavedika.com", "Arthavedika Support");
		helper.setTo(user.getEmail_id());

		String subject = "Here's the link to reset your password";
		String mContent = "<p> Dear " + user.getFirstname() + ",</p>";
		mContent += "<p>Please Use the OTP to Reset your account password </p>";
		mContent += "<p>Your OTP is valid only for 5 minutes </p>";
		// String verifyURL = siteURL + "/verify?code=" + newUser.getVerificationCode();
		// mailContent += "<h3><a href=\"" + verifyURL + "\">CLICK TO VERIFY </a></h3>";
		mContent += "<p> Your OTP is " + user.getOtp() + "</p>";
		mContent += "<p>Thank You<br>Arthavedika Team</p>";
		helper.setSubject(subject);
		helper.setText(mContent, true);
		mailSender.send(message);
	}
}
