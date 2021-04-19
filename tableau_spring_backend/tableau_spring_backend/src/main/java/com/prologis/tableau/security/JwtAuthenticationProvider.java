package com.prologis.tableau.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.prologis.tableau.security.model.AuthenticatedUser;
import com.prologis.tableau.security.model.JwtAuthenticationToken;
import com.prologis.tableau.security.model.JwtUser;



@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {


	@Autowired
	private JwtValidator validator;
	
	@Override
	public boolean supports(Class<?> aClass) {
		return (JwtAuthenticationToken.class.isAssignableFrom(aClass));
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken)
			throws AuthenticationException {
		
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) usernamePasswordAuthenticationToken;
		String token = jwtAuthenticationToken.getToken();
		String type = jwtAuthenticationToken.getType();
		JwtUser jwtUser = validator.validate(token,type);
		
		if (jwtUser == null) {
			throw new RuntimeException("Jwt Token is incorrect");
		}
		
		List<GrantedAuthority> grantedAuthorities = null;//jwtUser.getRole();
		
		return new AuthenticatedUser(jwtUser.getName(),jwtUser.getId(),
				token,jwtUser.getEmail(),
				grantedAuthorities);
	}
}
