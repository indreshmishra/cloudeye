package com.rwork.cloudeye.jworker.job;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.jworker.dao.CommandHostDao;
import com.rwork.cloudeye.jworker.dao.WorkerNodeDao;
import com.rwork.cloudeye.jworker.executor.CommandExecutor;
import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.WorkerNode;

@Component
public class WorkerJob {
	
	@Autowired
	private CommandHostDao commandhostDao;
	
	
	
	@Autowired
	private WorkerNodeDao workernodeDao;
	
	@Autowired
	private CommandExecutor commandexecutor;

	public void pickupCommandHosts(String workerid, ThreadPoolExecutor jobexecutor){
		System.out.println("Picking up All queued command host");
		List<CommandHost> listofcommandhosts = commandhostDao.getAllQueueCommandsToRun(workerid);
		System.out.println("Picked up "+ listofcommandhosts.size()+" commands to work upon");
		WorkerNode workernode= workernodeDao.getWorkerNodeByUUID(workerid);
		workernode.setTotalThreadRunning(listofcommandhosts.size());
		workernodeDao.updateWorkerNode(workernode);
		
		for(CommandHost ch: listofcommandhosts){
			jobexecutor.execute(new Runnable() {
				
				@Override
				public void run() {
					commandexecutor.execute(ch);
					
				}
			});
		}
	}
}
