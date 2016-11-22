package com.rwork.cloudeye.notifier.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rwork.cloudeye.model.UserNotification;

@Component
public class MessageSender {
	
	@Autowired
	private Environment env;
	
	
	public boolean sendMessage(String type, UserNotification notification){
		
		Channel channel = null;
		Connection connection= null;
		try {
			ConnectionFactory factory=new ConnectionFactory();
			factory.setHost(env.getProperty("message.rabbitmq.host"));
			factory.setPort(Integer.parseInt(env.getProperty("message.rabbitmq.port")));
			 connection= factory.newConnection();
			 channel = connection.createChannel();
		} catch (IOException | TimeoutException e) {
			
			e.printStackTrace();
			System.out.println("Failed to get connection to rabbitmq");
			return false;
		}
		
		try {
			channel.queueDeclare(type, false, false, false, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("failed to create the desired queue");
			return false;
		}
		
		try {
			channel.basicPublish("", type, null, notification.getMessage().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Failed to send the message");
			return false;
		}
		System.out.println("[x] Sent ' "+ notification.getMessage()+"'");
		try {
			if(connection.isOpen()) connection.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("failed to close connection");
		}
		try {
			if(channel.isOpen()) channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public com.rabbitmq.client.Channel getChannel() throws IOException, TimeoutException{
		ConnectionFactory factory=new ConnectionFactory();
		factory.setHost(env.getProperty("message.rabbitmq.host"));
		factory.setPort(Integer.parseInt(env.getProperty("message.rabbitmq.port")));
		com.rabbitmq.client.Connection connection= factory.newConnection();
		com.rabbitmq.client.Channel channel = connection.createChannel();
		return channel;
	}
	

}
