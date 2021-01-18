package com.tri.nguyen.demo.models.req;

import javax.validation.constraints.NotNull;

public class TeamAssignMemberReq {
	@NotNull(message = "User id is mandatory.")
	private Integer userId;
	@NotNull(message = "Team id is mandatory.")
	private Integer teamId;

	public TeamAssignMemberReq() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

}
