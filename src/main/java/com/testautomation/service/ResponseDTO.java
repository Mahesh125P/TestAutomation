package com.testautomation.service;

import java.util.ArrayList;

public class ResponseDTO {

	private String status;

	private String message;

	public ArrayList<String> errorUsersList = new ArrayList<String>();
	
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

	public ArrayList<String> getErrorUsersList() {
		return errorUsersList;
	}

	public void setErrorUsersList(ArrayList<String> errorUsersList) {
		this.errorUsersList = errorUsersList;
	}

}
