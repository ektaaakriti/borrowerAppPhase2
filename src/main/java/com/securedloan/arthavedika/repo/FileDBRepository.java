package com.securedloan.arthavedika.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.securedloan.arthavedika.model.EkycFileDB;
import com.securedloan.arthavedika.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
	@Query("SELECT a FROM FileDB a where applicant_id=?1 ")
	public List<FileDB> documentById(Long applicant_id);
}