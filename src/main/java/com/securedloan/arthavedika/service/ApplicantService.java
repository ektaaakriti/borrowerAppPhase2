package com.securedloan.arthavedika.service;

import java.util.List;

import javax.transaction.Transactional;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.Res;
import com.securedloan.arthavedika.repo.ApplicantRepository;

@Service
@Transactional
public class ApplicantService {
	@Autowired
	private ApplicantRepository applRepo;
	
	public Applicant saveApplicant(Applicant applicant) throws Exception {
		return applRepo.save(applicant);
	}
	
	public Applicant findByApplicant_mobile_no(String applicant_mobile_no) throws Exception {
		return applRepo.findByApplicant_mobile_no(applicant_mobile_no);
	}

	public String concat(String str,byte[] bytes) {
		final StringBuilder sb = new StringBuilder();
		sb.append(str);
		for (byte b : bytes) {
			sb.append(b);
		}
		return sb.toString();
	}

	public  List<Applicant> findById(long applicant_id) throws Exception{
		// TODO Auto-generated method stub
		return applRepo.findByApplicant_id(applicant_id);
	}
	public Applicant findByIds(long applicant_id) throws Exception{
		// TODO Auto-generated method stub
		return applRepo.findByApplicant_ids(applicant_id);
	}
	public List<Applicant> findAllAppicant()throws Exception{
		return applRepo.findAllApplicant();
	}
	//public Object authoriseaApplicant(String company_name,String authorise_by,List Applicant_id) {
	//	return applRepo.authoriseApplicant(company_name, authorise_by, Applicant_id);
	//}

	public Boolean authImage(List<String> image) throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		JSONObject requestBody = new JSONObject();
		requestBody.put("images", image);
//		String url = "http://122.160.96.98:1300/start";
		String url = "http://122.160.201.198:1300/start";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			String responseBody = response.getBody();
			ObjectMapper mapper = new ObjectMapper();
			Res describeAccessResponse = mapper.readValue(responseBody, Res.class);
			return describeAccessResponse.isResult();
		} else {
			throw new Exception("API Response Failed !!");
		}
	}
	

}
