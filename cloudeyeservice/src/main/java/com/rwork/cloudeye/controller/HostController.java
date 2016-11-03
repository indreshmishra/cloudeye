package com.rwork.cloudeye.controller;

import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.dao.HostDao;
import com.rwork.cloudeye.model.Host;

@RestController
public class HostController {

	@Autowired
	HostDao hostDao;
	
	@RequestMapping("/host/{hostid}")
	public Host getHost(@PathVariable long hostid){
		return hostDao.getHost(hostid);
	}
	
	@RequestMapping("/host")
	public List<?> getAllHost(){
		return hostDao.getAllHost();
	}
	
	@RequestMapping(path="/host",method=RequestMethod.POST)
	public ResponseEntity<?> createHost(@RequestBody Host host){
		hostDao.createHost(host);
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	@RequestMapping(path="/host",method=RequestMethod.PUT)
	public ResponseEntity<?> updateHost(@RequestBody Host host){
		hostDao.updateHost(host);
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
	
}
