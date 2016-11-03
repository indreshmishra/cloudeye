package com.rwork.cloudeye.jobs.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.WorkerNode;

@Repository
@Transactional
public class WorkerNodeDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<?> getAllWorkerNodes(){
		return entityManager.createQuery("from WorkerNode where alive=true").getResultList();
	}
	
	public void updateWorkerNode(WorkerNode workernode){
		entityManager.merge(workernode);
	}
}
