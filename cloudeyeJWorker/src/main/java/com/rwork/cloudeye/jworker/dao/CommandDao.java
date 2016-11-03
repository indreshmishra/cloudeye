package com.rwork.cloudeye.jworker.dao;

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
	
	public Command findById(Long id){
		return entityManager.find(Command.class, id);
	}
	
	
}
