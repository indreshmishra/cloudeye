package com.rwork.cloudeye.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.CommandHost;

@Repository
@Transactional
public class CommandHostDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void createCommandHost(CommandHost commandhost){
		entityManager.persist(commandhost);
	}
	
	public void updateCommandHost(CommandHost ch){
		entityManager.merge(ch);
	}
	
	public void deleteCommandHost(CommandHost commandHost){
		entityManager.remove(commandHost);
	}
	
	public List<?> getAll(){
		return entityManager.createQuery("from CommandHost").getResultList();
	}
	
	public CommandHost findById(long id){
		return entityManager.find(CommandHost.class, id);
	}

}
