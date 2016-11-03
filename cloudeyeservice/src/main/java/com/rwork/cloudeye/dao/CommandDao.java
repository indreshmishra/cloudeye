package com.rwork.cloudeye.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.Command;

@Repository
@Transactional
public class CommandDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void createCommand(Command command)
	{
		entityManager.persist(command);
	}
	
	public List<?> getAll()
	{
		return entityManager.createQuery("from Command").getResultList();
	}
	
	public Command findByID(long id)
	{
		return entityManager.find(Command.class, id);
	}

}
