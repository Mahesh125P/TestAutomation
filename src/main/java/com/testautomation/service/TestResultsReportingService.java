package com.testautomation.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.io.Files;
import com.testautomation.model.Application;
import com.testautomation.model.Screen;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.repositories.ApplicationRepository;
import com.testautomation.repositories.ScreenRepository;
import com.testautomation.repositories.TestResultsReportingRepository;

/**
 * @author sowmiya.r
 *
 */
@Service
public class TestResultsReportingService {

	
	@Autowired
	TestResultsReportingRepository testReportRepository;
	
	@Autowired
	ApplicationRepository appRepository;
	
	@Autowired
	ScreenRepository scrRepository;
	
	@PersistenceContext
    private EntityManager em;
	
	final static Logger logger = LoggerFactory.getLogger(TestResultsReportingService.class);
	
		
	public List<TestResultsReporting> getAllTestReports(TestResultsReporting trReport){
		
		logger.info("Entering @TestResultsReportingService - getAllTestReports::::");
		List<TestResultsReporting> testResults = null;
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			StringBuffer searchQuery = new StringBuffer(" SELECT APP.TAM02_APPLICATION_NAME,   SCR.TAM03_SCREEN_NAME,   TR.TAT01_TEST_CASE_NAME,   TR.TAT01_TESTED_BY,   TR.TAT01_TEST_INPUT,  "
					+ " TR.TAT01_TEST_OUTPUT,   DATE_FORMAT(TR.TAT01_TEST_START_DT, '%d-%m-%Y %H:%i:%s') AS TAT01_TEST_START_DT,  DATE_FORMAT(TR.TAT01_TEST_END_DT, '%d-%m-%Y %H:%i:%s') AS TAT01_TEST_END_DT "
					+ " FROM KTAT01_TEST_RESULT TR JOIN KTAM02_APPLICATION APP ON APP.TAM02_APPLICATION_ID = TR.TAM02_APPLICATION_ID JOIN KTAM03_SCREEN SCR ON SCR.TAM03_SCREEN_ID = TR.TAM03_SCREEN_ID  WHERE 1=1  ");
			
			if (trReport.getApplicationID() != null && trReport.getApplicationID() != 0) {
				searchQuery.append(" AND TR.TAM02_APPLICATION_ID IN(" + trReport.getApplicationID() + ")");
			}
	
			/*
			 * if (trReport.getScreenID() != null && trReport.getScreenID() != 0) {
			 * searchQuery.append(" AND TR.TAM03_SCREEN_ID IN(" + trReport.getScreenID() +
			 * ")"); }
			 */
			if (trReport.getScreenIDList() != null && trReport.getScreenIDList().size() > 0) {
				searchQuery.append(" AND TR.TAM03_SCREEN_ID IN(" +getIdFromListMap(trReport.getScreenIDList())+ ")");
			}
			
			if (trReport.getTestStartDate() != null && trReport.getTestEndDate() != null) {
				searchQuery.append(" AND TR.TAT01_TEST_START_DT BETWEEN STR_TO_DATE('" + (format1.format(trReport.getTestStartDate().getTime())) + "', '%d-%m-%Y %H:%i')  "
						+ " AND STR_TO_DATE('" + (format1.format(trReport.getTestEndDate().getTime())) + "', '%d-%m-%Y %H:%i') ");
			}
			
			if (trReport.getTestedByUser() != null && trReport.getTestedByUser().size() > 0) {
				searchQuery.append(" AND TR.TAT01_TESTED_BY IN(" + (convertListToString(trReport.getTestedByUser()))+ ")");
			}
			/*
			 * if (trReport.getTestedBy() != null && trReport.getTestedBy().trim().length()
			 * > 0) { searchQuery.append(" AND TR.TAT01_TESTED_BY IN(" +
			 * (convertListToString(Arrays.asList(trReport.getTestedBy().split(","))))+
			 * ")"); }
			 */
				
			if (trReport.getTestOutput() != null && trReport.getTestOutput().trim().length() > 0 && !trReport.getTestOutput().trim().equalsIgnoreCase("B")) {
				searchQuery.append(" AND TR.TAT01_TEST_OUTPUT IN('"+ trReport.getTestOutput()+ "')");
			}
			
			logger.info("Entering @TestResultsReportingService - getAllTestReports::::"+searchQuery.toString());
			Query qry = em.createNativeQuery(searchQuery.toString());
			testResults =qry.getResultList();
		
		}catch(Exception e) {
        	logger.error("Exception @TestResultsReportingService - getAllTestReports::::");
    		e.printStackTrace();
        }
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
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			List<TestResultsReporting> testResults = getAllTestReports(trReport);
			Iterator it = testResults.iterator();
			while(it.hasNext()){
			     Object[] rowObj = (Object[]) it.next();
			     TestResultsReporting trRep = new TestResultsReporting();
			     trRep.setTestRAppName(rowObj[0].toString());
			     trRep.setTestRScreenName(rowObj[1].toString());
			     trRep.setTestedCaseName(rowObj[2].toString());
			     trRep.setTestStartDate(format1.parse(rowObj[6] != null ? rowObj[6].toString() : rowObj[7].toString() ));
			     trRep.setTestEndDate(format1.parse(rowObj[7] != null ? rowObj[7].toString() : rowObj[6].toString() ));
			     trRep.setTestedBy(rowObj[3].toString());
			     trRep.setTestInputs(rowObj[4].toString());
			     trRep.setTestOutput(rowObj[5].toString() != null && rowObj[5].toString().equalsIgnoreCase("P") ? "Pass":"Fail");			     
			     testResultReports.add(trRep);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception @TestResultsReportingService - getTestReportsForExport");
		}
		logger.info("Exiting @TestResultsReportingService - getTestReportsForExport::::");
		return testResultReports;
	}
	
	public void persistTestResult(String selectedApp, HashMap<String,HashMap<String,String>> testResultMap) {
	 			
		for (Map.Entry<String, HashMap<String,String>> testResult : testResultMap.entrySet()) {
				TestResultsReporting testResultsReporting = new TestResultsReporting();
				Application app = testReportRepository.getApplicationByName(selectedApp);
				String[] screenName = testResult.getKey().split("~");
				Screen screen = testReportRepository.getScreenByName(screenName[1]);				
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
			testStartDt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(testResultDtls.get("TestStartDate"));
			testEndDt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(testResultDtls.get("TestEndDate"));
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
	
	public void persistTestResults(String userName) {
		
		try {
			Application app = testReportRepository.getApplicationById(1);
			
			TestResultsReporting testResultsReporting = new TestResultsReporting();
			testResultsReporting.setTestedCaseName("TC004");
			testResultsReporting.setTestedBy(userName);
			testResultsReporting.setTestInputs("TEST Input1");
			testResultsReporting.setTestOutput("P");	
			testResultsReporting.setApplicationID(1);
			testResultsReporting.setScreenID(1);
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
	
	public StringBuffer getIdFromListMap(ArrayList<HashMap<String,String>> maplist){
		
		StringBuffer idBuf = new StringBuffer() ;
        StringBuffer bufFinal = new StringBuffer();
		ArrayList<String> id = new ArrayList<String>();
		TreeSet<String> idSet = new TreeSet<String>();
		for(HashMap<String, String> maps : maplist){
        	//System.out.println("maps:::"+maps.get("id"));
        	idSet.add(maps.get("id"));
        }
		id.addAll(idSet);
		for(String id_s:id) {
			idBuf.append(id_s).append(",");
		}
		bufFinal.append(idBuf.subSequence(0, idBuf.length()-1));idBuf.setLength(0);
		System.out.println(bufFinal);
		return bufFinal;
	}
	
	
	public ArrayList<String> getAllTestedUsersByApp(Integer appId) {
		return testReportRepository.getAllTestedUsersByApp(appId);
	}
	 
	
	public ArrayList<LookupDTO> getAllApplicationNames() {
		return appRepository.getAllApplicationNames();
	}
	
	
	public ArrayList<LookupDTO> getAllScreensByApp(Integer appId) {
		return scrRepository.getAllScreensByApp(appId);
	}
	
	public ArrayList<Integer> getAllAppsList() {
		return appRepository.getAllAppsList();
	}
	
	public ArrayList<Integer> getAllScreensList(Integer appId) {
		return scrRepository.getAllScreensByAppList(appId);
	}

}
