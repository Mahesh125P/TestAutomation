package com.testautomation.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testautomation.model.Application;
import com.testautomation.model.Screen;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.repositories.TestResultsReportingRepository;

@Service
public class TestResultsReportingService {

	
	@Autowired
	TestResultsReportingRepository testReportRepository;
	
	
	@PersistenceContext
    private EntityManager em;
	
	final static Logger logger = LoggerFactory.getLogger(TestResultsReportingService.class);
	
	public List<TestResultsReporting> getAllTestReports(TestResultsReporting trReport){
		logger.info("Entering @TestResultsReportingService - getAllTestReports::::");
		//List<TestResultsReporting> testResults = testReportRepository.findAll();
		// failed ::using entity:

		/*
		 * StringBuffer searchQuery = new
		 * StringBuffer(" FROM TestResultsReporting WHERE 1=1  ");
		 * 
		 * if (trReport.getApplicationTestReport().getApplicationID() != null &&
		 * trReport.getApplicationTestReport().getApplicationID() != 0) {
		 * searchQuery.append(" AND applicationTestReport.applicationID IN(" +
		 * trReport.getApplicationTestReport().getApplicationID() + ")"); } if
		 * (trReport.getScreenTestReport().getScreenName() != null &&
		 * trReport.getScreenTestReport().getScreenName().trim().length() > 0) {
		 * searchQuery .append(" AND screenTestReport.screenID IN(" +
		 * trReport.getScreenTestReport().getScreenID() + ")"); }
		 * 
		 * if (trReport.getTestFromDate() != null && trReport.getTestToDate() != null) {
		 * searchQuery.append( " AND testedDate BETWEEN " + trReport.getTestFromDate() +
		 * " AND " + trReport.getTestToDate()); }
		 * 
		 * if (trReport.getTestedBy() != null && trReport.getTestedBy().trim().length()
		 * > 0) { searchQuery.append(" AND testedBy IN('" + trReport.getTestedBy() +
		 * "')"); }
		 * 
		 * if (trReport.getTestResults() != null &&
		 * trReport.getTestResults().trim().length() > 0) {
		 * 
		 * searchQuery.append(" AND testResults IN('" + (trReport.getTestResults() !=
		 * null && trReport.getTestResults().equalsIgnoreCase("Pass") ? "P" : "F") +
		 * "')"); }
		 * 
		 * Query qry = em.createQuery(searchQuery.toString());
		 */
		 
		
		StringBuffer searchQuery = new StringBuffer(" SELECT APP.TAM02_APPLICATION_NAME,   SCR.TAM03_SCREEN_NAME,   TR.TAT01_TEST_CASE_NAME,   TR.TAT01_TESTED_BY,   TR.TAT01_TEST_INPUT,  "
				+ " TR.TAT01_TEST_OUTPUT,   TR.TAT01_TEST_START_DT,   TR.TAT01_TEST_END_DT "
				+ " FROM KTAT01_TEST_RESULT TR JOIN KTAM02_APPLICATION APP ON APP.TAM02_APPLICATION_ID = TR.TAM02_APPLICATION_ID JOIN KTAM03_SCREEN SCR ON SCR.TAM03_SCREEN_ID = TR.TAM03_SCREEN_ID  WHERE 1=1  ");
		
		if (trReport.getApplicationID() != null && trReport.getApplicationID() != 0) {
			searchQuery.append(" AND TR.TAM02_APPLICATION_ID IN(" + trReport.getApplicationID() + ")");
		}

		if (trReport.getScreenID() != null && trReport.getScreenID() != 0) {
			searchQuery.append(" AND TR.TAM03_SCREEN_ID IN(" + trReport.getScreenID() + ")");
		}
		
		if (trReport.getTestFromDate() != null && trReport.getTestToDate() != null) {
			searchQuery.append(" AND TR.TAT01_TEST_START_DT BETWEEN " + trReport.getTestFromDate() + " AND " + trReport.getTestToDate());
		}
		
		if (trReport.getTestedBy() != null && trReport.getTestedBy().trim().length() > 0) {
			searchQuery.append(" AND TR.TAT01_TESTED_BY IN(" + (convertListToString(Arrays.asList(trReport.getTestedBy().split(","))))+ ")");
		}

		if (trReport.getTestOutput() != null && trReport.getTestOutput().trim().length() > 0) {
			searchQuery.append(" AND TR.TAT01_TEST_OUTPUT IN('"+ (trReport.getTestOutput() != null && trReport.getTestOutput().equalsIgnoreCase("Pass") ? "P"
							: "F")+ "')");
		}
		
		logger.info("Entering @TestResultsReportingService - getAllTestReports::::"+searchQuery.toString());
		Query qry = em.createNativeQuery(searchQuery.toString());
		List<TestResultsReporting> testResults =qry.getResultList();
		return testResults;	
	
	}
	
	
	public String convertListToString(List<String> listToConvert) {
		String finalStr = "";
		if(listToConvert!=null && listToConvert.size()>0){
		for (String s : listToConvert) {
			if (!"Select".equals(s.trim())) {
				finalStr = finalStr+"'"+s.trim()+"',";
			}
		}
		finalStr = finalStr.trim().length() > 0?finalStr.substring(0, finalStr.length()-1):finalStr;
		}
		return finalStr.trim(); 
	}
	
	
	
