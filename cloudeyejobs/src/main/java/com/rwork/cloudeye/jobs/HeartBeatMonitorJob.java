package com.rwork.cloudeye.jobs;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.jobs.dao.WorkerNodeDao;
import com.rwork.cloudeye.model.WorkerNode;

@Component
public class HeartBeatMonitorJob {

	@Autowired
	private WorkerNodeDao workernodeDao;
	
	@Autowired
	private Environment env;
	
	public void updateWorkerNodeStatus(){
		System.out.println("Updating Worker Node Alive Status");
		List<WorkerNode> nodes=(List<WorkerNode>) workernodeDao.getAllWorkerNodes();
		System.out.println("Number of Alive Worker nodes "+ nodes.size());
		Date now=new Date();
		String c = env.getProperty("heartbeat.monitor.threesoldtime");
		long tperiod= Long.parseLong(c);
		for(WorkerNode workernode: nodes){
			long diff = (now.getTime() - workernode.getAliveAt().getTime()) / 1000;
			if(diff > tperiod){
				System.out.println("Worker Node "+ workernode.getUuid() +" is expired ,so marking is non-alive");
				workernode.setAlive(false);
				workernode.setStoppedAt(new Date());
				workernodeDao.updateWorkerNode(workernode);
			}
		}
	}
}
