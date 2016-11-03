package com.rwork.cloudeye.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.dao.CommandDao;
import com.rwork.cloudeye.dao.UserDao;
import com.rwork.cloudeye.model.Command;

@CrossOrigin(origins="*",maxAge=18000,allowedHeaders="*",allowCredentials="false")
@RestController
public class CommandController {
	
	@Autowired
	private CommandDao commandDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/command")
	@Secured({"USER"})
	public List<?> getCommands()
	{
		List<?> list= commandDao.getAll();
		//list.add(Command.dummy());
		return list;
	}
	
	
	
	@RequestMapping("/command/{id}")
	public Command  getCommand(@PathVariable String id)
	{
		
		return commandDao.findByID(Long.parseLong(id));
	}
	
	@RequestMapping(path="/command",method=RequestMethod.POST)
	public ResponseEntity<?> createCommand(@RequestBody Command c)
	{
		
		commandDao.createCommand(c);
		
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	

}
