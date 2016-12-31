package com.rwork.cloudeye.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.Tenant;
import com.rwork.cloudeye.model.User;

@Repository
@Transactional
public class TenantDao {
	
	@PersistenceContext
	private EntityManager em;

	
	public void createTenant(Tenant t)
	{
		em.persist(t);
	}
	
	public Tenant getTenant(long id)
	{
		return em.find(Tenant.class, id);
	}
	
	public Tenant updateTenant(Tenant t)
	{
		return em.merge(t);
	}
	
	public List<?> getAll(){
		return em.createQuery("from Tenant").getResultList();
	}
	public Tenant getTenantByName(String name){
		Tenant tenant= null;
		try{
			tenant= (Tenant) em.createQuery("from Tenant where name = ?1").setParameter(1, name).getSingleResult();
		}
		catch(Exception e){
			
		}
		
		return tenant;
	}
}
