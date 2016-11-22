package com.rwork.cloudeye.model;

public class UserNotification {
	
	private User user;
	private Boolean success;
	private Boolean resumed;
	private Host host;
	private Command command;
	private String message;
	private String title;
	private String channel; // email , sms , elk  etc.
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public Command getCommand() {
		return command;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
