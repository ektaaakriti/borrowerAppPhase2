package com.securedloan.arthavedika.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "applicant_table")
@SequenceGenerator(name = "port_gen", sequenceName = "port_gen", initialValue = 4650)

public class Applicant {

	private @GeneratedValue(strategy = GenerationType.AUTO, generator = "port_gen") @Id @NotNull long applicant_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	private @NotNull String userIdStr;
    
	private @NotNull String loanAmount;
	private @NotNull String purposeOfLoan;
private @NotNull long groupId;
private boolean anygroup;
	
	public boolean isAnygroup() {
	return anygroup;
}

public void setAnygroup(boolean anygroup) {
	this.anygroup = anygroup;
}



	public long getGroupId() {
	return groupId;
}

public void setGroupId(long groupId) {
	this.groupId = groupId;
}



	public String getCompany_name() {
	return company_name;
}

public void setCompany_name(String company_name) {
	this.company_name = company_name;
}

public String getCompany_code() {
	return company_code;
}

public void setCompany_code(String company_code) {
	this.company_code = company_code;
}



	@Column(name = "published")
	private boolean published;
	private String spouse_name;
	private String mother_name;
	private String aadhar_no;
	private String pan_no;
	private String account_no;
	private String eligible_loan_amount;
	
	public String getEligible_loan_amount() {
		return eligible_loan_amount;
	}

	public void setEligible_loan_amount(String eligible_loan_amount) {
		this.eligible_loan_amount = eligible_loan_amount;
	}

	public String getSpouse_name() {
		return spouse_name;
	}

	public void setSpouse_name(String spouse_name) {
		this.spouse_name = spouse_name;
	}

	public String getMother_name() {
		return mother_name;
	}

	public void setMother_name(String mother_name) {
		this.mother_name = mother_name;
	}



	private @NotNull LocalDate dataentdt;
	private @NotNull LocalDate datamoddt;
	private @NotNull String applicant_address_line_1;
	private @NotNull String applicant_address_line_2;
	private @NotNull String applicant_address_line_3;
	private @NotNull String applicant_state;
	private @NotNull long applicant_PIN;
	private @NotNull String applicant_firstname;
	private @NotNull String applicant_middle_name;
	private @NotNull String applicant_lastname;
	private @NotNull String applicant_email_id;
	private @NotNull String applicant_mobile_no;
	private  @NotNull String applicant_family_mobile_no;
	private  @NotNull  Date applicant_date_of_birth;
	private  @NotNull String gender;
	private @NotNull String applicant_father_firstname;
	private  @NotNull String applicant_father_middle_name;
	private @NotNull String applicant_father_lastname;
	private @NotNull String applicant_city_name;

	// latest added fields
	private boolean submited;
	private String ration_card;
	public String getRation_card() {
		return ration_card;
	}

	public void setRation_card(String ration_card) {
		this.ration_card = ration_card;
	}



	private  @NotNull int no_of_family_member;
	private  @NotNull int no_of_earning_member;
	private  @NotNull int no_of_children;
	private  @NotNull int no_of_children_studying;
	private  @NotNull  String education_expenses_on_children;
	private  @NotNull  int no_of_sick_members;
	private boolean Family_covered_under_aayushman_or_similar_scheme;
	private boolean applicant_alcoholic;
	private boolean applicant_tobacco_smoking_or_chewing;
	private   @NotNull String friend1_name;
	private   @NotNull String friend1_mobile_no;
	private   @NotNull String friend1_job;
	private   @NotNull String friend2_name;
	private   @NotNull String friend2_mobile_no;
	private   @NotNull String friend2_job;
	private   @NotNull String house_locality;
	private   @NotNull String house_rent_type;
	private   @NotNull String house_type;
	private boolean applicant_agr_land_owned;
	private   @NotNull String applicant_land_area;
	private boolean applicant_shop_owned;
	private   @NotNull String applicant_qualification;
	private   @NotNull String applicant_job;
	private   @NotNull String type_of_income;
	private boolean applicant_income_agriculture;
	private boolean applicant_income_non_agriculture;
	private   @NotNull String applicant_total_income_agriculture_session;
	private   @NotNull String applicant_total_income_agriculture_crop;
	private   @NotNull String applicant_non_agriculture_income_type;
	private   @NotNull String applicant_role;
	private   @NotNull String applicant_income;
	private   @NotNull String applicant_employment_type;
	private boolean applicant_industry_stable;
	private   @NotNull String applicant_organisation_type;
	private   @NotNull String applicant_business;
	private   @NotNull String applicant_business_product;
	private   @NotNull String applicant_business_turnover;
	private   @NotNull String applicant_business_income;
	private   @NotNull String applicant_customer;
	private   @NotNull String applicant_business_other;
	private   @NotNull String applicant_rasan_card_type;
	private boolean applicant_hold_bank_account;
	private boolean applicant_invest;
	private boolean applicant_owned_tractor;
	private boolean applicant_owned_car;
	private boolean applicant_owned_scooter_motorcycle;
	private boolean applicant_owned_bicycle;
	private boolean applicant_owned_auto;
	private boolean applicant_bank_loan;
	private boolean applicant_sahukar_loan;
	private   @NotNull String applicant_loan_amount;
	private   @NotNull String applicant_loan_amount_sahukar;
	private boolean applicant_have_agriculture_machinery;
	private boolean applicant_have_non_agriculture_machinery;
	private   @NotNull String applicant_expense_monthly;
	private   @NotNull String applicant_education_expense_monthly;
	private   @NotNull String applicant_medical_expense;
	private   @NotNull String applicant_children_school_type;
	private   @NotNull String maritalstatus;
	private   @NotNull String houserent;
	private   @NotNull String landunit;
	private	  @NotNull String applicant_bill_amount;
	private   @NotNull String applicant_bill_paid;
	private   @NotNull String applicant_kisan_card;
	private   @NotNull String loanskill;
	private boolean  is_having_livestock ;
	private boolean is_having_electricit ;
	private @NotNull float food_expenses ;
	private @NotNull float utilities_expenses ;
	private @NotNull float transport_expenses;
	private @NotNull float clothing_expenses ;
	private @NotNull float house_renovation_expenses ;
	private @NotNull float functions_expenses ;
	private @NotNull float land_area_inacres ;
	private @NotNull String appl_main_income_src;
	private @NotNull String AV_approval;
	private @NotNull String MK_approval;
	private @NotNull String SH_approval;
	private Date av_approval_date;
	private Date mk_approval_date;
	private Date sh_approval_date;
	int age;
	String religion;
	Date nominee_dob;
	String nominee_name;
	String nominee_relation;
	int nominee_age;
	Float total_monthly_bill_payment;
	String medical_insurance;
	Float current_loan_outstanding_principal;
	String vehicle_no;
	Float income_from_other_sources;
	Float current_loan_outstanding_interest;
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public Date getNominee_dob() {
		return nominee_dob;
	}

