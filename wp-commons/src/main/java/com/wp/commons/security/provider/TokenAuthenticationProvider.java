package com.wp.commons.security.provider;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import com.auth0.jwt.interfaces.Claim;
import com.wp.common.security.token.AuthenticationwithToken;
import com.wp.commons.security.TokenService;


public class TokenAuthenticationProvider implements AuthenticationProvider {

	private TokenService tokenService;
	private boolean skipValidation;
	private String jwkURL;
	
	private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationProvider.class);

	public TokenAuthenticationProvider(TokenService tokenService, String jwkURL, boolean performTokenExtractionOnly) {
		this.tokenService = tokenService;
		this.jwkURL = jwkURL;
		this.skipValidation = performTokenExtractionOnly;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Optional<String> token = (Optional) authentication.getPrincipal();
		if (!token.isPresent() || token.get().isEmpty()) {
			throw new BadCredentialsException("Invalid token");
		}
		try {
			String newToken = token.get().replaceAll("Bearer", "").trim();
			if(!skipValidation)
				tokenService.ValidateToken(this.jwkURL, newToken);
			Map<String, Claim> claims = tokenService.getTokenClaims(newToken);
			String id = claims.get("sub").asString();
			String name = claims.get("name").asString();
			String email = claims.get("email").asString();
			String customerId = claims.get("tenantId") != null ? claims.get("tenantId").asString(): "-1s" ;
			AuthenticationwithToken authToken = new AuthenticationwithToken(name, null);
			authToken.setAuthenticated(true);
			authToken.setId(id);
			authToken.setEmail(email);
			authToken.setCustomerId(customerId);
			return authToken;
		} catch (Exception e) {
			throw new SessionAuthenticationException(e.getMessage());
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(PreAuthenticatedAuthenticationToken.class);
	}
}