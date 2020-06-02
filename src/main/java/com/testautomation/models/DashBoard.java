package com.testautomation.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;

public class DashBoard {
	
private Integer applicationID;
	
	
	private Integer screenID;
	private String testedCaseName;
	private Date testStartDate;
	private Date testEndDate;
	private String monthDate;
	private String[] TotalCase;
	private String[] passedData;
	private String[] failedData;
	private String[] month;
	private ArrayList<HashMap<String,String>> screenIDList;
	
	public String[] getTotalCase() {
		return TotalCase;
	}

	public void setTotalCase(String[] totalCase) {
		TotalCase = totalCase;
	}
	
	public String[] getPassedData() {
		return passedData;
	}

	public void setPassedData(String[] passedData) {
		this.passedData = passedData;
	}

	public String[] getFailedData() {
		return failedData;
	}

	public void setFailedData(String[] failedData) {
		this.failedData = failedData;
	}

	public String[] getMonth() {
		return month;
	}

	public void setMonth(String[] month) {
		this.month = month;
	}

	public Integer getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Integer applicationID) {
		this.applicationID = applicationID;
	}

	public Integer getScreenID() {
		return screenID;
	}

	public void setScreenID(Integer screenID) {
		this.screenID = screenID;
	}

	public String getTestedCaseName() {
		return testedCaseName;
	}

	public void setTestedCaseName(String testedCaseName) {
		this.testedCaseName = testedCaseName;
	}

	public Date getTestStartDate() {
		return testStartDate;
	}

	public void setTestStartDate(Date testStartDate) {
		this.testStartDate = testStartDate;
	}

	public Date getTestEndDate() {
		return testEndDate;
	}

	public void setTestEndDate(Date testEndDate) {
		this.testEndDate = testEndDate;
	}

	public ArrayList<HashMap<String, String>> getScreenIDList() {
		return screenIDList;
	}

	public void setScreenIDList(ArrayList<HashMap<String, String>> screenIDList) {
		this.screenIDList = screenIDList;
	}

	public String getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(String monthDate) {
		this.monthDate = monthDate;
	}

}
