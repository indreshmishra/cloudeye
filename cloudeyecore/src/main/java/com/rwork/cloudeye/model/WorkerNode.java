package com.rwork.cloudeye.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WorkerNode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String uuid;
	
	private String workername;
	
	private Boolean alive;
	
	private String hostname;
	
	private String ipaddress;
	
	private String clustername;
	
	private Date startedAt;
	
	private Date aliveAt;
	
	private Date stoppedAt;
	
	private long totalSystemMemoryInBytes;
	
	private long totalMemoryFreeInBytes;
	
	private long totalThreadRunning;

	public long getTotalThreadRunning() {
		return totalThreadRunning;
	}

	public void setTotalThreadRunning(long totalThreadRunning) {
		this.totalThreadRunning = totalThreadRunning;
	}

	public long getTotalSystemMemoryInBytes() {
		return totalSystemMemoryInBytes;
	}

	public void setTotalSystemMemoryInBytes(long totalSystemMemoryInBytes) {
		this.totalSystemMemoryInBytes = totalSystemMemoryInBytes;
	}

	public long getTotalMemoryFreeInBytes() {
		return totalMemoryFreeInBytes;
	}

	public void setTotalMemoryFreeInBytes(long totalMemoryFreeInBytes) {
		this.totalMemoryFreeInBytes = totalMemoryFreeInBytes;
	}

	public Date getStoppedAt() {
		return stoppedAt;
	}

	public void setStoppedAt(Date stoppedAt) {
		this.stoppedAt = stoppedAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWorkername() {
		return workername;
	}

	public void setWorkername(String workername) {
		this.workername = workername;
	}

	public Boolean getAlive() {
		return alive;
	}

	public void setAlive(Boolean alive) {
		this.alive = alive;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	
	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getClustername() {
		return clustername;
	}

	public void setClustername(String clustername) {
		this.clustername = clustername;
	}

	public Date getStartedAt() {
		return startedAt;
	}

	public void setStartedAt(Date startedAt) {
		this.startedAt = startedAt;
	}

	public Date getAliveAt() {
		return aliveAt;
	}

	public void setAliveAt(Date aliveAt) {
		this.aliveAt = aliveAt;
	}
	
	
}
