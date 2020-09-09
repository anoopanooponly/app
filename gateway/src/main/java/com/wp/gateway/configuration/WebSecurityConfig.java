package com.wp.gateway.configuration;

import javax.servlet.http.HttpServletResponse;

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

import com.wp.commons.configuration.CommonConfig;
import com.wp.commons.security.TokenService;
import com.wp.commons.security.provider.TokenAuthenticationProvider;
import com.wp.gateway.security.auth.filter.AuthenticationFilter;



@Configuration
@Import(CommonConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
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
								"/auth/**"
//								"/graphql/**"
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
	    	boolean skipValidation = false;
	        return new TokenAuthenticationProvider(tokenService(),"",  skipValidation);
	    }
	    
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(tokenAuthenticationProvider());
	    }


	    @Bean
	    public AuthenticationEntryPoint unauthorizedEntryPoint() {
	        return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	    }
	    
//	 private static Claims verifyToken(String token, PublicKey publicKey) {
//	        Claims claims;
//	        try {
//	            claims = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
//
//	            System.out.println(claims.get("preferred_username"));
//	            System.out.println(claims.get("tenantId"));
//
//	        } catch (Exception e) {
//
//	            claims = null;
//	            e.printStackTrace();
//	        }
//	        return claims;
//	    }
//	
//	
//	public static void main(String[] args) {
//		verifyToken("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJPQmlWcFlKRGVsM2F3VUlCeGsxdTNET0h5OXdNMlU1WXNRUXJ3czdwa3djIn0.eyJleHAiOjE1OTkxMzUzNzYsImlhdCI6MTU5OTEzNTA3NiwiYXV0aF90aW1lIjoxNTk5MTM1MDc1LCJqdGkiOiIzNjVlMTkwMC1hNTc0LTQ5YWUtOTQxMC1mNDRkN2U4N2UxYzMiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZGVtbyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI3MGJiOTIzZi1jYTcwLTQ1ZWQtYjFhNy1jZjI2NjY5ZjlhOTUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJkZW1vY2xpZW50Iiwibm9uY2UiOiI0YzA0ZDRlZC0yOWU4LTRmYTUtODllMy1hNjNmMzNmYmYyZTIiLCJzZXNzaW9uX3N0YXRlIjoiZDJjZDgyYjUtMmU0OC00YTkwLWFmZDEtYTcyY2Y2YzQ3MDAwIiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwidGVuYW50SWQiOiIxIiwibmFtZSI6ImFub29wIG0iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhbm9vcCIsImdpdmVuX25hbWUiOiJhbm9vcCIsImZhbWlseV9uYW1lIjoibSIsImVtYWlsIjoiYW5vb3BAZi5jb20ifQ.BNV_kKcyJ_6v3kCmK2ZuJUjMB1Quz84iAUyMMaw5mQezAAHPWRL2e5c6Osv0qlgSB_3EzlTUOBLnsSFPFv7SU3ZhzAbFSsfoA6XwaIEKZ8ZAztugZU_nVdFc-TtXw9bKMjguG_1TQd1Sgz0JZ8Y4eI34RGcdFRayFek4SBFSZK5mq7ndFaPjaXJ65QYTtAu7RFjayjPs1-6HzW9DxB5Itkzy3-Ju_rQPBBNfDzetdhBLUBo5e0otOMs-pBla0WuP0osLQcjazJ0pVkiEaS1Rk4Y8Y7Toq3ZfyKgS3ozqi8KRjc3c3s5KKScxogeHRcFowgApdRTi_CLfGnlJnAxQXQ", getKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgbwAmSxxVRuCEfTqBjPZ0KKrY+8YR3rIXbOBF16mdjaOwRMALdVspWII3mgtSbKWgMKdgzv1eVoJejN6VqzQuI4e5tJxKmeQ5jZLJ1pgc+9/7wCFak6EF6TsspC07VtGBbmTBTfaIdTcmGdcaS6qyaVk/tw4Nutc0nS0v/wYbk//y2ifNbkMIHYl66T4+oDMYE5PPjA2qFYdZ++ltV0o0BIdSTdi2BOvAafduwMx9mVgJXC+QuJYZiqM11sh2PC/FX5jfV/ORz4Bi5hY3EiYty6TeK0HUA5XUujT+NdA2FJ2F9QXLKcDHb9YcmcQXQMj0PgRx4FXYdeaahXpnSZzswIDAQAB"));
//	} 
//	public static PublicKey getKey(String key){
//	    try{
//	        byte[] byteKey = Base64.decodeBase64(key.getBytes());
//	        X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
//	        KeyFactory kf = KeyFactory.getInstance("RSA");
//
//	        return kf.generatePublic(X509publicKey);
//	    }
//	    catch(Exception e){
//	        e.printStackTrace();
//	    }
//
//	    return null;
//	}


}
