package com.securedloan.arthavedika.payload;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class Document {
	private String name;

	private String docName;
	
	private String document;
	MultipartFile file;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDocName() {
		return docName;
	}
	public void setDocName(String docName) {
		this.docName = docName;
	}
	public String getDocument() {
		return document;
	}
	public void setDocument(String document) {
		this.document = document;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
