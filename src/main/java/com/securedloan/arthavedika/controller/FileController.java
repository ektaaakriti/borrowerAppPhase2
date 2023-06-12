package com.securedloan.arthavedika.controller;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.response.ResponseMessage;
import com.securedloan.arthavedika.service.FileStorageService;
import com.securedloan.arthavedika.service.ReportService;

import net.sf.jasperreports.engine.JRException;

@Controller
@CrossOrigin()
public class FileController {

	private final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

	@Autowired
	private FileStorageService storageService;
	@Autowired
	private ReportService reportService;

	// @PostMapping("/upload/v1")
	@RequestMapping(value = { "/upload/v1" }, method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseStatus(value = HttpStatus.OK)
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("applicant_id") Applicant applicant, @RequestParam("docName") String docName,@RequestParam("doc") String doc) {
		LOGGER.info("Upload api has been called !!! Start Of Method uploadFile");
		String message = "";
		try {
			storageService.store(file, applicant, docName,doc );
			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			LOGGER.info("End Of Method uploadFile !!!");
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			LOGGER.error("Error While Uploading the file" + e.getMessage());
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/jasper/report/{applicant_id}")
	public void generateReport(HttpServletResponse response,@PathVariable String applicant_id) throws IOException, SQLException, JRException {
	try {	LOGGER.info("Jasper Report api has been called !!! Start Of Method generateReport");
		System.out.println(applicant_id);
		response.setContentType("application/x-download");
		response.setHeader("Content-Disposition", String.format("attachment;filename=\"applicant.pdf\""));
		OutputStream out =response.getOutputStream();
		reportService.exportReport(applicant_id,out);
	}
	catch (Exception e) {
		LOGGER.error("Error While Uploading the file" + e.getMessage());
	}
}}






































//	// @GetMapping("/files")
//	@RequestMapping(value = { "/files/v1" }, method = RequestMethod.GET, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	@ResponseStatus(value = HttpStatus.OK)
//	public ResponseEntity<List<ResponseFile>> getListFiles() {
//		LOGGER.info("Files api has been called !!! Start Of Method getListFiles");
//		try {
//		List<ResponseEntity<ResponseFile>> files = storageService.getAllFiles().map(dbFile -> {
//			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
//					.path(dbFile.getId()).toUriString();
//
//			//return new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length);
//			return ResponseEntity.status(HttpStatus.OK).body(new ResponseFile(dbFile.getName(), fileDownloadUri, dbFile.getType(), dbFile.getData().length));
//		}).collect(Collectors.toList());
//		LOGGER.info("End Of Method getListFiles");
////		return ResponseEntity.status(HttpStatus.OK).body(files);
//		return List ResponseEntity.status(HttpStatus.OK).body(new ResponseFile(files));
//	}catch(Exception e) {
//		
//	}
//	}
////  @GetMapping("/files/{id}")
//	@RequestMapping(value = { "/files/v1/{id}" }, method = RequestMethod.GET, produces = {
//			MediaType.APPLICATION_JSON_VALUE })
//	@ResponseStatus(value = HttpStatus.OK)
//	public ResponseEntity<byte[]> getFile(@PathVariable String id) {
//		LOGGER.info("Files api has been called !!! Start Of Method getListFiles");
//		try {
//		FileDB fileDB = storageService.getFile(id);
//
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
//				.body(fileDB.getData());
//	}catch(Exception e) {
//		
//	}
//}
