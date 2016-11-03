package com.rwork.cloudeye.jworker.job;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.jworker.dao.CommandHostDao;
import com.rwork.cloudeye.jworker.dao.CommandRunHistoryRepository;
import com.rwork.cloudeye.jworker.model.CommandRunHistory;
import com.rwork.cloudeye.jworker.runner.SSHRunner;
import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandStatus;

@Component
public class WorkerJob {
	
	@Autowired
	private CommandHostDao commandhostDao;
	
	@Autowired
	private SSHRunner sshrunner;
	
	@Autowired
	private CommandRunHistoryRepository commandrunHistoryRepo;

	public void pickupCommandHosts(String workerid, ThreadPoolExecutor jobexecutor){
		System.out.println("Picking up All queued command host");
		List<CommandHost> listofcommandhosts = commandhostDao.getAllQueueCommandsToRun(workerid);
		for(CommandHost ch: listofcommandhosts){
			jobexecutor.execute(new Runnable() {
				
				@Override
				public void run() {
					String output=null;
					ch.setCommandStatus(CommandStatus.RUNNING);
					commandhostDao.updateCommandHost(ch);
					try {
						 output = sshrunner.runCommand(ch.getHost().getHostipaddress(), ch.getCommand().getCommandstring(), ch.getHost().getHostuser(), ch.getHost().getHostpassword());
						 ch.setOutput(output);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						ch.setSuccess(false);
						ch.setOutput("exception");
					}
					if(output != null && output.contains("FAILED")){
						ch.setSuccess(false);
						ch.setOutput(output);
					}
					if(output!= null && output.contains(ch.getCommand().getContainString())){
						ch.setSuccess(true);
						commandhostDao.updateCommandHost(ch);
						
						CommandRunHistory history= new CommandRunHistory();
						history.setCommandid(ch.getCommand().getId());
						history.setCommandoutput(ch.getOutput());
						history.setCommandstring(ch.getCommand().getCommandstring());
						history.setExpectedoutputcontains(ch.getCommand().getExpectedOutput());
						history.setHostid(ch.getHost().getId());
						history.setHostname(ch.getHost().getHostname());
						history.setOwnerid(ch.getCommand().getOwner().getId());
						history.setOwnername(ch.getCommand().getOwner().getName());
						history.setRunDate(new Date());
						history.setSuccess(ch.getSuccess());
						//history.setTenantid(ch.getHost());
						commandrunHistoryRepo.save(history);
					}
					ch.setCommandStatus(CommandStatus.QUEUED);
					commandhostDao.updateCommandHost(ch);
				}
			});
		}
	}
}