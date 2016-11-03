package com.rwork.cloudeye.jworker.dao;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class DataBaseConfig {
	
	
	
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource datasource=new DriverManagerDataSource();
		datasource.setDriverClassName(env.getProperty("db.driver"));
		datasource.setUrl(env.getProperty("db.url"));
		datasource.setUsername(env.getProperty("db.username"));
		datasource.setPassword(env.getProperty("db.password"));
		return datasource;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryNew()
	{
		LocalContainerEntityManagerFactoryBean entityManagerFactory= new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setDataSource(dataSource);
		entityManagerFactory.setPackagesToScan(env.getProperty("entitymanager.packagesToScan"));
		
		HibernateJpaVendorAdapter vendoradapter=new HibernateJpaVendorAdapter();
		entityManagerFactory.setJpaVendorAdapter(vendoradapter);
		 Properties additionalProperties = new Properties();
		    additionalProperties.put(
		        "hibernate.dialect", 
		        env.getProperty("hibernate.dialect"));
		    additionalProperties.put(
		        "hibernate.show_sql", 
		        env.getProperty("hibernate.show_sql"));
		    additionalProperties.put(
		        "hibernate.hbm2ddl.auto", 
		        env.getProperty("hibernate.hbm2ddl.auto"));
		   // additionalProperties.put("hibernate.format_sql", "true");
		   // additionalProperties.put("hibernate.use_sql_comments", "true");
		   // additionalProperties.put("log4j.org.hibernate.type", "TRACE");
		    entityManagerFactory.setJpaProperties(additionalProperties);
		    
		    
		return entityManagerFactory;
	}
	
	@Bean
	  public JpaTransactionManager transactionManager() {
	    JpaTransactionManager transactionManager = 
	        new JpaTransactionManager();
	    transactionManager.setEntityManagerFactory(
	    		entityManagerFactoryNew.getObject());
	    return transactionManager;
	  }
	
	@Bean
	  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
	    return new PersistenceExceptionTranslationPostProcessor();
	  }
	

	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private LocalContainerEntityManagerFactoryBean entityManagerFactoryNew;
}
