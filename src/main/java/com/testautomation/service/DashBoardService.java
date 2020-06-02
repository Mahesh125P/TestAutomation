package com.testautomation.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

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
			Iterator it = testResults.iterator();
			DashBoard dB = new DashBoard();
			String failedData = "";
			String passedData = "";
			String month = "";
			String totalCase = "";
			while(it.hasNext()){
			     Object[] rowObj = (Object[]) it.next();
			     
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
			dB.setTotalCase(totalCase.split(","));
			dB.setFailedData(failedData.split(","));
			dB.setPassedData(passedData.split(","));
			dB.setMonth(month.split(","));
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
						"SELECT COUNT(*),(SELECT COUNT(tat01_test_output)FROM ktat01_test_result FD WHERE tat01_test_output = 'F' AND date(FD.tat01_test_start_dt) = date(TC.tat01_test_start_dt) AND  FD.TAM02_APPLICATION_ID =  TC.TAM02_APPLICATION_ID AND  FD.TAM03_SCREEN_ID  = TC.TAM03_SCREEN_ID) as failedData, "
						+ "(Select count(tat01_test_output) FROM ktat01_test_result PD WHERE tat01_test_output = 'P' AND date(PD.tat01_test_start_dt) = date(TC.tat01_test_start_dt) AND PD.TAM02_APPLICATION_ID =  TC.TAM02_APPLICATION_ID AND  PD.TAM03_SCREEN_ID  = TC.TAM03_SCREEN_ID) as passedData, "
						+ "date_format(date(tat01_test_start_dt),\"%M %d %Y\")  \r\n" + ""
						+ "from ktat01_test_result TC WHERE 1= 1 ");
			} else {
			    searchQuery = new StringBuffer("\r\n" + 
					"SELECT COUNT(*),(SELECT COUNT(tat01_test_output)FROM ktat01_test_result FD WHERE tat01_test_output = 'F' AND MONTHNAME(FD.tat01_test_start_dt) = MONTHNAME(TC.tat01_test_start_dt) AND  FD.TAM02_APPLICATION_ID =  TC.TAM02_APPLICATION_ID AND  FD.TAM03_SCREEN_ID  = TC.TAM03_SCREEN_ID) as failedData, "
					+ "(Select count(tat01_test_output) FROM ktat01_test_result PD WHERE tat01_test_output = 'P' AND MONTHNAME(PD.tat01_test_start_dt) = MONTHNAME(TC.tat01_test_start_dt) AND PD.TAM02_APPLICATION_ID =  TC.TAM02_APPLICATION_ID AND  PD.TAM03_SCREEN_ID  = TC.TAM03_SCREEN_ID) as passedData, "
					+ "MONTHNAME(tat01_test_start_dt)  "
					+ "from ktat01_test_result TC WHERE 1= 1 ");
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
				searchQuery.append(" GROUP BY date(tat01_test_start_dt) ORDER BY tat01_test_start_dt ");
			} else {
				searchQuery.append(" GROUP BY MONTHNAME(tat01_test_start_dt) ORDER BY tat01_test_start_dt ");
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
