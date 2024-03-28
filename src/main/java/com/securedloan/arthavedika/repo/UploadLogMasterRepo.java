package com.securedloan.arthavedika.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.UploadLogMaster;

public interface UploadLogMasterRepo extends JpaRepository<UploadLogMaster, Long> {

}
