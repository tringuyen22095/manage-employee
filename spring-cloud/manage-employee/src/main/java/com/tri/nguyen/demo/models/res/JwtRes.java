package com.tri.nguyen.demo.models.res;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tri.nguyen.demo.models.entity.common.Constant;

public class JwtRes {
	
	private String token;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.DATETIME_FORMAT)
	private Date expiredAt;

	public JwtRes() {
	}

	private JwtRes(Builder builder) {
		this.token = builder.token;
		this.expiredAt = builder.expiredAt;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String token;
		private Date expiredAt;

		private Builder() {
		}

		public Builder withToken(String token) {
			this.token = token;
			return this;
		}

		public Builder withExpiredAt(Date expiredAt) {
			this.expiredAt = expiredAt;
			return this;
		}

		public JwtRes build() {
			return new JwtRes(this);
		}
	}

}
