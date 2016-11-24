package com.rwork.cloudeye.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class HostInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String hostName;
	
	private String osInfo;
	
	private String memInfo;
	
	private String diskInfo;
	
	private Date infoFechedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getOsInfo() {
		return osInfo;
	}

	public void setOsInfo(String osInfo) {
		this.osInfo = osInfo;
	}

	public String getMemInfo() {
		return memInfo;
	}

	public void setMemInfo(String memInfo) {
		this.memInfo = memInfo;
	}

	public String getDiskInfo() {
		return diskInfo;
	}

	public void setDiskInfo(String diskInfo) {
		this.diskInfo = diskInfo;
	}

	public Date getInfoFechedAt() {
		return infoFechedAt;
	}

	public void setInfoFechedAt(Date infoFechedAt) {
		this.infoFechedAt = infoFechedAt;
	}
	
	  
}
