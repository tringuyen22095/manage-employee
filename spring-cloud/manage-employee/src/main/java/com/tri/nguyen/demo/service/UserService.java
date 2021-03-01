package com.tri.nguyen.demo.service;

import java.util.Optional;

import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.req.CriteriaReq;
import com.tri.nguyen.demo.models.res.SearchResults;

public interface UserService {

	void signIn(User entity, String password);

	void save(User entity);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Integer id);

	SearchResults searchMember(CriteriaReq req);

	void assignMember(Team team, User user);

	void unassignMember(Team team, User user);

}
