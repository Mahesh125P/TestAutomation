package com.testautomation.util;
import java.io.File;
/**
 * The ExcelAction class is used to store the data from the excel into map
 *
 * 
 */
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.testautomation.MainTestNG;
import com.testautomation.models.CapturedObjectPropModel;
import com.testautomation.models.TestCase;
import com.testautomation.service.DataFromDatabaseService;
import com.testautomation.service.LoginService;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DateUtil;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.Reporter;

import com.testautomation.actions.MethodType;

public class ExcelAction {
	WebDriver driver;
	static ExcelLibrary excel = new ExcelLibrary();
	static ReadConfigProperty config = new ReadConfigProperty();
	static Map<String, Object> testCaseSheet = new HashMap<String, Object>();
	static Map<String, Object> testCaseStatus = new HashMap<String, Object>();
	static Map<String, Object> testCaseFailures = new HashMap<String, Object>();
	static Map<String, String> readFromConfigFile = new HashMap<String, String>();
	static Map<String, Object> testSuiteSheet = new HashMap<String, Object>();
	static Map<String, Object> testDataSheet = new HashMap<String, Object>();
	static Map<String, Object> capObjPropSheet = new HashMap<String, Object>();

	public static List listOfTestCases = new ArrayList();
	int numberOfTimeExecution = 0;
	MethodType methodtype = new MethodType();
	static String actionName = null;
	String testcasepth = "TestCasePath";
	static String fail = null;
	public static DataFromDatabaseService dataFromDbService;
	
	public static String selectedApplication = null;
	public static String selectedScreen = null;
	public static String selectedComponent = null;
	public static String currentTestAutoUser = null;
	HashMap<String,String> tempResultMap = new HashMap<String,String>();
	public static HashMap<String,HashMap<String,String>> testResultMap = new HashMap<String,HashMap<String,String>>();
	StringBuffer inputValue = new StringBuffer();
	
	final static String testcasesprojectfilePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src"
            + File.separator + "main" + File.separator + "resources" + File.separator + "TestCase" + File.separator ;
    final static String dbtestcasesprojectfilePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src"
            + File.separator + "main" + File.separator + "resources" + File.separator  + "DBData"+ File.separator;
    final static String testsuitprojectfilePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src"
            + File.separator + "main" + File.separator + "resources" + File.separator + "TestSuite" + File.separator ;
        
	/*
	 * public static void main(String[] args) throws Exception { ExcelAction action
	 * = new ExcelAction(); action.readCapturedObjectProperties();
	 * action.readLocators("PAGE", "SEARCH_BOX"); }
	 */

	
	/**
	 * Read test data sheet
	 * @throws Exception 
	 */
	public void readTestDataSheet() throws Exception {
		
		String sheetName;
		StringBuffer dynamicFilePath = new StringBuffer();			
		/*
		 * dynamicFilePath.append(projectfilePath+ "TestCase\\").append(selectedApplication).append("\\").append("TestCase_").append(
		 * selectedApplication) .append("_").append(selectedScreen).append(".xlsx");
		 */
		
		if(DataFromDatabaseService.userNDataFromDBMap.get(currentTestAutoUser).equalsIgnoreCase("Yes")){
            dynamicFilePath.append(dbtestcasesprojectfilePath).append(selectedApplication).append("\\").append("DbData_TestCase_");
        } else {
            dynamicFilePath.append(testcasesprojectfilePath).append(selectedApplication).append("\\").append("TestCase_");
        }
        
        dynamicFilePath.append(selectedApplication).append("_").append(selectedScreen).append(".xlsx");
        
		
		String pathOFFile = dynamicFilePath.toString();
		List<String> list = ExcelLibrary
				.getNumberOfSheetsinTestDataSheet(dynamicFilePath.toString());
		for (int i = 0; i < list.size(); i++) {
			sheetName = list.get(i);
			Map<String, Object> temp1 = new HashMap<String, Object>();

			try {
				Reporter.log("Test Case Sheet Name...." + sheetName + "----"
						+ "pathOFFile" + pathOFFile);
				List listColumnNames = ExcelLibrary.getColumnNames(
						sheetName, pathOFFile,
						ExcelLibrary.getColumns(sheetName, pathOFFile));
				// iterate through columns in sheet
				for (int j = 0; j < listColumnNames.size(); j++) {
					// get Last Row for each Column
					int row = 1;
					List listColumnValues = new ArrayList();
					do {
						listColumnValues.add(ExcelLibrary.readCell(row, j,
								sheetName, pathOFFile));
						row++;
					} while ((ExcelLibrary.readCell(row, j, sheetName,
							pathOFFile)) != null);
					temp1.put((String) listColumnNames.get(j), listColumnValues);
				}
				listColumnNames.clear();
			} catch (InvalidFormatException | IOException e) {
				// check after run
				MainTestNG.LOGGER.info("InvalidFormatException,IOException...."+e);
			} catch (Exception e) {
				// check after run
				MainTestNG.LOGGER.info("Exception....."+e);
				throw (e);
			}
			if(sheetName.startsWith("TC00")) {
				sheetName = sheetName+ "~" + selectedScreen;
			}
			testDataSheet.put(sheetName, temp1);
		}
	}

