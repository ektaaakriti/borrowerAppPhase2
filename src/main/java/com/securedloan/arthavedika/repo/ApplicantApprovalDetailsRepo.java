package com.securedloan.arthavedika.repo;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.securedloan.arthavedika.model.Applicant_approval_details;
public interface ApplicantApprovalDetailsRepo extends JpaRepository<Applicant_approval_details,Long> {
	@Modifying
	@Transactional
@Query("update Applicant_approval_details  a set a.av_authorisation_status=?1,a.av_authorisation_by=?2,a.av_comment=?3,a.av_authorisation_datetime=?4 where a.applicant_id=?5")
public void updateAvApprovalDetails(String av_authorisation_status,
	String av_authorisation_by,
	String av_comment,
	Date av_authorisation_datetime, Long Applicant_id);
	@Modifying
	@Transactional
@Query("update Applicant_approval_details a set a.mk_verified_status=?1,a.mk_verified_by=?2,a.mk_comment=?3,a.mk_verify_datetime=?4 where a.applicant_id=?5")
public void updateMkverifyDetails(String mk_verified_status,
	String mk_verified_by,
	String mk_comment,
	Date mk_verify_datetime, Long Applicant_id);
	@Modifying
	@Transactional
@Query("update Applicant_approval_details  a set a.sh_approval_status=?1,a.sh_approval_by=?2,a.sh_comment=?3,a.sh_approval_datetime=?4 where a.applicant_id=?5")
public void updateshapprovalDetails(String sh_approval_status,
	String sh_approval_by,
	String sh_comment,
	Date sh_approval_datetime, Long Applicant_id);
}
