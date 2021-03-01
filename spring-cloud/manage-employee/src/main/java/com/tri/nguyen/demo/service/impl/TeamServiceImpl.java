package com.tri.nguyen.demo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.ObjectStatus;
import com.tri.nguyen.demo.repository.TeamRepository;
import com.tri.nguyen.demo.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamRepository teamRepo;

	@Override
	public void save(Team entity) {
		this.teamRepo.saveAndFlush(entity);
	}

	@Override
	public void delete(Team entity) {
		entity.setObjectStatus(ObjectStatus.DELETED);

		this.teamRepo.saveAndFlush(entity);
	}

	@Override
	public Optional<Team> findById(Integer id) {
		return this.teamRepo.findById(id);
	}

}
