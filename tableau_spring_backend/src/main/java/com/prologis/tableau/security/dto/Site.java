package com.prologis.tableau.security.dto;

public class Site {
	
	private String id;
	private String contentUrl;
	
	public Site() {}
	
	public Site(String id, String contentUrl) {
		super();
		this.id = id;
		this.contentUrl = contentUrl;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContentUrl() {
		return contentUrl;
	}
	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}
	
	

}
