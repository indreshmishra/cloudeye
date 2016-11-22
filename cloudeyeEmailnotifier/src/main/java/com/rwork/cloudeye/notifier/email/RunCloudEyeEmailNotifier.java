package com.rwork.cloudeye.notifier.email;

import java.io.IOException;
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

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class RunCloudEyeEmailNotifier implements CommandLineRunner{

	
	@Autowired
	private Environment env;
	
	
	private static String USER_QUEUE= "user";
	
	
	public static void main(String[] args){
		System.out.println("Running Cloud Eye Email Notifier");
		SpringApplication.run(RunCloudEyeEmailNotifier.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
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
		
		channel.queueDeclare(USER_QUEUE, false, false, false, null);
		Consumer consumer=new DefaultConsumer(channel){
			 @Override
		      public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
		          throws IOException {
		        String message = new String(body, "UTF-8");
		        System.out.println(" [x] Received '" + message + "'");
		      }
		};
		channel.basicConsume(USER_QUEUE, true, consumer);
	}

}
