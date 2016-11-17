package com.rwork.cloudeye.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.jobs.dao.CommandHostDao;
import com.rwork.cloudeye.jobs.dao.WorkerNodeDao;
import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandStatus;
import com.rwork.cloudeye.model.WorkerNode;

@Component
public class WorkAllocatorJob {
	
	@Autowired
	private WorkerNodeDao workernodeDao;
	
	@Autowired
	private CommandHostDao commandhostDao;
	
	/**
	 * below is used when commands are created first time ,which nodes they will be assigned
	 */
	public void allocateWorkToNodes(){
	  System.out.println("Distributing load of Workers");
	  List<WorkerNode> alivenodes=(List<WorkerNode>) workernodeDao.getAllWorkerNodes();
	  
	  List<CommandHost> chs = commandhostDao.getAllCreatedCommandsToBeQueued();
	  
	  System.out.println("Number of alive worker nodes are "+ alivenodes.size());
	  System.out.println("Number of commands recently created "+ chs.size());
	  
	  if(alivenodes.size()> 0 && chs.size()>0){
		  int i =0;
		  for(CommandHost ch: chs){
			  int anode= i % alivenodes.size();
			  ch.setAssignedWorkerNode(alivenodes.get(anode));
			  ch.setCommandStatus(CommandStatus.QUEUED);
			  commandhostDao.updateCommandHost(ch);
			  i++;
		  }
	  }
	}
	
	/**
	 * this will take care to redistribute work from non-alive nodes to alive nodes.
	 */
	public void reallocateWorkOfNonAliveNodes(){
		System.out.println("ReDistributing load of Workers for stucked commands");
		  List<WorkerNode> alivenodes=(List<WorkerNode>) workernodeDao.getAllWorkerNodes();
		  
		  List<CommandHost> chs = commandhostDao.getAllStuckedCommandsToBeReQueued();
		  System.out.println("Number of alive worker nodes are "+ alivenodes.size());
		  System.out.println("Number of commands stucked "+ chs.size());
		  
		  if(alivenodes.size()> 0 && chs.size()>0){
			  int i =0;
			  for(CommandHost ch: chs){
				  int anode= i % alivenodes.size();
				  ch.setAssignedWorkerNode(alivenodes.get(anode));
				  ch.setCommandStatus(CommandStatus.QUEUED);
				  commandhostDao.updateCommandHost(ch);
				  i++;
			  }
		  }
	}

}
