package com.rwork.cloudeye.jworker.runner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandOutput;

@Component
public class PingRunner implements ICommandRunner{

	@Autowired
	private Environment env;
	
	public CommandOutput runCommand(CommandHost ch){
		
		String ip= ch.getHost().getHostipaddress();
		
		String uri= ch.getHost().getHosturl();
		
		InetAddress address=null;
		
		CommandOutput coutput= new CommandOutput();
		coutput.setSuccess(false);
		
		if(ip!= null && !"".equals(ip)){
			try {
				address= InetAddress.getByName(ip);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(uri != null){
			try {
				address= InetAddress.getByName(uri);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(address != null){
			try {
				boolean flag=address.isReachable(Integer.parseInt(env.getProperty("jworker.ping.timeout")));
				if(flag){
					coutput.setSuccess(true);
					coutput.setOutput("host is reachable");
				}
			} catch (NumberFormatException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return coutput;
	}

}
