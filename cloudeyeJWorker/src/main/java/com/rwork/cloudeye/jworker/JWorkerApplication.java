package com.rwork.cloudeye.jworker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.rwork.cloudeye.jworker.dao.CommandDao;
import com.rwork.cloudeye.jworker.dao.CommandRunHistoryRepository;
import com.rwork.cloudeye.jworker.job.HeartBeatJob;
import com.rwork.cloudeye.jworker.job.WorkerJob;

@SpringBootApplication
public class JWorkerApplication implements CommandLineRunner{
	
	private static String workerUUID;
	
	private static ScheduledThreadPoolExecutor heartbeatnode , schedulerjob;
	
	private static ThreadPoolExecutor jobexecutor;

	@Autowired
	private CommandRunHistoryRepository repository;
	
	@Autowired
	private CommandDao commandDao;
	
	@Autowired
	private HeartBeatJob heartbeatJob;
	
	@Autowired
	private WorkerJob workerjob;
	
	@Autowired
	private Environment env;
	
	public static void main(String[] args) {
		SpringApplication.run(JWorkerApplication.class, args);

		System.out.println("Cloud eye J Worker Running");
	}
	
	@Override
	public void run(String... args) throws Exception{
		ExecutorService registernode= Executors.newSingleThreadExecutor();
		registernode.submit(() -> {
			workerUUID = heartbeatJob.createWorkerNodeEntry();
		});
		registernode.shutdown();
		
		jobexecutor=(ThreadPoolExecutor)Executors.newCachedThreadPool();
		
		
		heartbeatnode= (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
		heartbeatnode.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				if(workerUUID != null)heartbeatJob.markWorkerNodeAlive(workerUUID);
				
			}
		}, 5, Integer.parseInt(env.getProperty("jworker.heartbeat.duration")), TimeUnit.SECONDS);
		
		schedulerjob= (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
		schedulerjob.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				workerjob.pickupCommandHosts(workerUUID, jobexecutor);
				
			}
		}, 5, Integer.parseInt(env.getProperty("jworker.job.scheduler.duration")), TimeUnit.SECONDS);
		
		
		Runtime r= Runtime.getRuntime();
		r.addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				heartbeatJob.markWorkerNodeGoneAway(workerUUID);
				heartbeatnode.shutdown();
				schedulerjob.shutdown();
				jobexecutor.shutdown();
			}
		}));
	}

}
