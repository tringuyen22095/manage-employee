package com.tri.nguyen.demo.models.req;

import javax.validation.constraints.NotNull;

import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.Team;

public class UpdateTeamReq extends CreateTeamReq {
	@NotNull(message = "Id is mandatory.")
	private Integer id;

	public UpdateTeamReq() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Team toModel(Team entity) {
		entity.setDescription(this.getDescription());
		entity.setName(this.getName());
		entity.setShortName(this.getShortName());
		entity.setDepartment(Department.builder().withId(this.getDepartmentId()).build());

		return entity;
	}

}
