package com.prologis.tableau.application.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prologis.tableau.application.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

	
}
