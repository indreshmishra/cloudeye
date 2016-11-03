package com.rwork.cloudeye.jworker.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class CommandRunHistory {
	
	@Id
	private String id;

	private Long commandid;
	
	private String commandstring;
	
	private Long hostid;
	
	private String hostname;
	
	private String commandoutput;
	
	private String expectedoutputcontains;
	
	private Boolean success;
	
	private Date runDate;
	
	
	private Long ownerid;
	
	private String ownername;
	
	private Long tenantid;
	
	private String tenantname;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCommandid() {
		return commandid;
	}

	public void setCommandid(Long commandid) {
		this.commandid = commandid;
	}

	public String getCommandstring() {
		return commandstring;
	}

	public void setCommandstring(String commandstring) {
		this.commandstring = commandstring;
	}

	public Long getHostid() {
		return hostid;
	}

	public void setHostid(Long hostid) {
		this.hostid = hostid;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getCommandoutput() {
		return commandoutput;
	}

	public void setCommandoutput(String commandoutput) {
		this.commandoutput = commandoutput;
	}

	public String getExpectedoutputcontains() {
		return expectedoutputcontains;
	}

	public void setExpectedoutputcontains(String expectedoutputcontains) {
		this.expectedoutputcontains = expectedoutputcontains;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	public Long getOwnerid() {
		return ownerid;
	}

	public void setOwnerid(Long ownerid) {
		this.ownerid = ownerid;
	}

	public String getOwnername() {
		return ownername;
	}

	public void setOwnername(String ownername) {
		this.ownername = ownername;
	}

	public Long getTenantid() {
		return tenantid;
	}

	public void setTenantid(Long tenantid) {
		this.tenantid = tenantid;
	}

	public String getTenantname() {
		return tenantname;
	}

	public void setTenantname(String tenantname) {
		this.tenantname = tenantname;
	}
	
	
	
}
