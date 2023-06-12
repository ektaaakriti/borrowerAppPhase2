package com.securedloan.arthavedika.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;

import com.securedloan.arthavedika.model.Applicant;

public class CSVHelper {
	private static final String[] HEADERS = { "Applicant Id", "Applicant Firstname", "Applicant Middlename",
			"Applicant Laststname", "Date of Birth", "Email address", "Mobile No.", "Applicant Father's Firstname",
			"Applicant Father's Middlename", "Applicant Father's Laststname", "Gender", "Address Line 1",
			"Address Line 2", "Address Line 3", "City", "State", "PIN", "Family Mobile No.", "Qualification", "Job",
			"No. of Family Members", "No. of Earning Members", "No. of Children", "No. of Children Studying",
			"Education Expenses", "No. of Sick Member", "Family Coverded under Ayushmaan or Similar Scheme",
			"Applicant Alcoholic", "Applicant Tobacco Smoking or Chewing", "Friend 1 Name", "Friend 1 Mobile No.",
			"Friend 1 Job", "Friend 2 Name", "Friend 2 Mobile No.", "Friend 2 Job", "House Locality", "House Rent Type",
			"House Type", "Applicant Agriculture Land Owned", "Land Area", "Shop Owned", "Agricultural Income",
			"Non Agricultural Income", "Income Per Agricultural Session", "No. of Crops",
			"Non Agricultural Income type", "Salaried Role", "Salaried Income", "Salaried Employment Type",
			"Industry Stability", "Organisation Type", "Business", "Business Product", "Business TurnOver",
			"Business Income", "Customer", "Other Income", "Rasan Card", "Bank Account", "Investment", "Tractor", "Car",
			"MotorCycle/Scooter", "Bicycle", "Auto", "Bank Loan", "Sahukar Loan", "Loan Amount (Bank)",
			"Loan Amount(Sahukar)", "Agricultural Machinery", "Non Agricultural Machinery", "Monthly Expense",
			"Education Expense Monthly", "Medical Expense", "Children School type" };

	public static ByteArrayInputStream applicantToCSV(List<Applicant> applicants) {
		final CSVFormat format = CSVFormat.DEFAULT.withHeader(HEADERS);

		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
				CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
			for (Applicant applicant : applicants) {
				List<Object> data = Arrays.asList(String.valueOf(applicant.getApplicant_id()),
						applicant.getApplicant_firstname(), applicant.getApplicant_middle_name(),
						applicant.getApplicant_lastname(), applicant.getApplicant_date_of_birth(),
						applicant.getApplicant_email_id(), applicant.getApplicant_mobile_no(),
						applicant.getApplicant_father_firstname(), applicant.getApplicant_father_middle_name(),
						applicant.getApplicant_father_lastname(), applicant.getGender(),
						applicant.getApplicant_address_line_1(), applicant.getApplicant_address_line_2(),
						applicant.getApplicant_address_line_3(), applicant.getApplicant_city_name(),
						applicant.getApplicant_state(), applicant.getApplicant_PIN(),
						applicant.getApplicant_family_mobile_no(), applicant.getApplicant_qualification(),
						applicant.getApplicant_job(), applicant.getNo_of_family_member(),
						applicant.getNo_of_earning_member(), applicant.getNo_of_children(),
						applicant.getNo_of_children_studying(), applicant.getEducation_expenses_on_children(),
						applicant.getNo_of_sick_members(),
						applicant.isFamily_covered_under_aayushman_or_similar_scheme(),
						applicant.isApplicant_alcoholic(), applicant.isApplicant_tobacco_smoking_or_chewing(),
						applicant.getFriend1_name(), applicant.getFriend1_mobile_no(), applicant.getFriend1_job(),
						applicant.getFriend2_name(), applicant.getFriend2_mobile_no(), applicant.getFriend2_job(),
						applicant.getHouse_locality(), applicant.getHouse_rent_type(), applicant.getHouse_type(),
						applicant.isApplicant_agr_land_owned(), applicant.getApplicant_land_area(),
						applicant.isApplicant_shop_owned(), applicant.isApplicant_income_agriculture(),
						applicant.isApplicant_income_non_agriculture(),
						applicant.getApplicant_total_income_agriculture_session(),
						applicant.getApplicant_total_income_agriculture_crop(),
						applicant.getApplicant_non_agriculture_income_type(), applicant.getApplicant_role(),
						applicant.getApplicant_income(), applicant.getApplicant_employment_type(),
						applicant.isApplicant_industry_stable(), applicant.getApplicant_organisation_type(),
						applicant.getApplicant_business(), applicant.getApplicant_business_product(),
						applicant.getApplicant_business_turnover(), applicant.getApplicant_business_income(),
						applicant.getApplicant_customer(), applicant.getApplicant_business_other(),
						applicant.getApplicant_rasan_card_type(), applicant.isApplicant_hold_bank_account(),
						applicant.isApplicant_invest(), applicant.isApplicant_owned_tractor(),
						applicant.isApplicant_owned_car(), applicant.isApplicant_owned_scooter_motorcycle(),
						applicant.isApplicant_owned_bicycle(), applicant.isApplicant_owned_auto(),
						applicant.isApplicant_bank_loan(), applicant.isApplicant_sahukar_loan(),
						applicant.getApplicant_loan_amount(), applicant.getApplicant_loan_amount_sahukar(),
						applicant.isApplicant_have_agriculture_machinery(),
						applicant.isApplicant_have_non_agriculture_machinery(),
						applicant.getApplicant_expense_monthly(), applicant.getApplicant_education_expense_monthly(),
						applicant.getApplicant_medical_expense(), applicant.getApplicant_children_school_type()

				);

				csvPrinter.printRecord(data);
			}

			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
		}
	}
}
