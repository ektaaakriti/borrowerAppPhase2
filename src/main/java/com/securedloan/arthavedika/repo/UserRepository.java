package com.securedloan.arthavedika.repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.securedloan.arthavedika.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT u FROM User u  WHERE u.mobile_no = ?1 and u.password= ?2 and u.delete_status='N' ")
	Collection<User> findUsers(String mobile_no, String password);

	@Query("SELECT u FROM User u WHERE u.otp = ?1 and u.email_id=?2 and u.delete_status='N' ")
	public User findByVerificationCode(String otp, String email_id);

//	@Query("UPDATE User u SET u.loggedIn = true WHERE user_id =?1")
//	@Modifying
//	public void loggedIn(String user_id);
	@Query("UPDATE User u SET u.verified = true WHERE user_id =?1")
	@Modifying
	public void verified(String user_id);

	@Query("SELECT u FROM User u WHERE u.email_id =?1 and u.delete_status='N'")
	public User findByEmail(String email_id);
	@Query("SELECT u FROM User u WHERE u.email_id =?1 and u.mobile_no=?2 and u.delete_status='N'")
	public User findByEmailNMobile(String email_id,String mobile_no);
@Query("select a from User a where a.delete_status='N'")
	List<User> getAllUsers();
@Query("select a from User a where a.user_id=?1 and a.delete_status='N' ")
User findUserByUser_Id(String user_id);
@Modifying
@Transactional
@Query("update  User a set a.mobile_no=?1, a.firstname=?2, a.lastname=?3,a.email_id=?4,"
		+ "a.company_code=?5, a.role=?6 , a.companyName=?7 where user_id=?8 ")
public void updateUser(String mobile_no,String firstname,String lastname,String email_id,String company_code,String role,String company_name, String user_id);

@Modifying
@Transactional
@Query("update User u set u.delete_status='Y' where u.user_id=?1")
public void deleteUser(String user_id);
	// public User findByResetPasswordToken(String token);
@Query("select a.password from User a where a.user_id=?1")
String getpassword(String user_id);
@Modifying
@Transactional
@Query("update User u set u.password=?1 where u.user_id=?2")
public void changePassword(String password,String user_id);
@Query("select a from User a where a.mobile_no=?1")
User findUserByMobile_no(String mobile_no);
@Query("SELECT u FROM User u WHERE u.otp = ?1 and u.delete_status='N' ")
User findByVerificationCode1(String otp);
@Modifying
@Transactional
@Query("update User a set is_first_login='N' where user_id=?1")
void updateIsfirstLogin(String user_id);
		
}
