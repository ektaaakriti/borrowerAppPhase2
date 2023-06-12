package com.securedloan.arthavedika.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.FileDB;
import com.securedloan.arthavedika.repo.FileDBRepository;

@Service
public class FileStorageService {

	@Autowired
	private FileDBRepository fileDBRepository;

	public FileDB store(MultipartFile file, Applicant applicant, String document, String documentname) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		applicant.setApplicant_id(applicant.getApplicant_id());
		FileDB FileDB = new FileDB(fileName, file.getContentType(), document,documentname, applicant, file.getBytes());

		return fileDBRepository.save(FileDB);
	}

	public FileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	//public Stream<FileDB> getAllFiles() {
	//	return fileDBRepository.findAll().stream();
	//}
	public List<FileDB> documentById(Long applicant_id){
		return fileDBRepository.documentById(applicant_id);
	}
}