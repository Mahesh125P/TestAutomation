package com.testautomation.service;

import java.util.ArrayList;
import java.util.HashMap;
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
	public static Login currentUser = new Login();
	
	public boolean isaValidUser(String userName) {
		
		boolean validUser = true;
		Optional<Login> loginUser = loginrepository.findById(userName);
		if(!loginUser.isPresent()) {
			validUser = false;
		} else {
			currentUser = loginUser.get();			
		}
		return validUser;
	}
	
	
	public ArrayList<String> getApplicationNames() {
		ArrayList<String> applicationNameList =	loginrepository.getAllApplicationNames();
		return applicationNameList;		
	}
	
	public void getApplicationDetails() {
		applicationNameList = new TreeSet<String>();		
		screenDetailsList = new ArrayList<String>(); 		
		screenDetailsMap = new HashMap<String,ArrayList<String>>();
		applicationDtlsMap = new HashMap<String,HashMap<String,String>>();
		HashMap<String,String> applicationURLPathMap = new HashMap<String,String>();
		ArrayList<Application> applicationDtlsList = new ArrayList<Application>();
		applicationDtlsList =	loginrepository.getApplicationDetails();
		/*
		 * String currentAppName = null; String prevAppName = null; for(Application
		 * application:applicationDtlsList) { currentAppName =
		 * application.getApplicationName(); applicationNameList.add(currentAppName);
		 * if(prevAppName!=null && currentAppName!=prevAppName) {
		 * applicationDtlsMap.put(prevAppName, applicationURLPathMap);
		 * screenDetailsMap.put(prevAppName, screenDetailsList); applicationURLPathMap =
		 * new HashMap<String,String>(); screenDetailsList = new ArrayList<String>(); }
		 * //screenDetailsList.add(screen.getScreenName());
		 * screenDetailsList.add("Compound Transfer");
		 * screenDetailsList.add("Transfer Route");
		 * applicationURLPathMap.put("applicationURL", application.getApplicationURL());
		 * applicationURLPathMap.put("applicationBrowser",
		 * application.getApplicationBrowser()); prevAppName = currentAppName; }
		 * if(applicationURLPathMap!=null) { applicationDtlsMap.put(currentAppName,
		 * applicationURLPathMap); screenDetailsMap.put(prevAppName, screenDetailsList);
		 * applicationURLPathMap = new HashMap<String,String>(); screenDetailsList = new
		 * ArrayList<String>(); }
		 */
		String AppName = null;
		for(Application application:applicationDtlsList) {
			AppName = application.getApplicationName();
			applicationNameList.add(AppName);	
			for(Screen screen: application.getScreen()) {
				screenDetailsList.add(screen.getScreenName());
				
			}
			screenDetailsMap.put(AppName, screenDetailsList);
			applicationURLPathMap.put("applicationURL", application.getApplicationURL());
			applicationURLPathMap.put("applicationBrowser", application.getApplicationBrowser());
			applicationDtlsMap.put(AppName, applicationURLPathMap);	
			screenDetailsList = new ArrayList<String>(); 
			applicationURLPathMap = new HashMap<String,String>();
		}		
	}
	
	
	public HashMap<String,ArrayList<String>> getAllScreenDetails(){
		ArrayList<Application> applicationDtlsList = loginrepository.getApplicationDetails();
		HashMap<String,ArrayList<String>> screenDetailsMapNew = new HashMap<String,ArrayList<String>>();
		ArrayList<String> screenDetailsListNew = new ArrayList<String>();
		String AppName = null;
		for(Application application:applicationDtlsList) {
			AppName = application.getApplicationName();
			for(Screen screen: application.getScreen()) {
				screenDetailsListNew.add(screen.getScreenName());			
			}
			screenDetailsMapNew.put(AppName, screenDetailsListNew);
			screenDetailsListNew = new ArrayList<String>();
		}
		return screenDetailsMapNew;
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
		
		ArrayList<String> screenNamesList =	null;//loginrepository.getAllScreenNames(applicationName);	
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
