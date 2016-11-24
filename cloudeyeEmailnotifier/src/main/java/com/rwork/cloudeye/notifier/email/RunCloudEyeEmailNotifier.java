package com.rwork.cloudeye.notifier.email;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.core.env.Environment;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rwork.cloudeye.model.UserNotification;
import com.rwork.cloudeye.util.BytesUtil;

/***
 * this worker could handle all types of email
 * user , system
 * @author indresh.mishra
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class RunCloudEyeEmailNotifier implements CommandLineRunner{

	
	@Autowired
	private Environment env;
	
	
	private static String USER_ROUTING_KEY= "email";
	
	private static String USER_EXCHANGE= "user";
	
	@Autowired
	private EmailSender emailsender;
	
	
	public static Properties emailProps=new Properties();
	
	public static void main(String[] args){
		System.out.println("Running Cloud Eye Email Notifier");
		
		
		SpringApplication.run(RunCloudEyeEmailNotifier.class, args);
	}
	/***
	 * later on ,we can have three threads running ,one for user type, one for system ,one for others type of notification
	 */
	@Override
	public void run(String... args) throws Exception {
		
		boolean askinput= Boolean.parseBoolean(env.getProperty("email.option.server.askinput"));
		
		if(askinput==true){
			Scanner scanner=new Scanner(System.in);
			System.out.println("Enter smtp server address");
			String server= scanner.next();
			System.out.println("Enter smtp port");
			String port= scanner.next();
			System.out.println("Enter smtp username");
			String username=scanner.next();
			System.out.println("Enter smtp user password");
			String password= System.console().readPassword().toString();
			emailProps.put("mail.smtp.auth", "true");
			emailProps.put("mail.smtp.starttls.enable", "true");
			emailProps.put("mail.smtp.host", server);
			emailProps.put("mail.smtp.port", port);
			emailProps.put("mail.smtp.user", username);
			emailProps.put("mail.smtp.password", password);
		}
		else{
			String provider = env.getProperty("email.option.use.provider");
			emailProps.put("mail.smtp.auth", "true");
			emailProps.put("mail.smtp.starttls.enable", "true");
			emailProps.put("mail.smtp.host", env.getProperty("email."+provider+".smtp.server"));
			emailProps.put("mail.smtp.port", env.getProperty("email."+provider+".smtp.port"));
			emailProps.put("mail.smtp.user", env.getProperty("email."+provider+".username"));
			emailProps.put("mail.smtp.password", env.getProperty("email."+provider+".password"));
			
		}
				
				
						
		ThreadPoolExecutor receiverexecutor=(ThreadPoolExecutor)Executors.newCachedThreadPool();
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
			
		}
		channel.exchangeDeclare(USER_EXCHANGE, "direct");
		String queuename=channel.queueDeclare().getQueue();
//		boolean durable=true;
//		channel.queueDeclare(queuename, durable, false, false, null);
		channel.queueBind(queuename, USER_EXCHANGE, USER_ROUTING_KEY);
		
		System.out.println("Waiting for messages");
		channel.basicQos(1);
		Consumer consumer=new DefaultConsumer(channel){
			 @Override
		      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		          throws IOException {
				 
				 receiverexecutor.execute(new Runnable() {
					
					@Override
					public void run() {
						 String message=null;
						try {
							//message = new String(body, "UTF-8");
							UserNotification notification=(UserNotification)BytesUtil.toObject(body);
							message= notification.getMessage();
							System.out.println(" [x] Received '" + message + "'");
							/**
							 * send email here
							 */
							emailsender.sendmail(notification.getEmail(), notification.getTitle(), notification.getMessage());
							
						} catch (UnsupportedEncodingException e) {
							
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					        
						
					}
				});
		       
		      }
		};
		boolean autoAck = true;
		channel.basicConsume(queuename, autoAck, consumer);
	}

}
