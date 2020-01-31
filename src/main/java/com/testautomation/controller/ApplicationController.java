package com.testautomation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.testautomation.service.ApplicationService;

/**
 * @author Mahesh Kumar P
 *
 */

@RestController
public class ApplicationController {

	@Autowired
	ApplicationService applicationService;
	
	@PostMapping(value = "/saveApplication")
	public void startTest1() {  //Sample Persist
		applicationService.persistApplication();
		
	}
	
}
