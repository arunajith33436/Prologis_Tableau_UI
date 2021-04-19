package com.prologis.tableau.security;


import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.prologis.tableau.security.model.JwtUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtGenerator {
	
	@Value("${jwt.token.secret}")
	private String secretAccess;
	
	@Value("${jwt.token.secret.refresh}")
	private String secretRefresh;
	
	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String START = "startTime";

	public String generate(JwtUser user) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		Claims claims = Jwts.claims().setSubject("access_token");
		claims.put(ID, String.valueOf(user.getId()));
		claims.put(NAME, user.getName());
		claims.put(EMAIL, user.getEmail());
		claims.put(START, new Date().getTime());
		return Jwts.builder()
				.setClaims(claims)
				 .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .signWith(getSigningKey(secretAccess))
				.compact();
	}
	
	private Key getSigningKey(String secret) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(secret.getBytes("UTF-8"));
		byte[] key = md.digest();
		return Keys.hmacShaKeyFor(key);
		}
	
    public String generateRefresh(JwtUser user) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {
		
		Claims claims = Jwts.claims().setSubject("refresh_token");
		claims.put(ID, String.valueOf(user.getId()));
		claims.put(NAME, user.getName());
		claims.put(EMAIL, user.getEmail());
		return Jwts.builder()
				.setClaims(claims)
				 .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (480 * 60 * 1000)))
                .signWith(getSigningKey(secretRefresh))
				.compact();
	}
	
}
