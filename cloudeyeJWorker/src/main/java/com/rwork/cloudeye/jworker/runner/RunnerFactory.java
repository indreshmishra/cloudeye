package com.rwork.cloudeye.jworker.runner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.model.CommandType;

@Component
public class RunnerFactory {

	@Autowired
	private SSHRunner sshrunner;
	
	@Autowired
	private PingRunner pingrunner;
	
	
	public ICommandRunner getCommandRunner(CommandType type){
		if(CommandType.SSH.equals(type)) return sshrunner;
		
		else if(CommandType.PING.equals(type)) return pingrunner;
		
		else return sshrunner; //need a default runner 
	}
}
