package com.securedloan.arthavedika.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.securedloan.arthavedika.model.Company;

public interface CompanyRepo extends JpaRepository<Company,String> {
@Query("select a from Company a")
List<Company> company_name();
@Modifying
@Transactional
@Query("update Company a set current_amount=?1 where company_code=?2")
void updateCurrentAmount(Float current_amount,String company_code);
@Query("select a from Company a where company_code=?1")
Company company_details(String company_code);
}
