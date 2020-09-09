package com.wp.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APITestController {

	private static final Logger logger = LoggerFactory.getLogger(APITestController.class);
	
	@GetMapping(value = "/me")
	public Authentication Test(Authentication authentication) {
		logger.debug((String) authentication.getPrincipal());
		return authentication;
	}
	
	@GetMapping(value = "/test")
	public String Test() {
		return "heloo";
	}
}
