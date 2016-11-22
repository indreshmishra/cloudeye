package com.rwork.cloudeye.notifier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class RunCloudNotifier {

	public static void main(String[] args){
		System.out.println("Running Cloud Notifier");
		ApplicationContext context= SpringApplication.run(RunCloudNotifier.class, args);
	}
	
}


@Configuration
@EnableWebMvc
class webconfig extends WebMvcConfigurerAdapter{
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
	}
	
}