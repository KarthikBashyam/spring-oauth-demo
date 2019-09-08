package com.demo.servicea;

import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.dto.ServiceResponse;

@RestController
@RequestMapping("/service-a")
public class ServiceAController {

	@GetMapping
	public ServiceResponse hello(@AuthenticationPrincipal JwtAuthenticationToken jwtAuthentication) {
		ServiceResponse serviceCallResponse = new ServiceResponse();
		serviceCallResponse.setServiceName("SERVICE_A");		
		serviceCallResponse.setJti(jwtAuthentication.getToken().getId());
		serviceCallResponse.setSub(jwtAuthentication.getToken().getSubject());
		serviceCallResponse.setAud(jwtAuthentication.getToken().getAudience());
		serviceCallResponse.setAuthorities(jwtAuthentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority).sorted().collect(Collectors.toList()));
		return serviceCallResponse;
	}

}
