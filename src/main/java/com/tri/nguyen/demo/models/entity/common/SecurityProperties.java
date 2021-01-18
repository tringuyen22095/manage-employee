package com.tri.nguyen.demo.models.entity.common;

import java.util.Base64;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.config")
public class SecurityProperties {

	private Integer time;

	private String prefix;

	private String key;

	private String header;

	@PostConstruct
	protected void init() {
		key = Base64.getEncoder().encodeToString(key.getBytes());
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

}
