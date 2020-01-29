package com.testautomation.model;

import java.util.ArrayList;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="KTAM03_SCREEN")
public class Screen {

	
	@Id
	@Column(name = "TAM03_SCREEN_ID")
	private Integer screenID;
	
	@Column(name="TAM03_SCREEN_NAME")
	private String screenName;
	
	@Column(name="TAM03_CREATED_BY")
	private String createdBy;
	
	@Column(name="TAM03_LASTUPDATED_BY")
	private String lastUpdatedBy;
	
	@ManyToOne()
	@JoinColumn(name = "TAM02_APPLICATION_ID", nullable = false)
	private Application application;
	
	@OneToMany(mappedBy = "screenTestReport")	
	private Set<TestResultsReporting> testResultsReporting;

	public Integer getScreenID() {
		return screenID;
	}

	public void setScreenID(Integer screenID) {
		this.screenID = screenID;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public Set<TestResultsReporting> getTestResultsReporting() {
		return testResultsReporting;
	}

	public void setTestResultsReporting(Set<TestResultsReporting> testResultsReporting) {
		this.testResultsReporting = testResultsReporting;
	}

	
	
}
