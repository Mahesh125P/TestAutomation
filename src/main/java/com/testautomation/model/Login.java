package com.testautomation.model;

import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name="KTAM01_USER")
public class Login {
	
	@Id
	@Column(name="TAM01_USER_ID")
	private String userName;

	@Transient
    private String password	;
	
	@Transient
	private ArrayList<String> applicationList;

	@Transient
	private ArrayList<String> screenNameList;
	
	@Transient
	private ArrayList<String> testCaseList;
	
	@Transient
	private String selectedApplicationName;

	@Transient
	private String selectedScreenName;
	
	@Transient
	private boolean isDataFromDBCheckbox;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String Password) {
		password = Password;
	}
	

	public ArrayList<String> getApplicationList() {
		return applicationList;
	}

	public void setApplicationList(ArrayList<String> applicationList) {
		this.applicationList = applicationList;
	}

	public ArrayList<String> getScreenNameList() {
		return screenNameList;
	}

	public void setScreenNameList(ArrayList<String> screenNameList) {
		this.screenNameList = screenNameList;
	}

	public ArrayList<String> getTestCaseList() {
		return testCaseList;
	}

	public void setTestCaseList(ArrayList<String> testCaseList) {
		this.testCaseList = testCaseList;
	}

	public String getSelectedApplicationName() {
		return selectedApplicationName;
	}

	public void setSelectedApplicationName(String selectedApplicationName) {
		this.selectedApplicationName = selectedApplicationName;
	}

	public String getSelectedScreenName() {
		return selectedScreenName;
	}

	public void setSelectedScreenName(String selectedScreenName) {
		this.selectedScreenName = selectedScreenName;
	}

	public boolean isDataFromDBCheckbox() {
		return isDataFromDBCheckbox;
	}

	public void setDataFromDBCheckbox(boolean isDataFromDBCheckbox) {
		this.isDataFromDBCheckbox = isDataFromDBCheckbox;
	}

	
}