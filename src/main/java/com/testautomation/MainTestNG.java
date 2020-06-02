package com.testautomation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
/**
 * The MainTestNG class is used to execute the jar
 *
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpSession;

import com.testautomation.repositories.ComponentMappingRepository;
import com.testautomation.service.DataFromDatabaseService;
import com.testautomation.service.TestComponentService;
import com.testautomation.service.TestResultsReportingService;
import com.testautomation.util.EmailUtil;
import com.testautomation.util.ExcelAction;
import com.testautomation.util.ExecuteTestCases;
import com.testautomation.util.ReadConfigProperty;
import com.testautomation.util.Report;
import com.testautomation.util.SwingTest;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * This is the class from where the execution gets started.
 */

//TestSuite
public class MainTestNG {
	static Handler filehandler;
	static Formatter formatter = null;
	public static final Logger LOGGER = Logger.getLogger(MainTestNG.class.getName());
	ReadConfigProperty config = new ReadConfigProperty();
	static SwingTest swing;
	static String dir = "user.dir";
	/*
	 * public static void main(String[] args) {
	 * 
	 * try { filehandler = new FileHandler("./log.txt"); } catch (SecurityException
	 * e) { MainTestNG.LOGGER.info(e.getMessage()); } catch (IOException e) {
	 * MainTestNG.LOGGER.info(e.getMessage()); }
	 * 
	 * LOGGER.addHandler(filehandler); formatter = new SimpleFormatter();
	 * filehandler.setFormatter(formatter); LOGGER.info("Logger Name: " +
	 * LOGGER.getName());
	 * 
	 * ReadConfigProperty.configpath = System.getProperty(dir);
	 * 
	 *//**
		 * SwingTest UI for properties file (Config.properties)
		 */

	/*
	 * //swing = new SwingTest(); MainTestNG test=new MainTestNG();
	 * 
	 *//**
		 * testNG execution starts here
		 *//*
			 * test.testng();
			 * 
			 * }
			 */

