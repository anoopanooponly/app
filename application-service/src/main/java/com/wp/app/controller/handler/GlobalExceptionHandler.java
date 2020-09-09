package com.wp.app.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler({SessionAuthenticationException.class, AuthenticationException.class})
	public void unAuthorizedException(Exception e) {
		logger.error("Unauthorized:", e);
	}
	
	
}
