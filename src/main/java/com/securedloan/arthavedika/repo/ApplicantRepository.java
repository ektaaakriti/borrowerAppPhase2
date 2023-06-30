package com.securedloan.arthavedika.repo;

import java.time.LocalDate;
import java.util.Date;
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
	@Query("update Applicant a set a.AV_approval=?1, a.authorisation_status=1, a.av_approval_date=?2 where a.applicant_id=?3")
	public void AVauthoriseApplicant(String AV_approval,Date av_approval_date, Long applicant_id);

@Transactional
@Modifying(clearAutomatically = false) 
	@Query("update Applicant a set a.MK_approval=?1, a.authorisation_status=2, a.mk_approval_date=?2 where a.applicant_id=?3")
	public void MKauthoriseApplicant(String MK_approval, Date mk_approval_date,Long applicant_id);

@Transactional
@Modifying(clearAutomatically = false) 
	@Query("update Applicant a set a.SH_approval=?1, a.authorisation_status=3, a.sh_approval_date=?2 where a.applicant_id=?3")
	public void SHauthoriseApplicant(String SH_approval,Date sh_approval_date, Long applicant_id);
@Query("select count(u) from Applicant u")
public int total_applicant();
@Query("select count(u) from Applicant u where u.AV_approval='Y'")
public int av_approval();
@Query("select count(u) from Applicant u where u.AV_approval='N'")
public int av_rejection();
@Query("select count(u) from Applicant u where u.MK_approval='Y'")
public int mk_approval();
@Query("select count(u) from Applicant u where u.MK_approval='N'")
public int MK_rejection();
@Query("select count(u) from Applicant u where u.SH_approval='Y'")
public int Sh_approval();
@Query("select count(u) from Applicant u where u.SH_approval='N'")
public int SH_rejection();
@Query("select count(u) from Applicant u where u.AV_approval='Y' and u.av_approval_date=?1")
public int today_av_approval(Date av_aproval_date);
@Query("select count(u) from Applicant u where u.MK_approval='Y' and u.mk_approval_date=?1")
public int today_mk_approval(Date mk_aproval_date);
@Query("select count(u) from Applicant u where u.SH_approval='Y' and u.sh_approval_date=?1")
public int today_sh_approval(Date sh_aproval_date);
@Query("select count(u) from Applicant u where u.MK_approval is null")
public int MK_pending();
@Query("select count(u) from Applicant u where u.SH_approval is null")
public int Sh_pending();
@Query("select count(u) from Applicant u where u.AV_approval is null")
public int av_pending();
}
//
//	@Query("SELECT a FROM Applicant a WHERE a.applicant_id =?1")
//    public Applicant findByApplicantId(long applicant_id);
//	
