package com.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(value = 0)
@EnableWebSecurity
/**
 * This example for Authenticating with GitHub OpenID Connect.
 * 
 * @EnableOAuth2Sso = @EnableOAuth2Client + authentication
 * @author Karthik
 *
 */
public class OAuthConfiguration extends WebSecurityConfigurerAdapter {


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.authorizeRequests()
			.mvcMatchers("/login**", "/public/**").permitAll()
			.anyRequest().authenticated()
			.and()
		.oauth2Login()
		//without login page if there are 2 client registrations it will prompt 2 links for login
		.loginPage("/oauth2/authorization/sample-auth")
		.and()
		.oauth2Client();
		// @formatter:on
		http.csrf().disable();

	}	
	


}
