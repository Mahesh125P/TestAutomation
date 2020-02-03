package com.testautomation.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.testautomation.MainTestNG;
import com.testautomation.model.Application;
import com.testautomation.model.Login;
import com.testautomation.model.Screen;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.service.LoginService;
import com.testautomation.service.TestResultsReportingService;

@RestController
public class TestResultsReportingController {

	@Autowired
	TestResultsReportingService testReportService;
	
	@Autowired
	LoginService loginservice;
	
		
	final static Logger logger = LoggerFactory.getLogger(TestResultsReportingController.class);
		
	@RequestMapping(value = "/loadTestReports")	
	public List<TestResultsReporting> getAllTestReports(ModelMap model,@ModelAttribute("trReport") TestResultsReporting trReport) {
		logger.info("Entering @TestResultsReportingController - getAllTestReports::::");
		/*
		 * Application app = new Application(); app.setApplicationName("VDS");
		 * app.setApplicationID(1); Screen scr = new Screen();
		 * scr.setScreenName("Compound Transfer"); //scr.setScreenID(1);
		 */		
		trReport.setApplicationID(1);
		trReport.setScreenID(1);
		trReport.setTestedBy("Manual,Sowmiya");
		return testReportService.getAllTestReports(trReport);
	}
	
	@RequestMapping(value = "/exportExlTestReports",produces = "application/vnd.ms-excel")
	 public ModelAndView exportTestReportsExcel() {
		
		logger.info("Entering @TestResultsReportingController - exportTestReportsExcel::::");
		TestResultsReporting trReport = new TestResultsReporting();
		Application app = new Application();
		app.setApplicationName("VDS");
		app.setApplicationID(1);
		Screen scr = new Screen();
		scr.setScreenName("Compound Transfer");
		//scr.setScreenID(1);
		trReport.setApplicationID(1);
		trReport.setScreenID(1);
		trReport.setTestedBy("Manual,Sowmiya");
		List<TestResultsReporting> testResultReports = testReportService.getTestReportsForExport(trReport);
		
		ModelAndView mv = new ModelAndView("exportExcelView", "testResultReports", testResultReports);
		mv.setViewName("exportExcelView");
		//return new ModelAndView("exportExcelView", "testResultReports", testResultReports);
		return mv;
	}
	
	
	@RequestMapping(value = "/exportTestExcel")
	public void downloadExcelTestFile(
	        HttpServletRequest request, 
	        HttpServletResponse response) throws IOException {

		logger.info("Entering @TestResultsReportingController - downloadExcelTestFile::::");
	    XSSFWorkbook wb = new XSSFWorkbook();

	    wb.createSheet("Sheet1");

	    //response.reset();
	    //response.setStatus(HttpServletResponse.SC_OK);
	    response.setContentType("application/vnd.ms-excel");
	    response.setHeader("Content-Disposition", "attachment; filename=test.xls");

	    OutputStream out = response.getOutputStream();
	     wb.write(out); 
	    
	    logger.info(" @TestResultsReportingController - downloadExcelTestFile::::");
	    out.flush();
	    out.close();
	    wb.close();
	    logger.info("Exiting @TestResultsReportingController - downloadExcelTestFile::::");
	    
	}
	
	@RequestMapping(value = "/startTest2", method = RequestMethod.GET)
	public void presistTestResults() {
		testReportService.persistTestResults();
	}
	
	@PostMapping(value = "/startTest")
	public String startTest(ModelMap model,@ModelAttribute("login") Login login) {
		System.out.println("Started startTest!!!");
		System.out.println("Selected App: "+login.getSelectedApplicationName());
		ArrayList<String> applicationList = loginservice.getApplicationNames();
		login.setSelectedApplicationName("VDS");
		ArrayList<String> screenNameList = loginservice.getScreenNames(login.getSelectedApplicationName());
		
		ArrayList<String> testCaseList = loginservice.getTestCaseNames(login.getSelectedApplicationName());
		
		System.out.println("Selected Screens String: "+loginservice.convertListToString(login.getScreenNameList()));
		
		login.setApplicationList(applicationList);
		login.setScreenNameList(screenNameList);
		login.setTestCaseList(testCaseList);
		loginservice.getApplicationDetails();
		model.addAttribute("applicationList", login.getApplicationList());
		model.addAttribute("screenNameList", login.getScreenNameList());
		model.addAttribute("testCaseList", login.getTestCaseList());
		model.addAttribute("selectedApplicationName", login.getSelectedApplicationName());
		model.addAttribute("selectedScreenName",login.getSelectedScreenName());
		System.out.println("Started executing Test!!!");
		MainTestNG testStart = new MainTestNG();
		testStart.startTest(testReportService,login.getSelectedApplicationName(),Arrays.asList(login.getSelectedScreenName().split(",")));
		//ApplicationService as = new ApplicationService();
		//as.persistApplication();
		testReportService.persistTestResults();
		System.out.println("Completed startTest!!!");
		return "homePage";
	}
	
}
