package com.rwork.cloudeye.model;

import java.util.Date;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class CommandHost extends BaseObject{
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	private Command command;
	
	@ManyToOne
	private Host host;
	
	
	
	
	private Boolean success;
	
	private String output;
	
	private Date lastRun;
	
	private Date nextRun;
	
	private Date firstRunAfterThisOnly;
	
	private Long  fixedDelay; //in seconds
	
	private Boolean runOnce;
	
	private Boolean runAgain;
	
	private Boolean disabled;
	
	private Boolean deleted;
	
	private Date deletedAt;
	
	
	
	
	private CommandStatus commandStatus;
	
	@OneToOne
	private WorkerNode assignedWorkerNode;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Command getCommand() {
		return command;
	}
	public void setCommand(Command command) {
		this.command = command;
	}
	public Host getHost() {
		return host;
	}
	public void setHost(Host host) {
		this.host = host;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
	public Date getLastRun() {
		return lastRun;
	}
	public void setLastRun(Date lastRun) {
		this.lastRun = lastRun;
	}
	public Date getNextRun() {
		return nextRun;
	}
	public void setNextRun(Date nextRun) {
		this.nextRun = nextRun;
	}
	public Date getFirstRunAfterThisOnly() {
		return firstRunAfterThisOnly;
	}
	public void setFirstRunAfterThisOnly(Date firstRunAfterThisOnly) {
		this.firstRunAfterThisOnly = firstRunAfterThisOnly;
	}
	public Long getFixedDelay() {
		return fixedDelay;
	}
	public void setFixedDelay(Long fixedDelay) {
		this.fixedDelay = fixedDelay;
	}
	public Boolean getRunOnce() {
		return runOnce;
	}
	public void setRunOnce(Boolean runOnce) {
		this.runOnce = runOnce;
	}
	public Boolean getRunAgain() {
		return runAgain;
	}
	public void setRunAgain(Boolean runAgain) {
		this.runAgain = runAgain;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Boolean getDeleted() {
		return deleted;
	}
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public CommandStatus getCommandStatus() {
		return commandStatus;
	}
	public void setCommandStatus(CommandStatus commandStatus) {
		this.commandStatus = commandStatus;
	}
	public WorkerNode getAssignedWorkerNode() {
		return assignedWorkerNode;
	}
	public void setAssignedWorkerNode(WorkerNode assignedWorkerNode) {
		this.assignedWorkerNode = assignedWorkerNode;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	
	
	
	

}


