package com.securedloan.arthavedika.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.securedloan.arthavedika.model.LoginDetail;
import com.securedloan.arthavedika.model.User;

@Repository
public interface LoginRepository extends JpaRepository<LoginDetail, Integer> {

	@Query(value = "SELECT * FROM login_table u WHERE u.user_id = ?1", nativeQuery = true)
	List<LoginDetail> findUserByUserNative(String user_id);

	List<LoginDetail> findByUser(User user);

}
