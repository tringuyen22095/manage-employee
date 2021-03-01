package com.tri.nguyen.demo.models.req;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.Team;

public class CreateTeamReq {
	@NotBlank(message = "Name is mandatory.")
	private String name;
	private String shortName;
	private String description;
	@NotNull(message = "Department id is mandatory.")
	private Integer departmentId;

	public CreateTeamReq() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Team toModel() {
		return Team.builder()
				.withDescription(this.description)
				.withName(this.name)
				.withShortName(this.shortName)
				.withDepartment(Department.builder().withId(this.departmentId).build())
				.build();
	}
	
}
