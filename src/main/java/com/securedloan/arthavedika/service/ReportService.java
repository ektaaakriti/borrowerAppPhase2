package com.securedloan.arthavedika.service;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.securedloan.arthavedika.repo.PDFGenerateRepo;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Service
public class ReportService {

	
	@Autowired
	private PDFGenerateRepo repo;

	public void exportReport(String applicant_id, OutputStream out)
			throws FileNotFoundException, SQLException, JRException {
		JasperPrint jasperPrint = repo.exportPDF(applicant_id);
		JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	}
}