	/**
	 * Iterate over each row in  testcase sheet and pass the data to execute method in MethodType.java
	 * @throws Exception 
	 */
	public void testSuiteIterate(String tcName) throws Exception {
		MainTestNG.LOGGER.info("testSuiteIterate() called  " + tcName);
		inputValue = new StringBuffer();
		tempResultMap = new HashMap<String,String>();
		String key = tcName;
		String[] screenName = tcName.split("~");
		fail = null;
		TestCase temp = (TestCase) testCaseSheet.get(key);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar calobj = Calendar.getInstance();
		try{					
		
		tempResultMap.put("TestStartDate", df.format(calobj.getTime()));
		tempResultMap.put("LoggedInUser",currentTestAutoUser);
		List testStepId = temp.getTestStepId();
		Reporter.log("size====" + testStepId.size());
		List dataColValues = null;
		int noOfExecution = 0;
		for (int i = 0; i < testStepId.size(); i++) {
			if (temp.getTestData().get(i)!= null) {
				System.out.println("temp.getTestData().get(i)...."+temp.getTestData().get(i));
				if (temp.getTestData().get(i).contains(".")) {

					String data = temp.getTestData().get(i);
					String[] testDataArray = data.split("\\.");
					System.out.println("testDataArray...."+testDataArray.toString());
					testDataArray[0] = testDataArray[0] + "~" + screenName[1];
					dataColValues = getColumnValue(testDataArray);
					System.out.println("dataColValues...."+dataColValues.toString());
					
					noOfExecution = dataColValues.size();

					break;
				}
			} else {
				noOfExecution = 0;
			}
		}
		MainTestNG.LOGGER.info("columnValue addedd newly numberOfTimesExecution==="+ dataColValues);
		MainTestNG.LOGGER.info("testCaseExecution==" + noOfExecution);

		if (noOfExecution != 0) {
			for (int execution = 0; execution < noOfExecution; execution++) {
				for (int i = 0; i < testStepId.size(); i++) {

					String methodTypeTemp = temp.getMethodType().get(i);
					String methodType = temp.getMethodType().get(i)+ "~" + screenName[1];
					String objectLocators = temp.getObjectNameFromPropertiesFile().get(i);
					String actionType = temp.getActionType().get(i);
					String header = temp.getHeaderValue().get(i);
					String dataValue = "";
					
					// Data Sheet logic
					if (temp.getTestData().get(i) != null) {
						if (!(temp.getTestData().get(i).isEmpty())) {
							if (temp.getTestData().get(i).contains(".")) {
								
								String data = temp.getTestData().get(i);
								String[] testDataArray = data.split("\\.");
								//String header = temp.getHeader().get(i);
								//String[] headerArray = header.split("\\.");
								//List headerValue = getColumnValue(headerArray);
								testDataArray[0] = testDataArray[0] + "~" + screenName[1];
								List columnValue = getColumnValue(testDataArray);
								if(header != null && header.equals("DB")) {
									//dataValue = "CQT20022501";
									dataValue = dataFromDbService.getDataFromDBForScenarioBuilding(screenName[1],columnValue.get(execution).toString());
								} else {
									dataValue = columnValue.get(execution).toString();
								}
								Reporter.log("column value======" + dataValue);
								inputValue.append("~").append(testDataArray[1]).append(" : ").append(dataValue);
								//Reporter.log("column value size==========="+ columnValue.size());
								try {
									//Reporter.log("testCaseExecution======================"+ noOfExecution);
									List<String> list=readLocators(methodType,objectLocators);
									methodType=list.get(0);
									objectLocators=list.get(1);
									MainTestNG.LOGGER.info("methodType="+methodType);
									MainTestNG.LOGGER.info("objectLocators as name="+objectLocators);
									methodtype.methodExecutor(methodType,objectLocators, actionType, dataValue, header);
									/*System.out.println("Statu fail.....");
									statusInfo.put("Status", "Pass");
									statusInfo.put("ActionType", actionName);
									statusInfo.put("objectLocators", objectLocators);
									testCaseStatus.put(temp.getTestCaseName(), statusInfo);*/
								} catch (Exception e) {
									Reporter.log("Process failed for this test record : " + dataValue);
									tempResultMap.put("FailedTestData", "methodType: "+methodTypeTemp+", actionType: "+actionType + ", InputValue: "+ dataValue);
									tempResultMap.put("TestOutput","F");
									tempResultMap.put("TestEndDate", df.format(calobj.getTime()));
									tempResultMap.put("InputValue",inputValue.toString());
									testResultMap.put(tcName, tempResultMap);
									System.out.println("In Exception....ExcelAction.testSuiteIterate.");
									fail = "fail";
									//Assert.fail();
									break;
								}
							}
					    
							if (execution == noOfExecution) {
								break;
							}
						} else {
						driver = WebDriverClass.getInstance();
						List<String> list=readLocators(methodType,objectLocators);
						methodType=list.get(0);
						objectLocators=list.get(1);
						MainTestNG.LOGGER.info("methodType="+methodType);
						methodtype.methodExecutor(methodType, objectLocators,
								actionType, null,null);
				
					   }
					}
				}
				if (execution == noOfExecution) {
					break;
				}
			}

		} else {
			for (int i = 0; i < testStepId.size(); i++) {

				String methodType = temp.getMethodType().get(i)+ "~" + screenName[1];
				String objectLocators = temp.getObjectNameFromPropertiesFile()
						.get(i);
				String actionType = temp.getActionType().get(i);

				driver = WebDriverClass.getInstance();
				
				List<String> list=readLocators(methodType,objectLocators);
				methodType=list.get(0);
				objectLocators=list.get(1);
				MainTestNG.LOGGER.info("methodType="+methodType);
				MainTestNG.LOGGER.info("objectLocators="+objectLocators);
				
				methodtype.methodExecutor(methodType, objectLocators,
						actionType, null, null);
				
			}
		}
		
		if(tempResultMap.get("TestOutput")==null){
			tempResultMap.put("TestOutput","P");
		}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("In Exception....ExcelAction.testSuiteIterate2.");
			tempResultMap.put("Exception",e.getLocalizedMessage());
			tempResultMap.put("TestOutput","F");
			fail = "fail";
			throw (e);
		}finally {
			tempResultMap.put("TestEndDate", df.format(calobj.getTime()));
			tempResultMap.put("InputValue",inputValue.toString());
			testResultMap.put(tcName, tempResultMap);
		}
		
	}

