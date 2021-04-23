package com.prologis.tableau.security.dto;

public class UsersDTO {
	
	private Pagination pagination;
	private Users users;
	
	public UsersDTO() {
		
	}
	
	public UsersDTO(Pagination pagination, Users users) {
		super();
		this.pagination = pagination;
		this.users = users;
	}
	
	public Pagination getPagination() {
		return pagination;
	}
	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}
	public Users getUsers() {
		return users;
	}
	public void setUsers(Users users) {
		this.users = users;
	}
	
	

}
