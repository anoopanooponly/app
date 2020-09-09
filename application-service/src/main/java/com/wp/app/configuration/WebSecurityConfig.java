package com.wp.app.configuration;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.wp.app.security.auth.filter.AuthenticationFilter;
import com.wp.commons.configuration.CommonConfig;
import com.wp.commons.security.TokenService;
import com.wp.commons.security.provider.TokenAuthenticationProvider;



@Configuration
@Import(CommonConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	AppConfig appConfig;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/",
								"/**/*.json",
								"/**/*.js",
								"/**/*.html",
								"/**/*.css",
								"/**/*.ico",
								"/**/*.map",
								"/**/*.json",
								"/**/*.png",
								"/**/*.gif",
								"/**/me/**"
								);
		
		
	}
	
	 @Override
	    protected void configure(HttpSecurity http) throws Exception
	    {
		 http.authorizeRequests()
		 .anyRequest()
		 .authenticated()
		 .and()
		 .sessionManagement()
		 .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		 .and()
		 .addFilterBefore(new AuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class)
		 .csrf()
		 .disable();
	    }
	 
	
	 @Bean
	    public TokenService tokenService() {
	        return TokenService.INSTANCE;
	    }

	 
	    @Bean
	    public AuthenticationProvider tokenAuthenticationProvider() {
	    	boolean skipValidation = true;
	    	String jwkURL = appConfig.getKeycloakValidationURL();
	        return new TokenAuthenticationProvider(tokenService(), jwkURL, skipValidation);
	    }
	    
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(tokenAuthenticationProvider());
	    }


	    @Bean
	    public AuthenticationEntryPoint unauthorizedEntryPoint() {
	        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    }
	    

}
