package com.testautomation.model;

import java.util.ArrayList;
import java.util.HashMap;
import javax.persistence.*;

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
	private boolean dataFromDBCheckbox;
	
	@Column(name="TAM01_APPLICATIONS")
	private String userApplications;
	
	@Transient
	private ArrayList<String> usersList;
	
	@Transient
	private ArrayList<String> applicationsList;
	
	@Transient
	private String applicationsMappedToUser;
	
	@Transient
	private String selectedComponentID;
	
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
		return dataFromDBCheckbox;
	}

	public void setDataFromDBCheckbox(boolean dataFromDBCheckbox) {
		this.dataFromDBCheckbox = dataFromDBCheckbox;
	}

	public String getUserApplications() {
		return userApplications;
	}

	public void setUserApplications(String userApplications) {
		this.userApplications = userApplications;
	}

	public ArrayList<String> getUsersList() {
		return usersList;
	}

	public void setUsersList(ArrayList<String> usersList) {
		this.usersList = usersList;
	}

	public ArrayList<String> getApplicationsList() {
		return applicationsList;
	}

	public void setApplicationsList(ArrayList<String> applicationsList) {
		this.applicationsList = applicationsList;
	}

	public String getApplicationsMappedToUser() {
		return applicationsMappedToUser;
	}

	public void setApplicationsMappedToUser(String applicationsMappedToUser) {
		this.applicationsMappedToUser = applicationsMappedToUser;
	}

	public String getSelectedComponentID() {
		return selectedComponentID;
	}

	public void setSelectedComponentID(String selectedComponentID) {
		this.selectedComponentID = selectedComponentID;
	}
}