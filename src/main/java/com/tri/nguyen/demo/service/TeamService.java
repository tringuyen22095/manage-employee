package com.tri.nguyen.demo.service;

import java.util.Optional;

import com.tri.nguyen.demo.models.entity.Team;

public interface TeamService {

	void save(Team entity);

	void delete(Team entity);

	Optional<Team> findById(Integer id);

}
