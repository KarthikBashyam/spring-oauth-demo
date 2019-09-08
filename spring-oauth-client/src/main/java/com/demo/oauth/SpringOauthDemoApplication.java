package com.demo.oauth;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;

import java.net.URI;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.demo.dto.ServiceResponse;
import com.demo.dto.TokenInfo;

@SpringBootApplication(scanBasePackages="com.demo.*")
@RestController
public class SpringOauthDemoApplication {
	
	@Autowired
	@Qualifier("webClient")
	private WebClient webClient;

	@GetMapping("/hello")
	public String hello(Principal principal) {
		OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;		
		return "Hello :" + token;
	}

	@GetMapping("/")
	public TokenInfo welcome(OAuth2AuthenticationToken token) {		
		OidcUser oidcUser = (OidcUser) token.getPrincipal();
		TokenInfo info = new TokenInfo();
		info.setName(oidcUser.getName());
		info.setEmail(oidcUser.getEmail());
		return info;
	}

	@GetMapping("/service-a")
	public ServiceResponse callServiceA(@RegisteredOAuth2AuthorizedClient("client-a") OAuth2AuthorizedClient clientA) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:9001/service-a");
		URI uri = uriBuilder.build().toUri();
		return this.webClient
				.get()
				.uri(uri)
				.attributes(oauth2AuthorizedClient(clientA))
				.retrieve()
				.bodyToMono(ServiceResponse.class)
				.block();
	}
	
	/**
	 * GitHub OAuth 2.0 Client
	 * 
	 * @param githubClient
	 * @return
	 */
	@GetMapping("/repos")
	public String repos(@RegisteredOAuth2AuthorizedClient("github-client") OAuth2AuthorizedClient githubClient) {
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString("https://api.github.com/users/KarthikBashyam/followers");
		URI uri = uriBuilder.build().toUri();
		return this.webClient
				.get()
				.uri(uri)
				.attributes(oauth2AuthorizedClient(githubClient))
				.retrieve()
				.bodyToMono(String.class)
				.block();
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringOauthDemoApplication.class, args);
	}

}
