package com.tri.nguyen.demo.models.entity.common;

public class EnumCommon {

	public enum Gender {
		MALE, FEMALE
	}

	public enum UserStatus {
		WORKING, TERMINATED
	}

	public enum ObjectStatus {
		ACTIVE, DELETED
	}

	public enum RoleId {
		CEO(1), MANAGER(2), MEMBER(3);

		public Integer id;

		private RoleId(Integer id) {
			this.id = id;
		}
	}

	public enum SortType {
		DESC(-1), ASC(1);

		private Integer value;

		public Integer getValue() {
			return this.value;
		}

		private SortType(Integer value) {
			this.value = value;
		}
	}

}
