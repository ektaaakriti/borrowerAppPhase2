package com.securedloan.arthavedika.payload;

import org.springframework.web.multipart.MultipartFile;

public class ekycPayload {
	MultipartFile pic1;
MultipartFile pic2;
public MultipartFile getPic1() {
	return pic1;
}
public void setPic1(MultipartFile pic1) {
	this.pic1 = pic1;
}
public MultipartFile getPic2() {
	return pic2;
}
public void setPic2(MultipartFile pic2) {
	this.pic2 = pic2;
}
}
