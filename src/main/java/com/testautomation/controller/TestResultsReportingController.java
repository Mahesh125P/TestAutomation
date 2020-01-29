package com.testautomation.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.testautomation.model.Application;
import com.testautomation.model.Screen;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.service.TestResultsReportingService;

@RestController
public class TestResultsReportingController {

	@Autowired
	TestResultsReportingService testReportService;
	
		
	final static Logger logger = LoggerFactory.getLogger(TestResultsReportingController.class);
		
	@RequestMapping(value = "/populateScreensByApp")	
	public ArrayList<String> displayScreensByApp(ModelMap model,@ModelAttribute("trReport") TestResultsReporting trReport) {
		logger.info("Entering @TestResultsReportingController - displayScreensByApp::::");
		
		//trReport.getApplicationTestReport().getApplicationName();
		//logger.info("TestResultsReportingController - Application Name ::::"+trReport.getApplicationTestReport().getApplicationName());
		return testReportService.displayAllScreenNames("VDS");
	}
	
	@RequestMapping(value = "/loadTestReports")	
	public List<TestResultsReporting> getAllTestReports(ModelMap model,@ModelAttribute("trReport") TestResultsReporting trReport) {
		logger.info("Entering @TestResultsReportingController - getAllTestReports::::");
		Application app = new Application();
		app.setApplicationName("VDS");
		app.setApplicationID(1);
		Screen scr = new Screen();
		scr.setScreenName("Compound Transfer");
		//scr.setScreenID(1);
		trReport.setApplicationTestReport(app);
		trReport.setScreenTestReport(scr);
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
		trReport.setApplicationTestReport(app);
		trReport.setScreenTestReport(scr);
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
	
	
}