	public List<TestResultsReporting> getTestReportsForExport(TestResultsReporting trReport){
		
		logger.info("Entering @TestResultsReportingService - getTestReportsForExport::::");
		List<TestResultsReporting> testResultReports= new ArrayList<TestResultsReporting>();
		try {
			SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
			List<TestResultsReporting> testResults = getAllTestReports(trReport);
			Iterator it = testResults.iterator();
			while(it.hasNext()){
			     Object[] rowObj = (Object[]) it.next();
			     TestResultsReporting trRep = new TestResultsReporting();
			     trRep.setTestRAppName(rowObj[0].toString());
			     trRep.setTestRScreenName(rowObj[1].toString());
			     trRep.setTestedCaseName(rowObj[2].toString());
			     trRep.setTestedBy(rowObj[3].toString());
			     trRep.setTestInputs(rowObj[4].toString());
			     trRep.setTestOutput(rowObj[5].toString());
			     trRep.setTestFromDate(new Date());
			     trRep.setTestToDate(new Date());
			     testResultReports.add(trRep);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Error @TestResultsReportingService - getTestReportsForExport");
		}
		logger.info("Exiting @TestResultsReportingService - getTestReportsForExport::::");
		return testResultReports;
	}
	
	public void persistTestResult(String selectedApp, String selectedScreen, HashMap<String,HashMap<String,String>> testResultMap) {
		
		for (Map.Entry<String, HashMap<String,String>> testResult : testResultMap.entrySet()) {
				TestResultsReporting testResultsReporting = new TestResultsReporting();
				Application app = testReportRepository.getApplicationByName(selectedApp);
				Screen screen = testReportRepository.getScreenByName(selectedScreen);
				testResultsReporting = convertMaptoTestResultBean(app.getApplicationID(),screen.getScreenID(),testResult.getKey(),testResult.getValue());
				saveOrUpdateTestResult(testResultsReporting);				
		}
		
	}
	
	public TestResultsReporting convertMaptoTestResultBean(Integer selectedAppID, Integer selectedScreenID,String testCaseName, HashMap<String,String> testResultDtls) {
		
		TestResultsReporting testResultsReporting = new TestResultsReporting();
		testResultsReporting.setTestedCaseName(testCaseName);
		testResultsReporting.setApplicationID(selectedAppID);
		testResultsReporting.setScreenID(selectedScreenID);		
		
		Date testStartDt = null;
		Date testEndDt = null;
		try {
			testStartDt = new SimpleDateFormat("dd/MM/yyyy").parse(testResultDtls.get("TestStartDate"));
			testEndDt = new SimpleDateFormat("dd/MM/yyyy").parse(testResultDtls.get("TestEndDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		testResultsReporting.setTestInputs(testResultDtls.get("InputValue"));
		testResultsReporting.setTestOutput(testResultDtls.get("TestOutput"));
		testResultsReporting.setTestStartDate(testStartDt);
		testResultsReporting.setTestEndDate(testEndDt);
		testResultsReporting.setTestedBy(testResultDtls.get("LoggedInUser"));
		testResultsReporting.setCreatedBy(testResultDtls.get("LoggedInUser"));
		testResultsReporting.setTestFailedData(testResultDtls.get("FailedTestData"));
		return testResultsReporting;
		
	}
	
	public void saveOrUpdateTestResult(TestResultsReporting testResult) {	
		try {	
			testReportRepository.save(testResult);
			System.out.println("Test Case " + testResult.getTestedCaseName()+ " Saved Successfully!");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
	
public void persistTestResults() {
		
	try {
		Application app = testReportRepository.getApplicationById(1);
		
		TestResultsReporting testResultsReporting = new TestResultsReporting();
		testResultsReporting.setTestedCaseName("TC004");
		testResultsReporting.setTestedBy("Mahesh4");
		testResultsReporting.setTestInputs("TEST Input1");
		testResultsReporting.setTestOutput("P");	
		testResultsReporting.setApplicationID(12);
		testResultsReporting.setScreenID(35);
		//testResultsReporting.setApplicationTestReport(app);
		//testResultsReporting.setScreenTestReport(app.getScreen().get(0));
			/*
			 * ArrayList<TestResultsReporting> testResultsReportingList = new
			 * ArrayList<TestResultsReporting>();
			 * testResultsReportingList.add(testResultsReporting);
			 * app.setTestResultsReporting(testResultsReportingList);
			 */
		saveOrUpdateTestResult1(testResultsReporting);
	}catch(Exception e){
		e.printStackTrace();
		System.out.println("Error: "+e.getLocalizedMessage());
	}
		
	}
	
	public void saveOrUpdateTestResult1(TestResultsReporting testResult) {	
		try {
			testReportRepository.save(testResult);
			System.out.println("Test Case " + testResult.getTestedCaseName()+ " Saved Successfully!");
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error");
		}
	}
}
