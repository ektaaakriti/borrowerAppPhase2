package com.securedloan.arthavedika.service;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.securedloan.arthavedika.model.Applicant;
import com.securedloan.arthavedika.model.EkycFileDB;
import com.securedloan.arthavedika.repo.EkycFileDBRepository;

@Service
public class EkycFileStorageService {

	@Autowired
	private EkycFileDBRepository fileDBRepository;

	public EkycFileDB store(MultipartFile file, Applicant applicant) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		applicant.setApplicant_id(applicant.getApplicant_id());
		EkycFileDB EkycFileDB = new EkycFileDB(fileName, file.getContentType(), applicant, file.getBytes());

		return fileDBRepository.save(EkycFileDB);
	}

	public EkycFileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	public Stream<EkycFileDB> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}
}