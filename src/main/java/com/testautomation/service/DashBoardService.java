package com.testautomation.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.math.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.testautomation.models.DashBoard;

@Service
public class DashBoardService {
	
	final static Logger logger = LoggerFactory.getLogger(TestResultsReportingService.class);
	
	@PersistenceContext
    private EntityManager em;
	
	public List<DashBoard> getTestResultsForDashboard(DashBoard searchFilter){
		
		logger.info("Entering @DashBoardService - getTestResultsForDashboard::::");
		List<DashBoard> testResult = new ArrayList<DashBoard>();
		try {
			List<DashBoard> testResults = getResultsForDashboard(searchFilter);
			List<DashBoard> testResultsForToday = getTodayResultsForDashboard();
			Iterator dashBoardresults = testResults.iterator();
			Iterator todayresults = testResultsForToday.iterator();
			DashBoard dB = new DashBoard();
			String failedData = "";
			String passedData = "";
			String month = "";
			String totalCase = "";
			String todayTestResult = "";
			Integer todayTotalTest = 0;
			while(dashBoardresults.hasNext()){
			     Object[] rowObj = (Object[]) dashBoardresults.next();
			     
			     if(rowObj[3] != null) {
			     if(totalCase != "")
			    	 totalCase = totalCase + "," +  rowObj[0].toString();
			     else
			    	 totalCase =  rowObj[0].toString();	
			     
			     if(failedData != "")
			    	 failedData = failedData + "," +  rowObj[1].toString();
			     else
			    	 failedData =  rowObj[1].toString();	
			     
			     if(passedData != "")
			    	 passedData = passedData + "," +  rowObj[2].toString();
				  else
					  passedData =  rowObj[2].toString();
			     
				 if(month != "")
			    	 month = month + "," +  rowObj[3].toString();
				 else
					 month =  rowObj[3].toString();
			     }
			}
			
			while(todayresults.hasNext()) {
			     Object[] rowObject = (Object[]) todayresults.next();
			     //todayTotalTest = todayTotalTest + ((BigInteger)rowObject[0]).intValue();
			     todayTotalTest = todayTotalTest + (((Integer)rowObject[0]));
			     if(todayTestResult != "") 
			    	 todayTestResult = todayTestResult + "," +  rowObject[0].toString();
				 else
					 todayTestResult =  rowObject[0].toString();
			}
			dB.setTotalCase(totalCase.split(","));
			dB.setFailedData(failedData.split(","));
			dB.setPassedData(passedData.split(","));
			dB.setMonth(month.split(","));
			dB.setMonth(month.split(","));
			dB.setPassFail(todayTestResult.split(","));
			dB.setTodayTotalCase(todayTotalTest.toString());
			testResult.add(dB);
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("Exception @DashBoardService - getTestResultsForDashboard");
		}
		logger.info("Exiting @DashBoardService - getTestResultsForDashboard::::");
		return testResult;
	}
	
