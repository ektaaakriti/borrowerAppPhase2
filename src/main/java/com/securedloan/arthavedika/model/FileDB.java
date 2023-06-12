package com.securedloan.arthavedika.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "files")
public class FileDB {
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	private String id;

	private String name;

	private String type;

	private String docName;
	
	private String document;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id")
	private Applicant applicant;
	
	

	public void setId(String id) {
		this.id = id;
	}

	//getter setter constuctor
	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	

	public Applicant getApplicant() {
		return applicant;
	}

	public FileDB() {
		super();
		// TODO Auto-generated constructor stub
	}

//	public FileDB(String name, String type, String docName, Applicant applicant, byte[] data) {
//		super();
//		this.name = name;
//		this.type = type;
//		this.docName = docName;
//		this.applicant = applicant;
//		this.data = data;
//	}
	

//public FileDB() {
//	super();
//	// TODO Auto-generated constructor stub
//}
//
//public FileDB(String name, String type, Applicant applicant, byte[] data) {
//	super();
//	this.name = name;
//	this.type = type;
//	this.applicant = applicant;
//	this.data = data;
//}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public FileDB(String name, String type, String docName, String document, Applicant applicant, byte[] data) {
	super();
	this.name = name;
	this.type = type;
	this.docName = docName;
	this.document = document;
	this.applicant = applicant;
	this.data = data;
}

	@Lob
	private byte[] data;

//  public FileDB() {
//  }
//
//  public FileDB(String name, String type, byte[] data) {
//    this.name = name;
//    this.type = type;
//    this.data = data;
//  }

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

}