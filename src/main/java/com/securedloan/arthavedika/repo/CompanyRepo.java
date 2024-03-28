package com.securedloan.arthavedika.repo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.securedloan.arthavedika.model.Company;

public interface CompanyRepo extends JpaRepository<Company,String> {
@Query("select a from Company a where a.delete_status='N' ")
List<Company> company_name();
@Modifying
@Transactional
@Query("update Company a set a.current_amount=?1 where a.company_code=?2 ")
void updateCurrentAmount(Float current_amount,String company_code);
@Query("select a from Company a where a.company_code=?1 and a.delete_status='N' ")
Company company_details(String company_code);

@Query("select a from Company a where a.company_id=?1 and a.delete_status='N' ")
public Company getcompanyById(String Company_id);
@Modifying
@Transactional
@Query("update Company a set a.delete_status='Y' where a.company_id=?1 ")
void deleteCompany(String company_id);
@Modifying
@Transactional
@Query("update Company a set a.company_code=?1, a.companyName=?2, a.company_address=?3, a.allowed_amount=?4,a.current_amount=?5,a.contact_person1=?6,a.contact_person_mobile1=?7,a.contact_person_designation1=?8, a.contact_person_email1=?9 where a.company_id=?10") 
public void updateCompany(String company_code,String companyName,String company_address,Float allowed_amount,Float current_amount,String contact_person1,
Long contact_person_mobile1,
String contact_person_designation1 ,
String  contact_person_email1,
String company_id);
}
