package com.wp.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {

	@Value("${app.provider.jwk.url: http://localhost:8080/auth/realms/demo/protocol/openid-connect/certs}")
	private String keycloakValidationURL;

	public String getKeycloakValidationURL() {
		return keycloakValidationURL;
	}

	public void setKeycloakValidationURL(String keycloakValidationURL) {
		this.keycloakValidationURL = keycloakValidationURL;
	}
}
