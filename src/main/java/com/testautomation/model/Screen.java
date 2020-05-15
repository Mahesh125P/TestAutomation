package com.testautomation.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	@JsonBackReference
	private Application application;
	 
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "TAM02_APPLICATION_ID")
	@JsonIgnore
	private List<TestResultsReporting> testResultsReporting;  
	
	/*
	 * @OneToOne(fetch = FetchType.LAZY,mappedBy = "componentMapping")
	 * 
	 * @JsonBackReference private ComponentMapping componentMapping;
	 */
	
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
