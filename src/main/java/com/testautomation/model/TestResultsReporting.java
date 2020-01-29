package com.testautomation.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="KTAT01_TEST_RESULT")
public class TestResultsReporting {

	@Id
	@Column(name="TAT01_TEST_RESULT_ID")
	private Integer testResultId;
	
	/*
	 * @Column(name="TAM02_APPLICATION_ID") private Integer testedApplicationId;
	 * 
	 * @Column(name="TAM03_SCREEN_ID") private Integer testedScreenId;
	 */
	
	
	@ManyToOne()
	@JoinColumn(name = "TAM02_APPLICATION_ID", nullable = false)
	private Application applicationTestReport;
	
	@ManyToOne()
	@JoinColumn(name = "TAM03_SCREEN_ID", nullable = false)
	private Screen screenTestReport;
	
	@Column(name="TAT01_TEST_CASE_NAME")
	private String testedCaseName;
	
	//@Temporal(TemporalType.DATE)
	@Column(name="TAT01_TESTED_DT")
	private Date testedDate;
	
	@Column(name="TAT01_TESTED_BY")
	private String testedBy;
	
	@Column(name="TAT01_TEST_INPUT")
	private String testInputs;
	
	@Column(name="TAT01_TEST_RESULT")
	private String testResults;

	@Transient
	private Date testFromDate;
	
	@Transient
	private Date testToDate;
	
	public Integer getTestResultId() {
		return testResultId;
	}

	public void setTestResultId(Integer testResultId) {
		this.testResultId = testResultId;
	}

	public Application getApplicationTestReport() {
		return applicationTestReport;
	}

	public void setApplicationTestReport(Application applicationTestReport) {
		this.applicationTestReport = applicationTestReport;
	}

	public Screen getScreenTestReport() {
		return screenTestReport;
	}

	public void setScreenTestReport(Screen screenTestReport) {
		this.screenTestReport = screenTestReport;
	}

	public String getTestedCaseName() {
		return testedCaseName;
	}

	public void setTestedCaseName(String testedCaseName) {
		this.testedCaseName = testedCaseName;
	}

	public Date getTestedDate() {
		return testedDate;
	}

	public void setTestedDate(Date testedDate) {
		this.testedDate = testedDate;
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

	public String getTestResults() {
		return testResults;
	}

	public void setTestResults(String testResults) {
		this.testResults = testResults;
	}

	public Date getTestFromDate() {
		return testFromDate;
	}

	public void setTestFromDate(Date testFromDate) {
		this.testFromDate = testFromDate;
	}

	public Date getTestToDate() {
		return testToDate;
	}

	public void setTestToDate(Date testToDate) {
		this.testToDate = testToDate;
	}
}
