package com.testautomation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.opera.core.systems.scope.protos.ScopeProtos.ServiceSelection;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.repositories.TestResultsReportingRepository;

@Service
public class TestResultsReportingService {

	
	@Autowired
	TestResultsReportingRepository testReportRepository;
	
	@PersistenceContext
    private EntityManager em;
	
	final static Logger logger = LoggerFactory.getLogger(TestResultsReportingService.class);
	
	//public List<TestResultsReporting> displayAllScreenNames(){
	public ArrayList<String> displayAllScreenNames(String applicationName){	
		ArrayList<String> screens = testReportRepository.getAllScreenNamesByApp(applicationName);
		//ArrayList<String> screens = testReportRepository.getAllDistinctScreenNames();
		//List<TestResultsReporting> screens = testReportRepository.findAll();
		
		return screens;
	}
	
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
		 
		
		StringBuffer searchQuery = new StringBuffer(" SELECT TAM02_APPLICATION_ID, TAM03_SCREEN_ID, TAT01_TEST_CASE_NAME, TAT01_TESTED_BY, TAT01_TEST_INPUT,"
				+ " TAT01_TEST_RESULT "
				+ "  FROM KTAT01_TEST_RESULT WHERE 1=1  ");
		
		if(trReport.getApplicationTestReport().getApplicationID() != null && trReport.getApplicationTestReport().getApplicationID() != 0) {
			searchQuery.append(" AND TAM02_APPLICATION_ID IN(" + trReport.getApplicationTestReport().getApplicationID() + ")");
		}
		
		if (trReport.getScreenTestReport().getScreenID() != null && trReport.getScreenTestReport().getScreenID() != 0) {
			searchQuery.append(" AND TAM03_SCREEN_ID IN(" + trReport.getScreenTestReport().getScreenID() + ")");
		}

		if (trReport.getTestFromDate() != null && trReport.getTestToDate() != null) {
			searchQuery.append(" AND TAT01_TESTED_DT BETWEEN " + trReport.getTestFromDate() + " AND " + trReport.getTestToDate());
		}

		if (trReport.getTestedBy() != null && trReport.getTestedBy().trim().length() > 0) {
			searchQuery.append(" AND TAT01_TESTED_BY IN(" + (convertListToString(Arrays.asList(trReport.getTestedBy().split(","))))+ ")");
		}

		if (trReport.getTestResults() != null && trReport.getTestResults().trim().length() > 0) {
			searchQuery.append(" AND TAT01_TEST_RESULT IN('"+ (trReport.getTestResults() != null && trReport.getTestResults().equalsIgnoreCase("Pass") ? "P"
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
			List<TestResultsReporting> testResults = getAllTestReports(trReport);
			Iterator it = testResults.iterator();
			while(it.hasNext()){
			     Object[] rowObj = (Object[]) it.next();
			     TestResultsReporting trRep = new TestResultsReporting();
			     trRep.setApplicationTestReport(trReport.getApplicationTestReport());
			     trRep.setScreenTestReport(trReport.getScreenTestReport());
			     trRep.setTestedCaseName(rowObj[2].toString());
			     trRep.setTestedBy(rowObj[3].toString());
			     trRep.setTestInputs(rowObj[4].toString());
			     trRep.setTestResults(rowObj[5].toString());
			     testResultReports.add(trRep);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Error @TestResultsReportingService - getTestReportsForExport");
		}
		logger.info("Exiting @TestResultsReportingService - getTestReportsForExport::::");
		return testResultReports;
	}
	
}
