package com.tri.nguyen.demo.config;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.tri.nguyen.demo.models.entity.User;
import com.tri.nguyen.demo.models.entity.common.SecurityProperties;
import com.tri.nguyen.demo.models.entity.common.UserDetail;
import com.tri.nguyen.demo.models.res.JwtRes;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Autowired
	private SecurityProperties securityProperties;

	public JwtRes generateToken(User user) {
		Claims claims = Jwts.claims()
				.setSubject(user.getEmail());
		claims.put("authorities", user.getRoles()
										.stream()
										.map(i -> String.format("ROLE_%s", i.getName().toUpperCase()))
										.collect(Collectors.toSet()));

		DateTime current = DateTime.now();
		DateTime expiredAt = current.plusMinutes(securityProperties.getTime());
		
		String token = Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(current.toDate())
				.setExpiration(expiredAt.toDate())
				.signWith(SignatureAlgorithm.HS512, securityProperties.getKey()).compact();

		return JwtRes.builder()
				.withToken(token)
				.withExpiredAt(expiredAt.toDate())
				.build();
	}

	@SuppressWarnings("unchecked")
	public List<SimpleGrantedAuthority> getGrantedAuthority(String token) {
		List<String> authorities = readToken(token).get("authorities", List.class);
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public Claims readToken(String token) {
		return Jwts.parser()
				.setSigningKey(securityProperties.getKey())
				.parseClaimsJws(token)
				.getBody();
	}

	public String getSubject(String token) {
		return this.readToken(token).getSubject();
	}

	public List<String> getAuthorities(String token) {
		List<?> roles = this.readToken(token).get("authorities", List.class); 
		return roles.stream()
				.map(Object::toString)
				.collect(Collectors.toList());
	}

	public boolean isTokenExpired(String token) {
		DateTime expireTime = new DateTime(this.readToken(token).getExpiration());
		return expireTime.isBeforeNow();
	}

	public UsernamePasswordAuthenticationToken getAuthentication(String token) {
		UserDetails userDetails = getUserDetails(token);
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	private UserDetails getUserDetails(String token) {
		String email = this.getSubject(token);
		List<String> roles = this.getAuthorities(token);
		String[] roleArr = new String[roles.size()];
		roles.toArray(roleArr);

		return new UserDetail(email, roleArr);
	}

}
