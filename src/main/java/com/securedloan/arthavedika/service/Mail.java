package com.securedloan.arthavedika.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.repo.UserRepository;

@Transactional
@Service
public class Mail {

	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private UserRepository userRepository;

	public void sendEmail(User newUser, String siteURL) throws Exception {
		System.out.println("Sending Email");
		String subject = "Please Verify Your Account";
		String senderName = "Arthavedika";
		String mailContent = "<p> Dear " + newUser.getFirstname() + ",</p>";
		mailContent += "<p>Please Use the OTP to verify your account </p>";
		mailContent += "<p>Your OTP is valid only for 5 minutes </p>";
		// String verifyURL = siteURL + "/verify?code=" + newUser.getVerificationCode();
		// mailContent += "<h3><a href=\"" + verifyURL + "\">CLICK TO VERIFY </a></h3>";
		mailContent += "<p> Your OTP is " + newUser.getOtp() + "</p>";
		mailContent += "<p>Thank You<br>Arthavedika Team</p>";
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("test1@arthavedika.com", senderName);
		helper.setTo(newUser.getEmail_id());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		javaMailSender.send(message);
		System.out.println("Email Sent...");

	}

	public void clearOTP(User user) {
		user.setOtp(null);
		user.setOtpRequestedTime(null);
		userRepository.save(user);

	}

	public boolean verify(String otp) {
		User newUser = userRepository.findByVerificationCode(otp);
		// if (newUser == null || newUser.isLoggedIn()) {
		if (newUser == null || newUser.isVerified()) {
			return false;
		} else {
			// userRepository.loggedIn(newUser.getUser_id());
			userRepository.verified(newUser.getUser_id());
			return true;
		}
	}

}
