package com.rwork.cloudeye.security.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig  extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private DataSource dataSource;
	
	@Bean
	public JwtAccessTokenConverter accessTokenConvertor(){
		JwtAccessTokenConverter convertor=new JwtAccessTokenConverter();
		convertor.setSigningKey(env.getProperty("security.jwt.key"));
		return convertor;
	}
	
//	@Bean
//	public TokenStore tokenstore(){
//		return new JwtTokenStore(accessTokenConvertor());
//	}
	
	@Bean
	public TokenStore tokenstore(){
		return new JdbcTokenStore(dataSource);
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer config){
		config.tokenStore(tokenstore()).resourceId("rwork").stateless(true);
	}
	
	@Bean
	protected AuthorizationCodeServices authorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenservices(){
		DefaultTokenServices defaultTokenServices=new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenstore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
	
	@Override
	public void configure(HttpSecurity http)throws Exception {
		//http.authorizeRequests().anyRequest().fullyAuthenticated().and().httpBasic().and().csrf().disable();
//		http.authorizeRequests()
//		.antMatchers("/about").permitAll()
//		.antMatchers("/command").hasRole("ADMIN").anyRequest().authenticated();
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/auth/check").hasAnyAuthority("USER","ADMIN")
		.antMatchers("/command/**").hasAuthority("USER")
		.antMatchers("/command/**/host/**").hasAuthority("USER")
		.antMatchers("/tenant/**").hasAuthority("ADMIN")
		.antMatchers("/user/**").hasAuthority("ADMIN")
		.antMatchers("/role/**").hasAuthority("ADMIN")
		.anyRequest().permitAll()
		.and()
		.httpBasic().realmName("OAuth Server").and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
}
