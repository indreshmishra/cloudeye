package com.rwork.cloudeye.model;

import java.io.Serializable;

public class UserNotification implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1992425643460452852L;
	private String username;
	private String email;
	private Boolean success;
	private Boolean resumed;
	private String host;
	private String command;
	private String message;
	private String title;
	private String channel; // email , sms , elk  etc.
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Boolean getResumed() {
		return resumed;
	}
	public void setResumed(Boolean resumed) {
		this.resumed = resumed;
	}
	
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	

}