	private List getColumnValue(String[] testDataArray) {
		Map<String, Object> dataSheet = (HashMap<String, Object>) testDataSheet
				.get(testDataArray[0]);
		String value = null;
		List coulmnValue = null;
		if(testDataArray.length > 2) {
			value =  dataSheet.get(testDataArray[1]).toString().replace("[", "").replace("]", "").concat(",").concat(dataSheet.get(testDataArray[3]).toString().replace("[", "").replace("]", ""));
			coulmnValue =  Arrays.asList(value);
		}else {
			coulmnValue = (ArrayList) dataSheet.get(testDataArray[1]);
		}
		return coulmnValue;
	}

	/**
	 * populate data to testSuitedata and listOfTestCases to be executed
	 * @throws Exception 
	 */
	public void readTestSuite() throws Exception {
		readFromConfigFile = config.readConfigFile();

		for (String suiteName : readFromConfigFile.values()) {

			//${catalina.home}/
			StringBuffer dynamicFilePath = new StringBuffer();	
			/*
			 * if (operationType.equals("Manual")) { dynamicFilePath.append(projectfilePath
			 * + "TestSuite\\") .append(selectedApplication)
			 * .append("\\").append("TestSuite_").append(selectedApplication)
			 * .append("_").append(selectedScreen).append(".xlsx"); } else {
			 * dynamicFilePath.append(projectfilePath + "TestSuite\\")
			 * .append(selectedApplication).append("\\").append("Automatic")
			 * .append("\\").append("TestSuite_").append(selectedApplication)
			 * .append("_").append(selectedScreen).append(".xlsx"); }
			 */
			//dynamicFilePath.append("C:\\SOWMIYA\\TESTING AUTOMATION\\TestAutomation_Workspace\\TestAutomation\\src\\main\\resources\\TestSuite\\")
            //String testSuiteFilePath = config.getConfigValues("TestSuiteName");
            
			if(selectedComponent != null && !selectedComponent.isEmpty()) {
                dynamicFilePath.append(testsuitprojectfilePath)
                .append(selectedApplication).append("\\").append("Automatic").append("\\").append(selectedComponent).append("\\")
                .append("TestSuite_").append(selectedApplication).append("_").append(selectedScreen).append(".xlsx");
            } else {
                dynamicFilePath.append(testsuitprojectfilePath)
                .append(selectedApplication).append("\\").append("TestSuite_")
                .append(selectedApplication).append("_").append(selectedScreen).append(".xlsx");
            }
			//String testSuiteFilePath = config.getConfigValues("TestSuiteName"); 
			String testSuiteFilePath = dynamicFilePath.toString();
			System.out.println(testSuiteFilePath);
			List<String> suiteSheets = ExcelLibrary.getNumberOfSheetsinSuite(testSuiteFilePath);
			System.out.println(suiteSheets.size());
			testSuiteSheet.clear();

			for (int i = 0; i < suiteSheets.size(); i++) {
				String sheetName = suiteSheets.get(i);
				System.out.println(sheetName);
				if (suiteName.trim().equalsIgnoreCase(sheetName)) {
					Map<String, Object> temp1 = new HashMap<String, Object>();
					try {
						for (int row = 1; row <= ExcelLibrary.getRows(
								sheetName, testSuiteFilePath); row++) {

							String testCaseName = ExcelLibrary.readCell(row, 0,suiteName.trim(), testSuiteFilePath);
							String testCaseState = ExcelLibrary.readCell(row,1, suiteName.trim(), testSuiteFilePath);
							if (("YES").equalsIgnoreCase(testCaseState)) {
								listOfTestCases.add(testCaseName+ "~" + selectedScreen);
							}
							temp1.put(testCaseName + "~" + selectedScreen, testCaseState);

						}
						Reporter.log("listOfTestCases=============*****************"+ listOfTestCases);
						testSuiteSheet.put(suiteName+ "~" + selectedScreen , temp1);
					} catch (InvalidFormatException | IOException e) {

						MainTestNG.LOGGER.info("e"+e);

					}
					catch (Exception e) {

						MainTestNG.LOGGER.info("e"+e);
						throw (e);
					}
				}
			}
		}

	}

