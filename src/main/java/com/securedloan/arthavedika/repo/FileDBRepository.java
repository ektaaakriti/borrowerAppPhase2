package com.securedloan.arthavedika.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.securedloan.arthavedika.model.EkycFileDB;
import com.securedloan.arthavedika.model.FileDB;

@Repository
public interface FileDBRepository extends JpaRepository<FileDB, String> {
	@Query("SELECT a FROM FileDB a where applicant_id=?1 ")
	public List<FileDB> documentById(Long applicant_id);
	@Query("SELECT a FROM FileDB a where applicant_id=?1 and  document=?2 ")
	public FileDB documentByIdnDocName(Long applicant_id,String document);
	@Transactional
	@Modifying
	@Query("update FileDB a set a.name=?1,a.data=?2,a.type=?3,docName=?4 where applicant_id=?5 and document=?6")
	public void updateDocument(String name, byte[] data,String type,String docName ,Long applicant_id, String document);
	
}