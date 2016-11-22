package com.rwork.cloudeye.notifier.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.model.UserNotification;
import com.rwork.cloudeye.notifier.service.MessageSender;



@RestController
public class NotifyUserController {

	@Autowired
	private MessageSender messageSender;
	
	@RequestMapping(path="/notify/{type}",method=RequestMethod.POST)
	public ResponseEntity<?> notify(@RequestBody UserNotification notification, @PathVariable String type){
		if(notification.getUser()==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		System.out.println("Sending notification to user ="+ notification.getUser().getUsername());
		System.out.println("notification type is "+ type);
		messageSender.sendMessage(type, notification);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
}
