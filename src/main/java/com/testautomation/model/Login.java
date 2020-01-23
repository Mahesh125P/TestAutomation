package com.testautomation.model;

import javax.persistence.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name="KVDSM02_USERMASTER")
public class Login {
	
	@Id
	@Column(name="VDSM02_USERID_D")
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
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String username) {
		userName = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		password = password;
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

	
	
}