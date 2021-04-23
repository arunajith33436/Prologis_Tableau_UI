package com.prologis.tableau.application.controller;

//import static com.albertsons.meam.application.config.InfosecConfig.encodeDataForLog;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.prologis.tableau.application.service.AuthenticationService;
import com.prologis.tableau.security.model.AuthenticatedUser;
import com.prologis.tableau.security.model.JwtUser;

import io.jsonwebtoken.security.InvalidKeyException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "Authentication" })
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

	@ApiOperation(value = "fetch user data / token", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "token")
	public ResponseEntity<JwtUser> checkGenerated(@RequestParam("code") String code)
			throws Exception {
		LOGGER.info("fetching user data / token");
		JwtUser user = authService.getToken(code);
	//	LOGGER.info("User data fetch successfull for {}", encodeDataForLog(user.getEmail()));
		LOGGER.info("User data fetch successfull for {}");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "fetch access token using refresh token", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "accesstoken")
	public ResponseEntity<Map<String, String>> getupdatedToken() throws Exception {
		LOGGER.info("fetching access token using refresh token");
		AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Map<String, String> token = authService.fetchToken(user);
		LOGGER.info("Token Fetch successfull");
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "fetch new tableau token", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "tableautoken")
	public ResponseEntity<Map<String, String>> getTableauToken() throws Exception {
		LOGGER.info("fetching new tableau token");
		AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Map<String, String> token = authService.fetchTableauToken(user);
		LOGGER.info("Tableau Token Fetch successfull");
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "invalidate session Id", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "cookie")
	public ResponseEntity<String> invalidate(HttpServletRequest request,HttpServletResponse response) throws Exception {
	        Cookie cookie = new Cookie("JSESSIONID", "invalid"); 
	        cookie.setMaxAge(0);
	        cookie.setPath("/");
	        cookie.setSecure(true);
	        response.addCookie(cookie);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "fetch new tableau trusted ticket", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "tableautrustedticket")
	public ResponseEntity<String> getTableauTrustedTicket() throws Exception {
		LOGGER.info("fetching new tableau trusted ticket");
//		AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
//				.getPrincipal();
//		Map<String, String> token = authService.fetchTableauToken(user);
		String ticket = authService.fetchTableauTrustedTicket();
		System.out.println(ticket);
		LOGGER.info("Tableau Trusted Ticket Fetch successfull");
		return new ResponseEntity<>(ticket, HttpStatus.OK);
	}


}
