package com.rwork.cloudeye.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.dao.RoleDao;
import com.rwork.cloudeye.model.Role;

@CrossOrigin(origins="*",maxAge=18000,allowedHeaders="*",allowCredentials="false")
@RestController
public class RoleController {

	
	@Autowired
	private RoleDao roleDao;
	
	@RequestMapping("/role/{id}")
	public Role getRole(@PathVariable long id){
		return roleDao.getRole(id);
	}
	
	@RequestMapping("/role")
	public List<?> getAllRoles(){
		return roleDao.getAll();
	}
	
	@RequestMapping(path="role",method=RequestMethod.POST)
	public ResponseEntity<?> createRole(@RequestBody Role r){
		roleDao.createRole(r);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
}
