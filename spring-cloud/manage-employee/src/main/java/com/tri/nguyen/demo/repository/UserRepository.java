package com.tri.nguyen.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tri.nguyen.demo.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

	@Query(value = "select u from User u "
			+ "left join fetch u.profile "
			+ "left join fetch u.departments "
			+ "left join fetch u.teams "
			+ "left join fetch u.roles")
	List<User> findAll();
	
}
