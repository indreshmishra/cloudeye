package com.rwork.cloudeye.reporter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class RunCloudEyeReporter {

	public static void main(String[] args){
		System.out.println("Cloud Eye Reporter is running");
		ApplicationContext context=SpringApplication.run(RunCloudEyeReporter.class, args);
	}
}


@Configuration
@EnableWebMvc
class webconfig extends WebMvcConfigurerAdapter{
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
	}
	
}