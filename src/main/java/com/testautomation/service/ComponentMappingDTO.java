package com.testautomation.service;

import java.util.Set;

import com.testautomation.model.ComponentMapping;

public class ComponentMappingDTO {

	String componentId;
	String applicationName;


	Set<ComponentMapping> componentMapping;

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public ComponentMappingDTO() {
	}

	public ComponentMappingDTO(String componentId, String  applicationName, Set<ComponentMapping> componentMapping) {
		super();
		this.componentId = componentId;
		this.applicationName = applicationName;
		this.componentMapping = componentMapping;
	}

	public Set<ComponentMapping> getComponentMapping() {
		return componentMapping;
	}

	public void setComponentMapping(Set<ComponentMapping> componentMapping) {
		this.componentMapping = componentMapping;
	}
	
	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

}
