package com.prs.business.user;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends CrudRepository<User, Integer>, 
	PagingAndSortingRepository<User, Integer> {
	Optional<User> findByUsernameAndPassword(String username, String password);
}
