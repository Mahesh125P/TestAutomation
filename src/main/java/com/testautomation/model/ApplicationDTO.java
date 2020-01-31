package com.testautomation.model;

public class ApplicationDTO {
	

	private Integer applicationID;
	
	private String applicationName;
	
	private String applicationURL;

	private String screenName;
	
	public ApplicationDTO(Integer applicationID, String applicationName, String applicationURL, String screenName) {
		super();
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.applicationURL = applicationURL;
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

}
