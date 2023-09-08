package com.securedloan.arthavedika.repo;
import com.securedloan.arthavedika.model.*;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AdvanceRequestRepo extends JpaRepository<AdvanceRequest,String> {
@Query("select a from AdvanceRequest a")
List<AdvanceRequest> getAllAdvanceRequest();
@Query("select a from AdvanceRequest a where applicant_id=?1")
List<AdvanceRequest> getAdvanceRequestByApplicant_id(Long Applicant_id);
@Modifying
@Transactional
@Query("update AdvanceRequest a set a.approval_status=?1, a.approved_amount=?2,a.approved_date_time=?3, a.approved_user_id=?4, a.comment_by_sh=?5 where a.applicant_id=?6")
public void approveAdvance(String approval_status, Float approval_amount, Date approved_date_time, String approved_user_id, String comment_by_sh, Long applicant_id);
}
