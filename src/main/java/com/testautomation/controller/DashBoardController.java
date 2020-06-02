package com.testautomation.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testautomation.model.TestResultsReporting;
import com.testautomation.models.DashBoard;
import com.testautomation.repositories.TestResultsReportingRepository;
import com.testautomation.service.DashBoardService;

@RestController
public class DashBoardController {
	
	final static Logger logger = LoggerFactory.getLogger(DashBoardController.class);
	
	@Autowired
	DashBoardService dashBoardService;
    
	 @RequestMapping(value = "/loadDashBoardDetails", method = RequestMethod.POST)
	    public List<DashBoard> getDashBoardDetails(@RequestBody(required = false) DashBoard searchFilter) {
	    	
	    	logger.info("Entering @DashBoardController - getDashBoardDetails::::");
	    	List<DashBoard> searchResults = null; 
	    	try {
	    		searchResults = dashBoardService.getTestResultsForDashboard(searchFilter);
	    	}catch(Exception e) {
	    		logger.error("Exception @DashBoardController - getDashBoardDetails::::");
	    		e.printStackTrace();
	    	}
			logger.info("Exiting @DashBoardController - getDashBoardDetails::::");
			return searchResults;
		}

}
