package com.testautomation.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.testautomation.model.ComponentMapping;
import com.testautomation.model.TestComponent;
import com.testautomation.service.ComponentMappingDTO;
import com.testautomation.service.LookupDTO;
import com.testautomation.service.TestComponentService;

@RestController
public class TestComponentController {

	@Autowired
	private TestComponentService testComponentService;

	final static Logger logger = LoggerFactory.getLogger(TestComponentController.class);

	@GetMapping(value = "/testComponent/{applicationId}")
	public ResponseEntity<List<LookupDTO>> GetAllTestComponentById(@PathVariable Integer applicationId) {

		logger.info("Entering @TestComponentController - GetAllTestComponentById::::");
		List<LookupDTO> testcomponentList = new ArrayList<>();
		try {
			testcomponentList = testComponentService.getTestComponentByAppId(applicationId);
		} catch (Exception e) {
			logger.error("Exception @TestComponentController - GetAllTestComponentById::::");
			e.printStackTrace();
		}
		logger.info("Completed @TestComponentController - GetAllTestComponentById::::");
		return new ResponseEntity<List<LookupDTO>>(testcomponentList, HttpStatus.OK);
	}

	@PostMapping(value = "/testComponent")
	public ResponseEntity<TestComponent> saveTestComponent(@RequestBody TestComponent testComponent) {

		logger.info("Entering @TestComponentController - saveTestComponent::::");
		TestComponent savedData = null;
		try {
			savedData = testComponentService.saveTestComponent(testComponent);
		} catch (Exception e) {
			logger.error("Exception @TestComponentController - saveTestComponent::::");
			e.printStackTrace();
		}
		logger.info("Completed @TestComponentController - saveTestComponent::::");
		return new ResponseEntity<TestComponent>(savedData, HttpStatus.OK);
	}

	@GetMapping(value = "/testComponent/mapping/{componentId}")
	public ResponseEntity<List<ComponentMapping>> fetchTestComponentMapping(@PathVariable Integer componentId) {

		logger.info("Entering @TestComponentController - fetchTestComponentMapping::::");
		List<ComponentMapping> result = new ArrayList<>();
		try {
			result = testComponentService.getComponentMapping(componentId);
		} catch (Exception e) {
			logger.error("Exception @TestComponentController - fetchTestComponentMapping::::");
			e.printStackTrace();
		}
		logger.info("Completed @TestComponentController - fetchTestComponentMapping::::");
		return new ResponseEntity<List<ComponentMapping>>(result, HttpStatus.OK);
	}

	@PostMapping(value = "/testComponent/mapping")
	public ResponseEntity<List<ComponentMapping>> saveTestComponentMapping(@RequestBody ComponentMappingDTO componentMapping) {

		logger.info("Entering @TestComponentController - saveTestComponentMapping::::");
		List<ComponentMapping> savedData = null;
		try {
			savedData = testComponentService.saveComponentMapping(componentMapping);
		} catch (Exception e) {
			logger.error("Exception @TestComponentController - saveTestComponentMapping::::");
			e.printStackTrace();
		}
		logger.info("Completed @TestComponentController - saveTestComponentMapping::::");
		return new ResponseEntity<List<ComponentMapping>>(savedData, HttpStatus.OK);
	}
}
