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

@Component
public class SSHRunner {
	public String runCommand(String hostip,String command, String user, String password) throws IOException{
		JSch jsch=new JSch();
		jsch.setConfig("StrictHostKeyChecking", "no");
		
		Session session;
		try {
			session = jsch.getSession(user, hostip);
			session.setPassword(password);
		} catch (JSchException e) {
			e.printStackTrace();
			return "FAILED_TO_GET_SESSION";
			
		}
		try {
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
			return "FAILED_TO_CONNECT";
		}
		Channel channel = null;
		try {
			 channel= session.openChannel("exec");
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED_TO_OPEN_CHANNEL";
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
			return "FAILED_TO_GET_INPUTSTREAM";
		}
		try {
			channel.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILED_TO_CONNECT";
		}
		StringBuffer sb=new StringBuffer();
		while(bio.read() >0){
			sb.append(bio.readLine());
		}
		channel.disconnect();
		session.disconnect();
		return sb.toString();
	}
}
