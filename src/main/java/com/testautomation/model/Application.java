package com.testautomation.model;

import java.util.Set;

import javax.persistence.*;

/**
 * @author Mahesh Kumar P
 *
 */

@Entity
@Table(name="KTAM02_APPLICATION")
public class Application {

	@Id
	@Column(name = "TAM02_APPLICATION_ID")
	private String applicationID;
	
	@Column(name="TAM02_APPLICATION_NAME")
	private String applicationName;
	
	@OneToMany(mappedBy = "application")	
	private Set<Screen> screen; 
	
	@Column(name="TAM02_APPLICATION_URL")
	private String applicationURL;
	
	@Column(name="TAM02_APPLICATION_BROWSER")
	private String applicationBrowser;
	
	@Column(name="TAM02_CREATED_BY")
	private String createdBy;
	
	@Column(name="TAM02_LASTUPDATED_BY")
	private String lastupdatedBy;

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
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

	public Set<Screen> getScreen() {
		return screen;
	}

	public void setScreen(Set<Screen> screen) {
		this.screen = screen;
	}

	public String getApplicationBrowser() {
		return applicationBrowser;
	}

	public void setApplicationBrowser(String applicationBrowser) {
		this.applicationBrowser = applicationBrowser;
	}
	
}