	public void setNominee_dob(Date nominee_dob) {
		this.nominee_dob = nominee_dob;
	}

	public String getNominee_name() {
		return nominee_name;
	}

	public void setNominee_name(String nominee_name) {
		this.nominee_name = nominee_name;
	}

	public String getNominee_relation() {
		return nominee_relation;
	}

	public void setNominee_relation(String nominee_relation) {
		this.nominee_relation = nominee_relation;
	}

	public int getNominee_age() {
		return nominee_age;
	}

	public void setNominee_age(int nominee_age) {
		this.nominee_age = nominee_age;
	}

	public Float getTotal_monthly_bill_payment() {
		return total_monthly_bill_payment;
	}

	public void setTotal_monthly_bill_payment(Float total_monthly_bill_payment) {
		this.total_monthly_bill_payment = total_monthly_bill_payment;
	}

	public String getMedical_insurance() {
		return medical_insurance;
	}

	public void setMedical_insurance(String medical_insurance) {
		this.medical_insurance = medical_insurance;
	}

	public Float getCurrent_loan_outstanding_principal() {
		return current_loan_outstanding_principal;
	}

	public void setCurrent_loan_outstanding_principal(Float current_loan_outstanding_principal) {
		this.current_loan_outstanding_principal = current_loan_outstanding_principal;
	}

	public String getVehicle_no() {
		return vehicle_no;
	}

	public void setVehicle_no(String vehicle_no) {
		this.vehicle_no = vehicle_no;
	}

	public Float getIncome_from_other_sources() {
		return income_from_other_sources;
	}

	public void setIncome_from_other_sources(Float income_from_other_sources) {
		this.income_from_other_sources = income_from_other_sources;
	}

	public Float getCurrent_loan_outstanding_interest() {
		return current_loan_outstanding_interest;
	}

	public void setCurrent_loan_outstanding_interest(Float current_loan_outstanding_interest) {
		this.current_loan_outstanding_interest = current_loan_outstanding_interest;
	}

	public Date getAv_approval_date() {
		return av_approval_date;
	}

	public void setAv_approval_date(Date av_approval_date) {
		this.av_approval_date = av_approval_date;
	}

	public Date getMk_approval_date() {
		return mk_approval_date;
	}

	public void setMk_approval_date(Date mk_approval_date) {
		this.mk_approval_date = mk_approval_date;
	}

	public Date getSh_approval_date() {
		return sh_approval_date;
	}

	public void setSh_approval_date(Date sh_approval_date) {
		this.sh_approval_date = sh_approval_date;
	}



	private @NotNull int authorisation_status;
	private @NotNull String company_name;
	private @NotNull String company_code;

	
	
 


	String created_by;
	String updated_by;
	String predicted_score;
	Float estimated_income;
	

