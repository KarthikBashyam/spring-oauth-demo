package com.demo.servicea.comfig;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ResourceServiceConfig extends WebSecurityConfigurerAdapter {

	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
			.mvcMatchers("/service-a/**").access("hasAuthority('SCOPE_authority-a')")
			.anyRequest().authenticated()
			.and()
		.oauth2ResourceServer()
			.jwt();


	}
	// @formatter:on
	

}
