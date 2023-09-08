package com.securedloan.arthavedika.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="m_location")
public class M_location {
@Id
int location_id;
public M_location(int location_id, String location_name) {
	super();
	this.location_id = location_id;
	this.location_name = location_name;
}
public int getLocation_id() {
	return location_id;
}
public void setLocation_id(int location_id) {
	this.location_id = location_id;
}
public String getLocation_name() {
	return location_name;
}
public void setLocation_name(String location_name) {
	this.location_name = location_name;
}
String location_name;
public M_location() {
	super();
	// TODO Auto-generated constructor stub
}

}
