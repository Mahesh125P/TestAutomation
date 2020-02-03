package com.testautomation.controller;
 
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.testautomation.MainTestNG;
import com.testautomation.model.Application;
import com.testautomation.model.Login;
import com.testautomation.model.Screen;
import com.testautomation.model.TestAutomationModel;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.service.LoginService;
import com.testautomation.service.LookupDTO;
import com.testautomation.service.TestResultsReportingService;
 
/**
 * @author sowmiya.r
 *
 */
 
@RestController
public class TestResultsReportingController {

	@Autowired
	TestResultsReportingService testReportService;
	
	@Autowired
	LoginService loginservice;
	
		
	final static Logger logger = LoggerFactory.getLogger(TestResultsReportingController.class);
		
	 @RequestMapping(value = "/loadTestReports", method = RequestMethod.POST)    
	 public List<TestResultsReporting> getAllTestReports(ModelMap model,@RequestBody(required = false) TestResultsReporting trReport) { 
		logger.info("Entering @TestResultsReportingController - getAllTestReports::::");
		/*
		 * Application app = new Application(); app.setApplicationName("VDS");
		 * app.setApplicationID(1); Screen scr = new Screen();
		 * scr.setScreenName("Compound Transfer"); //scr.setScreenID(1);
		 */		
		/*
		 * trReport.setApplicationID(1); trReport.setScreenID(1);
		 * trReport.setTestedBy("Manual,Sowmiya");
		 */
		return testReportService.getAllTestReports(trReport);
	}
	
    @RequestMapping(value = "/exportExlTestReports",produces = "application/vnd.ms-excel")
	 public ModelAndView exportTestReportsExcel() {
		
		logger.info("Entering @TestResultsReportingController - exportTestReportsExcel::::");
		TestResultsReporting trReport = new TestResultsReporting();
		/*
		 * Application app = new Application(); app.setApplicationName("VDS");
		 * app.setApplicationID(1); Screen scr = new Screen();
		 * scr.setScreenName("Compound Transfer");
		 */
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
	
    public void downloadExcelTestResults(
            HttpServletRequest request, 
            HttpServletResponse response) throws IOException {
 
        logger.info("Entering @TestResultsReportingController - downloadExcelTestFile::::");
                
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
        
        LocalDateTime localDate = LocalDateTime.now();      
        ZonedDateTime zdt = localDate.atZone(ZoneId.systemDefault());
        Date output = Date.from(zdt.toInstant());
        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");       
        String outputDate = format2.format(output);
        
        //String fileName = "TestResultReports-" + localDate.toString() + ".xlsx"; 
        String fileName = "TestResultReports-" + outputDate.toString() + ".xlsx"; 
 
         //response.reset();
        //response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/vnd.ms-excel");
       // response.setHeader("Content-Disposition", "attachment; filename=test.xls");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        XSSFWorkbook wb = new XSSFWorkbook();
 
        Sheet sheet = wb.createSheet("TestResults_Report");
        
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Application");
        header.createCell(1).setCellValue("Screen");
        header.createCell(2).setCellValue("TestCase");
        header.createCell(3).setCellValue("Tested From");
        header.createCell(4).setCellValue("Tested To");
        header.createCell(5).setCellValue("TestedBy");
        header.createCell(6).setCellValue("TestInput");
        header.createCell(7).setCellValue("TestResult Output");
        
        // Create data cells
        int rowCount = 1;
        if(testResultReports != null && testResultReports.size() > 0) {
            logger.info("Entering @ExportXlsView - resultReports.size()::::"+testResultReports.size());
            for (TestResultsReporting resultReport : testResultReports){
                logger.info("Entering @ExportXlsView - resultReport::::"+resultReport);
                Row reportRow = sheet.createRow(rowCount++);
                reportRow.createCell(0).setCellValue(resultReport.getTestRAppName());
	            reportRow.createCell(1).setCellValue(resultReport.getTestRScreenName());
	            reportRow.createCell(2).setCellValue(resultReport.getTestedCaseName());
	            reportRow.createCell(3).setCellValue(resultReport.getTestFromDate());
	            reportRow.createCell(4).setCellValue(resultReport.getTestToDate());
	            reportRow.createCell(5).setCellValue(resultReport.getTestedBy());
	            reportRow.createCell(6).setCellValue(resultReport.getTestInputs());
	            reportRow.createCell(7).setCellValue(resultReport.getTestOutput() != null && resultReport.getTestOutput().equalsIgnoreCase("P") ? "Pass" :"Fail");
            }
        }
        
        OutputStream out = response.getOutputStream();
        wb.write(out); 
        
        logger.info(" @TestResultsReportingController - downloadExcelTestFile::::");
        out.flush();
        out.close();
        wb.close();
        logger.info("Exiting @TestResultsReportingController - downloadExcelTestFile::::");
        
    }
    
    
    @RequestMapping(value ="/loadtestDetails")
    public TestAutomationModel loadTestReports() {
        
        ArrayList<LookupDTO> testAppsList = testReportService.getAllApplicationNames();
        ArrayList<String> allTestedUsers = testReportService.getAllTestedUsersByApp(1);
        ArrayList<LookupDTO> testScreensList= testReportService.getAllScreensByApp(1);
        
        TestAutomationModel tm = new TestAutomationModel();
        tm.testUsersList = allTestedUsers;
        tm.testAppsList = testAppsList;
        tm.testScreensList = testScreensList;
       // tm.testReportColumnsList = testReportService.reportHeaderColumnsList;
        
        return tm;
    }
    
    @RequestMapping(value ="/loadtestDetails1")
    public ArrayList<LookupDTO> loadAppNames() {    	
    	return testReportService.getAllApplicationNames();
    }
}
