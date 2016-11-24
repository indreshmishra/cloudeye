package com.rwork.cloudeye.restclient;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rwork.cloudeye.model.UserNotification;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Component
public class NotificationRestClient {
	
	public void sendNotificationToUser(UserNotification notification){
		Client client=new Client();
		WebResource resource= client.resource("http://localhost:9092/notify/user");
		Gson gson=new Gson();
		String json=gson.toJson(notification);
		ClientResponse response= resource.type("application/json").post(ClientResponse.class, json);
		if(response.getStatus()!=202){
			System.out.println("failed to send notification");
			return;
		}
		System.out.println("notification is send");
		
	}

}
