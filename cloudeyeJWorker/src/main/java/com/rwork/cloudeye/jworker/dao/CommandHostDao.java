package com.rwork.cloudeye.jworker.dao;

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
	public List<CommandHost> getAllQueueCommandsToRun(String workerid){
		Query query= entityManager.createQuery("from CommandHost where commandStatus=:arg1 and disabled=false and assignedWorkerNode.uuid=:arg2"); //TODO for worker id filtering
		query.setParameter("arg1", CommandStatus.QUEUED);
		query.setParameter("arg2", workerid);
		List<?> chs= query.getResultList();
		
		return (List<CommandHost>) chs;
	}
	
	public void updateCommandHost(CommandHost ch){
		entityManager.merge(ch);
	}
}
