package com.prologis.tableau.security.model;


public class JwtUser {

	private String name;
	private String email;
	private String id;
	private String accessToken;
	private String refreshToken;
	private String tableauToken;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getTableauToken() {
		return tableauToken;
	}

	public void setTableauToken(String tableauToken) {
		this.tableauToken = tableauToken;
	}


}
