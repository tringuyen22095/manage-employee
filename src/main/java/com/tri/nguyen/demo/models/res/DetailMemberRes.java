package com.tri.nguyen.demo.models.res;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tri.nguyen.demo.models.entity.Department;
import com.tri.nguyen.demo.models.entity.Profile;
import com.tri.nguyen.demo.models.entity.Role;
import com.tri.nguyen.demo.models.entity.Team;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.Gender;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.UserStatus;

@JsonInclude(value = Include.NON_EMPTY)
public class DetailMemberRes {
	private String fullName;
	private String email;
	private UserStatus status;
	private Date dateOfBirth;
	private Gender gender;
	private String phoneNumber;
	private String roleName;
	private List<DepartmentRes> departments;
	private List<TeamRes> teams;

	public DetailMemberRes() {
	}

	public DetailMemberRes(User member) {
		this.email = member.getEmail();
		this.status = member.getStatus();

		this.roleName = String.join(", ", member.getRoles().stream().map(Role::getName).toArray(String[]::new));

		Profile profile = member.getProfile();
		this.fullName = String.format("%s %s", profile.getFirstName(), profile.getLastName());
		this.dateOfBirth = profile.getDateOfBirth();
		this.gender = profile.getGender();
		this.phoneNumber = profile.getPhoneNumber();

		this.departments = member.getDepartments()
				.stream()
				.map(DepartmentRes::new)
				.collect(Collectors.toList());

		this.teams = member.getTeams()
				.stream()
				.map(TeamRes::new)
				.collect(Collectors.toList());
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<DepartmentRes> getDepartments() {
		return departments;
	}

	public void setDepartments(List<DepartmentRes> departments) {
		this.departments = departments;
	}

	public List<TeamRes> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamRes> teams) {
		this.teams = teams;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}

class DepartmentRes {
	private String name;
	private String shortName;
	private String description;

	public DepartmentRes(Department department) {
		this.name = department.getName();
		this.shortName = department.getShortName();
		this.description = department.getDescription();
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

}

class TeamRes {
	private String name;
	private String shortName;
	private String description;

	public TeamRes(Team team) {
		this.name = team.getName();
		this.shortName = team.getShortName();
		this.description = team.getDescription();
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

}