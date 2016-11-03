package com.rwork.cloudeye.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.model.Info;

@CrossOrigin
@RestController
public class InfoController {

	
	@RequestMapping("/about")
	public Info about(){
		Info info=new Info();
		info.setCompany("RWORK");
		info.setMessage("This is Cloud Eye Application. @copyright 2016");
		return info;
	}
	
	@RequestMapping("/loggedinuser")
	public Principal getuser(Principal user){
		return user;
	}
}
