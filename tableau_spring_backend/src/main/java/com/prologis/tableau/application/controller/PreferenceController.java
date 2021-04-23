package com.prologis.tableau.application.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prologis.tableau.application.dto.PreferenceDTO;
import com.prologis.tableau.application.service.PreferenceService;
import com.prologis.tableau.security.model.AuthenticatedUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = { "Preference" })
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class PreferenceController {
	
	@Autowired
	private PreferenceService preferenceService;

	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "Fetch prefernce", produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = "/preference/{id}")  
	private PreferenceDTO getPreference(@PathVariable UUID id)   
	{ 
	return preferenceService.getUserByID(id);  
	} 
	
	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "Set prefernce", produces = MediaType.APPLICATION_JSON_VALUE)
	@PostMapping(value = "/preference")  
	private PreferenceDTO setPreference(@RequestBody PreferenceDTO preferenceDTO)   
	{ 
	return preferenceService.setPreference(preferenceDTO);  
	}  
	
	
	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "Update prefernce", produces = MediaType.APPLICATION_JSON_VALUE)
	@PutMapping(value = "/preference/{id}")  
	private PreferenceDTO updatePreference(@RequestBody PreferenceDTO preferenceDTO, @PathVariable UUID id)   
	{ 
	return preferenceService.updatePreference(preferenceDTO, id);  
	} 
	
	
	@ApiImplicitParam(name = "Authorization", value = "authorization header containing the bearer token (value should have the format - Bearer {token}) ", paramType = "header")
	@ApiOperation(value = "Update prefernce", produces = MediaType.APPLICATION_JSON_VALUE)
	@DeleteMapping(value = "/preference/{id}")  
	private Boolean updatePreference(@PathVariable UUID id)   
	{ 
	return preferenceService.deletePreference(id);  
	} 
}
