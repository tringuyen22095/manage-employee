package com.tri.nguyen.demo.models.req;

import javax.validation.constraints.NotBlank;

import com.tri.nguyen.demo.models.entity.Department;

public class CreateDepartmentReq {
	@NotBlank(message = "Name is mandatory.")
	private String name;
	private String shortName;
	private String description;

	public CreateDepartmentReq() {
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

	public Department toModel() {
		return Department.builder()
				.withDescription(this.description)
				.withName(this.name)
				.withShortName(this.shortName)
				.build();
	}
	
}
