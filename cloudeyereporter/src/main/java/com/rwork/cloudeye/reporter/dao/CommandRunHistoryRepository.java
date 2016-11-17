package com.rwork.cloudeye.reporter.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rwork.cloudeye.model.CommandRunHistory;

public interface CommandRunHistoryRepository extends MongoRepository<CommandRunHistory, String>{

	public List<CommandRunHistory> findByHostname(String hostname);
	
	public List<CommandRunHistory> findByCommandstring(String commandstring);
	
	public List<CommandRunHistory> findByRunDateGreaterThan(Date fromDate);
}
