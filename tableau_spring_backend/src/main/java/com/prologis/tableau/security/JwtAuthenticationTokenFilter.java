package com.prologis.tableau.security;


import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;

import com.prologis.tableau.security.exception.AuthException;
import com.prologis.tableau.security.model.JwtAuthenticationToken;



public class JwtAuthenticationTokenFilter extends AbstractAuthenticationProcessingFilter {
	
	private static final String AUTHORIZATION = "Authorization";
	private static final String ACCESS = "access";
	private static final String REFRESH = "refresh";
	private static final String TOKEN = "accesstoken";
	private static final String BEARER = "Bearer";

	public JwtAuthenticationTokenFilter() {
		super(new NegatedRequestMatcher(new AntPathRequestMatcher("/authorize/**")));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws AuthenticationException, ServletException, IOException {

		String header = httpServletRequest.getHeader(AUTHORIZATION);
        String type = ACCESS;
		if (header == null || !header.startsWith(BEARER)) {
			throw new AuthException("No Bearer token");
		}
        if(httpServletRequest.getRequestURI().endsWith(TOKEN)) {
        	type = REFRESH;
        }
		String authenticationToken = header.substring(7);
		

		JwtAuthenticationToken token = new JwtAuthenticationToken(authenticationToken,type);
		Authentication auth = getAuthenticationManager().authenticate(token);
		
		return auth;
	}

	@Override
	public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

 	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		getFailureHandler().onAuthenticationFailure(request, response, failed);
 	}
	


}
