package com.testautomation.service;

public class ResponseDTO {

	private String status;

	private String message;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ResponseDTO(String status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ResponseDTO() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
