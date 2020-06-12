package com.testautomation.model;

public class ApplicationDTO {
	

	private Integer applicationID;
	
	private String applicationName;
	
	private String applicationURL;
	
	private String applicationBrowser;

	private String screenName;
	
	private String applicationDataBase;
	
	public ApplicationDTO(Integer applicationID, String applicationName, String applicationURL, 
			String applicationBrowser, String applicationDataBase, String screenName) {
		super();
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.applicationURL = applicationURL;
		this.applicationBrowser = applicationBrowser;
		this.applicationDataBase = applicationDataBase;
		this.screenName = screenName;
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


	public String getApplicationURL() {
		return applicationURL;
	}


	public void setApplicationURL(String applicationURL) {
		this.applicationURL = applicationURL;
	}


	public String getScreenName() {
		return screenName;
	}


	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	public String getApplicationBrowser() {
		return applicationBrowser;
	}

	public void setApplicationBrowser(String applicationBrowser) {
		this.applicationBrowser = applicationBrowser;
	}

	public String getApplicationDataBase() {
		return applicationDataBase;
	}

	public void setApplicationDataBase(String applicationDataBase) {
		this.applicationDataBase = applicationDataBase;
	}

}