	public void startTest(TestResultsReportingService testReportService, String selectedApplication, List<String> selectedScreenList, String selectedComponent,DataFromDatabaseService dataFromDbService) {

		try {
			filehandler = new FileHandler("./log.txt");
		} catch (SecurityException e) {
			MainTestNG.LOGGER.info(e.getMessage());
		} catch (IOException e) {
			MainTestNG.LOGGER.info(e.getMessage());
		}

		LOGGER.addHandler(filehandler);
		formatter = new SimpleFormatter();
		filehandler.setFormatter(formatter);
		LOGGER.info("Logger Name: " + LOGGER.getName());
		HashMap<String,HashMap<String,String>> testResultMap = new HashMap<String,HashMap<String,String>> ();
		ReadConfigProperty.configpath = System.getProperty(dir);
		ExcelAction.listOfTestCases.clear();

		/**
		 * SwingTest UI for properties file (Config.properties)
		 */
		// swing = new SwingTest();
		MainTestNG test = new MainTestNG();
		//ComponentMappingRepository componentMappingRepository = new ComponentMappingRepository();
		/**
		 * testNG execution starts here
		 */
		try {
			ExecuteTestCases.selectedApplication = selectedApplication;
			ExcelAction.selectedApplication = selectedApplication;
			ExcelAction.selectedComponent = selectedComponent;
			ExcelAction.dataFromDbService = dataFromDbService;
			ExecuteTestCases.selectedScreenList = selectedScreenList;
			//ExcelAction.listOfTestCases.clear();
			
			test.testng();
			testResultMap = ExcelAction.testResultMap;
			testReportService.persistTestResult(selectedApplication,testResultMap);
			
		}catch (Exception e) {
			try {
				EmailUtil.sendWithAttachment("maheshkumar.p@thirdware.com", null, "Test Automation Error",
						e.toString(),
						"");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		/*
		 * for(String selectedScreen:selectedScreenList) { try {
		 * //test.updatePropertyFile(selectedScreen); ExecuteTestCases.selectedScreen =
		 * selectedScreen; ExcelAction.selectedScreen = selectedScreen; test.testng();
		 * testResultMap = ExcelAction.testResultMap;
		 * testReportService.persistTestResult(selectedApplication,selectedScreen,
		 * testResultMap);
		 * 
		 * }catch (Exception e) { try {
		 * EmailUtil.sendWithAttachment("maheshkumar.p@thirdware.com", null,
		 * "Test Automation Error", e.toString(), ""); } catch (Exception e1) { // TODO
		 * Auto-generated catch block e1.printStackTrace(); } } }
		 */
		try {
			
			EmailUtil.sendWithAttachment("maheshkumar.p@thirdware.com", null, "Test Automation Result",
					"Test Result!!!",
					"C:\\Workspace_TestAutomation\\TestAutomation\\test-output\\Sample Suite\\Test Results.html");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * adding listners, setting test-output folder Mentioning the TestSuite Name
	 */
	public void testng() {
		// RegressionSuite
		System.out.println("testng");
		XmlSuite mySuite = new XmlSuite();
		mySuite.setName("Sample Suite");
		mySuite.addListener("org.uncommons.reportng.HTMLReporter");
		mySuite.addListener("org.uncommons.reportng.JUnitXMLReporter");
		mySuite.addListener("com.testautomation.util.TestListener");
		TestNG myTestNG = new TestNG();
		myTestNG.setOutputDirectory("test-output");
		XmlTest myTest = new XmlTest(mySuite);
		myTest.setName("Test Results");
		List<XmlClass> myClasses = new ArrayList<XmlClass>();
		myClasses.add(new XmlClass("com.testautomation.util.ExecuteTestCases"));
		myTest.setXmlClasses(myClasses);
		List<XmlTest> myTests = new ArrayList<XmlTest>();
		myTests.add(myTest);
		mySuite.setTests(myTests);
		List<XmlSuite> mySuites = new ArrayList<XmlSuite>();

		mySuites.add(mySuite);
		myTestNG.setXmlSuites(mySuites);
		myTestNG.setUseDefaultListeners(true);
		/**
		 * testng run method
		 */
		myTestNG.run();

		/**
		 * Report generation--Generating XSLT report from ReportNG report
		 */
		Report.report();
	}

	public void updatePropertyFile(String selectedScreen) {
		FileInputStream in;
		try {

			PropertiesConfiguration props;
			props = new PropertiesConfiguration("config.properties");
			if (selectedScreen.equalsIgnoreCase("Compound Transfer Module")) {
				props.setProperty("TestSuiteName",
						"C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestSuite_C.xlsx");
				props.setProperty("TestCasePath",
						"C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestCases_CompoundTransfer.xlsx");
			} else {
				props.setProperty("TestSuiteName",
						"C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestSuite_R.xlsx");
				props.setProperty("TestCasePath",
						"C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestCases_TransferRoute.xlsx");
			}
			props.save();

			/*
			 * in = new FileInputStream("config.properties"); Properties props = new
			 * Properties(); props.load(in); in.close();
			 * 
			 * FileOutputStream out = new FileOutputStream("config.properties");
			 * if(selectedScreen.equalsIgnoreCase("CompoundTransfer")) {
			 * props.setProperty("TestSuiteName",
			 * "C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestSuite_C.xlsx"
			 * ); props.setProperty("TestCasePath",
			 * "C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestCases_CompoundTransfer.xlsx"
			 * ); }else { props.setProperty("TestSuiteName",
			 * "C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestSuite_R.xlsx"
			 * ); props.setProperty("TestCasePath",
			 * "C:\\Workspace_TestAutomation\\TestAutomation\\src\\main\\resources\\TestCases_TransferRoute.xlsx"
			 * ); } props.store(out, null); out.close();
			 */
		} catch (ConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
