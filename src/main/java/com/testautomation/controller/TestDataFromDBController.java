package com.testautomation.controller;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.testautomation.model.TestAutomationModel;
import com.testautomation.service.LoginService;
import com.testautomation.service.LookupDTO;
import com.testautomation.service.ResponseDTO;
import com.testautomation.service.TestDataFromDBService;
import com.testautomation.service.TestResultsReportingService;
import com.testautomation.service.UserApplicationMappingService;

@RestController
public class TestDataFromDBController {

	@Autowired
    TestResultsReportingService testReportService;
	
	@Autowired
	TestDataFromDBService testDataFromDbService;
	
	@Autowired
	LoginService loggedUserDetails;
	
	@Autowired
	UserApplicationMappingService usermapping;
	
	final static Logger logger = LoggerFactory.getLogger(TestDataFromDBController.class);
	
	
	@RequestMapping(value ="/loadTestDataFromDBDetails/{userName}")
    public TestAutomationModel loadTestDataFromDBDetails(@PathVariable String userName) {
        
    	logger.info("Entering @TestDataFromDBController - loadTestDataFromDBDetails::::");
    	TestAutomationModel tAModel = null;
		try {
	    	
			//ArrayList<LookupDTO> testAppsList = usermapping.getAppsByUser(loggedUserDetails.currentUser.getUserName());//testReportService.getAllApplicationNames();
	    	
			//UserApp Mapping changes::::::::::
			ArrayList<LookupDTO> testAppsList = usermapping.getAllAppsByUserDTO(userName);//(loggedUserDetails.currentUser.getUserName());//testReportService.getAllApplicationNames();
			ArrayList<LookupDTO> testScreensList = testReportService.getAllScreensByApp(testAppsList.get(0).getId() );//appsList.get(0)
			
			//ArrayList<Integer> appsList = testReportService.getAllAppsList();
			//ArrayList<LookupDTO> testAppsList = testReportService.getAllApplicationNames();
			//ArrayList<LookupDTO> testScreensList = testReportService.getAllScreensByApp(testAppsList.get(0).getId());
			
	    	   
	        tAModel = new TestAutomationModel();
	        tAModel.testAppsList = testAppsList;
	        tAModel.testScreensList = testScreensList;
		}catch(Exception e) {
        	logger.error("Exception @TestDataFromDBController - loadTestDataFromDBDetails::::");
    		e.printStackTrace();
        }
		logger.info("Exiting @TestDataFromDBController - loadTestDataFromDBDetails::::");
        return tAModel;
    }
	
	@GetMapping(value ="/loadScreensForApps/{applicationId}/{userName}")
    public TestAutomationModel reloadTestScreensForApps(@PathVariable Integer applicationId, @PathVariable String userName) {
        
    	logger.info("Entering @TestDataFromDBController - reloadTestScreensForApps::::");
    	TestAutomationModel tAModel = null;
		try {

			//UserApp Mapping changes::::::::::
			ArrayList<LookupDTO> testAppsList = usermapping.getAllAppsByUserDTO(userName);//(loggedUserDetails.currentUser.getUserName());//testReportService.getAllApplicationNames();
			
			//ArrayList<LookupDTO> testAppsList = testReportService.getAllApplicationNames();
			ArrayList<LookupDTO> testScreensList = testReportService.getAllScreensByApp(applicationId);
			String searchResults = testDataFromDbService.getQueryDetails(testScreensList.get(0).getId());
			
			
	        tAModel = new TestAutomationModel();
	        tAModel.testAppsList = testAppsList;
	        tAModel.testScreensList = testScreensList;
	        tAModel.screenQuery = searchResults;
	        tAModel.screenID_App = testScreensList.get(0).getId().toString();
	    }catch(Exception e) {
        	logger.error("Exception @TestDataFromDBController - reloadTestScreensForApps::::");
    		e.printStackTrace();
        }
        logger.info("Exiting @TestDataFromDBController - reloadTestScreensForApps::::");
        return tAModel;
    }
	
	@GetMapping(value = "/getScreenQuery/{screenID}")
    public TestAutomationModel getScreenQueryFromDB(@PathVariable Integer screenID) {
		
    	logger.info("Entering @TestDataFromDBController - getScreenQueryFromDB::::");
    	String searchResults = null;
    	TestAutomationModel tAModel = null;
    	try {
    		searchResults = testDataFromDbService.getQueryDetails(screenID);
    		tAModel = new TestAutomationModel();
    		tAModel.screenQuery = searchResults;
    	}catch(Exception e) {
    		logger.error("Exception @TestDataFromDBController - getScreenQueryFromDB::::");
    		e.printStackTrace();
    	}
		logger.info("Exiting @TestDataFromDBController - getScreenQueryFromDB::::");
		return tAModel;
	}
	
	
	@PostMapping(value = "/updateScreenQuery")
	public ResponseEntity<ResponseDTO> updateScreenQuery1(@RequestParam("screenAppID") String screen_id,
			@RequestParam("screenQuery") String screenQuery) {
		logger.info("Entering @TestDataFromDBController - updateScreenQuery1::::");
		ResponseDTO response = new ResponseDTO();
		logger.info("@TestDataFromDBController - updateScreenQuery1::::"+screen_id);
		boolean isFlag = testDataFromDbService.saveQueryDetails(Integer.parseInt(screen_id), screenQuery);
		if (!isFlag) {
			response.setStatus("error");
		} else {
			response.setStatus("success");
		}
		logger.info("Exiting @TestDataFromDBController - updateScreenQuery1::::"+response.getStatus());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateScreenQuery1", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> updateScreenQuery(@RequestParam("screenAppID") String screen_id,
			@RequestParam("screenQuery") String screenQuery) {
		ResponseDTO response = new ResponseDTO();
		try {
			boolean isFlag = testDataFromDbService.saveQueryDetails(Integer.parseInt(screen_id), screenQuery);
			if (isFlag) {
				response.setStatus("error");
			} else {
				response.setStatus("success");
			}
		} catch (IllegalStateException | NumberFormatException   e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
