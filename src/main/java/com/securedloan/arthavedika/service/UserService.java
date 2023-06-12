package com.securedloan.arthavedika.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securedloan.arthavedika.model.LoginDetail;
import com.securedloan.arthavedika.model.User;
import com.securedloan.arthavedika.repo.LoginRepository;
import com.securedloan.arthavedika.repo.UserRepository;

@Service
@Transactional
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	LoginRepository loginRepository;

	public List<User> findAllUser() throws Exception {
		return userRepository.findAll();
	}

	public User saveUser(User user) throws Exception  {
		return userRepository.save(user);

	}

	public List<User> findUsers(String mobile_no, String password) throws Exception {
		return (List<User>) userRepository.findUsers(mobile_no, password);
	}

	public List<LoginDetail> findUserByUserNative(String user_id) throws Exception {
		return loginRepository.findUserByUserNative(user_id);
	}

	public LoginDetail saveLogin(LoginDetail loginDetail) throws Exception  {
		return loginRepository.save(loginDetail);
	}

}
