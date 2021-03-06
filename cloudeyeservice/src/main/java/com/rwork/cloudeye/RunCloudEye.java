package com.rwork.cloudeye;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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

import com.rwork.cloudeye.dao.RoleDao;
import com.rwork.cloudeye.dao.TenantDao;
import com.rwork.cloudeye.dao.UserDao;
import com.rwork.cloudeye.filter.TestFilter;
import com.rwork.cloudeye.interceptors.CountTimeInterceptor;
import com.rwork.cloudeye.model.Contact;
import com.rwork.cloudeye.model.Role;
import com.rwork.cloudeye.model.Tenant;
import com.rwork.cloudeye.model.User;

//@ComponentScan("com.rwork.cloudeye.model")
@SpringBootApplication
public class RunCloudEye {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TenantDao tenantDao;
	
	@Autowired
	private RoleDao roleDao;
	
	private static UserDao userdaos;
	private static TenantDao tenantdaos;
	private static RoleDao roledaos;
	
	@PostConstruct
	public void setDaoStatic()
	{
		userdaos=this.userDao;
		tenantdaos=this.tenantDao;
		roledaos=this.roleDao;
	}
	
	public static void main(String[] args)
	{
		System.out.println("Cloud Eye is Running");
		ApplicationContext context= SpringApplication.run(RunCloudEye.class, args);
		List<?> users= userdaos.getAll();
		System.out.println("number of users in system "+users.size());
		if(users.size()==0){
			System.out.println("Bootstrapping Tenant , User, Role");
			/*
			 * bootstrap admin user
			 */
			
			Role adminrole =new Role();
			adminrole.setName("ADMIN");
			roledaos.createRole(adminrole);
			
			Role userrole=new Role();
			userrole.setName("USER");
			roledaos.createRole(userrole);
			
			List<?> roles= roledaos.getAll();
			Role adminrolecreated=null;
			for(Object r: roles){
				Role role=(Role)r;
				if("ADMIN".equals(role.getName())){
					adminrolecreated=role;
					break;
				}
			}
			
			Tenant tenant=new Tenant();
			tenant.setName("Admin");
			tenantdaos.createTenant(tenant);
			Tenant createdtenant= tenantdaos.getTenantByName("Admin");
			
			User adminuser=new User();
			adminuser.setName("admin");
			adminuser.setPassword("admin");
			adminuser.setEnabled(true);
			adminuser.setUsername("admin");
			adminuser.setCanNotbeDeletedEver(true);
			adminuser.setLocked(false);
			
			Contact contact=new Contact();
			contact.setEmailId(System.getenv("bootstrap.admin.emailid"));
			adminuser.setContact(contact);
			adminuser.setTenant(createdtenant);
			List<Role> roles2=new ArrayList<>();
			roles2.add(adminrolecreated);
			adminuser.setRoles(roles2);
			
			userdaos.createUser(adminuser);
		}
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
	
	
//	@Bean
//	public FilterRegistrationBean myTestFilter(){
//		FilterRegistrationBean bean=new FilterRegistrationBean(new TestFilter());
//		//bean.addUrlPatterns("/command/");
//		return bean;
//	}
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
//		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/auth/check");
//		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/command/**");
//		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/command/**/host/**");
//		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/commandhost/**");
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
		.antMatchers("/command/**").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/command/**/host/**").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/host/**").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/commandhost/**").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/commandhost/**/**").hasAnyAuthority("USER","ADMIN")
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