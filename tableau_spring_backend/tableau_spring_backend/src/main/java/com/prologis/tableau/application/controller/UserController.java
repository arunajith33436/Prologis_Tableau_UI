package com.prologis.tableau.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prologis.tableau.application.dto.UserDTO;
import com.prologis.tableau.application.service.UserService;
import com.prologis.tableau.security.model.AuthenticatedUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "User" })
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class UserController {

	@Autowired
	private UserService userService;

	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "Fetch user", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/user")  
	private UserDTO getUser()   
	{  
	AuthenticatedUser user = (AuthenticatedUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
	
	return userService.getUserByEmailId(user.getEmail());  
	}  

}
