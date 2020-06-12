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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.testautomation.MainTestNG;
import com.testautomation.model.Login;
import com.testautomation.model.TestAutomationModel;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.repositories.ComponentMappingRepository;
import com.testautomation.repositories.TestComponentRepository;
import com.testautomation.service.DataFromDatabaseService;
import com.testautomation.service.LoginService;
import com.testautomation.service.LookupDTO;
import com.testautomation.service.TestResultsReportingService;
import com.testautomation.service.UserApplicationMappingService;

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
    
    @Autowired
    DataFromDatabaseService dataFromDbService;
    
    @Autowired
    ComponentMappingRepository componentMappingRepository;    
    
    @Autowired
	UserApplicationMappingService userAppService;
	
	@Autowired
	LoginService loggedUserDetails;
    
	@Autowired
	TestComponentRepository testComponentRepository;
    
    final static Logger logger = LoggerFactory.getLogger(TestResultsReportingController.class);
    
    
    @RequestMapping(value = "/loadTestReports", method = RequestMethod.POST)
    public List<TestResultsReporting> getAllTestReports(@RequestBody(required = false) TestResultsReporting trReport) {
    	
    	logger.info("Entering @TestResultsReportingController - getAllTestReports::::");
    	List<TestResultsReporting> searchResults = null; 
    	try {
    		searchResults = testReportService.getTestReportsForExport(trReport);
    	}catch(Exception e) {
    		logger.error("Exception @TestResultsReportingController - getAllTestReports::::");
    		e.printStackTrace();
    	}
		logger.info("Exiting @TestResultsReportingController - getAllTestReports::::");
		return searchResults;
	}
	
    
	/*
	 * @RequestMapping(value = "/startTest2", method = RequestMethod.GET) public
	 * void presistTestResults() { testReportService.persistTestResults(); }
	 */
	
	@PostMapping(value = "/startTest")
	public String startTest(ModelMap model,@ModelAttribute("login") Login login) {
		System.out.println("Started startTest!!!");
		System.out.println("Selected App: "+login.getSelectedApplicationName());
		
		logger.info("isDataFromDBCheckbox(): UserName:::::::::"+login.getDataFromDBCheckbox()+login.getUserName());
				
		ArrayList<String> applicationList = loginservice.getApplicationNames();
		//login.setSelectedApplicationName("VDS");
		ArrayList<String> screenNameList = loginservice.getScreenNames(login.getSelectedApplicationName());
		
		ArrayList<String> testCaseList = loginservice.getTestCaseNames(login.getSelectedApplicationName());
		
		System.out.println("Selected Screens String: "+loginservice.convertListToString(login.getScreenNameList()));
		List<String> selectedScreenList = null;
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
		String testComponentName = "";
		MainTestNG testStart = new MainTestNG();
		if(login.getDataFromDBCheckbox().equalsIgnoreCase("true")) {
			dataFromDbService.setuserNDataFromDBMap(login.getUserName(),"Yes");
			dataFromDbService.doCopyFileToDbData(login.getUserName(),login.getSelectedApplicationName(),Arrays.asList(login.getSelectedScreenName().split(",")));
		} else {
			dataFromDbService.setuserNDataFromDBMap(login.getUserName(),"No");
		}
		
		if(login.getSelectedComponentID()!= null && !login.getSelectedComponentID().isEmpty() &&  !login.getSelectedComponentID().equals("Choose Component")) {
			selectedScreenList = componentMappingRepository.findScreenNameByComponentId(Integer.parseInt(login.getSelectedComponentID()));
			testComponentName = testComponentRepository.findComponentName(Integer.parseInt(login.getSelectedComponentID()));
		}
		else
		selectedScreenList = Arrays.asList(login.getSelectedScreenName().split(","));
		
		testStart.startTest(testReportService,login.getSelectedApplicationName(),selectedScreenList,testComponentName,dataFromDbService);
		//testStart.startTest(testReportService,login.getSelectedApplicationName(),Arrays.asList(login.getSelectedScreenName().split(",")));
		//ApplicationService as = new ApplicationService();
		//as.persistApplication();
		//testReportService.persistTestResults(login.getUserName());
		System.out.println("Completed startTest!!!");
		return "homePage";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downloadTestReportExcel", method = RequestMethod.POST)
    public void downloadExcelTestResults(HttpServletRequest request, 
            HttpServletResponse response,@RequestBody(required = false) TestResultsReporting trReport) throws IOException {
 
        logger.info("Entering @TestResultsReportingController - downloadExcelTestResults::::");
        
        OutputStream out = null;
        XSSFWorkbook wb = null;
        try {
	        List<TestResultsReporting> testResultReports = testReportService.getTestReportsForExport(trReport);
	        
	        LocalDateTime localDate = LocalDateTime.now();      
	        ZonedDateTime zdt = localDate.atZone(ZoneId.systemDefault());
	        Date output = Date.from(zdt.toInstant());
	        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");       
	        String outputDate = format2.format(output);
	        
	        String fileName = "TestResultReports-" + outputDate.toString() + ".xlsx"; 
	 
	        response.setContentType("application/vnd.ms-excel");
	        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	        wb = new XSSFWorkbook();
	 
	        Sheet sheet = wb.createSheet("TestResults_Report");
	        
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("Application");
	        header.createCell(1).setCellValue("Screen");
	        header.createCell(2).setCellValue("TestCase");
	        header.createCell(3).setCellValue("Tested From");
	        header.createCell(4).setCellValue("Tested To");
	        header.createCell(5).setCellValue("TestedBy");
	        header.createCell(6).setCellValue("TestInput");
	        header.createCell(7).setCellValue("TestOutput");
	        
	        
	        // Create data cells
	        int rowCount = 1;
	        if(testResultReports != null && testResultReports.size() > 0) {
	            for (TestResultsReporting resultReport : testResultReports){
	                Row reportRow = sheet.createRow(rowCount++);
	                reportRow.createCell(0).setCellValue(resultReport.getTestRAppName());
		            reportRow.createCell(1).setCellValue(resultReport.getTestRScreenName());
		            reportRow.createCell(2).setCellValue(resultReport.getTestedCaseName());
		            
		            Cell cell3 = reportRow.createCell(3);
		            CellStyle cellStyle3 = wb.createCellStyle();
		            CreationHelper createHelper3 = wb.getCreationHelper();
		            cellStyle3.setDataFormat(createHelper3.createDataFormat().getFormat("dd-mm-yyyy hh:mm:ss"));
		            cell3.setCellValue(resultReport.getTestStartDate());
		            cell3.setCellStyle(cellStyle3);
		            
		            Cell cell4 = reportRow.createCell(4);
		            CellStyle cellStyle4 = wb.createCellStyle();
		            CreationHelper createHelper4 = wb.getCreationHelper();
		            cellStyle4.setDataFormat(createHelper4.createDataFormat().getFormat("dd-mm-yyyy hh:mm:ss"));
		            cell4.setCellValue(resultReport.getTestEndDate());
		            cell4.setCellStyle(cellStyle4);
		            //reportRow.createCell(4).setCellValue(resultReport.getTestToDate());
		            
		            reportRow.createCell(5).setCellValue(resultReport.getTestedBy());
		            reportRow.createCell(6).setCellValue(resultReport.getTestInputs());
		            reportRow.createCell(7).setCellValue(resultReport.getTestOutput() != null && (resultReport.getTestOutput().equalsIgnoreCase("P") || resultReport.getTestOutput().equalsIgnoreCase("Pass") )? "Pass" :"Fail");
	            }
	        }
	        
	        out = response.getOutputStream();
	        wb.write(out);  
        }catch(Exception e) {
        	logger.error("Exception @TestResultsReportingController - downloadExcelTestResults::::");
    		e.printStackTrace();
        } finally {
        	out.flush();
            out.close();
            wb.close();
        }
        
        logger.info("Exiting @TestResultsReportingController - downloadExcelTestResults::::");
        
    }
    
    
    @RequestMapping(value ="/loadTestReportDetails/{userName}")
    public TestAutomationModel loadTestReports(@PathVariable String userName) {
        
    	logger.info("Entering @TestResultsReportingController - loadTestReports::::");
    	TestAutomationModel tAModel = null;
		try {
	    	
	    	ArrayList<LookupDTO> testAppsList = userAppService.getAllAppsByUserDTO(userName);
	    	ArrayList<LookupDTO> testScreensList = testReportService.getAllScreensByApp(testAppsList.get(0).getId() ); 
	    	ArrayList<String> allTestedUsers = testReportService.getAllTestedUsersByApp(testAppsList.get(0).getId() );       
	    	
	    	//ArrayList<Integer> appsList = testReportService.getAllAppsList();
	    	//ArrayList<LookupDTO> testAppsList = testReportService.getAllApplicationNames();
	    	//ArrayList<LookupDTO> testScreensList = testReportService.getAllScreensByApp(appsList.get(0));
	    	//ArrayList<String> allTestedUsers = testReportService.getAllTestedUsersByApp(appsList.get(0));       
	        
	        tAModel = new TestAutomationModel();
	        tAModel.testUsersList = allTestedUsers;
	        tAModel.testAppsList = testAppsList;
	        tAModel.testScreensList = testScreensList;
		}catch(Exception e) {
        	logger.error("Exception @TestResultsReportingController - loadTestReports::::");
    		e.printStackTrace();
        }
		logger.info("Exiting @TestResultsReportingController - loadTestReports::::");
        return tAModel;
    }
    
    
    @GetMapping(value ="/reloadloadTestReportDetails/{userName}/{applicationId}")
    public TestAutomationModel reloadTestReports(@PathVariable String userName,@PathVariable Integer applicationId) {
        
    	logger.info("Entering @TestResultsReportingController - reloadTestReports::::");
    	TestAutomationModel tAModel = null;
		try {

			ArrayList<LookupDTO> testAppsList = userAppService.getAllAppsByUserDTO(userName);
	    	
			//ArrayList<LookupDTO> testAppsList = testReportService.getAllApplicationNames();
			ArrayList<LookupDTO> testScreensList = testReportService.getAllScreensByApp(applicationId);
			ArrayList<String> allTestedUsers = testReportService.getAllTestedUsersByApp(applicationId);       
	        
	        tAModel = new TestAutomationModel();
	        tAModel.testUsersList = allTestedUsers;
	        tAModel.testAppsList = testAppsList;
	        tAModel.testScreensList = testScreensList;
		}catch(Exception e) {
        	logger.error("Exception @TestResultsReportingController - reloadTestReports::::");
    		e.printStackTrace();
        }
        logger.info("Exiting @TestResultsReportingController - reloadTestReports::::");
        return tAModel;
    }
    
 }
