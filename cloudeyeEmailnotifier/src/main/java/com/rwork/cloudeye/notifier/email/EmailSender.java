package com.rwork.cloudeye.notifier.email;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class EmailSender {
	
	@Autowired
	private Environment env;
	
	public boolean sendmail(String toemail, String title, String body){
		final String username= RunCloudEyeEmailNotifier.emailProps.getProperty("mail.smtp.user");
		final String password= RunCloudEyeEmailNotifier.emailProps.getProperty("mail.smtp.password");
		
		
		
		Session session=Session.getInstance(RunCloudEyeEmailNotifier.emailProps, new Authenticator() {
			
			protected  PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
				
			}
		});
		
		try{
			Message message=new MimeMessage(session);
			message.setFrom(new InternetAddress("no-reply@cloudeye"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toemail));
			message.setSubject(title);
			message.setText(body);
			
			Transport.send(message);
			System.out.println("Done sending message");
		}
		catch(MessagingException e){
			System.out.println("exception while sending the message");
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
