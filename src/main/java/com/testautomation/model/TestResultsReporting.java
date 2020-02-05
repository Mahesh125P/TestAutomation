package com.testautomation.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="KTAT01_TEST_RESULT")
public class TestResultsReporting {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TAT01_TEST_RESULT_ID")
	private Integer testResultId;
	
	@Column(name = "TAM02_APPLICATION_ID")
	private Integer applicationID;
	
	@Column(name = "TAM03_SCREEN_ID")
	private Integer screenID;
	
	@Column(name="TAT01_TEST_CASE_NAME")
	private String testedCaseName;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="TAT01_TEST_START_DT")
	private Date testStartDate;
	
	@Column(name="TAT01_TEST_END_DT")
	private Date testEndDate;
	
	@Column(name="TAT01_TESTED_BY")
	private String testedBy;
	
	@Column(name="TAT01_TEST_INPUT")
	private String testInputs;
	
	@Column(name="TAT01_TEST_OUTPUT")
	private String testOutput;
	
	@Column(name="TAT01_TEST_FAILED_DATA")
	private String testFailedData;
	
	@Column(name="TAT01_CREATED_BY")
	private String createdBy;

	@Transient
	private String testRAppName;
	
	@Transient
	private String testRScreenName;
	
	
	public Integer getTestResultId() {
		return testResultId;
	}

	public void setTestResultId(Integer testResultId) {
		this.testResultId = testResultId;
	}

	public String getTestedCaseName() {
		return testedCaseName;
	}

	public void setTestedCaseName(String testedCaseName) {
		this.testedCaseName = testedCaseName;
	}

	public String getTestedBy() {
		return testedBy;
	}

	public void setTestedBy(String testedBy) {
		this.testedBy = testedBy;
	}

	public String getTestInputs() {
		return testInputs;
	}

	public void setTestInputs(String testInputs) {
		this.testInputs = testInputs;
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

	public String getTestOutput() {
		return testOutput;
	}

	public void setTestOutput(String testOutput) {
		this.testOutput = testOutput;
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

	public String getTestRAppName() {
		return testRAppName;
	}

	public void setTestRAppName(String testRAppName) {
		this.testRAppName = testRAppName;
	}

	public String getTestRScreenName() {
		return testRScreenName;
	}

	public void setTestRScreenName(String testRScreenName) {
		this.testRScreenName = testRScreenName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getTestFailedData() {
		return testFailedData;
	}

	public void setTestFailedData(String testFailedData) {
		this.testFailedData = testFailedData;
	}
}
