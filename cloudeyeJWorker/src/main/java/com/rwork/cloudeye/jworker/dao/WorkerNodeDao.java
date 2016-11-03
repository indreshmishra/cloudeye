package com.rwork.cloudeye.jworker.dao;

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
	
	public void createWorkerNode(WorkerNode workernode){
		entityManager.persist(workernode);
	}
	
	public void updateWorkerNode(WorkerNode workernode){
		entityManager.merge(workernode);
	}
	
	public WorkerNode getWorkerNodeByUUID(String uuid){
		return (WorkerNode)entityManager.createQuery("from WorkerNode where uuid = ?1").setParameter(1, uuid).getSingleResult();
	}
}
