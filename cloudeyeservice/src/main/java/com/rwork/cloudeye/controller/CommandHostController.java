package com.rwork.cloudeye.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.dao.CommandDao;
import com.rwork.cloudeye.dao.CommandHostDao;
import com.rwork.cloudeye.dao.HostDao;
import com.rwork.cloudeye.model.Command;
import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandStatus;
import com.rwork.cloudeye.model.Host;
import com.rwork.cloudeye.runner.SSHRunner;
import com.rwork.cloudeye.service.WorkerNodeService;

@RestController
@CrossOrigin(origins="*",maxAge=18000,allowedHeaders="*",allowCredentials="false")
public class CommandHostController {
	
	@Autowired
	CommandDao commandDao;
	
	@Autowired
	HostDao hostDao;
	
	@Autowired
	CommandHostDao commandhostdao;
	
	@Autowired
	SSHRunner sshrunner;
	
	@Autowired
	private WorkerNodeService workernodeService;

	@RequestMapping(path="/command/{commandid}/host/{hostid}",method=RequestMethod.POST)
	public ResponseEntity<?> assignCommandToHost(@PathVariable Long commandid,@PathVariable Long hostid){
		CommandHost ch=new CommandHost();
		Command command= commandDao.findByID(commandid);
		Host host=hostDao.getHost(hostid);
		
		ch.setCommand(command);
		ch.setHost(host);
		ch.setCommandStatus(CommandStatus.CREATED);
		commandhostdao.createCommandHost(ch);
		
		workernodeService.assignWorkerNodeToCommandHost(ch);
		
		return new ResponseEntity(HttpStatus.CREATED);
	}
	
	@RequestMapping("/commandhost")
	public List<?> getAllCommandHost(){
		return commandhostdao.getAll();
	}
	@RequestMapping("/commandhost/{id}")
	public CommandHost getCommandHost(@PathVariable long id){
		return commandhostdao.findById(id);
	}
	
	@RequestMapping(path="/commandhost/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCommand(@PathVariable Long id){
		
		commandhostdao.deleteById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
	  
	@RequestMapping(path="/commandhost/{id}/run",method=RequestMethod.PUT)
	public ResponseEntity<?> runCommandOnHost(@PathVariable long id){
		CommandHost ch= commandhostdao.findById(id);
		String output=null;
		try {
			 output = sshrunner.runCommand(ch.getHost().getHostipaddress(), ch.getCommand().getCommandstring(), ch.getHost().getHostuser(), ch.getHost().getHostpassword());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity("exception during execution",HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(output != null && output.contains("FAILED")){
			return new ResponseEntity(output, HttpStatus.BAD_REQUEST);
		}
		if(output!= null && output.contains(ch.getCommand().getContainString())){
			ch.setSuccess(true);
			commandhostdao.updateCommandHost(ch);
		}
		return new ResponseEntity(HttpStatus.ACCEPTED);
	}
}
