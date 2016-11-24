package com.rwork.cloudeye.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserLicense {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String license;
	
	private Boolean disabled;
	
	private Boolean canHaveOwnWorkerNode;
	
	private Integer maxOwnWorkerNode;
	
	private Integer maxHostAllowed;
	
	private Integer maxCommandAllowed;
	
	private Integer maxUserAllowed;
	
	private Integer maxCommandHostAllowed;
	
	private Integer maxCommandPerCommandHostAllowed;
	
	private Date activationDate;
	
	private Date expiryDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Boolean getCanHaveOwnWorkerNode() {
		return canHaveOwnWorkerNode;
	}

	public void setCanHaveOwnWorkerNode(Boolean canHaveOwnWorkerNode) {
		this.canHaveOwnWorkerNode = canHaveOwnWorkerNode;
	}

	public Integer getMaxOwnWorkerNode() {
		return maxOwnWorkerNode;
	}

	public void setMaxOwnWorkerNode(Integer maxOwnWorkerNode) {
		this.maxOwnWorkerNode = maxOwnWorkerNode;
	}

	public Integer getMaxHostAllowed() {
		return maxHostAllowed;
	}

	public void setMaxHostAllowed(Integer maxHostAllowed) {
		this.maxHostAllowed = maxHostAllowed;
	}

	public Integer getMaxCommandAllowed() {
		return maxCommandAllowed;
	}

	public void setMaxCommandAllowed(Integer maxCommandAllowed) {
		this.maxCommandAllowed = maxCommandAllowed;
	}

	public Integer getMaxUserAllowed() {
		return maxUserAllowed;
	}

	public void setMaxUserAllowed(Integer maxUserAllowed) {
		this.maxUserAllowed = maxUserAllowed;
	}

	public Integer getMaxCommandHostAllowed() {
		return maxCommandHostAllowed;
	}

	public void setMaxCommandHostAllowed(Integer maxCommandHostAllowed) {
		this.maxCommandHostAllowed = maxCommandHostAllowed;
	}

	public Integer getMaxCommandPerCommandHostAllowed() {
		return maxCommandPerCommandHostAllowed;
	}

	public void setMaxCommandPerCommandHostAllowed(Integer maxCommandPerCommandHostAllowed) {
		this.maxCommandPerCommandHostAllowed = maxCommandPerCommandHostAllowed;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
}
