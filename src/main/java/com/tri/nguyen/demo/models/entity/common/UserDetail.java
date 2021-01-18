package com.tri.nguyen.demo.models.entity.common;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class UserDetail implements UserDetails {

	private static final long serialVersionUID = -36801252273159368L;

	private String username;

	private String password;

	private Integer active;

	private boolean isLocked;

	private boolean isExpired;

	private boolean isEnabled;

	private List<GrantedAuthority> grantedAuthorities;

	public UserDetail(String username, String[] authorities) {
		this.username = username;
		this.grantedAuthorities = AuthorityUtils.createAuthorityList(authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuthorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return active == 1;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !isExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
