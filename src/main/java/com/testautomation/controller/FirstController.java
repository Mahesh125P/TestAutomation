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
import com.testautomation.service.LoginService;
import com.testautomation.util.EmailUtil;


/**
 * @author Mahesh Kumar P
 *
 */

@RestController
public class FirstController {
	
	@Autowired
	LoginService loginservice;
	
	/*
	 * @RequestMapping("/welcome") public ModelAndView firstPage() { return new
	 * ModelAndView("welcome"); }
	 */

	

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String showWelcomePage(ModelMap model) {
		//logger.info(" @LoginController -  showLoginPage()");
		return "welcome";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLoginPage(ModelMap model) {
		//logger.info(" @LoginController -  showLoginPage()");
		Login login = new Login();
		model.addAttribute("login", login);
		return "login";
	}
	
	@RequestMapping(value = "/loginSubmit", method = RequestMethod.GET)
	public TestAutomationModel LoginSubmit(ModelMap model,@ModelAttribute("login") Login login) {
		//logger.info(" @LoginController -  showLoginPage()");
		//model.addAttribute("login", new Login());
		System.out.println("Started LoginSubmit!!!");
		System.out.println("Username:"+login.getUserName() + "Password:  "+login.getPassword());
		/*
		 * if(!loginservice.isaValidUser(login.getUserName())){
		 * model.put("errorMessage", "Invalid Credentials"); return null; }
		 */
		System.out.println("111");
		System.out.println(login.getSelectedApplicationName());
		model.addAttribute("userName", login.getUserName());
		loginservice.getApplicationDetails();
		TreeSet<String> applicationList = LoginService.applicationNameList;
		ArrayList<String> screenNameList = LoginService.screenDetailsMap.get(applicationList.first());
		
		TestAutomationModel tm = new TestAutomationModel();
		tm.applicationNameList = applicationList;
		tm.screenDetailsList = screenNameList;
		/*
		 * ArrayList<String> applicationList = loginservice.getApplicationNames();
		 * ArrayList<String> screenNameList = loginservice.getScreenNames(applicationList.get(0));
		 */
		//loginservice.getApplicationDetails();
		//ArrayList<String> testCaseList = loginservice.getTestCaseNames("VDS");		
		
		
		
		//login.setApplicationList(applicationList);
		//login.setScreenNameList(screenNameList);
		//login.setTestCaseList(testCaseList);
		//login.setSelectedApplicationName("VDS");
		
		System.out.println("login.getApplicationName()" + login.getApplicationList());
		
		//model.addAttribute("applicationList", login.getApplicationList());
		model.addAttribute("screenNameList", screenNameList);
		model.addAttribute("testCaseList", login.getTestCaseList());
		model.addAttribute("selectedApplicationName", "VDS");
		model.addAttribute("applicationList", applicationList);
		model.addAttribute("login", login);
		
		System.out.println("Completed LoginSubmit!!!");
		return tm;
	}
	
	@RequestMapping(value = "/homeSubmit", method = RequestMethod.GET)
	public String HomeSubmit(ModelMap model,@ModelAttribute("login") Login login) {
		//logger.info(" @LoginController -  showLoginPage()");
		//model.addAttribute("login", new Login());
		/*
		 * System.out.println("Username:"+login.getUserName() +
		 * "Password:  "+login.getPassword());
		 * if(!loginservice.isaValidUser(login.getUserName())){
		 * model.put("errorMessage", "Invalid Credentials"); return "login"; }
		 */
		//model.addAttribute("applicationName", login.getApplicationName());
		//login.setApplicationName("ITRS");
		System.out.println("Started HomePage!!!");
		System.out.println("Selected App: "+login.getSelectedApplicationName());
		
		//ArrayList<String> applicationList = loginservice.getApplicationNames();	
		ArrayList<String> screenNameList = loginservice.getScreenNames(login.getSelectedApplicationName());
		ArrayList<String> testCaseList = loginservice.getTestCaseNames(login.getSelectedApplicationName());
		
		//login.setApplicationList(applicationList);
		login.setScreenNameList(screenNameList);
		login.setTestCaseList(testCaseList);
		
		//System.out.println("login.getApplicationName()" + login.getApplicationList());
		
		//model.addAttribute("applicationList", login.getApplicationList());
		model.addAttribute("screenNameList", login.getScreenNameList());
		model.addAttribute("testCaseList", login.getTestCaseList());
		model.addAttribute("selectedApplicationName", login.getSelectedApplicationName());
		
		System.out.println("Completed HomePage!!!");
		return "homePage";
	}
	
	@RequestMapping(value = "/startTest", method = RequestMethod.GET)
	public String startTest(ModelMap model,@ModelAttribute("login") Login login) {
		System.out.println("Started startTest!!!");
		System.out.println("Selected App: "+login.getSelectedApplicationName());
		ArrayList<String> applicationList = loginservice.getApplicationNames();
		login.setSelectedApplicationName("VDS");
		ArrayList<String> screenNameList = loginservice.getScreenNames(login.getSelectedApplicationName());
		
		//System.out.println("Selected Screens List: "+login.getScreenNameList().get(0));
		
		
		//ArrayList<String> screenNameList = loginservice.getScreenNames(login.getSelectedApplicationName());
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
		testStart.startTest(login.getSelectedApplicationName(),Arrays.asList(login.getSelectedScreenName().split(",")));
		
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
		
		TestAutomationModel tm = new TestAutomationModel();
		tm.applicationNameList = LoginService.applicationNameList;
		tm.screenDetailsList = screenNameList;
		
		return tm;
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