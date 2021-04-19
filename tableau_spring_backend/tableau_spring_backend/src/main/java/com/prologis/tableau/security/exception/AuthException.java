package com.prologis.tableau.security.exception;


import org.springframework.security.core.AuthenticationException;

public class AuthException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AuthException(String msg) {
		super(msg);
	}

}
