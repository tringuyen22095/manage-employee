package com.tri.nguyen.demo.models.res;

public class ErrorRes {
	private String message;
	
	public ErrorRes() {
	}

	public ErrorRes(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
