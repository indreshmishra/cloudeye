package com.rwork.cloudeye.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.dao.TenantDao;
import com.rwork.cloudeye.dao.UserDao;
import com.rwork.cloudeye.model.Tenant;
import com.rwork.cloudeye.model.User;

@RestController
public class TenantController {

	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private UserDao userDao;
	
	@RequestMapping("/tenant")
	public List<?> getAll()
	{
		return tenantDao.getAll();
	}
	@RequestMapping("/tenant/{id}")
	public Tenant getTenant(@PathVariable long id)
	{
		return tenantDao.getTenant(id);
	}
	
	@RequestMapping(path="/tenant",method=RequestMethod.POST)
	public ResponseEntity<?> createTenant(@RequestBody Tenant t)
	{
		tenantDao.createTenant(t);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	
	
	
}
