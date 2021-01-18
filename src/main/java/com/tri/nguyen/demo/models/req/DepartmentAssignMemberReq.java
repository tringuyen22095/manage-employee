package com.tri.nguyen.demo.models.req;

import javax.validation.constraints.NotNull;

public class DepartmentAssignMemberReq {
	@NotNull(message = "User id is mandatory.")
	private Integer userId;
	@NotNull(message = "User id is mandatory.")
	private Integer departmentId;

	public DepartmentAssignMemberReq() {
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

}
