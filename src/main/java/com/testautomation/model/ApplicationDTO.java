package com.testautomation.model;

public class ApplicationDTO {
	

	private Integer applicationID;
	
	private String applicationName;
	
	private String applicationURL;
	
	private String applicationBrowser;

	private String screenName;
	
	private String applicationDataBase;
	
	private String dataBaseURL;
	
	private String dataBaseUserName;
	
	private String dataBasePassword;
	
	public ApplicationDTO(Integer applicationID, String applicationName, String applicationURL, 
			String applicationBrowser, String applicationDataBase, 
			String dataBaseURL,String dataBaseUserName,String dataBasePassword,String screenName) {
		super();
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.applicationURL = applicationURL;
		this.applicationBrowser = applicationBrowser;
		this.applicationDataBase = applicationDataBase;
		this.dataBaseURL = dataBaseURL;
		this.dataBaseUserName = dataBaseUserName;
		this.dataBasePassword = dataBasePassword;
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

	public String getDataBaseURL() {
		return dataBaseURL;
	}

	public void setDataBaseURL(String dataBaseURL) {
		this.dataBaseURL = dataBaseURL;
	}

	public String getDataBaseUserName() {
		return dataBaseUserName;
	}

	public void setDataBaseUserName(String dataBaseUserName) {
		this.dataBaseUserName = dataBaseUserName;
	}

	public String getDataBasePassword() {
		return dataBasePassword;
	}

	public void setDataBasePassword(String dataBasePassword) {
		this.dataBasePassword = dataBasePassword;
	}

}
