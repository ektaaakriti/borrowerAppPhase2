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
	@Query("select a from Applicant a where SH_approval='Y' ")
	List<Applicant> AllApprovedAppplicat();
	@Transactional
	@Modifying
	@Query("update Applicant a set a.vehicle_no=?1,a.company_name=?2,a.applicant_firstname=?3,a.applicant_date_of_birth=?4,a.age=?5,maritalstatus=?6,"
			+ "a.nominee_name=?7,a.nominee_dob=?8,a.nominee_age=?9,a.nominee_relation=?10,a.spouse_name=?11,a.applicant_father_firstname=?12,a.religion=?13,a.applicant_qualification=?14,"
			+ "a.applicant_employment_type=?15,a.applicant_address_line_1=?16,a.applicant_city_name=?17,a.applicant_PIN=?18,"
			+ "a.applicant_mobile_no=?19,a.no_of_family_member=?20,a.no_of_earning_member=?21,a.house_type=?22,	a.ration_card=?23,a.medical_insurance=?24,"
			+ "a.current_loan_outstanding_principal=?25,a.current_loan_outstanding_interest=?26,a.applicant_income=?27"
			+ ",a.income_from_other_sources=?28,a.food_expenses=?29,a.houserent=?30,a.house_renovation_expenses=?31,"
			+ "a.total_monthly_bill_payment=?32,a.applicant_expense_monthly=?33,a.updated_by=?34,a.datamoddt=?35 where applicant_id=?36")
	public void updateTruckersDetails(String vehicle_no,String company_name,String applicant_firstname,Date applicant_date_of_birth ,int age,
			String maritalstatus,String nominee_name,Date nominee_dob,int nominee_age,String nominee_relation,String spouse_name,
			String applicant_father_firstname,String religion,String applicant_qualification,String applicant_employment_type,
			String applicant_address_line_1,String applicant_city_name,int applicant_pin, Long applicant_mobile_no,
			int no_of_family_member,int no_of_earning_member,String house_type,String Ration_Card,
			String medical_insurance,Float current_loan_outstanding_principal,Float current_loan_outstanding_interest,
			Float applicant_income,Float income_from_other_sources,Float food_expenses,Float houserent,
			Float house_renovation_expenses,Float total_monthly_bill_payment,Float applicant_expense_monthly,String created_by, Date datamoddt,
			Long applicant_id);
@Transactional
@Modifying(clearAutomatically = false) 
	@Query("update Applicant a set a.AV_approval=?1, a.authorisation_status=1, a.av_approval_date=?2 where a.applicant_id=?3")
	public void AVauthoriseApplicant(String AV_approval,Date av_approval_date, Long applicant_id);

@Transactional
@Modifying(clearAutomatically = false) 
	@Query("update Applicant a set a.MK_approval=?1, a.authorisation_status=2, a.mk_approval_date=?2 where a.applicant_id=?3")
	public void MKauthoriseApplicant(String MK_approval, Date mk_approval_date,Long applicant_id);
@Query("select a from Applicant a where a.SH_approval='Y'")
public List<Applicant> getMKVerifiedApplicant();
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
