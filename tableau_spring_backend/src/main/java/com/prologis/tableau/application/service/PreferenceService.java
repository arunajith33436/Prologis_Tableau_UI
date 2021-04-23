package com.prologis.tableau.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prologis.tableau.application.dto.PreferenceDTO;
import com.prologis.tableau.application.entity.Preference;
import com.prologis.tableau.application.repository.PreferenceRepository;

@Service
public class PreferenceService {
	@Autowired
	private PreferenceRepository preferenceRepo;
	
	
	public PreferenceDTO getUserByID(UUID id) {
		Optional<Preference> optional = preferenceRepo.findById(id);
		System.out.println(optional.toString());
		PreferenceDTO preferenceDTO = new PreferenceDTO();
		if(optional.isPresent()) {
		 BeanUtils.copyProperties(optional.get(), preferenceDTO);
		}
		return preferenceDTO;
	}
	
	
	public PreferenceDTO setPreference(PreferenceDTO preferenceDTO) {
		Preference preference = new Preference();
		preference.setEmailId(preferenceDTO.getEmailId());
		preference.setTheme(preferenceDTO.getTheme());
		preference.setLanguage(preferenceDTO.getLanguage());
		preferenceRepo.save(preference);
		return preferenceDTO;
	}
	
	
	public PreferenceDTO updatePreference(PreferenceDTO preferenceDTO, UUID id) {
		Preference preference = new Preference();
		preference.setId(id);
		preference.setEmailId(preferenceDTO.getEmailId());
		preference.setTheme(preferenceDTO.getTheme());
		preference.setLanguage(preferenceDTO.getLanguage());
		preferenceRepo.save(preference);
		return preferenceDTO;
	}
	

	public Boolean deletePreference(UUID id) {
		preferenceRepo.deleteById(id);
		return true;
	}
	
}
