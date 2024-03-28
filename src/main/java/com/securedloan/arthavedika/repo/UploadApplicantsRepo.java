package com.securedloan.arthavedika.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Uploadapplicants;

public interface UploadApplicantsRepo extends JpaRepository<Uploadapplicants,Integer> {
	@Query("SELECT a FROM Uploadapplicants a WHERE a.Filename =?1 and a.File_id=?2")
	public List<Uploadapplicants> findLog(String Filename,int File_id);
}
