package com.rwork.cloudeye.jobs;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class JobsRunApplication implements CommandLineRunner{
	@Autowired
	private Environment env;
	
	@Autowired
	private HeartBeatMonitorJob heartbeatmonitorjob;
	
	@Autowired
	private WorkAllocatorJob workallocator;
	
	public static void main(String[] args){
		SpringApplication.run(JobsRunApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running Jobs of Cloudeye");
		ScheduledThreadPoolExecutor jobmonitor= (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
		jobmonitor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try{
				heartbeatmonitorjob.updateWorkerNodeStatus();
				}
				catch(Exception e){
					System.out.println("ERROR: Exception while updating worker node status "+e.getMessage());
				}
				
			}
		}, 0, Integer.parseInt(env.getProperty("hearbeat.monitor.job")), TimeUnit.SECONDS);
		
		
		jobmonitor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try{
					workallocator.allocateWorkToNodes();
				}
				catch(Exception e){
					System.out.println("ERROR: exception while allocating work to worker nodes");
				}
				
			}
		}, 0, Integer.parseInt(env.getProperty("work.allocatorjob.interval")), TimeUnit.SECONDS);
		
			jobmonitor.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try{
					workallocator.reallocateWorkOfNonAliveNodes();
				}
				catch(Exception e){
					System.out.println("ERROR: exception while allocating work to worker nodes for stucked command");
				}
				
			}
		}, 0, Integer.parseInt(env.getProperty("work.reallocateWorkOfNonAliveNodes.interval")), TimeUnit.SECONDS);
			
			jobmonitor.scheduleAtFixedRate(new Runnable() {
				
				@Override
				public void run() {
					try{
						workallocator.redistributeWorkAmongAliveNodes();
					}
					catch(Exception e){
						System.out.println("ERROR: exception while allocating work to worker nodes for running jobs");
					}
					
				}
			}, 0, Integer.parseInt(env.getProperty("work.redistributeWorkAmongAliveNodes.interval")), TimeUnit.SECONDS);
	}

}
