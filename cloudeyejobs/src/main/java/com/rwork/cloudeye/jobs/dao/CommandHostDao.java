package com.rwork.cloudeye.jobs.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.CommandHost;
import com.rwork.cloudeye.model.CommandStatus;

@Repository
@Transactional
public class CommandHostDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@SuppressWarnings("unchecked")
	public List<CommandHost> getAllCreatedCommandsToBeQueued(){
		Query query= entityManager.createQuery("from CommandHost where commandStatus=:arg1 and disabled=false"); //TODO for worker id filtering
		query.setParameter("arg1", CommandStatus.CREATED);
		
		List<?> chs= query.getResultList();
		
		return (List<CommandHost>) chs;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommandHost> getAllStuckedCommandsToBeReQueued(){
		Query query= entityManager.createQuery("from CommandHost where commandStatus !=:arg1 and assignedWorkerNode.alive=false and disabled=false"); //TODO for worker id filtering
		query.setParameter("arg1", CommandStatus.STOPPED);
		
		List<?> chs= query.getResultList();
		
		return (List<CommandHost>) chs;
	}
	
	@SuppressWarnings("unchecked")
	public List<CommandHost> getAllCommandsToBeReDistributed(){
		Query query= entityManager.createQuery("from CommandHost where commandStatus !=:arg1 and assignedWorkerNode.alive=true and disabled=false"); //TODO for worker id filtering
		query.setParameter("arg1", CommandStatus.STOPPED);
		
		List<?> chs= query.getResultList();
		
		return (List<CommandHost>) chs;
	}
	
	public void updateCommandHost(CommandHost ch){
		entityManager.merge(ch);
	}
}
