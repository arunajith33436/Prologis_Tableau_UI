package com.prologis.tableau.application.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table
public class User {

	@Id
	@Column
	@ColumnTransformer(read = "emailId", write = "LOWER(?)")
	private String emailId;
	
	@Column
	private String userName;
	
	@Column
	private String role;

	@Column
	private String region;
	
	@OneToMany
	@JoinColumn(name="emailId")
	private List<Preference> preference;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public List<Preference> getPreference() {
		return preference;
	}

	public void setPreference(List<Preference> preference) {
		this.preference = preference;
	}

	@Override
	public String toString() {
		return "User [emailId=" + emailId + ", userName=" + userName + ", role=" + role + ", region=" + region
				+ ", preference=" + preference + "]";
	}


	
	
	
	
}
