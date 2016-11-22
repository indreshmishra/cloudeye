package com.rwork.cloudeye.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="host")
public class Host {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	private String hostname;
	private String hostipaddress;
	private String hostuser;
	private String hostpassword;
	
	@ManyToOne
	private User owner;
//	private Tenant tenant;
//	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHostname() {
		return hostname;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public String getHostipaddress() {
		return hostipaddress;
	}
	public void setHostipaddress(String hostipaddress) {
		this.hostipaddress = hostipaddress;
	}
	public String getHostuser() {
		return hostuser;
	}
	public void setHostuser(String hostuser) {
		this.hostuser = hostuser;
	}
	public String getHostpassword() {
		return hostpassword;
	}
	public void setHostpassword(String hostpassword) {
		this.hostpassword = hostpassword;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
//	public Tenant getTenant() {
//		return tenant;
//	}
//	public void setTenant(Tenant tenant) {
//		this.tenant = tenant;
//	}
	

}
