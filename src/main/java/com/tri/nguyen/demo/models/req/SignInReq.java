package com.tri.nguyen.demo.models.req;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.tri.nguyen.demo.models.entity.common.Constant;

public class SignInReq {
	@Email(regexp = Constant.EMAIL_REGEX, message = "Email is invalid.")
	@NotBlank(message = "Email is mandatory.")
	private String email;
	@NotBlank(message = "Password is mandatory.")
	private String password;

	public SignInReq() {
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

}