	/**
	 * Read the content of the excel testcase sheet and store the data in model and store this model in hashmap
	 * @throws Exception 
	 */
	public void readTestCaseInExcel() throws Exception {

		String testsheetnme = "TestCase_SheetName";
		StringBuffer dynamicFilePath = new StringBuffer();	
		/*
		 * dynamicFilePath.append(projectfilePath + "\\TestCase\\")
		 * .append(selectedApplication).append("\\").append("TestCase_").append(
		 * selectedApplication) .append("_").append(selectedScreen).append(".xlsx");
		 */
		if(DataFromDatabaseService.userNDataFromDBMap.get(currentTestAutoUser).equalsIgnoreCase("Yes")){
            dynamicFilePath.append(dbtestcasesprojectfilePath).append(selectedApplication).append("\\").append("DbData_TestCase_");
        } else {
            dynamicFilePath.append(testcasesprojectfilePath).append(selectedApplication).append("\\").append("TestCase_");
        }        
        dynamicFilePath.append(selectedApplication).append("_").append(selectedScreen).append(".xlsx");
        
		String testCasePath = dynamicFilePath.toString();
		String testCaseSheetName = config.getConfigValues(testsheetnme);

		TestCase tc = null;
		try {
			for (int row = 1; row <= ExcelLibrary.getRows(testCaseSheetName,
					testCasePath); row++) {
				if(ExcelLibrary.readCell(row, 0, testCaseSheetName,
						testCasePath) != null) {
					if (!(ExcelLibrary.readCell(row, 0, testCaseSheetName,
							testCasePath).isEmpty())) {
	
						tc = new TestCase();
						tc.setTestCaseName(ExcelLibrary.readCell(row, 0,testCaseSheetName, testCasePath) + "~" + selectedScreen);
						tc.setTestStepId(ExcelLibrary.readCell(row, 1,testCaseSheetName, testCasePath));
						tc.setMethodType(ExcelLibrary.readCell(row, 3,testCaseSheetName, testCasePath));
						tc.setObjectNameFromPropertiesFile(ExcelLibrary.readCell(row, 4, testCaseSheetName, testCasePath));
						tc.setActionType(ExcelLibrary.readCell(row, 5,testCaseSheetName, testCasePath));
						tc.setOnFail(ExcelLibrary.readCell(row, 6,testCaseSheetName, testCasePath));
						tc.setTestData(ExcelLibrary.readCell(row, 7,testCaseSheetName, testCasePath));
						tc.setHeaderValue(ExcelLibrary.readCell(row, 8,testCaseSheetName, testCasePath));
						testCaseSheet.put(ExcelLibrary.readCell(row, 0,testCaseSheetName, testCasePath) + "~" + selectedScreen, tc);
					} else {
	
						tc.setTestStepId(ExcelLibrary.readCell(row, 1,
								testCaseSheetName, testCasePath));
						tc.setMethodType(ExcelLibrary.readCell(row, 3,
								testCaseSheetName, testCasePath));
						tc.setObjectNameFromPropertiesFile(ExcelLibrary.readCell(
								row, 4, testCaseSheetName, testCasePath));
						tc.setActionType(ExcelLibrary.readCell(row, 5,
								testCaseSheetName, testCasePath));
						tc.setOnFail(ExcelLibrary.readCell(row, 6,
								testCaseSheetName, testCasePath));
						tc.setTestData(ExcelLibrary.readCell(row, 7,
								testCaseSheetName, testCasePath));
						tc.setHeaderValue(ExcelLibrary.readCell(row, 8,
								testCaseSheetName, testCasePath));
						System.out.println("TestCase Details..else..."+row);
					}
				}
			System.out.println("TestCase Details..else..."+testCaseSheet.size());
			}
		} catch (InvalidFormatException e) {

			MainTestNG.LOGGER.info(e.getMessage());
			throw (e);
		} catch (Exception e) {
			e.printStackTrace();
			MainTestNG.LOGGER.info(e.getMessage());
			throw (e);
		}
	}

