package com.testautomation.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "KTAM05_SCENARIO_MAP")
public class ComponentMapping implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4088507368130188094L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TAM05_SCENARIO_ID")
	private Integer componentMappingId;

	@Column(name = "TAM05_TEST_ORDER_N0")
	private Integer testOrder;

	@Column(name = "TAM05_TEST_CASE")
	private String testcase;

	@Column(name = "TAM05_CREATED_BY")
	private String createdBy;

	@Column(name = "TAM05_LASTUPDATED_BY")
	private String lastupdatedBy;

	@Column(name = "TAM05_CREATED_DT")
	private Date createdDate;

	@Column(name = "TAM05_LASTUPDATED_DT")
	private Date lastupdatedDate;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JoinColumn(name = "TAM03_SCREEN_ID") 
	private Screen screen;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TAM04_COMPONENT_ID", nullable = false)
	@JsonBackReference
	private TestComponent testComponent;

	ComponentMapping() {
	}

	
	public Integer getComponentMappingId() {
		return componentMappingId;
	}


	public void setComponentMappingId(Integer componentMappingId) {
		this.componentMappingId = componentMappingId;
	}


	public Screen getScreen() {
		return screen;
	}


	public void setScreen(Screen screen) {
		this.screen = screen;
	}


	public Integer getTestOrder() {
		return testOrder;
	}

	public void setTestOrder(Integer testOrder) {
		this.testOrder = testOrder;
	}

	public String getTestcase() {
		return testcase;
	}

	public void setTestcase(String testcase) {
		this.testcase = testcase;
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

	public TestComponent getTestComponent() {
		return testComponent;
	}

	public void setTestComponent(TestComponent testComponent) {
		this.testComponent = testComponent;
	}

}
