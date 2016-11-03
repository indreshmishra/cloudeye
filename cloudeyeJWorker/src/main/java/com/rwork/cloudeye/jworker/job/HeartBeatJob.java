package com.rwork.cloudeye.jworker.job;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.UUID;

import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.jworker.dao.WorkerNodeDao;
import com.rwork.cloudeye.model.WorkerNode;

@Component
public class HeartBeatJob {

	@Autowired
	private WorkerNodeDao workernodeDao;
	
	@Autowired
	private Environment env;
	
	/**
	 * 
	 * @return the uuid of worker node
	 */
	public String createWorkerNodeEntry(){
		WorkerNode worker=new WorkerNode();
		worker.setAlive(true);
		worker.setAliveAt(new Date());
		worker.setClustername(env.getProperty("jworker.cluster.name"));
		String hostname="";
		try {
			InetAddress ip=InetAddress.getLocalHost();
			worker.setHostname(ip.getHostName());
			worker.setIpaddress(ip.getHostAddress());
			hostname=ip.getHostName();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("unable to find hostname of worker");
		}
		worker.setStartedAt(new Date());
		worker.setWorkername(hostname+":"+Thread.currentThread().getName());
		worker.setUuid(UUID.randomUUID().toString());
		worker.setTotalSystemMemoryInBytes(Runtime.getRuntime().totalMemory());
		worker.setTotalMemoryFreeInBytes(Runtime.getRuntime().freeMemory());
		workernodeDao.createWorkerNode(worker);
		return worker.getUuid();
	}
	
	public void markWorkerNodeAlive(String uuid){
		WorkerNode node= workernodeDao.getWorkerNodeByUUID(uuid);
		node.setAlive(true);
		node.setAliveAt(new Date());
		node.setTotalMemoryFreeInBytes(Runtime.getRuntime().freeMemory());
		workernodeDao.updateWorkerNode(node);
	}
	
	public void markWorkerNodeGoneAway(String uuid){
		WorkerNode node= workernodeDao.getWorkerNodeByUUID(uuid);
		node.setAlive(false);
		node.setStoppedAt(new Date());
		workernodeDao.updateWorkerNode(node);
	}
	
}
