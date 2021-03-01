package com.tri.nguyen.demo.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tri.nguyen.demo.controller.exception.CustomException;
import com.tri.nguyen.demo.helper.PagingHelper;
import com.tri.nguyen.demo.models.contants.ErrorMessages;
import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.req.CriteriaReq;
import com.tri.nguyen.demo.models.res.DetailMemberRes;
import com.tri.nguyen.demo.models.res.SearchResults;
import com.tri.nguyen.demo.repository.UserRepository;
import com.tri.nguyen.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void signIn(User entity, String password) {
		if (!bCryptPasswordEncoder.matches(password, entity.getPassword()))
			throw new CustomException(ErrorMessages.WRONG_EMAIL_OR_PASSWORD);
	}

	@Override
	public void save(User entity) {
		this.userRepo.saveAndFlush(entity);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return this.userRepo.findByEmail(email);
	}

	@Override
	public Optional<User> findById(Integer id) {
		return this.userRepo.findById(id);
	}

	@Override
	public SearchResults searchMember(CriteriaReq req) {
		List<DetailMemberRes> res = this.userRepo.findAll()
				.stream()
				.map(DetailMemberRes::new)
				.filter(i -> {
					if(!StringUtils.isBlank(req.getKeyword())) {
						String keyword = req.getKeyword().toLowerCase();
						if (i.getFullName().toLowerCase().contains(keyword)
							||
							i.getEmail().toLowerCase().contains(keyword))
							return true;
						return false;
					}
					return true;
				})
				.collect(Collectors.toList());

		return PagingHelper.sort(req, res, new DetailMemberRes());
	}

	@Override
	public void assignMember(Team team, User user) {
		Set<Team> teams = user.getTeams();
		teams.add(team);
		user.setTeams(teams);

		this.save(user);
	}

	@Override
	public void unassignMember(Team team, User user) {
		Set<Team> teams = user.getTeams();
		teams.remove(team);
		user.setTeams(teams);

		this.save(user);
	}

}
