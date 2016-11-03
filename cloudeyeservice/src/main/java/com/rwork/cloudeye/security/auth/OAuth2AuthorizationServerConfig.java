package com.rwork.cloudeye.security.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private DataSource dataSource;
	
//	public AuthenticationManager authenticationManager(){
//		AuthenticationManager auth=new client;
//		
//	}
	
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
	
	@Bean
	protected AuthorizationCodeServices authorizationCodeServices() {
		return new JdbcAuthorizationCodeServices(dataSource);
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints){
//		endpoints.tokenStore(tokenstore())
//		    .accessTokenConverter(accessTokenConvertor())
//		    .userDetailsService(userDetailsService);
		endpoints.tokenStore(tokenstore())
		.userDetailsService(userDetailsService)
		.authorizationCodeServices(authorizationCodeServices())
		.authenticationManager(authenticationManager)
		.approvalStoreDisabled();
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
//		clients.inMemory().withClient("curl")
//		.authorities("ADMIN","USER").resourceIds("rwork").scopes("read","write","trust")
//		.authorizedGrantTypes("client_credentials","authorization_code","implicit","refresh_token")
//		.secret("password").accessTokenValiditySeconds(6000);
//		
		clients.jdbc(dataSource).withClient("curl")
		.authorities("ADMIN","USER").resourceIds("rwork").scopes("read","write","trust")
		.authorizedGrantTypes("client_credentials","authorization_code","implicit","refresh_token")
		.secret("password").accessTokenValiditySeconds(6000);
	}
	
	@Bean
	@Primary
	public DefaultTokenServices tokenservices(){
		DefaultTokenServices defaultTokenServices=new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenstore());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}
