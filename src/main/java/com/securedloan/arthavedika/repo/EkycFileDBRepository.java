package com.securedloan.arthavedika.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.EkycFileDB;

@Repository
public interface EkycFileDBRepository extends JpaRepository<EkycFileDB, String> {
	
}
