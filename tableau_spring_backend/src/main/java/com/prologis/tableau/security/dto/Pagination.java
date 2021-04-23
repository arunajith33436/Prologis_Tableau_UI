package com.prologis.tableau.security.dto;

public class Pagination {

	private String pageNumber;
	private String pageSize;
	private String totalAvailable;
	
	public Pagination() {
		
	}
	
	public Pagination(String pageNumber, String pageSize, String totalAvailable) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalAvailable = totalAvailable;
	}
	
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getTotalAvailable() {
		return totalAvailable;
	}
	public void setTotalAvailable(String totalAvailable) {
		this.totalAvailable = totalAvailable;
	}
	
	
}
