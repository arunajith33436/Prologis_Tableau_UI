package com.prologis.tableau.application.service;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prologis.tableau.security.JwtGenerator;
import com.prologis.tableau.security.dto.Credentials;
import com.prologis.tableau.security.dto.CredentialsDTO;
import com.prologis.tableau.security.dto.Site;
import com.prologis.tableau.security.dto.User;
import com.prologis.tableau.security.dto.UsersDTO;
import com.prologis.tableau.security.exception.AuthException;
import com.prologis.tableau.security.model.AuthenticatedUser;
import com.prologis.tableau.security.model.JwtUser;

import io.jsonwebtoken.security.InvalidKeyException;

@Service
public class AuthenticationService {

	@Autowired
	RestTemplate restTemplate;

	@Value("${spring.security.oauth2.client.provider.azure.token-uri}")
	private String authUrl;

	@Value("${auth.grant.type}")
	private String authorizationCode;

	@Value("${spring.security.oauth2.client.registration.azure.client-secret}")
	private String clientSecret;

	@Value("${spring.security.oauth2.client.registration.azure.client-id}")
	private String clientId;

	@Value("${spring.security.oauth2.client.registration.azure.redirect-uri}")
	private String redirectUri;
	
	@Value("${tableau.admin.credentials.name}")
	private String tableauAdminName;
	
	@Value("${tableau.admin.credentials.password}")
	private String tableauAdminPassword;
	
	@Value("${tableau.site.content-uri}")
	private String tableauSiteContentUri;
	
	@Value("${tableau.signin-uri}")
	private String tableauSigninUri;
	
	@Value("${tableau.users-uri}")
	private String tableauUsersUri;

	
	
	@Autowired
	private JwtGenerator tokenGenerator;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

	public JwtUser getToken(String code) throws  IOException, NoSuchAlgorithmException, JSONException, Exception{

//		LOGGER.info(" In fetchUserFromToken() with  code {}" ,encodeDataForLog(code));
		LOGGER.info(" In fetchUserFromToken()");
		HttpHeaders headers = new HttpHeaders();
		String accessTokenUrl = authUrl;
		ObjectMapper mapper = new ObjectMapper();
		MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();

		body.add("code", code);
		body.add("grant_type", authorizationCode);
		body.add("client_secret", clientSecret);
		body.add("client_id", clientId);
		body.add("redirect_uri", redirectUri);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(body,
				headers);
		ResponseEntity<HashMap<String, String>> response = null;
		try {
			response = restTemplate.exchange(accessTokenUrl, HttpMethod.POST, request,
					new ParameterizedTypeReference<HashMap<String, String>>() {
					});
		} catch (HttpStatusCodeException e) {
	//		LOGGER.error("error in user api {} ", encodeDataForLog(e.getResponseBodyAsString()));
			LOGGER.error("error in user api {} ");
			throw e;
		}

		String[] parts = response.getBody().get("access_token").split("\\.");
		HashMap<String, Object> accessTokenJsonMap = (HashMap<String, Object>) mapper.readValue(
				(new String(Base64.getDecoder().decode(parts[1]))), new TypeReference<Map<String, Object>>() {
				});
		JwtUser user = new JwtUser();

		user.setEmail(accessTokenJsonMap.get("unique_name").toString());
		user.setId(accessTokenJsonMap.get("unique_name").toString().split("@")[0]);
		user.setName(accessTokenJsonMap.get("name").toString());
		user.setTableauToken(getTableauToken(accessTokenJsonMap.get("unique_name").toString()));
		user.setAccessToken(tokenGenerator.generate(user));
		user.setRefreshToken(tokenGenerator.generateRefresh(user));
		return user;
	}

	public Map<String, String> fetchToken(AuthenticatedUser userDetail)
			throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {

		JwtUser user = new JwtUser();
		user.setEmail(userDetail.getEmail());
		user.setId(userDetail.getId());
		user.setName(userDetail.getUsername());
		String token = tokenGenerator.generate(user);
		Map<String, String> accessToken = new HashMap<>();
		accessToken.put("accessToken", token);
		return accessToken;
	}
	
	
	public Map<String, String> fetchTableauToken(AuthenticatedUser userDetail) throws Exception{

		Map<String, String> accessToken = new HashMap<>();
		accessToken.put("tableauToken", getTableauToken(userDetail.getId()));
		return accessToken;
	
	}
	
	private String getTableauToken(String userId) {
		
		LOGGER.info("In getTableauToken..");
		ResponseEntity<CredentialsDTO> response;
		try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		CredentialsDTO credentialsDTO = new CredentialsDTO();
	    Credentials credentials = new Credentials();
		Site site = new Site();
		User user = new User();
		credentials.setName(tableauAdminName);
		credentials.setPassword(tableauAdminPassword);
		site.setContentUrl(tableauSiteContentUri);
		credentials.setSite(site);
		credentialsDTO.setCredentials(credentials);
		user.setId(getTableauUserId(getTableauAdminToken(credentialsDTO), userId));
		credentials.setUser(user);
		credentialsDTO.setCredentials(credentials);
	
		HttpEntity<CredentialsDTO> request = new HttpEntity<>(credentialsDTO, headers);
	
		response = restTemplate.postForEntity(tableauSigninUri, request, CredentialsDTO.class);
		}catch(Exception e) {
			LOGGER.info("Error in getting the tableau token...");
			throw new AuthException("Error in getting the tableau token...");
		}
		return response.getBody().getCredentials().getToken();
	}
	
	
private Credentials getTableauAdminToken(CredentialsDTO credentialsDTO) {
	
	LOGGER.info("In getTableauAdminToken..");
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	
	HttpEntity<CredentialsDTO> request = new HttpEntity<>(credentialsDTO, headers);

	ResponseEntity<CredentialsDTO> response = restTemplate.postForEntity(tableauSigninUri, request, CredentialsDTO.class);
	Credentials credentials = response.getBody().getCredentials();
	
	return credentials;
}

private String getTableauUserId(Credentials credentials, String userId)  {
	
	LOGGER.info("In getTableauUserId..");

	HttpHeaders headers = new HttpHeaders();
	headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	headers.add("X-Tableau-Auth", credentials.getToken());
	HttpEntity request = new HttpEntity(headers);
	
	Map<String, String> uriVariables = new HashMap<>();
	System.out.println(credentials.getSite().getId());
	uriVariables.put("siteId", credentials.getSite().getId());
	uriVariables.put("userId", userId);

	ResponseEntity<UsersDTO> response = restTemplate.exchange(tableauUsersUri, HttpMethod.GET, request, UsersDTO.class, uriVariables);
	if(response.getBody().getUsers().getUser().isEmpty()) {
		throw new AuthException("Tableau :: Invalid user id");
	}

	return response.getBody().getUsers().getUser().get(0).getId();
}

public String fetchTableauTrustedTicket() {
	LOGGER.info("In fetch tableau trusted ticket...");
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
	map.add("username", "prologis_tableau_poc");
	map.add("client_ip", "20.51.187.196:8081");

	HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
	
	ResponseEntity<String> response = restTemplate.exchange("https://20.51.187.196/trusted", HttpMethod.POST,request, String.class);
	return response.getBody();
}

}
