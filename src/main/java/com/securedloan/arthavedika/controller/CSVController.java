package com.securedloan.arthavedika.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.securedloan.arthavedika.service.CSVService;

@CrossOrigin()
@Controller
@RequestMapping("/api/csv")
public class CSVController {
	private final Logger LOGGER = LoggerFactory.getLogger(CSVController.class);
	@Autowired
	CSVService fileService;

//  @GetMapping("/download/v1")
	@RequestMapping(value = { "/download/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<Resource> getFile(@RequestParam("applicant_id") String applicant_id) {
		LOGGER.info("SignIn api has been called !!! Start Of Method registerUser");
		try {
			String filename = "applicant.csv";
			InputStreamResource file = new InputStreamResource(fileService.load(Long.parseLong(applicant_id)));
			LOGGER.info("End Of Method registerUser");
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
					.contentType(MediaType.parseMediaType("application/csv")).body(file);
		} catch (Exception e) {
			LOGGER.error("Error While Registering the Applicant" + e.getMessage());
			return ResponseEntity.badRequest().body(null);
		}

	}
	
	
	@RequestMapping(value = { "/download/usingDate/v1" }, method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
//	public ResponseEntity<Resource> getFile(
//			@RequestParam("from_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate){
//			@RequestParam("to_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate) {
 public ResponseEntity<Resource> getFiles(@RequestParam("from_date")String fromDate,@RequestParam("to_date")String toDate) throws Exception {
		String filename = "applicantbydate.csv";
		InputStreamResource file = new InputStreamResource(fileService.loadDate(fromDate,toDate));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}
}
