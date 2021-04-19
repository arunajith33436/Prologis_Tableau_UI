package com.prologis.tableau.application.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prologis.tableau.application.dto.PreferenceDTO;
import com.prologis.tableau.application.dto.UserDTO;
import com.prologis.tableau.application.entity.Preference;
import com.prologis.tableau.application.entity.User;
import com.prologis.tableau.application.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository; 
	
	public UserDTO getUserByEmailId(String emailId) {
		Optional<User> optional = userRepository.findById(emailId);
		System.out.println(optional.toString());
		
		List<PreferenceDTO> preferenceDTO = new ArrayList<PreferenceDTO>();
		for(Preference pref : optional.get().getPreference()) {
			PreferenceDTO PrefDTO = new PreferenceDTO();
			PrefDTO.setEmailId(pref.getEmailId());
			PrefDTO.setTheme(pref.getTheme());
			PrefDTO.setLanguage(pref.getLanguage());
			
			preferenceDTO.add(PrefDTO);
		}
		UserDTO userDTO = new UserDTO();
		if(optional.isPresent()) {
		 BeanUtils.copyProperties(optional.get(), userDTO);
		 userDTO.setPreference(preferenceDTO);
		}
		System.out.println(userDTO);
		return userDTO;
	}

}
