package com.testautomation.model;

import java.util.ArrayList;
import java.util.TreeSet;

import com.testautomation.service.LookupDTO;

public class TestAutomationModel {

	public TreeSet<String> applicationNameList = new TreeSet<String>();	
	public ArrayList<String> screenDetailsList = new ArrayList<String>();
	
	public ArrayList<String> testReportColumnsList = new ArrayList<String>();
	public ArrayList<LookupDTO> testAppsList = new ArrayList<LookupDTO>();
	public ArrayList<LookupDTO> testScreensList = new ArrayList<LookupDTO>();
	public ArrayList<String> testUsersList = new ArrayList<String>();
	public ArrayList<String> applicationsList = new ArrayList<String>();
	public ArrayList<String> errorUsersList = new ArrayList<String>();
	public ArrayList<String> errorApplicationList = new ArrayList<String>();
	
	
	public String screenQuery = new String();
	public String screenID_App = new String();
	
}
