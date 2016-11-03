package com.rwork.cloudeye.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.rwork.cloudeye.model.Host;

@Repository
@Transactional
public class HostDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void createHost(Host host){
		entityManager.persist(host);
		
	}
	
	public void updateHost(Host host){
		entityManager.merge(host);
	}
	
	public List<?> getAllHost(){
		return entityManager.createQuery("from Host").getResultList();
	}
	public Host getHost(Long hostid){
		return entityManager.find(Host.class, hostid);
	}
	public void deleteHost(Long hostid){
		Host h= this.getHost(hostid);
		entityManager.remove(h);
	}
}
