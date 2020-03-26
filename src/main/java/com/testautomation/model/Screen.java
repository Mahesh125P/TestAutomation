package com.testautomation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "KTAM03_SCREEN")
public class Screen {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TAM03_SCREEN_ID")
	private Integer screenID;

	@Column(name = "TAM03_SCREEN_NAME")
	private String screenName;

	@Column(name = "TAM03_CREATED_BY")
	private String createdBy;

	@Column(name = "TAM03_LASTUPDATED_BY")
	private String lastUpdatedBy;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TAM02_APPLICATION_ID") 
	private Application application;
	 
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "TAM02_APPLICATION_ID")
	private List<TestResultsReporting> testResultsReporting;  
	
	@Transient
	private MultipartFile file;

	@Column(name = "TAM03_SCREEN_QUERY")
	private String screenQuery;
	/*
	 * @OneToMany(mappedBy = "screenTestReport") private Set<TestResultsReporting>
	 * testResultsReporting;
	 */

	public Integer getScreenID() {
		return screenID;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
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

	public List<TestResultsReporting> getTestResultsReporting() {
		return testResultsReporting;
	}

	public void setTestResultsReporting(List<TestResultsReporting> testResultsReporting) {
		this.testResultsReporting = testResultsReporting;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public String getScreenQuery() {
		return screenQuery;
	}

	public void setScreenQuery(String screenQuery) {
		this.screenQuery = screenQuery;
	}

	/*
	 * public Set<TestResultsReporting> getTestResultsReporting() { return
	 * testResultsReporting; }
	 * 
	 * public void setTestResultsReporting(Set<TestResultsReporting>
	 * testResultsReporting) { this.testResultsReporting = testResultsReporting; }
	 */

}
