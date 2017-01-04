package com.rwork.cloudeye.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="command")
public class Command {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String name;
	
	private String commandstring;
	private String expectedOutput;
	private String containString;
	
	private Date lastRunDate;
	@ManyToOne
	private User owner;
	//private Tenant tenant;
	
	private CommandType commandType;
	
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
	public String getCommandstring() {
		return commandstring;
	}
	public void setCommandstring(String commandstring) {
		this.commandstring = commandstring;
	}
	public String getExpectedOutput() {
		return expectedOutput;
	}
	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
	public Date getLastRunDate() {
		return lastRunDate;
	}
	public void setLastRunDate(Date lastRunDate) {
		this.lastRunDate = lastRunDate;
	}
	
	
	public String getContainString() {
		return containString;
	}
	public void setContainString(String containString) {
		this.containString = containString;
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
	
	
	
	public static Command dummy()
	{
		Command c=new Command();
		c.setCommandstring("ping google.com");
		c.setName("pinging google always");
		//c.setCreatedAt(new Date());
		//c.setOwner(User.dummy());
		
		return c;
	}
	public CommandType getCommandType() {
		return commandType;
	}
	public void setCommandType(CommandType commandType) {
		this.commandType = commandType;
	}
	
}
