package com.wp.common.security.token;

import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthenticationwithToken extends PreAuthenticatedAuthenticationToken {

	public AuthenticationwithToken(Object aPrincipal, Object aCredentials) {
		super(aPrincipal, aCredentials);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7857937393624853524L;
	String customerId;
	String id;
	String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
