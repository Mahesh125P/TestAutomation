package com.testautomation.model;

import javax.persistence.*;

@Entity
@Table(name="KTAM03_SCREEN")
public class Screen {

	
	@Id
	@Column(name="TAM03_SCREEN_NAME")
	private String screenName;
	
	@Column(name="TAM03_CREATED_BY")
	private String createdBy;
	
	@Column(name="TAM03_LASTUPDATED_BY")
	private String lastUpdatedBy;
	
	@ManyToOne()
	@JoinColumn(name = "TAM02_APPLICATION_ID", nullable = false)
	private Application application;
	
	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
}
