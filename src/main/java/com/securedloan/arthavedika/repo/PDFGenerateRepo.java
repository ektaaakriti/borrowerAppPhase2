package com.securedloan.arthavedika.repo;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import com.securedloan.arthavedika.model.Applicant;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

@Transactional
@Repository
public class PDFGenerateRepo {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JasperPrint exportPDF(String applicant_id) throws SQLException, JRException, FileNotFoundException {
		try {
			Connection connection = jdbcTemplate.getDataSource().getConnection();
			File file = ResourceUtils.getFile("classpath:Sahayak_ReportNew.jrxml");
			JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
			Map<String, Object> parameters = new HashMap<>();
			parameters.put("applicant_id",applicant_id);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
			return jasperPrint;
		} catch (Exception e) {
			System.out.println("Error"+e);
			return null;
		}
	}

}