	public void clean() {
		excel.clean();
		

	}

	/**
	 * Capture object properties in excel sheet
	 * @throws Exception 
	 */
	public void readCapturedObjectProperties() throws Exception {
		String testSheetName = "CapturedObjectProperties";
		StringBuffer dynamicFilePath = new StringBuffer();			
		/*
		 * dynamicFilePath.append(projectfilePath + "TestCase\\")
		 * .append(selectedApplication).append("\\").append("TestCase_").append(
		 * selectedApplication) .append("_").append(selectedScreen).append(".xlsx");
		 */
		if(DataFromDatabaseService.userNDataFromDBMap.get(currentTestAutoUser).equalsIgnoreCase("Yes")){
            dynamicFilePath.append(dbtestcasesprojectfilePath).append(selectedApplication).append("\\").append("DbData_TestCase_");
        } else {
            dynamicFilePath.append(testcasesprojectfilePath).append(selectedApplication).append("\\").append("TestCase_");
        }        
        dynamicFilePath.append(selectedApplication).append("_").append(selectedScreen).append(".xlsx");
        
		String testCasePath = dynamicFilePath.toString();
		MainTestNG.LOGGER.info("testCasePath=="+testCasePath);
		try {
			int totrows = ExcelLibrary.getRows(testSheetName, testCasePath);
			MainTestNG.LOGGER.info("total rows=" + totrows);

			String prevPagename="";
			Map<String, Object> pageInfo = null; 
			for (int j = 1; j <= totrows; j++) {
				String pagename = ExcelLibrary.readCell(j, 0, testSheetName,
						testCasePath);
			
				
				if(prevPagename.equals(pagename)){
					
					String page=ExcelLibrary.readCell(j, 0,testSheetName, testCasePath);
					String name=ExcelLibrary.readCell(j, 1,
							testSheetName, testCasePath);
					String property=ExcelLibrary.readCell(j, 2,
							testSheetName, testCasePath);
					String value=ExcelLibrary.readCell(j, 3,
							testSheetName, testCasePath);
					
					CapturedObjectPropModel capModel = new CapturedObjectPropModel();
					capModel.setPage(page+ "~" + selectedScreen);
					capModel.setName(name);
					capModel.setProperty(property);
					capModel.setValue(value);
					MainTestNG.LOGGER.info(capModel.getPage()+"  "+capModel.getName()+"  "+capModel.getValue()+"  "+capModel.getProperty());
					pageInfo.put(name, capModel);
					actionName = name;
					
				}else{
					if(prevPagename!=null){
						capObjPropSheet.put(prevPagename+ "~" + selectedScreen, pageInfo);
					}
					pageInfo=new HashMap<String, Object>();
					String page=ExcelLibrary.readCell(j, 0,
							testSheetName, testCasePath);
					String name=ExcelLibrary.readCell(j, 1,
							testSheetName, testCasePath);
					String property=ExcelLibrary.readCell(j, 2,
							testSheetName, testCasePath);
					String value=ExcelLibrary.readCell(j, 3,
							testSheetName, testCasePath);
					
					CapturedObjectPropModel capModel = new CapturedObjectPropModel();
					capModel.setPage(pagename+ "~" + selectedScreen);
					capModel.setName(name);
					capModel.setProperty(property);
					capModel.setValue(value);
					MainTestNG.LOGGER.info(capModel.getPage()+"  "+capModel.getName()+"  "+capModel.getValue()+"  "+capModel.getProperty());
					
					pageInfo.put(name, capModel);
					prevPagename=pagename;
					actionName = name;
				}
				
				
				if(prevPagename!=null){
					capObjPropSheet.put(prevPagename+ "~" + selectedScreen, pageInfo);
				}
			}
			

		} catch (InvalidFormatException e) {
			
			MainTestNG.LOGGER.info("InvalidFormatException="+e);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			throw (e);
		} catch (Exception e) {
			
			e.printStackTrace();
			throw (e);
		}
	}

	/**
	 * Capture object Locators in excel sheet
	 */
	public List<String> readLocators(String page,String name) {
		MainTestNG.LOGGER.info(page);
		MainTestNG.LOGGER.info(name);
		List<String> locators=new ArrayList<>();
		try {
		Map<String,Object> temp=(Map<String, Object>) capObjPropSheet.get(page);
		
		MainTestNG.LOGGER.info("objects"+capObjPropSheet.get(page));
		if(capObjPropSheet.get(page) != null){
			
			MainTestNG.LOGGER.info("name"+temp.get(name));
			CapturedObjectPropModel c=(CapturedObjectPropModel) temp.get(name);
		//	MainTestNG.LOGGER.info(c.getName());
			MainTestNG.LOGGER.info("c.getPage()="+c.getPage());
			
			if(c.getPage().equals(page) && c.getName().equals(name)){
				locators.add(c.getProperty());
				locators.add(c.getValue());
				MainTestNG.LOGGER.info("locators"+locators);
			}
		}
		} catch (Exception e) {
			
			e.printStackTrace();
			throw (e);
		}
		MainTestNG.LOGGER.info("size"+locators.size());
		return locators;
	}
}
