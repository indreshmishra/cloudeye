package com.rwork.cloudeye.jobs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rwork.cloudeye.jobs.dao.WorkerNodeDao;
import com.rwork.cloudeye.model.WorkerNode;

@Component
public class WorkAllocatorJob {
	
	@Autowired
	private WorkerNodeDao workernodeDao;
	
	
	public void allocateWorkToNodes(){
	  System.out.println("Distributing load of Workers");
	  List<WorkerNode> alivenodes=(List<WorkerNode>) workernodeDao.getAllWorkerNodes();
	  
	}

}
