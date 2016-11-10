package com.rwork.cloudeye;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jersey.JerseyProperties.Filter;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rwork.cloudeye.dao.UserDao;
import com.rwork.cloudeye.filter.TestFilter;
import com.rwork.cloudeye.interceptors.CountTimeInterceptor;
import com.rwork.cloudeye.model.Role;
import com.rwork.cloudeye.model.User;

//@ComponentScan("com.rwork.cloudeye.model")
@SpringBootApplication
public class RunCloudEye {
	
	
	
	
	public static void main(String[] args)
	{
		System.out.println("Cloud Eye is Running");
		ApplicationContext context= SpringApplication.run(RunCloudEye.class, args);
	}

}

@Configuration
@EnableWebMvc
class WebConfig extends WebMvcConfigurerAdapter{
	
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("*").allowedHeaders("*");
	}
	
	@Autowired
	CountTimeInterceptor countTimeInterceptor;
	
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(countTimeInterceptor);
	}
	
	
	@Bean
	public FilterRegistrationBean myTestFilter(){
		FilterRegistrationBean bean=new FilterRegistrationBean(new TestFilter());
		bean.addUrlPatterns("/command/");
		return bean;
	}
}

@Configuration
class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter{
	
	
}

//class ResourceServerConfiguration extends Resource

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;
	@Autowired
	private UserDao userDao;
	
	
	
	@Bean
	public UserDetailsService userDetailsService(){
		
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
				User user= userDao.getUserByName(username);
				
				if(user!=null){
					List<Role> rolelist=user.getRoles();
					if(rolelist==null)rolelist=new ArrayList<>();
					String[] roles=new String[rolelist.size()];
					for(int i=0; i< rolelist.size(); i++){
						roles[i]=rolelist.get(i).getName();
					}
					return new org.springframework.security.core.userdetails.User(username, user.getPassword(), AuthorityUtils.createAuthorityList(roles));
				}
				else{
					throw new UsernameNotFoundException("user is not found with name "+username);
				}
				
			}
		};
	}
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		/**
		 * below will only add the below mentioned users only to have access to application resources
		 */
		//auth.inMemoryAuthentication().withUser("cloudeyeadmin").password("password").roles("ADMIN","USER");
		//auth.inMemoryAuthentication().withUser("cloudeyeadmin").password("password").roles("ADMIN");
		auth.userDetailsService(userDetailsService());
	}
	
	public void configure(WebSecurity web)throws Exception{
		web.ignoring().antMatchers("/about");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/auth/check");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/command/**");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/commandhost/**");
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "**");
		
	}
	
	protected void configure(HttpSecurity http)throws Exception {
		//http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable();
//		http.authorizeRequests()
//		.antMatchers("/about").permitAll()
//		.antMatchers("/command").hasRole("ADMIN").anyRequest().authenticated();
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/auth/check").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/command/**").hasAuthority("USER")
		.antMatchers("/command/**/host/**").hasAuthority("USER")
		.antMatchers("/host/**").hasAnyAuthority("USER")
		.antMatchers("/commandhost/**").hasAnyAuthority("USER")
		.antMatchers("/tenant/**").hasAuthority("ADMIN")
		.antMatchers("/user/**").hasAuthority("ADMIN")
		.antMatchers("/role/**").hasAuthority("ADMIN")
		.antMatchers("/workernode/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.OPTIONS).permitAll()
		.anyRequest().permitAll()
		.and()
		.httpBasic().realmName("OAuth Server").and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}