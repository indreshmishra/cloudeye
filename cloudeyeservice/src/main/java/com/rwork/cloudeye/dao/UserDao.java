package com.rwork.cloudeye.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.User;

@Repository
@Transactional
public class UserDao {
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void createUser(User u)
	{
		entityManager.persist(u);
	}
	
	
	public List<?> getAll()
	{
		return entityManager.createQuery("from User").getResultList();
	}
	
	public User getUser(long id)
	{
		return entityManager.find(User.class, id);
	}
	
	public User updateUser(User u)
	{
		return entityManager.merge(u);
	}
	
	public User getUserByName(String name){
		return (User) entityManager.createQuery("from User where username = ?1").setParameter(1, name).getSingleResult();
	}

}