    public List<DashBoard> getResultsForDashboard(DashBoard searchFilter){
		
		logger.info("Entering @TestResultsReportingService - getResultsForDashboard::::");
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		List<DashBoard> dashBoardDetails = null;
		StringBuffer searchQuery = null;
		try {
			if (searchFilter.getMonthDate() != null && searchFilter.getMonthDate().equals("Date")) {
				searchQuery = new StringBuffer("\r\n" + 
						"SELECT COUNT(*),COUNT(FD.tat01_test_output) as failedData, COUNT(PD.tat01_test_output) as passedData, "
						//+ "date_format(date(TC.tat01_test_start_dt),\"%M %d %Y\")  \r\n" + "" //MySql
						+ " replace(convert(varchar, TC.tat01_test_start_dt, 107),',','') as testStartDate  \r\n" + "" //SQL Server
						+ "FROM ktat01_test_result TC LEFT OUTER JOIN ktat01_test_result FD ON FD.tat01_test_output = 'F' AND FD.TAT01_TEST_RESULT_ID = TC.TAT01_TEST_RESULT_ID "
						+ "LEFT OUTER JOIN ktat01_test_result PD ON PD.tat01_test_output = 'P' AND PD.TAT01_TEST_RESULT_ID = TC.TAT01_TEST_RESULT_ID "
						+ "WHERE 1= 1 ");
			} else {
			    searchQuery = new StringBuffer("\r\n" + 
					"SELECT COUNT(*),COUNT(FD.tat01_test_output) as failedData, COUNT(PD.tat01_test_output) as passedData, "
					//+ "MONTHNAME(TC.tat01_test_start_dt)  "//MySql
					+ " DATENAME(MONTH,TC.tat01_test_start_dt) As Month "//SQL Server
					+ "FROM ktat01_test_result TC LEFT OUTER JOIN ktat01_test_result FD ON FD.tat01_test_output = 'F' AND FD.TAT01_TEST_RESULT_ID = TC.TAT01_TEST_RESULT_ID "
					+ "LEFT OUTER JOIN ktat01_test_result PD ON PD.tat01_test_output = 'P' AND PD.TAT01_TEST_RESULT_ID = TC.TAT01_TEST_RESULT_ID "
					+ "WHERE 1= 1 ");
			}
			if (searchFilter.getApplicationID() != null && searchFilter.getApplicationID() != 0) {
				searchQuery.append(" AND TC.TAM02_APPLICATION_ID IN(" + searchFilter.getApplicationID() + ")");
			}
	
			if (searchFilter.getScreenIDList() != null && searchFilter.getScreenIDList().size() > 0) {
				searchQuery.append(" AND TC.TAM03_SCREEN_ID IN(" +getIdFromListMap(searchFilter.getScreenIDList())+ ")");
			}
			
			if (searchFilter.getTestStartDate() != null && searchFilter.getTestEndDate() != null) {
				searchQuery.append(" AND TC.TAT01_TEST_START_DT BETWEEN STR_TO_DATE('" + (format1.format(searchFilter.getTestStartDate().getTime())) + "', '%d-%m-%Y %H:%i')  "
						+ " AND STR_TO_DATE('" + (format1.format(searchFilter.getTestEndDate().getTime())) + "', '%d-%m-%Y %H:%i') ");
			}
			if (searchFilter.getMonthDate() != null && searchFilter.getMonthDate().equals("Date")) {
				//searchQuery.append(" GROUP BY date(TC.tat01_test_start_dt) ORDER BY TC.tat01_test_start_dt "); //MySql
				searchQuery.append(" GROUP BY convert(varchar, TC.tat01_test_start_dt, 107)  ORDER BY convert(varchar, TC.tat01_test_start_dt, 107) "); // SQL Server
			} else {
				//searchQuery.append(" GROUP BY MONTHNAME(TC.tat01_test_start_dt) ORDER BY TC.tat01_test_start_dt ");//MySql
				searchQuery.append(" GROUP BY DATENAME(MONTH,TC.tat01_test_start_dt) ORDER BY  DATENAME(MONTH,TC.tat01_test_start_dt) ");//Sql Server
			}
			
			logger.info("Entering @DashBoardService - getResultsForDashboard::::"+searchQuery.toString());
			Query query = em.createNativeQuery(searchQuery.toString());
			dashBoardDetails =query.getResultList();
		
		}catch(Exception e) {
        	logger.error("Exception @DashBoardService - getResultsForDashboard::::");
    		e.printStackTrace();
        }
		return dashBoardDetails;	
	
	}
    
    public List<DashBoard> getTodayResultsForDashboard(){
		
		logger.info("Entering @TestResultsReportingService - getTodayResultsForDashboard::::");
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		List<DashBoard> todayResults = null;
		StringBuffer queryResult = null;
		try {
		
		    queryResult = new StringBuffer("\r\n" + 
				"SELECT count(tat01_test_output),tat01_test_output FROM ktat01_test_result \r\n" + 
				//"WHERE tat01_test_start_dt =CURDATE()\r\n" + //MySql
				"WHERE CONVERT(DATE,tat01_test_start_dt) = CONVERT(DATE, GETDATE())\r\n" + //SqlServer
				"GROUP BY tat01_test_output ORDER BY tat01_test_output desc ");
			
			logger.info("Entering @DashBoardService - getTodayResultsForDashboard::::"+queryResult.toString());
			Query query = em.createNativeQuery(queryResult.toString());
			todayResults =query.getResultList();
		
		}catch(Exception e) {
        	logger.error("Exception @DashBoardService - getTodayResultsForDashboard::::");
    		e.printStackTrace();
        }
		return todayResults;	
	
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

}
