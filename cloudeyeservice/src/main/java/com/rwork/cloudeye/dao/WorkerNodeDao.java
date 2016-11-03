package com.rwork.cloudeye.dao;

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
	
	@SuppressWarnings("unchecked")
	public List<WorkerNode> getAllWorkerNodes(){
		return (List<WorkerNode>) entityManager.createQuery("from WorkerNode where alive=true ").getResultList();
	}
}
