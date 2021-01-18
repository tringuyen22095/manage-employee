package com.tri.nguyen.demo.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tri.nguyen.demo.controller.exception.CustomException;
import com.tri.nguyen.demo.models.contants.ErrorMessages;
import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.req.CreateTeamReq;
import com.tri.nguyen.demo.models.req.CriteriaReq;
import com.tri.nguyen.demo.models.req.TeamAssignMemberReq;
import com.tri.nguyen.demo.models.req.UpdateTeamReq;
import com.tri.nguyen.demo.models.res.SearchResults;
import com.tri.nguyen.demo.service.TeamService;
import com.tri.nguyen.demo.service.UserService;

@Validated
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/team")
public class TeamController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

	@Autowired
	private TeamService teamSer;

	@Autowired
	private UserService userSer;

	@Secured({ "ROLE_CEO" })
	@GetMapping("/members")
	public ResponseEntity<SearchResults> searchMembers(CriteriaReq req) {
		return ResponseEntity.ok(this.userSer.searchMember(req));
	}

	@Secured({ "ROLE_CEO", "ROLE_MANAGER" })
	@PostMapping
	public ResponseEntity<Void> createTeam(@Valid @RequestBody CreateTeamReq req) {
		this.teamSer.save(req.toModel());

		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_CEO", "ROLE_MANAGER" })
	@PutMapping
	public ResponseEntity<Void> updateTeam(@Valid @RequestBody UpdateTeamReq req) {
		Optional<Team> teamOpt = this.teamSer.findById(req.getId());
		if (!teamOpt.isPresent()) {
			LOGGER.error(ErrorMessages.TEAM_NOT_FOUND);
			throw new CustomException(ErrorMessages.TEAM_NOT_FOUND);
		}

		Team updatedEntity = req.toModel();
		this.teamSer.save(updatedEntity);

		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_CEO", "ROLE_MANAGER" })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTeam(@PathVariable("id") Integer id) {
		Optional<Team> teamOpt = this.teamSer.findById(id);
		if (!teamOpt.isPresent()) {
			LOGGER.error(ErrorMessages.TEAM_NOT_FOUND);
			throw new CustomException(ErrorMessages.TEAM_NOT_FOUND);
		}

		Team deletedEntity = teamOpt.get();
		this.teamSer.delete(deletedEntity);

		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_CEO", "ROLE_MANAGER" })
	@PutMapping("/assign")
	public ResponseEntity<Void> assignMember(@Valid @RequestBody TeamAssignMemberReq req) {
		Optional<User> userOpt = this.userSer.findById(req.getUserId());
		if (!userOpt.isPresent()) {
			LOGGER.error(ErrorMessages.USER_NOT_FOUND);
			throw new CustomException(ErrorMessages.USER_NOT_FOUND);
		}

		Optional<Team> teamOpt = this.teamSer.findById(req.getTeamId());
		if (!teamOpt.isPresent()) {
			LOGGER.error(ErrorMessages.TEAM_NOT_FOUND);
			throw new CustomException(ErrorMessages.TEAM_NOT_FOUND);
		}

		User user = userOpt.get();
		Team team = teamOpt.get();
		this.userSer.assignMember(team, user);

		return ResponseEntity.noContent().build();
	}

	@Secured({ "ROLE_CEO", "ROLE_MANAGER" })
	@PutMapping("/unassign")
	public ResponseEntity<Void> unassignMember(@Valid @RequestBody TeamAssignMemberReq req) {
		Optional<User> userOpt = this.userSer.findById(req.getUserId());
		if (!userOpt.isPresent()) {
			LOGGER.error(ErrorMessages.USER_NOT_FOUND);
			throw new CustomException(ErrorMessages.USER_NOT_FOUND);
		}

		Optional<Team> teamOpt = this.teamSer.findById(req.getTeamId());
		if (!teamOpt.isPresent()) {
			LOGGER.error(ErrorMessages.TEAM_NOT_FOUND);
			throw new CustomException(ErrorMessages.TEAM_NOT_FOUND);
		}

		User user = userOpt.get();
		Team team = teamOpt.get();
		this.userSer.unassignMember(team, user);

		return ResponseEntity.noContent().build();
	}

}
