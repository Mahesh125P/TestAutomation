package com.testautomation.model;

import java.util.Set;

import javax.persistence.*;


@Entity
@Table(name="KTAM02_APPLICATION")
public class Application {

	@Id
	@Column(name = "TAM02_APPLICATION_ID")
	private Integer applicationID;
	
	@Column(name="TAM02_APPLICATION_NAME")
	private String applicationName;
	
	@OneToMany(mappedBy = "application")	
	private Set<Screen> screen; 
	
	@OneToMany(mappedBy = "applicationTestReport")
	private Set<TestResultsReporting> testResultsReporting;
	
	@Column(name="TAM02_APPLICATION_URL")
	private String applicationURL;
	
	@Column(name="TAM02_CREATED_BY")
	private String createdBy;
	
	@Column(name="TAM02_LASTUPDATED_BY")
	private String lastupdatedBy;

	public Integer getApplicationID() {
		return applicationID;
	}

	public void setApplicationID(Integer applicationID) {
		this.applicationID = applicationID;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public Set<Screen> getScreen() {
		return screen;
	}

	public void setScreen(Set<Screen> screen) {
		this.screen = screen;
	}

	public Set<TestResultsReporting> getTestResultsReporting() {
		return testResultsReporting;
	}

	public void setTestResultsReporting(Set<TestResultsReporting> testResultsReporting) {
		this.testResultsReporting = testResultsReporting;
	}

	public String getApplicationURL() {
		return applicationURL;
	}

	public void setApplicationURL(String applicationURL) {
		this.applicationURL = applicationURL;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastupdatedBy() {
		return lastupdatedBy;
	}

	public void setLastupdatedBy(String lastupdatedBy) {
		this.lastupdatedBy = lastupdatedBy;
	}

	
	
}
