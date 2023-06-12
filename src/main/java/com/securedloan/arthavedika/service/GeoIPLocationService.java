package com.securedloan.arthavedika.service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.securedloan.arthavedika.response.GeoIP;

@Service
@Transactional
public class GeoIPLocationService {

	private final Logger LOGGER = LoggerFactory.getLogger(GeoIPLocationService .class);
	private DatabaseReader dbReader;

	public GeoIPLocationService() throws IOException {
		//C:\phase 2\phase2\src\main\resources
		File database = new File("C:\\phase 2\\phase2\\src\\main\\resources\\GeoLite2-City.mmdb");
		dbReader = new DatabaseReader.Builder(database).build();
	}

	public GeoIP getLocation(String ip) 
      throws IOException, GeoIp2Exception {
		try {
        InetAddress ipAddress = InetAddress.getByName(ip);
        CityResponse response = dbReader.city(ipAddress);
        System.out.println("hope you got your city name");
        String cityName = response.getCity().getName();
        String latitude = 
          response.getLocation().getLatitude().toString();
        String longitude = 
          response.getLocation().getLongitude().toString();
        String postalCode = response.getPostal().getCode();
        return new GeoIP(ip, cityName, latitude, longitude,postalCode);
    }
	catch(Exception e){
		LOGGER.error("Error While Finding location" + e.getMessage());
		  return new GeoIP(null,null,null,null,null);
	
    }}
}
