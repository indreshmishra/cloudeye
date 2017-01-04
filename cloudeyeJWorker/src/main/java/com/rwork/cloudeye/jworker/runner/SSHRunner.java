package com.rwork.cloudeye.jworker.runner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandOutput;

@Component
public class SSHRunner implements ICommandRunner {
	public CommandOutput runCommand(CommandHost ch) throws IOException{
		
		
		CommandOutput cout= runCommand(ch.getHost().getHostipaddress(), ch.getCommand().getCommandstring(), ch.getHost().getHostuser(), ch.getHost().getHostpassword());
		
		if(ch.getCommand().getContainString()!= null){
			if(cout.getOutput().contains(ch.getCommand().getContainString())){
				cout.setSuccess(true);
			}
		}
		else{
			cout.setSuccess(true); // as there is no expected output
		}
		
		return cout;
	}
	
	public CommandOutput runCommand(String hostip,String command, String user, String password) throws IOException{
		CommandOutput cout=new CommandOutput();
		cout.setSuccess(false);
		
		
		JSch jsch=new JSch();
		jsch.setConfig("StrictHostKeyChecking", "no");
		
		Session session=null;
		try {
			session = jsch.getSession(user, hostip);
			session.setPassword(password);
		} catch (JSchException e) {
			e.printStackTrace();
			 cout.setOutput("FAILED_TO_GET_SESSION");
			 cout.setSuccess(false);
			 return cout;
			
		}
		try {
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
			 cout.setOutput( "FAILED_TO_CONNECT");
			 cout.setSuccess(false);
			 return cout;
		}
		Channel channel = null;
		try {
			 channel= session.openChannel("exec");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 cout.setOutput( "FAILED_TO_OPEN_CHANNEL");
			 cout.setSuccess(false);
			 return cout;
		}
		((ChannelExec)channel).setCommand(command);
		InputStream in =null;
		BufferedReader bio =null;
		try {
			 in= channel.getInputStream();
			 bio= new BufferedReader(new InputStreamReader(in));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 cout.setOutput( "FAILED_TO_GET_INPUTSTREAM");
			 cout.setSuccess(false);
			 return cout;
		}
		try {
			channel.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 cout.setOutput( "FAILED_TO_CONNECT");
			 cout.setSuccess(false);
			 return cout;
		}
		StringBuffer sb=new StringBuffer();
		while(bio.read() >0){
			sb.append(bio.readLine());
		}
		channel.disconnect();
		session.disconnect();
		 cout.setOutput( sb.toString());
		 
		 return cout;
	}
}
