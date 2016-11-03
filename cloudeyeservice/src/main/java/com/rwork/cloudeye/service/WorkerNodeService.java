package com.rwork.cloudeye.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rwork.cloudeye.dao.CommandHostDao;
import com.rwork.cloudeye.dao.WorkerNodeDao;
import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandStatus;
import com.rwork.cloudeye.model.WorkerNode;

@Service
public class WorkerNodeService {

	@Autowired
	private WorkerNodeDao workernodeDao;
	
	@Autowired
	private CommandHostDao commandhostDao;
	
	/**
	 * this can be done as async
	 * @param commandhost
	 */
	public void assignWorkerNodeToCommandHost(CommandHost commandhost){
		List<WorkerNode> nodelist= workernodeDao.getAllWorkerNodes();
		if(nodelist.size()==0){
			System.out.println("WARNING: no worker nodes available ");
			return;
		}
		Random r = new Random();
		int i = r.nextInt(nodelist.size());
		WorkerNode node= nodelist.get(i);
		commandhost.setAssignedWorkerNode(node);
		commandhost.setCommandStatus(CommandStatus.QUEUED);
		commandhostDao.updateCommandHost(commandhost);
		
	}
}
