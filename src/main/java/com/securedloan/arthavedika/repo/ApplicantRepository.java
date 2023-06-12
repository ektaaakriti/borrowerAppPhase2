package com.securedloan.arthavedika.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.securedloan.arthavedika.model.Applicant;
import java.util.Optional;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

	@Query("SELECT a FROM Applicant a WHERE a.applicant_mobile_no =?1")
	public Applicant findByApplicant_mobile_no(String applicant_mobile_no);
	
	@Query("SELECT a FROM Applicant a WHERE a.applicant_id=?1")
	public List<Applicant> findByApplicant_id(long applicant_id);

	@Query("SELECT a FROM Applicant a WHERE a.applicant_id=?1")
	public Applicant findByApplicant_ids(long applicant_id);
	@Query("SELECT a FROM Applicant a WHERE a.dataentdt between ?1 and ?2")
	public Optional <List<Applicant>> findAllByDate(LocalDate dataentdt,LocalDate enddt );

	@Query("SELECT a FROM Applicant a  ")
	public List<Applicant> findAllApplicant();
	
@Transactional
@Modifying(clearAutomatically = false) 
	@Query("update Applicant a set a.AV_approval=?1, a.authorisation_status=1 where a.applicant_id=?2")
	public void AVauthoriseApplicant(String AV_approval, Long applicant_id);

@Transactional
@Modifying(clearAutomatically = false) 
	@Query("update Applicant a set a.MK_approval=?1, a.authorisation_status=2 where a.applicant_id=?2")
	public void MKauthoriseApplicant(String MK_approval, Long applicant_id);

@Transactional
@Modifying(clearAutomatically = false) 
	@Query("update Applicant a set a.SH_approval=?1, a.authorisation_status=3 where a.applicant_id=?2")
	public void SHauthoriseApplicant(String SH_approval, Long applicant_id);
}
//
//	@Query("SELECT a FROM Applicant a WHERE a.applicant_id =?1")
//    public Applicant findByApplicantId(long applicant_id);
//	
