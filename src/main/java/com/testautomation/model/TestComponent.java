package com.testautomation.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "KTAM04_COMPONENT")
public class TestComponent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2463215590137919523L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TAM04_COMPONENT_ID")
	private Integer testComponentID;

	@Column(name = "TAM02_APPLICATION_ID")
	private Integer application;

	@Column(name = "TAM04_COMPONENT_NAME")
	private String componentName;

	@Column(name = "TAM04_CREATED_BY")
	private String createdBy;

	@Column(name = "TAM04_LASTUPDATED_BY")
	private String lastupdatedBy;

	@Column(name = "TAM04_CREATED_DT")
	private Date createdDate;

	@Column(name = "TAM04_LASTUPDATED_DT")
	private Date lastupdatedDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "testComponent")
	@JsonManagedReference
	Set<ComponentMapping> componentMapping;

	public Set<ComponentMapping> getComponentMapping() {
		return componentMapping;
	}

	public void setComponentMapping(Set<ComponentMapping> componentMapping) {
		this.componentMapping = componentMapping;
	}

	public Integer getTestComponentID() {
		return testComponentID;
	}

	public void setTestComponentID(Integer testComponentID) {
		this.testComponentID = testComponentID;
	}

	public TestComponent() {
	}

	public TestComponent(Integer testComponentID, Integer application, String componentName, String createdBy,
			String lastupdatedBy, Date createdDate, Date lastupdatedDate) {
		super();
		this.testComponentID = testComponentID;
		this.application = application;
		this.componentName = componentName;
		this.createdBy = createdBy;
		this.lastupdatedBy = lastupdatedBy;
		this.createdDate = createdDate;
		this.lastupdatedDate = lastupdatedDate;
	}

	public Integer getApplication() {
		return application;
	}

	public void setApplication(Integer application) {
		this.application = application;
	}

	public String getComponentName() {
		return componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
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
}
