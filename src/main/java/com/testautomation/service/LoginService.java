package com.testautomation.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testautomation.model.Application;
import com.testautomation.model.Login;
import com.testautomation.repositories.LoginRepository;

@Service
public class LoginService {

	@Autowired
	LoginRepository loginrepository;
	
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
	
	public ArrayList<String> getScreenNames(String applicationName) {
		
		ArrayList<String> screenNameList = new ArrayList<String>();
		/*
		 * if(applicationName.equalsIgnoreCase("VDS")) {
		 * screenNameList.add("Transfer Route Master");
		 * screenNameList.add("Compound Transfer Module"); }else {
		 * screenNameList.add("ITRS City Master");
		 * screenNameList.add("ITRS Dealer Master"); }
		 */
		
		ArrayList<String> screenNamesList =	loginrepository.getAllScreenNames();		
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