	public Applicant(@NotNull long applicant_id, User user, @NotNull String userIdStr, @NotNull String loanAmount,
			@NotNull String purposeOfLoan, @NotNull long groupId, boolean anygroup, boolean published,
			String spouse_name, String mother_name, String aadhar_no, String pan_no, String account_no,
			String eligible_loan_amount, @NotNull LocalDate dataentdt, @NotNull LocalDate datamoddt,
			@NotNull String applicant_address_line_1, @NotNull String applicant_address_line_2,
			@NotNull String applicant_address_line_3, @NotNull String applicant_state, @NotNull long applicant_PIN,
			@NotNull String applicant_firstname, @NotNull String applicant_middle_name,
			@NotNull String applicant_lastname, @NotNull String applicant_email_id, @NotNull String applicant_mobile_no,
			@NotNull String applicant_family_mobile_no, @NotNull Date applicant_date_of_birth, @NotNull String gender,
			@NotNull String applicant_father_firstname, @NotNull String applicant_father_middle_name,
			@NotNull String applicant_father_lastname, @NotNull String applicant_city_name, boolean submited,
			String ration_card, @NotNull int no_of_family_member, @NotNull int no_of_earning_member,
			@NotNull int no_of_children, @NotNull int no_of_children_studying,
			@NotNull String education_expenses_on_children, @NotNull int no_of_sick_members,
			boolean family_covered_under_aayushman_or_similar_scheme, boolean applicant_alcoholic,
			boolean applicant_tobacco_smoking_or_chewing, @NotNull String friend1_name,
			@NotNull String friend1_mobile_no, @NotNull String friend1_job, @NotNull String friend2_name,
			@NotNull String friend2_mobile_no, @NotNull String friend2_job, @NotNull String house_locality,
			@NotNull String house_rent_type, @NotNull String house_type, boolean applicant_agr_land_owned,
			@NotNull String applicant_land_area, boolean applicant_shop_owned, @NotNull String applicant_qualification,
			@NotNull String applicant_job, @NotNull String type_of_income, boolean applicant_income_agriculture,
			boolean applicant_income_non_agriculture, @NotNull String applicant_total_income_agriculture_session,
			@NotNull String applicant_total_income_agriculture_crop,
			@NotNull String applicant_non_agriculture_income_type, @NotNull String applicant_role,
			@NotNull String applicant_income, @NotNull String applicant_employment_type,
			boolean applicant_industry_stable, @NotNull String applicant_organisation_type,
			@NotNull String applicant_business, @NotNull String applicant_business_product,
			@NotNull String applicant_business_turnover, @NotNull String applicant_business_income,
			@NotNull String applicant_customer, @NotNull String applicant_business_other,
			@NotNull String applicant_rasan_card_type, boolean applicant_hold_bank_account, boolean applicant_invest,
			boolean applicant_owned_tractor, boolean applicant_owned_car, boolean applicant_owned_scooter_motorcycle,
			boolean applicant_owned_bicycle, boolean applicant_owned_auto, boolean applicant_bank_loan,
			boolean applicant_sahukar_loan, @NotNull String applicant_loan_amount,
			@NotNull String applicant_loan_amount_sahukar, boolean applicant_have_agriculture_machinery,
			boolean applicant_have_non_agriculture_machinery, @NotNull String applicant_expense_monthly,
			@NotNull String applicant_education_expense_monthly, @NotNull String applicant_medical_expense,
			@NotNull String applicant_children_school_type, @NotNull String maritalstatus, @NotNull String houserent,
			@NotNull String landunit, @NotNull String applicant_bill_amount, @NotNull String applicant_bill_paid,
			@NotNull String applicant_kisan_card, @NotNull String loanskill, boolean is_having_livestock,
			boolean is_having_electricit, @NotNull float food_expenses, @NotNull float utilities_expenses,
			@NotNull float transport_expenses, @NotNull float clothing_expenses,
			@NotNull float house_renovation_expenses, @NotNull float functions_expenses,
			@NotNull float land_area_inacres, @NotNull String appl_main_income_src, @NotNull String aV_approval,
			@NotNull String mK_approval, @NotNull String sH_approval, Date av_approval_date, Date mk_approval_date,
			Date sh_approval_date, int age, String religion, Date nominee_dob, String nominee_name,
			String nominee_relation, int nominee_age, Float total_monthly_bill_payment, String medical_insurance,
			Float current_loan_outstanding_principal, String vehicle_no, Float income_from_other_sources,
			Float current_loan_outstanding_interest, @NotNull int authorisation_status, @NotNull String company_name,
			@NotNull String company_code, String created_by, String updated_by, String predicted_score,
			Float estimated_income, boolean psycho_page, String prediciton, String latitude, String longitude,
			List<PsyAns> psyAns, boolean is_having_cell) {
		super();
		this.applicant_id = applicant_id;
		this.user = user;
		this.userIdStr = userIdStr;
		this.loanAmount = loanAmount;
		this.purposeOfLoan = purposeOfLoan;
		this.groupId = groupId;
		this.anygroup = anygroup;
		this.published = published;
		this.spouse_name = spouse_name;
		this.mother_name = mother_name;
		this.aadhar_no = aadhar_no;
		this.pan_no = pan_no;
		this.account_no = account_no;
		this.eligible_loan_amount = eligible_loan_amount;
		this.dataentdt = dataentdt;
		this.datamoddt = datamoddt;
		this.applicant_address_line_1 = applicant_address_line_1;
		this.applicant_address_line_2 = applicant_address_line_2;
		this.applicant_address_line_3 = applicant_address_line_3;
		this.applicant_state = applicant_state;
		this.applicant_PIN = applicant_PIN;
		this.applicant_firstname = applicant_firstname;
		this.applicant_middle_name = applicant_middle_name;
		this.applicant_lastname = applicant_lastname;
		this.applicant_email_id = applicant_email_id;
		this.applicant_mobile_no = applicant_mobile_no;
		this.applicant_family_mobile_no = applicant_family_mobile_no;
		this.applicant_date_of_birth = applicant_date_of_birth;
		this.gender = gender;
		this.applicant_father_firstname = applicant_father_firstname;
		this.applicant_father_middle_name = applicant_father_middle_name;
		this.applicant_father_lastname = applicant_father_lastname;
		this.applicant_city_name = applicant_city_name;
		this.submited = submited;
		this.ration_card = ration_card;
		this.no_of_family_member = no_of_family_member;
		this.no_of_earning_member = no_of_earning_member;
		this.no_of_children = no_of_children;
		this.no_of_children_studying = no_of_children_studying;
		this.education_expenses_on_children = education_expenses_on_children;
		this.no_of_sick_members = no_of_sick_members;
		Family_covered_under_aayushman_or_similar_scheme = family_covered_under_aayushman_or_similar_scheme;
		this.applicant_alcoholic = applicant_alcoholic;
		this.applicant_tobacco_smoking_or_chewing = applicant_tobacco_smoking_or_chewing;
		this.friend1_name = friend1_name;
		this.friend1_mobile_no = friend1_mobile_no;
		this.friend1_job = friend1_job;
		this.friend2_name = friend2_name;
		this.friend2_mobile_no = friend2_mobile_no;
		this.friend2_job = friend2_job;
		this.house_locality = house_locality;
		this.house_rent_type = house_rent_type;
		this.house_type = house_type;
		this.applicant_agr_land_owned = applicant_agr_land_owned;
		this.applicant_land_area = applicant_land_area;
		this.applicant_shop_owned = applicant_shop_owned;
		this.applicant_qualification = applicant_qualification;
		this.applicant_job = applicant_job;
		this.type_of_income = type_of_income;
		this.applicant_income_agriculture = applicant_income_agriculture;
		this.applicant_income_non_agriculture = applicant_income_non_agriculture;
		this.applicant_total_income_agriculture_session = applicant_total_income_agriculture_session;
		this.applicant_total_income_agriculture_crop = applicant_total_income_agriculture_crop;
		this.applicant_non_agriculture_income_type = applicant_non_agriculture_income_type;
		this.applicant_role = applicant_role;
		this.applicant_income = applicant_income;
		this.applicant_employment_type = applicant_employment_type;
		this.applicant_industry_stable = applicant_industry_stable;
		this.applicant_organisation_type = applicant_organisation_type;
		this.applicant_business = applicant_business;
		this.applicant_business_product = applicant_business_product;
		this.applicant_business_turnover = applicant_business_turnover;
		this.applicant_business_income = applicant_business_income;
		this.applicant_customer = applicant_customer;
		this.applicant_business_other = applicant_business_other;
		this.applicant_rasan_card_type = applicant_rasan_card_type;
		this.applicant_hold_bank_account = applicant_hold_bank_account;
		this.applicant_invest = applicant_invest;
		this.applicant_owned_tractor = applicant_owned_tractor;
		this.applicant_owned_car = applicant_owned_car;
		this.applicant_owned_scooter_motorcycle = applicant_owned_scooter_motorcycle;
		this.applicant_owned_bicycle = applicant_owned_bicycle;
		this.applicant_owned_auto = applicant_owned_auto;
		this.applicant_bank_loan = applicant_bank_loan;
		this.applicant_sahukar_loan = applicant_sahukar_loan;
		this.applicant_loan_amount = applicant_loan_amount;
		this.applicant_loan_amount_sahukar = applicant_loan_amount_sahukar;
		this.applicant_have_agriculture_machinery = applicant_have_agriculture_machinery;
		this.applicant_have_non_agriculture_machinery = applicant_have_non_agriculture_machinery;
		this.applicant_expense_monthly = applicant_expense_monthly;
		this.applicant_education_expense_monthly = applicant_education_expense_monthly;
		this.applicant_medical_expense = applicant_medical_expense;
		this.applicant_children_school_type = applicant_children_school_type;
		this.maritalstatus = maritalstatus;
		this.houserent = houserent;
		this.landunit = landunit;
		this.applicant_bill_amount = applicant_bill_amount;
		this.applicant_bill_paid = applicant_bill_paid;
		this.applicant_kisan_card = applicant_kisan_card;
		this.loanskill = loanskill;
		this.is_having_livestock = is_having_livestock;
		this.is_having_electricit = is_having_electricit;
		this.food_expenses = food_expenses;
		this.utilities_expenses = utilities_expenses;
		this.transport_expenses = transport_expenses;
		this.clothing_expenses = clothing_expenses;
		this.house_renovation_expenses = house_renovation_expenses;
		this.functions_expenses = functions_expenses;
		this.land_area_inacres = land_area_inacres;
		this.appl_main_income_src = appl_main_income_src;
		AV_approval = aV_approval;
		MK_approval = mK_approval;
		SH_approval = sH_approval;
		this.av_approval_date = av_approval_date;
		this.mk_approval_date = mk_approval_date;
		this.sh_approval_date = sh_approval_date;
		this.age = age;
		this.religion = religion;
		this.nominee_dob = nominee_dob;
		this.nominee_name = nominee_name;
		this.nominee_relation = nominee_relation;
		this.nominee_age = nominee_age;
		this.total_monthly_bill_payment = total_monthly_bill_payment;
		this.medical_insurance = medical_insurance;
		this.current_loan_outstanding_principal = current_loan_outstanding_principal;
		this.vehicle_no = vehicle_no;
		this.income_from_other_sources = income_from_other_sources;
		this.current_loan_outstanding_interest = current_loan_outstanding_interest;
		this.authorisation_status = authorisation_status;
		this.company_name = company_name;
		this.company_code = company_code;
		this.created_by = created_by;
		this.updated_by = updated_by;
		this.predicted_score = predicted_score;
		this.estimated_income = estimated_income;
		this.psycho_page = psycho_page;
		this.prediciton = prediciton;
		this.latitude = latitude;
		this.longitude = longitude;
		this.psyAns = psyAns;
		this.is_having_cell = is_having_cell;
	}

