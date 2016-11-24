package com.rwork.cloudeye.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	
	private String username;
	private String password;
	private Boolean enabled;
	private Boolean locked;
	private Boolean passwordExpired;
	private Boolean doespasswordeverexpires;
	private Date passwordexpiryDate;
	private Long expiryperiod;
	private Boolean canNotbeDeletedEver;

	@ManyToMany(fetch=FetchType.EAGER,cascade=CascadeType.MERGE)
	private List<Role> roles;
	
	@ManyToOne(cascade=CascadeType.MERGE)
	private Tenant tenant;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private Contact contact;
	
	@OneToOne(fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private UserLicense userLicense;
	
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public UserLicense getUserLicense() {
		return userLicense;
	}
	public void setUserLicense(UserLicense userLicense) {
		this.userLicense = userLicense;
	}
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public Boolean getPasswordExpired() {
		return passwordExpired;
	}
	public void setPasswordExpired(Boolean passwordExpired) {
		this.passwordExpired = passwordExpired;
	}
	public Boolean getDoespasswordeverexpires() {
		return doespasswordeverexpires;
	}
	public void setDoespasswordeverexpires(Boolean doespasswordeverexpires) {
		this.doespasswordeverexpires = doespasswordeverexpires;
	}
	public Date getPasswordexpiryDate() {
		return passwordexpiryDate;
	}
	public void setPasswordexpiryDate(Date passwordexpiryDate) {
		this.passwordexpiryDate = passwordexpiryDate;
	}
	public Long getExpiryperiod() {
		return expiryperiod;
	}
	public void setExpiryperiod(Long expiryperiod) {
		this.expiryperiod = expiryperiod;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	
	
	
	public Boolean getCanNotbeDeletedEver() {
		return canNotbeDeletedEver;
	}
	public void setCanNotbeDeletedEver(Boolean canNotbeDeletedEver) {
		this.canNotbeDeletedEver = canNotbeDeletedEver;
	}
	public static User dummy(){
		User u=new User();
		u.setName("cloud");
		u.setPassword("encrytedpassword");
		u.enabled=true;
		u.setDoespasswordeverexpires(false);
		List<Role> roles=new ArrayList<>();
		roles.add(Role.dummy());
		//u.setRoles(roles);
		return u;
	}

}
