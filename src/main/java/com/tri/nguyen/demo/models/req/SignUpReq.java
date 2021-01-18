package com.tri.nguyen.demo.models.req;

import java.util.Collections;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.tri.nguyen.demo.models.entity.Profile;
import com.tri.nguyen.demo.models.entity.Role;
import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.entity.common.Constant;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.Gender;
import com.tri.nguyen.demo.models.entity.common.EnumCommon.RoleId;

public class SignUpReq {
	@Email(regexp = Constant.EMAIL_REGEX, message = "Email is invalid.")
	@NotBlank(message = "Email is mandatory.")
	private String email;
	@NotBlank(message = "Password is mandatory.")
	@Size(min = 6, message = "Password need to have at least 6 letters.")
	private String password;
	@NotBlank(message = "First name is mandatory.")
	private String firstName;
	@NotBlank(message = "Last name is mandatory.")
	private String lastName;
	@NotBlank(message = "Phone number is mandatory.")
	@Size(max = 10, message = "Phone number can not have more than 10 letters.")
	private String phoneNumber;
	@NotNull(message = "Gender is mandatory.")
	private Gender gender;
	@NotNull(message = "Date of birth is mandatory.")
	private Date dateOfBirth;
	@NotNull(message = "Role id is mandatory.")
	private RoleId role;

	public SignUpReq() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public RoleId getRole() {
		return role == null ? RoleId.MEMBER : role;
	}

	public void setRole(RoleId role) {
		this.role = role;
	}

	public User toModel(BCryptPasswordEncoder bCryptPasswordEncoder) {
		Profile profile = Profile.builder()
				.withFirstName(this.firstName)
				.withLastName(this.lastName)
				.withGender(this.gender)
				.withPhoneNumber(this.phoneNumber)
				.withDateOfBirth(this.dateOfBirth)
				.build();
		return User.builder()
				.withEmail(this.email)
				.withPassword(bCryptPasswordEncoder.encode(this.password))
				.withProfile(profile)
				.withRoles(Collections.singleton(new Role(this.getRole().id)))
				.build();
	}
	
}
