package com.securedloan.arthavedika.response;


public class GeoIP {
	
    private String ipAddress;
    private String city;
    private String latitude;
    private String longitude;
    private String postalCode;
    
    
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public GeoIP(String ipAddress, String city, String latitude, String longitude, String postalCode) {
		super();
		this.ipAddress = ipAddress;
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
		this.postalCode = postalCode;
		
	}
	public GeoIP() {
		super();
		// TODO Auto-generated constructor stub
	}
    

}

