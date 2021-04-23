package com.prologis.tableau.security;



import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.prologis.tableau.security.exception.AuthException;
import com.prologis.tableau.security.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtValidator {

	@Value("${jwt.token.secret}")
	private String secretAccess;
	
	@Value("${jwt.token.secret.refresh}")
	private String secretRefresh;

	public JwtUser validate(String token,String type) {
		String secret = secretAccess;
		if(type.equals("refresh")) {
			secret = secretRefresh;
		}
		JwtUser jwtUser = null;
		Claims body;
		try {
			 body = Jwts.parser().setSigningKey(getSigningKey(secret)).parseClaimsJws(token).getBody();			
		} catch (Exception e) {
			throw new AuthException("Invalid Token");
		}
	   																	
		jwtUser = new JwtUser();
		jwtUser.setName((String)body.get("userId"));
		jwtUser.setEmail((String)body.get("email"));
		jwtUser.setId((String) body.get("id"));
		return jwtUser;
	}
	

	private Key getSigningKey(String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(secret.getBytes("UTF-8"));
		byte[] key = md.digest();
		return Keys.hmacShaKeyFor(key);
		}
}
