package com.rwork.cloudeye.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.dao.RoleDao;
import com.rwork.cloudeye.dao.TenantDao;
import com.rwork.cloudeye.dao.UserDao;
import com.rwork.cloudeye.model.Role;
import com.rwork.cloudeye.model.Tenant;
import com.rwork.cloudeye.model.User;

/**
 * secured annotation to be used at method level
 * @author indresh.mishra
 *
 */
//@CrossOrigin(origins="*",maxAge=18000,allowedHeaders="*",allowCredentials="false")
@RestController
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	@RequestMapping(path="/user/{id}")
	public User getUser(@PathVariable long id)
	{
		return userDao.getUser(id);
	}
	
	@RequestMapping(path="/user/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable long id)
	{
		User u = userDao.getUser(id);
		if(u.getCanNotbeDeletedEver() != null && u.getCanNotbeDeletedEver()==true){
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		 userDao.deleteById(id);
		 return new ResponseEntity(HttpStatus.OK);
	}
	
	
	@RequestMapping("/userbyname")
	public User getUser(@RequestHeader String username){
		return userDao.getUserByName(username);
	}
	
	@RequestMapping("/user")
	@Secured({"ADMIN"})
	public List<?> getUsers()
	{
		return userDao.getAll();
	}
	
	@RequestMapping(path="/user",method=RequestMethod.POST)
	public ResponseEntity<?> createUser(@RequestBody User user)
	{
		if(user.getName()==null || user.getUsername()==null || user.getRoles().size()< 1 || user.getPassword()==null){
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		User au= userDao.getUserByName(user.getUsername());
		if(au != null){
			return new ResponseEntity(HttpStatus.FORBIDDEN);
		}
		user.setEnabled(true);
		user.setDoespasswordeverexpires(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		userDao.createUser(user);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	@RequestMapping(path="/user",method=RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@RequestBody User user)
	{
		User dbuser= userDao.getUser(user.getId());
		dbuser.setCanNotbeDeletedEver(user.getCanNotbeDeletedEver());
		dbuser.setName(user.getName());
		dbuser.setPassword(user.getPassword());
		dbuser.getContact().setEmailId(user.getContact().getEmailId());
		if(!dbuser.getUsername().equals(user.getUsername())){
			/*
			 * if username is changed
			 */
			User au= userDao.getUserByName(user.getUsername());
			if(au != null){
				return new ResponseEntity(HttpStatus.FORBIDDEN);
			}
			dbuser.setUsername(user.getUsername());
		}
		
		 userDao.updateUser(user);
		 return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(path="/user/{userid}/role/{roleid}", method=RequestMethod.PUT)
	public ResponseEntity<?> assignRoletoUser(@PathVariable long userid,@PathVariable long roleid){
		
		User user= userDao.getUser(userid);
		if(user==null) return new ResponseEntity("user is not found", HttpStatus.BAD_REQUEST);
		Role role= roleDao.getRole(roleid);
		if(role==null) return new ResponseEntity("role is not found", HttpStatus.BAD_REQUEST);
		if(user.getRoles()==null){
			user.setRoles(new ArrayList<Role>());
		}
		user.getRoles().add(role);
		userDao.updateUser(user);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(path="/user/{userid}/tenant/{tenantid}", method=RequestMethod.PUT)
	public ResponseEntity<?> addUserToTenant(@PathVariable long tenantid,@PathVariable long userid)
	{
		Tenant t= tenantDao.getTenant(tenantid);
		User u= userDao.getUser(userid);
		u.setTenant(t);
		userDao.updateUser(u);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	

}
