package com.testautomation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testautomation.model.ComponentMapping;
import com.testautomation.model.TestComponent;
import com.testautomation.repositories.ComponentMappingRepository;
import com.testautomation.repositories.TestComponentRepository;

@Service
@Transactional
public class TestComponentService {

	@Autowired
	private TestComponentRepository testComponentRepository;

	@Autowired
	private ComponentMappingRepository componentMappingRepository;

	public ArrayList<LookupDTO> getTestComponentByAppId(Integer appid) {
		return testComponentRepository.findTestComponentByAppId(appid);
	}

	public TestComponent saveTestComponent(TestComponent testComponent) {
		testComponent.setCreatedBy("Mahesh4");
		testComponent.setLastupdatedBy("Mahesh4");
		testComponent.setCreatedDate(new Date());
		testComponent.setLastupdatedDate(new Date());
		return testComponentRepository.save(testComponent);

	}
	/*
	 * public ComponentMapping saveTestSuiteMapping(ComponentMapping mapping){
	 * mapping.setCreatedBy("Mahesh4"); mapping.setLastupdatedBy("Mahesh4");
	 * mapping.setCreatedDate(new Date()); mapping.setLastupdatedDate(new Date());
	 * return testComponentRepository.save(mapping);
	 * 
	 * }
	 */

	public List<ComponentMapping> saveComponentMapping(ComponentMappingDTO componentMappingDTO) {
		List<ComponentMapping> componentMappingList = new ArrayList<>();
		TestComponent testComponent = new TestComponent();
		testComponent.setTestComponentID(componentMappingDTO.componentId);
		componentMappingDTO.getComponentMapping().stream().forEach(data -> {
			ComponentMapping componentMapping = data;
			componentMapping.setCreatedBy("Mahesh4");
			componentMapping.setLastupdatedBy("Mahesh4");
			componentMapping.setCreatedDate(new Date());
			componentMapping.setLastupdatedDate(new Date());
			componentMapping.setTestComponent(testComponent);
			ComponentMapping result = componentMappingRepository.saveAndFlush(componentMapping);
			componentMappingList.add(result);
		});
		return componentMappingList;
	}

	public List<ComponentMapping> getComponentMapping(Integer componentId) {
		return componentMappingRepository.findAllByComponentId(componentId);
	}

}
