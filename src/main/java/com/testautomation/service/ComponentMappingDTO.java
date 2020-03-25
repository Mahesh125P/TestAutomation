package com.testautomation.service;

import java.util.Set;

import com.testautomation.model.ComponentMapping;

public class ComponentMappingDTO {

	Integer componentId;

	Set<ComponentMapping> componentMapping;

	public Integer getComponentId() {
		return componentId;
	}

	public void setComponentId(Integer componentId) {
		this.componentId = componentId;
	}

	public ComponentMappingDTO() {
	}

	public ComponentMappingDTO(Integer componentId, Set<ComponentMapping> componentMapping) {
		super();
		this.componentId = componentId;
		this.componentMapping = componentMapping;
	}

	public Set<ComponentMapping> getComponentMapping() {
		return componentMapping;
	}

	public void setComponentMapping(Set<ComponentMapping> componentMapping) {
		this.componentMapping = componentMapping;
	}

}
