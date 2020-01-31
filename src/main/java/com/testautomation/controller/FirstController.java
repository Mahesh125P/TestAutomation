package com.testautomation.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.testautomation.MainTestNG;
import com.testautomation.model.Login;
import com.testautomation.model.TestAutomationModel;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.service.ApplicationService;
import com.testautomation.service.LoginService;
import com.testautomation.service.TestResultsReportingService;
import com.testautomation.util.EmailUtil;


/**
 * @author Mahesh Kumar P
 *
 */

@Controller
public class FirstController {
	
	@Autowired
	LoginService loginservice;
	
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		return "welcome";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		Login login = new Login();
		model.addAttribute("login", login);
		return "login";
	}
	
	@RequestMapping(value = "/loginSubmit", method = RequestMethod.GET)
	public TestAutomationModel LoginSubmit(ModelMap model,@ModelAttribute("login") Login login) {
		System.out.println("Started LoginSubmit!!!");
		System.out.println("Username:"+login.getUserName() + "Password:  "+login.getPassword());
		System.out.println("111");
		System.out.println(login.getSelectedApplicationName());
		loginservice.getApplicationDetails();
		TreeSet<String> applicationList = LoginService.applicationNameList;
		ArrayList<String> screenNameList = LoginService.screenDetailsMap.get(applicationList.first());
		
		TestAutomationModel testAutomationModel = new TestAutomationModel();
		testAutomationModel.applicationNameList = applicationList;
		testAutomationModel.screenDetailsList = screenNameList;
		System.out.println("login.getApplicationName()" + login.getApplicationList());
		System.out.println("Completed LoginSubmit!!!");
		return testAutomationModel;
	}
	
	@RequestMapping(value = "/loginSubmit1", method = RequestMethod.POST)
	public String LoginSubmit1(ModelMap model,@ModelAttribute("login") Login login) {
		System.out.println("Started LoginSubmit!!!");
		System.out.println("Username:"+login.getUserName() + "Password:  "+login.getPassword());
		if(!loginservice.isaValidUser(login.getUserName())){
			model.put("errorMessage", "Invalid Credentials");
			return "login";
		}
		System.out.println("111");
		System.out.println(login.getSelectedApplicationName());
		model.addAttribute("userName", login.getUserName());
		loginservice.getApplicationDetails();
		TreeSet<String> applicationList = LoginService.applicationNameList;
		ArrayList<String> screenNameList = LoginService.screenDetailsMap.get(applicationList.first());	
		System.out.println("login.getApplicationName()" + login.getApplicationList());
		
		model.addAttribute("screenNameList", screenNameList);
		model.addAttribute("testCaseList", login.getTestCaseList());
		model.addAttribute("selectedApplicationName", "VDS");
		model.addAttribute("applicationList", applicationList);
		model.addAttribute("login", login);
		
		System.out.println("Completed LoginSubmit!!!");
		return "homePage";
	}
	
	@RequestMapping(value = "/homeSubmit", method = RequestMethod.GET)
	public String HomeSubmit(ModelMap model,@ModelAttribute("login") Login login) {
		System.out.println("Started HomePage!!!");
		System.out.println("Selected App: "+login.getSelectedApplicationName());
		
		ArrayList<String> screenNameList = loginservice.getScreenNames(login.getSelectedApplicationName());
		ArrayList<String> testCaseList = loginservice.getTestCaseNames(login.getSelectedApplicationName());
		
		login.setScreenNameList(screenNameList);
		login.setTestCaseList(testCaseList);
		
		model.addAttribute("screenNameList", login.getScreenNameList());
		model.addAttribute("testCaseList", login.getTestCaseList());
		model.addAttribute("selectedApplicationName", login.getSelectedApplicationName());
		
		System.out.println("Completed HomePage!!!");
		return "homePage";
	}
	
	@RequestMapping(value = "/Test2", method = RequestMethod.GET)
	public String startTest(ModelMap model,@ModelAttribute("login") Login login) {
		System.out.println("Started startTest!!!");
		System.out.println("Selected App: "+login.getSelectedApplicationName());
		ArrayList<String> applicationList = loginservice.getApplicationNames();
		login.setSelectedApplicationName("VDS");
		ArrayList<String> screenNameList = loginservice.getScreenNames(login.getSelectedApplicationName());
		
		ArrayList<String> testCaseList = loginservice.getTestCaseNames(login.getSelectedApplicationName());
		
		System.out.println("Selected Screens String: "+loginservice.convertListToString(login.getScreenNameList()));
		
		login.setApplicationList(applicationList);
		login.setScreenNameList(screenNameList);
		login.setTestCaseList(testCaseList);
		
		model.addAttribute("applicationList", login.getApplicationList());
		model.addAttribute("screenNameList", login.getScreenNameList());
		model.addAttribute("testCaseList", login.getTestCaseList());
		model.addAttribute("selectedApplicationName", login.getSelectedApplicationName());
		model.addAttribute("selectedScreenName",login.getSelectedScreenName());
		System.out.println("Started executing Test!!!");
		MainTestNG testStart = new MainTestNG();
		//testStart.startTest(login.getSelectedApplicationName(),Arrays.asList(login.getSelectedScreenName().split(",")));
		ApplicationService as = new ApplicationService();
		as.persistApplication();
		TestResultsReportingService tsService = new TestResultsReportingService();
		tsService.persistTestResults();
		System.out.println("Completed startTest!!!");
		return "homePage";
	}
	
	@RequestMapping(value = "/appToTest", method = RequestMethod.GET)
	public TestAutomationModel loadScreensList(ModelMap model,@ModelAttribute("login") Login login) {
		
		System.out.println("Selected Application Name: "+login.getSelectedApplicationName());
		model.addAttribute("applicationList", LoginService.applicationNameList);
		ArrayList<String> screenNameList = LoginService.screenDetailsMap.get(login.getSelectedApplicationName());
		login.setScreenNameList(screenNameList);
		model.addAttribute("screenNameList", login.getScreenNameList());
		
		TestAutomationModel testAutomationModel = new TestAutomationModel();
		testAutomationModel.applicationNameList = LoginService.applicationNameList;
		testAutomationModel.screenDetailsList = screenNameList;
		
		return testAutomationModel;
	}
	
	@RequestMapping(value = "/appToTest1", method = RequestMethod.GET)
	public String loadScreensList1(ModelMap model,@ModelAttribute("login") Login login) {
		
		System.out.println("Selected Application Name: "+login.getSelectedApplicationName());
		model.addAttribute("applicationList", LoginService.applicationNameList);
		ArrayList<String> screenNameList = LoginService.screenDetailsMap.get(login.getSelectedApplicationName());
		login.setScreenNameList(screenNameList);
		model.addAttribute("screenNameList", login.getScreenNameList());;
		
		return "homePage";
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
	//logger.info(" @LoginController -  logoutPage()");
	/*
	* Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	* if (auth != null){ new SecurityContextLogoutHandler().logout(request,
	* response, auth); }
	*/

	HttpSession session = request.getSession(false);
	   if (session != null) {
	       session.invalidate();
	   }
	   
	  // return "logintovds";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	 return   "redirect:/login";
	}
}