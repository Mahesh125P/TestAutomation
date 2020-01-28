package com.testautomation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testautomation.model.Application;
import com.testautomation.model.Login;
import com.testautomation.model.Screen;
import com.testautomation.repositories.LoginRepository;

/**
 * @author Mahesh Kumar P
 *
 */

@Service
public class LoginService {

	@Autowired
	LoginRepository loginrepository;
	
	public static TreeSet<String> applicationNameList = new TreeSet<String>();		
	public static ArrayList<String> screenDetailsList = new ArrayList<String>(); 		
	public static HashMap<String,ArrayList<String>> screenDetailsMap = new HashMap<String,ArrayList<String>>();
	public static HashMap<String,HashMap<String,String>> applicationDtlsMap = new HashMap<String,HashMap<String,String>>();
	
	public boolean isaValidUser(String userName) {
		
		boolean validUser = true;
		Optional<Login> loginUser = loginrepository.findById(userName);
		if(!loginUser.isPresent()) {
			validUser = false;
		}
		return validUser;
	}
	
	
	public ArrayList<String> getApplicationNames() {
		ArrayList<String> applicationList = new ArrayList<String>();
		/*
		 * applicationList.add("VDS"); applicationList.add("ITRS");
		 */
		ArrayList<String> applicationNameList =	loginrepository.getAllApplicationNames();
		return applicationNameList;		
	}
	
	public void getApplicationDetails() {
				
		HashMap<String,String> applicationURLPathMap = new HashMap<String,String>();
		ArrayList<Screen> applicationDtlsList = new ArrayList<Screen>();
		/*
		 * applicationList.add("VDS"); applicationList.add("ITRS");
		 */
		 applicationDtlsList =	loginrepository.getApplicationDetails();
		 String currentAppName = null;
		 String prevAppName = null;
		 for(Screen screen:applicationDtlsList) {
			 currentAppName = screen.getApplication().getApplicationName();			
			 applicationNameList.add(currentAppName);			 
			 if(prevAppName!=null && currentAppName!=prevAppName) {				 
				 applicationDtlsMap.put(prevAppName, applicationURLPathMap);						 
				 screenDetailsMap.put(prevAppName, screenDetailsList);
				 applicationURLPathMap = new HashMap<String,String>();
				 screenDetailsList = new ArrayList<String>(); 
			 }
			 screenDetailsList.add(screen.getScreenName());
			 applicationURLPathMap.put("applicationURL", screen.getApplication().getApplicationURL());
			 applicationURLPathMap.put("applicationBrowser", screen.getApplication().getApplicationBrowser());
			 prevAppName = currentAppName;
		 }
		if(applicationURLPathMap!=null) {
			applicationDtlsMap.put(currentAppName, applicationURLPathMap);
			screenDetailsMap.put(prevAppName, screenDetailsList);
			applicationURLPathMap = new HashMap<String,String>();
			screenDetailsList = new ArrayList<String>(); 
		}
		//return applicationDtlsList;		
	}
	
	public ArrayList<String> getScreenNames(String applicationName) {
		
		ArrayList<String> screenNameList = new ArrayList<String>();
		/*
		 * if(applicationName.equalsIgnoreCase("VDS")) {
		 * screenNameList.add("Transfer Route Master");
		 * screenNameList.add("Compound Transfer Module"); }else {
		 * screenNameList.add("ITRS City Master");
		 * screenNameList.add("ITRS Dealer Master"); }
		 */
		
		ArrayList<String> screenNamesList =	loginrepository.getAllScreenNames(applicationName);	
		//ArrayList<String> screenNamesDtlList =	loginrepository.getAllScreenDetails(applicationName);
		return screenNamesList;
	}
	

	public ArrayList<String> getTestCaseNames(String applicationName) {
	//public HashMap<String,String> getTestCaseNames(String applicationName) {
		/*
		 * HashMap<String,String> testCaseList = new LinkedHashMap<String,String>();
		 * if(applicationName.equalsIgnoreCase("VDS")) {
		 * testCaseList.put("TH001","TH001"); testCaseList.put("TH002","TH002");
		 * testCaseList.put("TH003","TH003"); testCaseList.put("TH004","TH004"); }else {
		 * testCaseList.put("TH001","TH001"); testCaseList.put("TH002","TH002");
		 * testCaseList.put("TH003","TH003"); testCaseList.put("TH004","TH004"); }
		 */
		
		ArrayList<String> testCaseList = new ArrayList<String>();
		if(applicationName.equalsIgnoreCase("VDS")) {
			testCaseList.add("Select All");
			testCaseList.add("TH001");
			testCaseList.add("TH002");
			testCaseList.add("TH003");
			testCaseList.add("TH004");
		}else {
			testCaseList.add("Select All");
			testCaseList.add("TH005");
			testCaseList.add("TH006");
			testCaseList.add("TH007");
			testCaseList.add("TH008");
		}
		return testCaseList;
	}
	
	
	public String convertListToString(ArrayList<String> listToConvert) {
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
	
}
