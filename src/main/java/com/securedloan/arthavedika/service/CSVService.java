package com.securedloan.arthavedika.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securedloan.arthavedika.helper.CSVHelper;
import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.repo.ApplicantRepository;

@Service
public class CSVService {

	@Autowired
	ApplicantRepository applicantRepository;

	public ByteArrayInputStream load(long applicant_id) {
		Optional<Applicant> applicants = applicantRepository.findById(applicant_id);
		if (applicants.isPresent()) {
			Applicant applicantDetail = applicants.get();
			ByteArrayInputStream in = CSVHelper.applicantToCSV(Arrays.asList(applicantDetail));
			return in;
		} else {
			return new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray());
		}
	}
	
	 public ByteArrayInputStream loadDate(String dataentdt,String enddt) throws Exception {
		  System.out.println("haan "+dataentdt);
		  LocalDate date2 = LocalDate.parse(enddt);
		  LocalDate date = LocalDate.parse(dataentdt);
		  System.out.println(date);
		   Optional<List<Applicant>> applicant = applicantRepository.findAllByDate(date,date2);
		   System.out.println("haan bhai hai koi"+applicant);
		   if(applicant.isPresent()) {
			   System.out.println("haan bhai hai"+applicant);
			   List<Applicant> applicantDetail=applicant.get(); 
			   System.out.println("haan bhai milgya"+applicantDetail);
		    ByteArrayInputStream in = CSVHelper.applicantToCSV(applicantDetail);
		    System.out.println("haan bhai milgya"+applicantDetail);
		    return in;
		   }
		   else {
			   return new ByteArrayInputStream(new ByteArrayOutputStream().toByteArray());
		   }
		  }

}