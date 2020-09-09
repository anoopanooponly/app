package com.wp.commons.security;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public enum TokenService {
	
	INSTANCE;
	
	public void ValidateToken(String jwkURL, String token) throws Exception {
		verifyToken(jwkURL, token);
	}
	
	private static void verifyToken(String jwkURL, String token) throws Exception {
		DecodedJWT jwt = JWT.decode(token);
		JwkProvider provider = new UrlJwkProvider(
				new URL("http://localhost:8080/auth/realms/demo/protocol/openid-connect/certs"));
		Jwk jwk = provider.get(jwt.getKeyId());
		Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
		algorithm.verify(jwt);

	}
	
	public Map<String, Claim> getTokenClaims(String token) {
		DecodedJWT jwt = JWT.decode(token);
		return jwt.getClaims();
	}
	
	public static void main(String[] args) {
		try {
			verifyToken("http://localhost:8080/auth/realms/demo/protocol/openid-connect/certs", "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJPQmlWcFlKRGVsM2F3VUlCeGsxdTNET0h5OXdNMlU1WXNRUXJ3czdwa3djIn0.eyJleHAiOjE1OTk0OTc4MjMsImlhdCI6MTU5OTQ5NzUyMywiYXV0aF90aW1lIjoxNTk5NDk3NTIyLCJqdGkiOiIzOTllOGYxNS0yNDY5LTQyYjItOTdjMS1kNWM4ZWM2NjRhYjkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZGVtbyIsImF1ZCI6ImFjY291bnQiLCJzdWIiOiI3MGJiOTIzZi1jYTcwLTQ1ZWQtYjFhNy1jZjI2NjY5ZjlhOTUiLCJ0eXAiOiJCZWFyZXIiLCJhenAiOiJkZW1vY2xpZW50Iiwibm9uY2UiOiJiYTgxZTE2MS0zOWIxLTQzMWItOTc2Yy05MTdmYjVkM2JmOTEiLCJzZXNzaW9uX3N0YXRlIjoiMWVlN2E0MzctYWNkNy00ZDI3LTgyNGEtMTE1NGRiZGVjMjM0IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyIqIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJvZmZsaW5lX2FjY2VzcyIsInVtYV9hdXRob3JpemF0aW9uIl19LCJyZXNvdXJjZV9hY2Nlc3MiOnsiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJvcGVuaWQgcHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwidGVuYW50SWQiOiIxIiwibmFtZSI6ImFub29wIG0iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhbm9vcCIsImdpdmVuX25hbWUiOiJhbm9vcCIsImZhbWlseV9uYW1lIjoibSIsImVtYWlsIjoiYW5vb3BAZi5jb20ifQ.TnCxKSuIMANLPB3JXMtP-4TzEat6VB9zoyXVTANnm_FykGKKorZdvNfZXY8Mk3xXjzdymW61F3IhSDpFguMuslwGIenhaXDB4adQEAYJAyn78hrxN6UU5AWPmAkO7zELKy6GwgwELT3Le1ys5tmha-JvIxuoefZCoTDvrGu9wUBaUnN_Fiae0Urox1UtWAlS8vNU_yrMODO-fgU-xQ7Tr6FJwgF-7dJXM_JrLwKv8Sk7zLkLxpoKpfozKHj5uqcn1cPOSIguYnMWrM8DSBRwI32LVg1nfo8L3bItJcqsOnNIld7n-VEaj4lHnpUofYze2ruNhI6R8XtdmSkPZlxVcw");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
