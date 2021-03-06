package com.rwork.cloudeye.reporter.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rwork.cloudeye.model.CommandRunHistory;
import com.rwork.cloudeye.reporter.dao.CommandRunHistoryRepository;

@RestController
public class RunHistoryController {
	
	@Autowired
	private CommandRunHistoryRepository historyrepo;

	@RequestMapping("/runhistory")
	public List<CommandRunHistory> getAllHistory(){
		Long now=new Date().getTime();
		Date startDate=new Date(now- now %(24 * 60 * 60 * 1000));
		
		return historyrepo.findByRunDateGreaterThan(startDate);
	}
}
