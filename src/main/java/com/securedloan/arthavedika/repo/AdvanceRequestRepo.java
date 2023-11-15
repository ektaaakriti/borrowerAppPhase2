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
@Query("select a.Loan_id from AdvanceRequest a where a.applicant_id=?1 and a.raised_date_time=?2")
public String getLoanId(Long applicant_id,Date Raised_date_time);
@Query("select a from AdvanceRequest a where a.Loan_id=?1")
public AdvanceRequest getDtlsByLoanId(String Loan_id);
@Query("select a from AdvanceRequest a where a.Loan_id=?1")
public List<AdvanceRequest> ListDtlsByLoanId(String Loan_id);
@Query("select a from AdvanceRequest a where a.applicant_id=?1 and a.approval_status='N'")
AdvanceRequest getRequestByApplicant(Long Applicant_id);
@Query("select a from AdvanceRequest a where applicant_id=?1")
List<AdvanceRequest> getAdvanceRequestByApplicant_id(Long Applicant_id);
@Modifying
@Transactional
@Query("update AdvanceRequest a set a.date_of_disbursemnt=?1, a.comment_by_sh=?2, a.transaction_id=?3,a.approved_amount=?4,a.disbursement_ifsc=?5,a.disbursement_account_no=?6 where a.Loan_id=?7")
public void SHDisbursemntDtls(Date date_of_disbursement,String comment_by_sh,String transaction_id,Float approval_amount,String disbursement_ifsc,String disbursement_account_no, String Loan_id);
@Modifying
@Transactional
@Query("update AdvanceRequest a set a.approval_status=?1, a.approved_amount=?2,a.approved_date_time=?3, a.approved_user_id=?4, a.comment_by_sh=?5 where a.applicant_id=?6")
public void approveAdvance(String approval_status, Float approval_amount, Date approved_date_time, String approved_user_id, String comment_by_sh, Long applicant_id);
@Modifying
@Transactional
@Query("update AdvanceRequest a set a.av_approval=?1, comment_by_av=?2 where a.Loan_id=?3")
public void updateAvApprovalDtls(String av_aproval,String comment_by_av, String Loan_id);

}
