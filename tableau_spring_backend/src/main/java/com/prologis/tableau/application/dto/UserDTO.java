package com.prologis.tableau.application.dto;

import java.util.List;

import com.prologis.tableau.application.entity.Preference;

public class UserDTO {

	private String emailId;
	
	private String userName;
	
	private String role;

	private String region;
	
	private List<PreferenceDTO> preference;
	
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

	public List<PreferenceDTO> getPreference() {
		return preference;
	}

	public void setPreference(List<PreferenceDTO> preference) {
		this.preference = preference;
	}

	@Override
	public String toString() {
		return "UserDTO [emailId=" + emailId + ", userName=" + userName + ", role=" + role + ", region=" + region
				+ ", preference=" + preference + "]";
	}

	

}
