package com.rwork.cloudeye.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins="*",maxAge=18000,allowedHeaders="*",allowCredentials="false")
@RestController
public class AuthController {

	@RequestMapping(path= "/auth/check",method=RequestMethod.POST)
	public ResponseEntity<?> checkauth(){
		return new ResponseEntity(HttpStatus.OK);
	}
}
