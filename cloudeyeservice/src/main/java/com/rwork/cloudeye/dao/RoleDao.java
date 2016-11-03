package com.rwork.cloudeye.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.Role;

@Transactional
@Repository
public class RoleDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void createRole(Role role){
		entityManager.persist(role);
	}

	
	public List<?> getAll()
	{
		return entityManager.createQuery("from Role").getResultList();
	}
	
	public Role getRole(long id){
		return entityManager.find(Role.class, id);
	}
}
