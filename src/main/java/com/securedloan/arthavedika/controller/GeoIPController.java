package com.securedloan.arthavedika.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.securedloan.arthavedika.response.GeoIP;
import com.securedloan.arthavedika.service.GeoIPLocationService;

@CrossOrigin()
@RestController
@RequestMapping("applicant/location")


public class GeoIPController {

	static Logger logger = LoggerFactory.getLogger(UserController.class);

   // private GeoIPLocationService locationService;
	
    
    @PostMapping("/GeoIP")
    public GeoIP getLocation(
      @RequestParam(value="ipAddress", required=true) String ipAddress
    ) throws Exception {
      System.out.println("Ip is:" +ipAddress);
     GeoIPLocationService locationService = new GeoIPLocationService();
     return locationService.getLocation(ipAddress);
    }
}
