package com.prologis.tableau.security.dto;


public class User {
	
	private String externalAuthUserId;
	private String id;
	private String lastLogin;
	private String name;
	private String siteRole;
	private String locale;
	private String language;
	
	
	public User() {}

	

	public User(String externalAuthUserId, String id, String lastLogin, String name, String siteRole, String locale,
			String language) {
		super();
		this.externalAuthUserId = externalAuthUserId;
		this.id = id;
		this.lastLogin = lastLogin;
		this.name = name;
		this.siteRole = siteRole;
		this.locale = locale;
		this.language = language;
	}



	public String getExternalAuthUserId() {
		return externalAuthUserId;
	}


	public void setExternalAuthUserId(String externalAuthUserId) {
		this.externalAuthUserId = externalAuthUserId;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getLastLogin() {
		return lastLogin;
	}


	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSiteRole() {
		return siteRole;
	}


	public void setSiteRole(String siteRole) {
		this.siteRole = siteRole;
	}


	public String getLocale() {
		return locale;
	}


	public void setLocale(String locale) {
		this.locale = locale;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	};
	
	
	
}
