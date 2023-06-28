package com.securedloan.arthavedika.payload;

public class ChangePassword {
String old_password;
String new_password;
String user_id;
String mobile_no;
public String getMobile_no() {
	return mobile_no;
}
public void setMobile_no(String mobile_no) {
	this.mobile_no = mobile_no;
}
public String getOld_password() {
	return old_password;
}
public void setOld_password(String old_password) {
	this.old_password = old_password;
}
public String getNew_password() {
	return new_password;
}
public void setNew_password(String new_password) {
	this.new_password = new_password;
}
public String getUser_id() {
	return user_id;
}
public void setUser_id(String user_id) {
	this.user_id = user_id;
}

}
