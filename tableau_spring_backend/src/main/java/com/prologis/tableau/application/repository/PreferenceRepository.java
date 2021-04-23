package com.prologis.tableau.application.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.prologis.tableau.application.entity.Preference;

public interface PreferenceRepository extends CrudRepository<Preference, UUID> {

}
