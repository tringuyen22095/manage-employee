package com.tri.nguyen.demo.models.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "team")
@Where(clause = "object_status = 0")
public class Team extends BaseObject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull
	private String name;
	@NotNull
	private String shortName;
	@NotNull
	private String description;

	// Relationship
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id", referencedColumnName = "id", nullable = false)
	private Department department;

	@ManyToMany(mappedBy = "teams", fetch = FetchType.LAZY)
	private Set<User> users;

	private Team(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.shortName = builder.shortName;
		this.description = builder.description;
		this.department = builder.department;
	}

	public Team() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public static Builder builder() {
		return new Builder();
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public static final class Builder {
		private Integer id;
		private String name;
		private String shortName;
		private String description;
		private Department department;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withShortName(String shortName) {
			this.shortName = shortName;
			return this;
		}

		public Builder withDescription(String description) {
			this.description = description;
			return this;
		}
	
		public Builder withDepartment(Department department) {
			this.department = department;
			return this;
		}

		public Team build() {
			return new Team(this);
		}
	}

}
