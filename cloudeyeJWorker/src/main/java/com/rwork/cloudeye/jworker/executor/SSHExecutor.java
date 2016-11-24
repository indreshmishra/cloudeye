package com.rwork.cloudeye.jworker.executor;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.jworker.dao.CommandHostDao;
import com.rwork.cloudeye.jworker.dao.CommandRunHistoryRepository;
import com.rwork.cloudeye.model.CommandRunHistory;
import com.rwork.cloudeye.jworker.runner.SSHRunner;
import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandStatus;
import com.rwork.cloudeye.model.UserNotification;
import com.rwork.cloudeye.restclient.NotificationRestClient;

@Component
public class SSHExecutor {
	
	@Autowired
	private CommandHostDao commandhostDao;
	
	@Autowired
	private SSHRunner sshrunner;
	
	@Autowired
	private CommandRunHistoryRepository commandrunHistoryRepo;
	
	@Autowired
	private Environment env;
	
	
	//TODO ,its giving error ,
	//@Autowired
	//private NotificationRestClient notificationRestClient;
	
	public void execute(CommandHost ch) {
		String output=null;
		ch.setCommandStatus(CommandStatus.RUNNING);
		int maxlength= Integer.parseInt(env.getProperty("command.output.maxlength"));
		commandhostDao.updateCommandHost(ch);
		Boolean earliersuccess= ch.getSuccess();
		try {
			System.out.println("============running command==========");
			 output = sshrunner.runCommand(ch.getHost().getHostipaddress(), ch.getCommand().getCommandstring(), ch.getHost().getHostuser(), ch.getHost().getHostpassword());
			 int m = (output.length() < maxlength)? output.length(): maxlength;
			 String output2=output.substring(0,m);
			 ch.setOutput(output2);
			 System.out.println("***********running command over************");
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
			int bindex= output.indexOf(ch.getCommand().getContainString());
			int m = (output.length() < maxlength)? output.length(): maxlength;
			if(output.length() > maxlength){
				int eindex = (bindex + m > output.length())? output.length(): bindex+m;
				ch.setOutput(output.substring(bindex,eindex));
			}
			else{
				ch.setOutput(output);
			}
			ch.setSuccess(true);
		}
		else{
			ch.setSuccess(false);
		}
		ch.setLastRun(new Date());
		commandhostDao.updateCommandHost(ch);
		
		if(earliersuccess != ch.getSuccess()){
			//change in status of success of command host ,so send notification
			System.out.println("preparing to send notification to user");
			UserNotification notification=new UserNotification();
			notification.setUsername(ch.getHost().getOwner().getUsername());
			notification.setSuccess(ch.getSuccess());
			notification.setHost(ch.getHost().getHostname());
			notification.setChannel("email");
			notification.setEmail(ch.getHost().getOwner().getContact().getEmailId());
			if(earliersuccess !=null){
				if(  earliersuccess==false){
					notification.setTitle("Now Stable");
					notification.setMessage("now your command is resumed back to normal");
				}
				else{
					notification.setTitle("Failure : pay attention");
					notification.setMessage("your command is failing ,pay attention to host");
				}
			}
			
			NotificationRestClient client=new NotificationRestClient();
			client.sendNotificationToUser(notification);
		}
		CommandRunHistory history= new CommandRunHistory();
		history.setCommandid(ch.getCommand().getId());
		history.setCommandoutput(output);
		history.setCommandstring(ch.getCommand().getCommandstring());
		history.setExpectedoutputcontains(ch.getCommand().getExpectedOutput());
		history.setHostid(ch.getHost().getId());
		history.setHostname(ch.getHost().getHostname());
		//history.setOwnerid(ch.getCommand().getOwner().getId());
		//history.setOwnername(ch.getCommand().getOwner().getName());
		history.setRunDate(new Date());
		history.setSuccess(ch.getSuccess());
		//history.setTenantid(ch.getHost());
		commandrunHistoryRepo.save(history);
		if(ch.getRunAgain() == true){
		ch.setCommandStatus(CommandStatus.QUEUED);
		}
		else{
			ch.setCommandStatus(CommandStatus.STOPPED);
		}
		if(ch.getNextAssignedWorkerNode() != null){
			ch.setAssignedWorkerNode(ch.getNextAssignedWorkerNode());
			ch.setNextAssignedWorkerNode(null);
		}
		commandhostDao.updateCommandHost(ch);
		
	}

}
