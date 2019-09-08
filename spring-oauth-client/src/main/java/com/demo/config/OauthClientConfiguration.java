package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class OauthClientConfiguration {
	
	@Autowired
	@Qualifier("messagingAppClientDetails")
	private OAuth2ProtectedResourceDetails messagingAppClientDetails;

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	@Bean
	public OAuth2RestTemplate messagingAppRestTemplate() {
		return new OAuth2RestTemplate(messagingAppClientDetails, oauth2ClientContext);
	}

	@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.sample-autht")
	@Bean
	public OAuth2ProtectedResourceDetails messagingAppClientDetails() {
		return new AuthorizationCodeResourceDetails();
	}
	
	

}
