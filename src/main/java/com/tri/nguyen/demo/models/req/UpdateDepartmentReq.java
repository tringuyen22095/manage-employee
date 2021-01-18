package com.tri.nguyen.demo.models.req;

import javax.validation.constraints.NotNull;

import com.tri.nguyen.demo.models.entity.Department;

public class UpdateDepartmentReq extends CreateDepartmentReq {
	@NotNull(message = "Id is mandatory.")
	private Integer id;

	public UpdateDepartmentReq() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Department toModel(Department entity) {
		entity.setDescription(this.getDescription());
		entity.setName(this.getName());
		entity.setShortName(this.getShortName());

		return entity;
	}

}