	// ends here

	public String getPredicted_score() {
		return predicted_score;
	}

	public void setPredicted_score(String predicted_score) {
		this.predicted_score = predicted_score;
	}

	public Float getEstimated_income() {
		return estimated_income;
	}

	public void setEstimated_income(Float estimated_income) {
		this.estimated_income = estimated_income;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public int getAuthorisation_status() {
		return authorisation_status;
	}

	public String getAV_approval() {
		return AV_approval;
	}

	public void setAV_approval(String aV_approval) {
		AV_approval = aV_approval;
	}

	public String getMK_approval() {
		return MK_approval;
	}

	public void setMK_approval(String mK_approval) {
		MK_approval = mK_approval;
	}

	public String getSH_approval() {
		return SH_approval;
	}

	public void setSH_approval(String sH_approval) {
		SH_approval = sH_approval;
	}

	public void setAuthorisation_status(int authorisation_status) {
		this.authorisation_status = authorisation_status;
	}

	public String getAadhar_no() {
		return aadhar_no;
	}

	
	

	public void setAadhar_no(String aadhar_no) {
		this.aadhar_no = aadhar_no;
	}

	public String getPan_no() {
		return pan_no;
	}

	public void setPan_no(String pan_no) {
		this.pan_no = pan_no;
	}

	public String getAccount_no() {
		return account_no;
	}

	public void setAccount_no(String account_no) {
		this.account_no = account_no;
	}



// PSYCHOMETRIC QUESTIONS
 private boolean psycho_page;
 private String prediciton;
	
	
	
	//location 
	private String latitude;
	private String longitude;
	@Transient
	private List<PsyAns> psyAns=new ArrayList<>();	

	

	


	public String getLoanskill() {
		return loanskill;
	}

	public void setLoanskill(String loanskill) {
		this.loanskill = loanskill;
	}

	public List<PsyAns> getPsyAns() {
		return psyAns;
	}

	public void setPsyAns(List<PsyAns> psyAns) {
		this.psyAns = psyAns;
	}

	public boolean isPsycho_page() {
		return psycho_page;
	}

	public void setPsycho_page(boolean psycho_page) {
		this.psycho_page = psycho_page;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	
	public long getApplicant_id() {
		return applicant_id;
	}

	public void setApplicant_id(long applicant_id) {
		this.applicant_id = applicant_id;
	}

	public String getUserIdStr() {
		return userIdStr;
	}

	public void setUserIdStr(String userIdStr) {
		this.userIdStr = userIdStr;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean isPublished) {
		this.published = isPublished;
	}

	public String getApplicant_address_line_1() {
		return applicant_address_line_1;
	}

	public void setApplicant_address_line_1(String applicant_address_line_1) {
		this.applicant_address_line_1 = applicant_address_line_1;
	}

	public String getApplicant_address_line_2() {
		return applicant_address_line_2;
	}

	public void setApplicant_address_line_2(String applicant_address_line_2) {
		this.applicant_address_line_2 = applicant_address_line_2;
	}

	public String getApplicant_address_line_3() {
		return applicant_address_line_3;
	}

	public void setApplicant_address_line_3(String applicant_address_line_3) {
		this.applicant_address_line_3 = applicant_address_line_3;
	}

	public String getApplicant_state() {
		return applicant_state;
	}

	public void setApplicant_state(String applicant_state) {
		this.applicant_state = applicant_state;
	}

	public long getApplicant_PIN() {
		return applicant_PIN;
	}

	public void setApplicant_PIN(long applicant_PIN) {
		this.applicant_PIN = applicant_PIN;
	}

	public String getApplicant_firstname() {
		return applicant_firstname;
	}

	public void setApplicant_firstname(String applicant_firstname) {
		this.applicant_firstname = applicant_firstname;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getApplicant_middle_name() {
		return applicant_middle_name;
	}

	public void setApplicant_middle_name(String applicant_middle_name) {
		this.applicant_middle_name = applicant_middle_name;
	}

	public String getApplicant_lastname() {
		return applicant_lastname;
	}

	public void setApplicant_lastname(String applicant_lastname) {
		this.applicant_lastname = applicant_lastname;
	}

	public String getApplicant_email_id() {
		return applicant_email_id;
	}

	public void setApplicant_email_id(String applicant_email_id) {
		this.applicant_email_id = applicant_email_id;
	}

	public String getApplicant_mobile_no() {
		return applicant_mobile_no;
	}

	public void setApplicant_mobile_no(String applicant_mobile_no) {
		this.applicant_mobile_no = applicant_mobile_no;
	}

	public String getApplicant_family_mobile_no() {
		return applicant_family_mobile_no;
	}

	
	
	public String getMaritalstatus() {
		return maritalstatus;
	}

	public void setMaritalstatus(String maritalstatus) {
		this.maritalstatus = maritalstatus;
	}

	public String getHouserent() {
		return houserent;
	}

	public void setHouserent(String houserent) {
		this.houserent = houserent;
	}

	public String getLandunit() {
		return landunit;
	}

	public void setLandunit(String landunit) {
		this.landunit = landunit;
	}

	public void setApplicant_family_mobile_no(String applicant_family_mobile_no) {
		this.applicant_family_mobile_no = applicant_family_mobile_no;
	}

	public Date getApplicant_date_of_birth() {
		return applicant_date_of_birth;
	}

	public void setApplicant_date_of_birth(Date applicant_date_of_birth) {
		this.applicant_date_of_birth = applicant_date_of_birth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getApplicant_father_firstname() {
		return applicant_father_firstname;
	}

	public void setApplicant_father_firstname(String applicant_father_firstname) {
		this.applicant_father_firstname = applicant_father_firstname;
	}

	public String getApplicant_father_middle_name() {
		return applicant_father_middle_name;
	}

	public void setApplicant_father_middle_name(String applicant_father_middle_name) {
		this.applicant_father_middle_name = applicant_father_middle_name;
	}

	public String getApplicant_father_lastname() {
		return applicant_father_lastname;
	}

	public void setApplicant_father_lastname(String applicant_father_lastname) {
		this.applicant_father_lastname = applicant_father_lastname;
	}

	public String getApplicant_city_name() {
		return applicant_city_name;
	}

	public int getNo_of_family_member() {
		return no_of_family_member;
	}

	public void setNo_of_family_member(int no_of_family_member) {
		this.no_of_family_member = no_of_family_member;
	}

	public int getNo_of_earning_member() {
		return no_of_earning_member;
	}

	public void setNo_of_earning_member(int no_of_earning_member) {
		this.no_of_earning_member = no_of_earning_member;
	}

	public int getNo_of_children() {
		return no_of_children;
	}

	public void setNo_of_children(int no_of_children) {
		this.no_of_children = no_of_children;
	}

	public int getNo_of_children_studying() {
		return no_of_children_studying;
	}

	public void setNo_of_children_studying(int no_of_children_studying) {
		this.no_of_children_studying = no_of_children_studying;
	}

	public String getEducation_expenses_on_children() {
		return education_expenses_on_children;
	}

	public void setEducation_expenses_on_children(String education_expenses_on_children) {
		this.education_expenses_on_children = education_expenses_on_children;
	}

	public int getNo_of_sick_members() {
		return no_of_sick_members;
	}

	public void setNo_of_sick_members(int no_of_sick_members) {
		this.no_of_sick_members = no_of_sick_members;
	}

	public boolean isFamily_covered_under_aayushman_or_similar_scheme() {
		return Family_covered_under_aayushman_or_similar_scheme;
	}

	public void setFamily_covered_under_aayushman_or_similar_scheme(
			boolean family_covered_under_aayushman_or_similar_scheme) {
		Family_covered_under_aayushman_or_similar_scheme = family_covered_under_aayushman_or_similar_scheme;
	}

	public boolean isApplicant_alcoholic() {
		return applicant_alcoholic;
	}

	public void setApplicant_alcoholic(boolean applicant_alcoholic) {
		this.applicant_alcoholic = applicant_alcoholic;
	}

	public boolean isApplicant_tobacco_smoking_or_chewing() {
		return applicant_tobacco_smoking_or_chewing;
	}

	public void setApplicant_tobacco_smoking_or_chewing(boolean applicant_tobacco_smoking_or_chewing) {
		this.applicant_tobacco_smoking_or_chewing = applicant_tobacco_smoking_or_chewing;
	}

	public String getFriend1_name() {
		return friend1_name;
	}

	public void setFriend1_name(String friend1_name) {
		this.friend1_name = friend1_name;
	}

	public String getFriend1_mobile_no() {
		return friend1_mobile_no;
	}

	public void setFriend1_mobile_no(String friend1_mobile_no) {
		this.friend1_mobile_no = friend1_mobile_no;
	}

	public String getFriend1_job() {
		return friend1_job;
	}

	public void setFriend1_job(String friend1_job) {
		this.friend1_job = friend1_job;
	}

	public String getFriend2_name() {
		return friend2_name;
	}

	public void setFriend2_name(String friend2_name) {
		this.friend2_name = friend2_name;
	}

	public String getFriend2_mobile_no() {
		return friend2_mobile_no;
	}

	public void setFriend2_mobile_no(String friend2_mobile_no) {
		this.friend2_mobile_no = friend2_mobile_no;
	}

	public String getFriend2_job() {
		return friend2_job;
	}

	public void setFriend2_job(String friend2_job) {
		this.friend2_job = friend2_job;
	}

	public String getHouse_locality() {
		return house_locality;
	}

	public void setHouse_locality(String house_locality) {
		this.house_locality = house_locality;
	}

	public String getHouse_rent_type() {
		return house_rent_type;
	}

	public void setHouse_rent_type(String house_rent_type) {
		this.house_rent_type = house_rent_type;
	}

	public String getHouse_type() {
		return house_type;
	}

	public void setHouse_type(String house_type) {
		this.house_type = house_type;
	}

	public boolean isApplicant_agr_land_owned() {
		return applicant_agr_land_owned;
	}

	public void setApplicant_agr_land_owned(boolean applicant_agr_land_owned) {
		this.applicant_agr_land_owned = applicant_agr_land_owned;
	}

	public String getApplicant_land_area() {
		return applicant_land_area;
	}

	public void setApplicant_land_area(String applicant_land_area) {
		this.applicant_land_area = applicant_land_area;
	}

	public boolean isApplicant_shop_owned() {
		return applicant_shop_owned;
	}

	public void setApplicant_shop_owned(boolean applicant_shop_owned) {
		this.applicant_shop_owned = applicant_shop_owned;
	}

	public String getApplicant_qualification() {
		return applicant_qualification;
	}

	public void setApplicant_qualification(String applicant_qualification) {
		this.applicant_qualification = applicant_qualification;
	}

	public String getApplicant_job() {
		return applicant_job;
	}

	public void setApplicant_job(String applicant_job) {
		this.applicant_job = applicant_job;
	}

	public String getType_of_income() {
		return type_of_income;
	}

	public void setType_of_income(String type_of_income) {
		this.type_of_income = type_of_income;
	}

	public boolean isApplicant_income_agriculture() {
		return applicant_income_agriculture;
	}

	public void setApplicant_income_agriculture(boolean applicant_income_agriculture) {
		this.applicant_income_agriculture = applicant_income_agriculture;
	}

	public boolean isApplicant_income_non_agriculture() {
		return applicant_income_non_agriculture;
	}

	public void setApplicant_income_non_agriculture(boolean applicant_income_non_agriculture) {
		this.applicant_income_non_agriculture = applicant_income_non_agriculture;
	}

	public String getApplicant_total_income_agriculture_session() {
		return applicant_total_income_agriculture_session;
	}

	public void setApplicant_total_income_agriculture_session(String applicant_total_income_agriculture_session) {
		this.applicant_total_income_agriculture_session = applicant_total_income_agriculture_session;
	}

	public String getApplicant_total_income_agriculture_crop() {
		return applicant_total_income_agriculture_crop;
	}

	public void setApplicant_total_income_agriculture_crop(String applicant_total_income_agriculture_crop) {
		this.applicant_total_income_agriculture_crop = applicant_total_income_agriculture_crop;
	}

	public String getApplicant_non_agriculture_income_type() {
		return applicant_non_agriculture_income_type;
	}

	public void setApplicant_non_agriculture_income_type(String applicant_non_agriculture_income_type) {
		this.applicant_non_agriculture_income_type = applicant_non_agriculture_income_type;
	}

	public String getApplicant_role() {
		return applicant_role;
	}

	public void setApplicant_role(String applicant_role) {
		this.applicant_role = applicant_role;
	}

	public String getApplicant_income() {
		return applicant_income;
	}

	public void setApplicant_income(String applicant_income) {
		this.applicant_income = applicant_income;
	}

	public String getApplicant_employment_type() {
		return applicant_employment_type;
	}

	public void setApplicant_employment_type(String applicant_employment_type) {
		this.applicant_employment_type = applicant_employment_type;
	}

	public boolean isApplicant_industry_stable() {
		return applicant_industry_stable;
	}

	public void setApplicant_industry_stable(boolean applicant_industry_stable) {
		this.applicant_industry_stable = applicant_industry_stable;
	}

	public String getApplicant_organisation_type() {
		return applicant_organisation_type;
	}

	public void setApplicant_organisation_type(String applicant_organisation_type) {
		this.applicant_organisation_type = applicant_organisation_type;
	}

	public String getApplicant_business() {
		return applicant_business;
	}

	public void setApplicant_business(String applicant_business) {
		this.applicant_business = applicant_business;
	}

	public String getApplicant_business_product() {
		return applicant_business_product;
	}

	public void setApplicant_business_product(String applicant_business_product) {
		this.applicant_business_product = applicant_business_product;
	}

	public String getApplicant_business_turnover() {
		return applicant_business_turnover;
	}

	public void setApplicant_business_turnover(String applicant_business_turnover) {
		this.applicant_business_turnover = applicant_business_turnover;
	}

	public String getApplicant_business_income() {
		return applicant_business_income;
	}

	public void setApplicant_business_income(String applicant_business_income) {
		this.applicant_business_income = applicant_business_income;
	}

	public String getApplicant_customer() {
		return applicant_customer;
	}

	public void setApplicant_customer(String applicant_customer) {
		this.applicant_customer = applicant_customer;
	}

	public String getApplicant_business_other() {
		return applicant_business_other;
	}

	public void setApplicant_business_other(String applicant_business_other) {
		this.applicant_business_other = applicant_business_other;
	}

	public String getApplicant_rasan_card_type() {
		return applicant_rasan_card_type;
	}

	public void setApplicant_rasan_card_type(String applicant_rasan_card_type) {
		this.applicant_rasan_card_type = applicant_rasan_card_type;
	}

	public boolean isApplicant_hold_bank_account() {
		return applicant_hold_bank_account;
	}

	public void setApplicant_hold_bank_account(boolean applicant_hold_bank_account) {
		this.applicant_hold_bank_account = applicant_hold_bank_account;
	}

	public boolean isApplicant_invest() {
		return applicant_invest;
	}

	public void setApplicant_invest(boolean applicant_invest) {
		this.applicant_invest = applicant_invest;
	}

	public boolean isApplicant_owned_tractor() {
		return applicant_owned_tractor;
	}

	public void setApplicant_owned_tractor(boolean applicant_owned_tractor) {
		this.applicant_owned_tractor = applicant_owned_tractor;
	}

	public boolean isApplicant_owned_car() {
		return applicant_owned_car;
	}

	public void setApplicant_owned_car(boolean applicant_owned_car) {
		this.applicant_owned_car = applicant_owned_car;
	}

	public boolean isApplicant_owned_scooter_motorcycle() {
		return applicant_owned_scooter_motorcycle;
	}

	public void setApplicant_owned_scooter_motorcycle(boolean applicant_owned_scooter_motorcycle) {
		this.applicant_owned_scooter_motorcycle = applicant_owned_scooter_motorcycle;
	}

	public boolean isApplicant_owned_bicycle() {
		return applicant_owned_bicycle;
	}

	public void setApplicant_owned_bicycle(boolean applicant_owned_bicycle) {
		this.applicant_owned_bicycle = applicant_owned_bicycle;
	}

	public boolean isApplicant_owned_auto() {
		return applicant_owned_auto;
	}

	public void setApplicant_owned_auto(boolean applicant_owned_auto) {
		this.applicant_owned_auto = applicant_owned_auto;
	}

	public boolean isApplicant_bank_loan() {
		return applicant_bank_loan;
	}

	public void setApplicant_bank_loan(boolean applicant_bank_loan) {
		this.applicant_bank_loan = applicant_bank_loan;
	}

	public boolean isApplicant_sahukar_loan() {
		return applicant_sahukar_loan;
	}

	public void setApplicant_sahukar_loan(boolean applicant_sahukar_loan) {
		this.applicant_sahukar_loan = applicant_sahukar_loan;
	}

	public String getApplicant_loan_amount() {
		return applicant_loan_amount;
	}

	public void setApplicant_loan_amount(String applicant_loan_amount) {
		this.applicant_loan_amount = applicant_loan_amount;
	}

	public String getApplicant_loan_amount_sahukar() {
		return applicant_loan_amount_sahukar;
	}

	public void setApplicant_loan_amount_sahukar(String applicant_loan_amount_sahukar) {
		this.applicant_loan_amount_sahukar = applicant_loan_amount_sahukar;
	}

	public boolean isApplicant_have_agriculture_machinery() {
		return applicant_have_agriculture_machinery;
	}

	public void setApplicant_have_agriculture_machinery(boolean applicant_have_agriculture_machinery) {
		this.applicant_have_agriculture_machinery = applicant_have_agriculture_machinery;
	}

	public boolean isApplicant_have_non_agriculture_machinery() {
		return applicant_have_non_agriculture_machinery;
	}

	public void setApplicant_have_non_agriculture_machinery(boolean applicant_have_non_agriculture_machinery) {
		this.applicant_have_non_agriculture_machinery = applicant_have_non_agriculture_machinery;
	}

	public String getApplicant_expense_monthly() {
		return applicant_expense_monthly;
	}

	public void setApplicant_expense_monthly(String applicant_expense_monthly) {
		this.applicant_expense_monthly = applicant_expense_monthly;
	}

	public String getApplicant_education_expense_monthly() {
		return applicant_education_expense_monthly;
	}

	public void setApplicant_education_expense_monthly(String applicant_education_expense_monthly) {
		this.applicant_education_expense_monthly = applicant_education_expense_monthly;
	}

	public String getApplicant_medical_expense() {
		return applicant_medical_expense;
	}

	public void setApplicant_medical_expense(String applicant_medical_expense) {
		this.applicant_medical_expense = applicant_medical_expense;
	}

	public String getApplicant_children_school_type() {
		return applicant_children_school_type;
	}

	public void setApplicant_children_school_type(String applicant_children_school_type) {
		this.applicant_children_school_type = applicant_children_school_type;
	}

	public void setApplicant_city_name(String applicant_city_name) {
		this.applicant_city_name = applicant_city_name;
	}


	public LocalDate getDataentdt() {
		return dataentdt;
	}

	public void setDataentdt(LocalDate dataentdt) {
		this.dataentdt = dataentdt;
	}

	public LocalDate getDatamoddt() {
		return datamoddt;
	}

	public void setDatamoddt(LocalDate datamoddt) {
		this.datamoddt = datamoddt;
	}

	public String getPrediciton() {
		return prediciton;
	}

	public void setPrediciton(String prediciton) {
		this.prediciton = prediciton;
	}

	
	
	
	
	public String getApplicant_bill_amount() {
		return applicant_bill_amount;
	}

	public void setApplicant_bill_amount(String applicant_bill_amount) {
		this.applicant_bill_amount = applicant_bill_amount;
	}

	public String getApplicant_bill_paid() {
		return applicant_bill_paid;
	}

	public void setApplicant_bill_paid(String applicant_bill_paid) {
		this.applicant_bill_paid = applicant_bill_paid;
	}

	public String getApplicant_kisan_card() {
		return applicant_kisan_card;
	}

	public void setApplicant_kisan_card(String applicant_kisan_card) {
		this.applicant_kisan_card = applicant_kisan_card;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getPurposeOfLoan() {
		return purposeOfLoan;
	}

	public void setPurposeOfLoan(String purposeOfLoan) {
		this.purposeOfLoan = purposeOfLoan;
	}

	public boolean isSubmited() {
		return submited;
	}

	public void setSubmited(boolean submited) {
		this.submited = submited;
	}
	private boolean is_having_cell ;
	public boolean isIs_having_cell() {
		return is_having_cell;
	}

	public void setIs_having_cell(boolean is_having_cell) {
		this.is_having_cell = is_having_cell;
	}

	public boolean isIs_having_livestock() {
		return is_having_livestock;
	}

	public void setIs_having_livestock(boolean is_having_livestock) {
		this.is_having_livestock = is_having_livestock;
	}

	public boolean isIs_having_electricit() {
		return is_having_electricit;
	}

	public void setIs_having_electricit(boolean is_having_electricit) {
		this.is_having_electricit = is_having_electricit;
	}

	public float getFood_expenses() {
		return food_expenses;
	}

	public void setFood_expenses(float food_expenses) {
		this.food_expenses = food_expenses;
	}

	public float getUtilities_expenses() {
		return utilities_expenses;
	}

	public void setUtilities_expenses(float utilities_expenses) {
		this.utilities_expenses = utilities_expenses;
	}

	public float getTransport_expenses() {
		return transport_expenses;
	}

	public void setTransport_expenses(float transport_expenses) {
		this.transport_expenses = transport_expenses;
	}

	public float getClothing_expenses() {
		return clothing_expenses;
	}

	public void setClothing_expenses(float clothing_expenses) {
		this.clothing_expenses = clothing_expenses;
	}



	public float getHouse_renovation_expenses() {
		return house_renovation_expenses;
	}

	public void setHouse_renovation_expenses(float house_renovation_expenses) {
		this.house_renovation_expenses = house_renovation_expenses;
	}

	public float getFunctions_expenses() {
		return functions_expenses;
	}

	public void setFunctions_expenses(float functions_expenses) {
		this.functions_expenses = functions_expenses;
	}

	public float getLand_area_inacres() {
		return land_area_inacres;
	}

	public void setLand_area_inacres(float land_area_inacres) {
		this.land_area_inacres = land_area_inacres;
	}

	public String getAppl_main_income_src() {
		return appl_main_income_src;
	}

	public void setAppl_main_income_src(String appl_main_income_src) {
		this.appl_main_income_src = appl_main_income_src;
	}



	
	public Applicant() {
		super();
		// TODO Auto-generated constructor stub
	}
	
/*
	public Applicant(@NotNull long applicant_id, User user, @NotNull String userIdStr, @NotNull String loanAmount,
			@NotNull String purposeOfLoan, @NotNull long groupId, boolean anygroup, boolean published,
			@NotNull LocalDate dataentdt, @NotNull LocalDate datamoddt, @NotNull String applicant_address_line_1,
			@NotNull String applicant_address_line_2, @NotNull String applicant_address_line_3,
			@NotNull String applicant_state, @NotNull long applicant_PIN, @NotNull String applicant_firstname,
			@NotNull String applicant_middle_name, @NotNull String applicant_lastname,
			@NotNull String applicant_email_id, @NotNull String applicant_mobile_no,
			@NotNull String applicant_family_mobile_no, @NotNull Date applicant_date_of_birth, @NotNull String gender,
			@NotNull String applicant_father_firstname, @NotNull String applicant_father_middle_name,
			@NotNull String applicant_father_lastname, @NotNull String applicant_city_name, boolean submited,
			@NotNull int no_of_family_member, @NotNull int no_of_earning_member, @NotNull int no_of_children,
			@NotNull int no_of_children_studying, @NotNull String education_expenses_on_children,
			@NotNull int no_of_sick_members, boolean family_covered_under_aayushman_or_similar_scheme,
			boolean applicant_alcoholic, boolean applicant_tobacco_smoking_or_chewing, @NotNull String friend1_name,
			@NotNull String friend1_mobile_no, @NotNull String friend1_job, @NotNull String friend2_name,
			@NotNull String friend2_mobile_no, @NotNull String friend2_job, @NotNull String house_locality,
			@NotNull String house_rent_type, @NotNull String house_type, boolean applicant_agr_land_owned,
			@NotNull String applicant_land_area, boolean applicant_shop_owned, @NotNull String applicant_qualification,
			@NotNull String applicant_job, @NotNull String type_of_income, boolean applicant_income_agriculture,
			boolean applicant_income_non_agriculture, @NotNull String applicant_total_income_agriculture_session,
			@NotNull String applicant_total_income_agriculture_crop,
			@NotNull String applicant_non_agriculture_income_type, @NotNull String applicant_role,
			@NotNull String applicant_income, @NotNull String applicant_employment_type,
			boolean applicant_industry_stable, @NotNull String applicant_organisation_type,
			@NotNull String applicant_business, @NotNull String applicant_business_product,
			@NotNull String applicant_business_turnover, @NotNull String applicant_business_income,
			@NotNull String applicant_customer, @NotNull String applicant_business_other,
			@NotNull String applicant_rasan_card_type, boolean applicant_hold_bank_account, boolean applicant_invest,
			boolean applicant_owned_tractor, boolean applicant_owned_car, boolean applicant_owned_scooter_motorcycle,
			boolean applicant_owned_bicycle, boolean applicant_owned_auto, boolean applicant_bank_loan,
			boolean applicant_sahukar_loan, @NotNull String applicant_loan_amount,
			@NotNull String applicant_loan_amount_sahukar, boolean applicant_have_agriculture_machinery,
			boolean applicant_have_non_agriculture_machinery, @NotNull String applicant_expense_monthly,
			@NotNull String applicant_education_expense_monthly, @NotNull String applicant_medical_expense,
			@NotNull String applicant_children_school_type, @NotNull String maritalstatus, @NotNull String houserent,
			@NotNull String landunit, @NotNull String applicant_bill_amount, @NotNull String applicant_bill_paid,
			@NotNull String applicant_kisan_card, @NotNull String loanskill, boolean psycho_page, String prediciton,
			String latitude, String longitude, List<PsyAns> psyAns) {
		super();
		this.applicant_id = applicant_id;
		this.user = user;
		this.userIdStr = userIdStr;
		this.loanAmount = loanAmount;
		this.purposeOfLoan = purposeOfLoan;
		this.groupId = groupId;
		this.anygroup = anygroup;
		this.published = published;
		this.dataentdt = dataentdt;
		this.datamoddt = datamoddt;
		this.applicant_address_line_1 = applicant_address_line_1;
		this.applicant_address_line_2 = applicant_address_line_2;
		this.applicant_address_line_3 = applicant_address_line_3;
		this.applicant_state = applicant_state;
		this.applicant_PIN = applicant_PIN;
		this.applicant_firstname = applicant_firstname;
		this.applicant_middle_name = applicant_middle_name;
		this.applicant_lastname = applicant_lastname;
		this.applicant_email_id = applicant_email_id;
		this.applicant_mobile_no = applicant_mobile_no;
		this.applicant_family_mobile_no = applicant_family_mobile_no;
		this.applicant_date_of_birth = applicant_date_of_birth;
		this.gender = gender;
		this.applicant_father_firstname = applicant_father_firstname;
		this.applicant_father_middle_name = applicant_father_middle_name;
		this.applicant_father_lastname = applicant_father_lastname;
		this.applicant_city_name = applicant_city_name;
		this.submited = submited;
		this.no_of_family_member = no_of_family_member;
		this.no_of_earning_member = no_of_earning_member;
		this.no_of_children = no_of_children;
		this.no_of_children_studying = no_of_children_studying;
		this.education_expenses_on_children = education_expenses_on_children;
		this.no_of_sick_members = no_of_sick_members;
		Family_covered_under_aayushman_or_similar_scheme = family_covered_under_aayushman_or_similar_scheme;
		this.applicant_alcoholic = applicant_alcoholic;
		this.applicant_tobacco_smoking_or_chewing = applicant_tobacco_smoking_or_chewing;
		this.friend1_name = friend1_name;
		this.friend1_mobile_no = friend1_mobile_no;
		this.friend1_job = friend1_job;
		this.friend2_name = friend2_name;
		this.friend2_mobile_no = friend2_mobile_no;
		this.friend2_job = friend2_job;
		this.house_locality = house_locality;
		this.house_rent_type = house_rent_type;
		this.house_type = house_type;
		this.applicant_agr_land_owned = applicant_agr_land_owned;
		this.applicant_land_area = applicant_land_area;
		this.applicant_shop_owned = applicant_shop_owned;
		this.applicant_qualification = applicant_qualification;
		this.applicant_job = applicant_job;
		this.type_of_income = type_of_income;
		this.applicant_income_agriculture = applicant_income_agriculture;
		this.applicant_income_non_agriculture = applicant_income_non_agriculture;
		this.applicant_total_income_agriculture_session = applicant_total_income_agriculture_session;
		this.applicant_total_income_agriculture_crop = applicant_total_income_agriculture_crop;
		this.applicant_non_agriculture_income_type = applicant_non_agriculture_income_type;
		this.applicant_role = applicant_role;
		this.applicant_income = applicant_income;
		this.applicant_employment_type = applicant_employment_type;
		this.applicant_industry_stable = applicant_industry_stable;
		this.applicant_organisation_type = applicant_organisation_type;
		this.applicant_business = applicant_business;
		this.applicant_business_product = applicant_business_product;
		this.applicant_business_turnover = applicant_business_turnover;
		this.applicant_business_income = applicant_business_income;
		this.applicant_customer = applicant_customer;
		this.applicant_business_other = applicant_business_other;
		this.applicant_rasan_card_type = applicant_rasan_card_type;
		this.applicant_hold_bank_account = applicant_hold_bank_account;
		this.applicant_invest = applicant_invest;
		this.applicant_owned_tractor = applicant_owned_tractor;
		this.applicant_owned_car = applicant_owned_car;
		this.applicant_owned_scooter_motorcycle = applicant_owned_scooter_motorcycle;
		this.applicant_owned_bicycle = applicant_owned_bicycle;
		this.applicant_owned_auto = applicant_owned_auto;
		this.applicant_bank_loan = applicant_bank_loan;
		this.applicant_sahukar_loan = applicant_sahukar_loan;
		this.applicant_loan_amount = applicant_loan_amount;
		this.applicant_loan_amount_sahukar = applicant_loan_amount_sahukar;
		this.applicant_have_agriculture_machinery = applicant_have_agriculture_machinery;
		this.applicant_have_non_agriculture_machinery = applicant_have_non_agriculture_machinery;
		this.applicant_expense_monthly = applicant_expense_monthly;
		this.applicant_education_expense_monthly = applicant_education_expense_monthly;
		this.applicant_medical_expense = applicant_medical_expense;
		this.applicant_children_school_type = applicant_children_school_type;
		this.maritalstatus = maritalstatus;
		this.houserent = houserent;
		this.landunit = landunit;
		this.applicant_bill_amount = applicant_bill_amount;
		this.applicant_bill_paid = applicant_bill_paid;
		this.applicant_kisan_card = applicant_kisan_card;
		this.loanskill = loanskill;
		this.psycho_page = psycho_page;
		this.prediciton = prediciton;
		this.latitude = latitude;
		this.longitude = longitude;
		this.psyAns = psyAns;
	}
	*/
	


}
