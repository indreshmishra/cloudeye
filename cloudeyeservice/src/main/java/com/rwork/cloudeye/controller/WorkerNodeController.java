package com.rwork.cloudeye.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.dao.WorkerNodeDao;
import com.rwork.cloudeye.model.WorkerNode;

@CrossOrigin(origins="*",maxAge=18000,allowedHeaders="*",allowCredentials="false")
@RestController
public class WorkerNodeController {

	@Autowired
	private WorkerNodeDao workernodeDao;
	
	@RequestMapping("/workernode/")
	public List<WorkerNode> getAllNodes(){
		return workernodeDao.getAllWorkerNodes();
	}
}
