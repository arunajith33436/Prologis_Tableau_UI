package com.prologis.tableau.security.dto;

public class Credentials {
	
	public Credentials() {
		
	}
	
	public Credentials(String name, String password, String token, Site site, User user) {
		super();
		this.name = name;
		this.password = password;
		this.token = token;
		this.site = site;
		this.user = user;
	}

	private String name;
	private String password;
	private String token;
	private Site site;
	private User user;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Credentials [name=" + name + ", password=" + password + ", token=" + token + ", site=" + site
				+ ", user=" + user + "]";
	}
	
	
	
}
