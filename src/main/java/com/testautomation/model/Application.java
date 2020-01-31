package com.testautomation.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 * @author Mahesh Kumar P
 *
 */

@Entity
@Table(name="KTAM02_APPLICATION")
public class Application implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TAM02_APPLICATION_ID")
	private Integer applicationID;
	
	@Column(name="TAM02_APPLICATION_NAME")
	private String applicationName;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "TAM02_APPLICATION_ID")
	private List<Screen> screen; 
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "TAM02_APPLICATION_ID")
	private List<TestResultsReporting> testResultsReporting; 
	 
	@Column(name="TAM02_APPLICATION_URL")
	private String applicationURL;
	
	@Column(name="TAM02_APPLICATION_BROWSER")
	private String applicationBrowser;
	
	@Column(name="TAM02_CREATED_BY")
	private String createdBy;
	
	@Column(name="TAM02_LASTUPDATED_BY")
	private String lastupdatedBy;
	
	@Column(name ="TAM02_CREATED_DT")
	private Date createdDate;
	
	@Column(name ="TAM02_LASTUPDATED_DT")
	private Date lastupdatedDate;

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastupdatedDate() {
		return lastupdatedDate;
	}

	public void setLastupdatedDate(Date lastupdatedDate) {
		this.lastupdatedDate = lastupdatedDate;
	}

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

	/*
	 * public Set<TestResultsReporting> getTestResultsReporting() { return
	 * testResultsReporting; }
	 * 
	 * public void setTestResultsReporting(Set<TestResultsReporting>
	 * testResultsReporting) { this.testResultsReporting = testResultsReporting; }
	 */

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

	public String getApplicationBrowser() {
		return applicationBrowser;
	}

	public void setApplicationBrowser(String applicationBrowser) {
		this.applicationBrowser = applicationBrowser;
	}
	
	public List<Screen> getScreen() {
		return screen;
	}

	public void setScreen(List<Screen> screen) {
		this.screen = screen;
	}

	public List<TestResultsReporting> getTestResultsReporting() {
		return testResultsReporting;
	}

	public void setTestResultsReporting(List<TestResultsReporting> testResultsReporting) {
		this.testResultsReporting = testResultsReporting;
	}
	
	
}
