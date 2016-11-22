package com.rwork.cloudeye.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="contact")
public class Contact {
	
	private String emailId;
	private String mobile;
	private String twitterhandle;
	private String facebookhandle;
	private String address;
	private String elkurl;
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getTwitterhandle() {
		return twitterhandle;
	}
	public void setTwitterhandle(String twitterhandle) {
		this.twitterhandle = twitterhandle;
	}
	public String getFacebookhandle() {
		return facebookhandle;
	}
	public void setFacebookhandle(String facebookhandle) {
		this.facebookhandle = facebookhandle;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getElkurl() {
		return elkurl;
	}
	public void setElkurl(String elkurl) {
		this.elkurl = elkurl;
	}
	
	
	

}
