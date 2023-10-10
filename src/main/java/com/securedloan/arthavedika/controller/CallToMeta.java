package com.securedloan.arthavedika.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.securedloan.arthavedika.EncryptionDecryptionClass;
import com.securedloan.arthavedika.payload.MetaPayload;
import com.securedloan.arthavedika.response.GeneralResponse;


@CrossOrigin()
@RestController
public class CallToMeta {
	EncryptionDecryptionClass encdec=new EncryptionDecryptionClass();
	@RequestMapping(value={"/meta"}, method = RequestMethod.POST, 
			produces= {MediaType.APPLICATION_JSON_VALUE})
	public void  callTometa(@RequestBody MetaPayload metaPayload,HttpServletResponse response ) throws IOException  {
	//HttpServletResponse response;
//	@RequestMapping("/meta")
	//  void handleFoo(HttpServletResponse response) throws IOException {,m
		try {
		String data="name="+metaPayload.getName()+"&clanguage="+metaPayload.getClanguage()+"&accnum="+metaPayload.getAccnum()+"&loanamout="+metaPayload.getLoanamout()+"&pendingamount="+metaPayload.getPendingamount()+"&EMIamount="+metaPayload.getEMIamount()+"&duedate="+metaPayload.getDuedate()+"&pemi="+metaPayload.getPemi() ; 
	    String encData=encdec.encryptnew(data);
	    System.out.println(data);
	    System.out.println(encData);

		response.sendRedirect("http://truckersapp.eastus.cloudapp.azure.com:9080/metaWebApp/index.html?data="+encData);
		System.out.println(response);
		}
	catch(Exception e){
		System.out.println("error is"+e);
	}
	  }
	
}
