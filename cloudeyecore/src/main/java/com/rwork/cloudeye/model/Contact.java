package com.rwork.cloudeye.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="contact")
public class Contact {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String emailId;
	private String mobile;
	private String twitterhandle;
	private String facebookhandle;
	private String address;
	private String elkurl;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
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
