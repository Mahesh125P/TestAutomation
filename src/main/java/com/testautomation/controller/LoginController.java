package com.testautomation.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.testautomation.model.Login;
import com.testautomation.service.LoginService;


/**
 * @author Mahesh Kumar P
 *
 */

@RestController
public class LoginController {
	
	@Autowired
	LoginService loginservice;
	
	final static Logger logger = LoggerFactory.getLogger(LoginController.class);
		
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
	
	@RequestMapping(value = "/loginSubmit", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String LoginSubmit(@RequestBody Login login,HttpServletRequest request) {
		logger.info("Started LoginSubmit!!!");
		logger.info("Username:"+login.getUserName() + "Password:  "+login.getPassword());
		if(!loginservice.isaValidUser(login.getUserName())){
			logger.info("Completed LoginSubmit! Login Failed!");
			return "FAILURE";
		}
		/*
		 * System.out.println("111");
		 * System.out.println(login.getSelectedApplicationName());
		 * loginservice.getApplicationDetails(); TreeSet<String> applicationList =
		 * LoginService.applicationNameList; ArrayList<String> screenNameList =
		 * LoginService.screenDetailsMap.get(applicationList.first());
		 * 
		 * TestAutomationModel testAutomationModel = new TestAutomationModel();
		 * testAutomationModel.applicationNameList = applicationList;
		 * testAutomationModel.screenDetailsList = screenNameList;
		 * System.out.println("login.getApplicationName()" +
		 * login.getApplicationList());
		 */
		logger.info("Completed LoginSubmit! Login Success!");
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/getAllScreenDetails", method = RequestMethod.GET)
	public HashMap<String,ArrayList<String>> getAllScreenDetails() {
		return loginservice.getAllScreenDetails();
	}
	
	@RequestMapping(value = "/getAllScreenDetailsForTestSuite", method = RequestMethod.GET)
	public HashMap<String,ArrayList<String>> getAllScreenDetailsForTestSuite() {
		return loginservice.getAllScreenDetailsForTestSuite();
	}
	
	@RequestMapping(value = "/loginSubmit1", method = RequestMethod.GET)
	public String LoginSubmit1(ModelMap model,@ModelAttribute("login") Login login) {
		logger.info("Started LoginSubmit!!!");
		logger.info("Username:"+login.getUserName() + "Password:  "+login.getPassword());
		if(!loginservice.isaValidUser(login.getUserName())){
			model.put("errorMessage", "Invalid Credentials");
			return "login";
		}
		logger.info("111");
		logger.info(login.getSelectedApplicationName());
		model.addAttribute("userName", login.getUserName());
		loginservice.getApplicationDetails();
		TreeSet<String> applicationList = LoginService.applicationNameList;
		ArrayList<String> screenNameList = LoginService.screenDetailsMap.get(applicationList.first());	
		logger.info("login.getApplicationName()" + login.getApplicationList());
		
		model.addAttribute("screenNameList", screenNameList);
		model.addAttribute("testCaseList", login.getTestCaseList());
		model.addAttribute("selectedApplicationName", "VDS");
		model.addAttribute("applicationList", applicationList);
		model.addAttribute("login", login);
		
		logger.info("Completed LoginSubmit!!!");
		return "homePage";
	}

	@RequestMapping(value = "/appToTest", method = RequestMethod.GET)
	public String loadScreensList1(ModelMap model,@ModelAttribute("login") Login login) {
		
		logger.info("Selected Application Name: "+login.getSelectedApplicationName());
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
	 return   "redirect:/login";
	}
}