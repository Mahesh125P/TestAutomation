package com.testautomation.util;
/**
 * The ExecuteTestCases class is used to execute the test cases
 */
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.testautomation.MainTestNG;
import com.testautomation.service.LoginService;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.internal.BaseTestMethod;

/**
 * From MainTesnNG, this class will be called
 */
public class ExecuteTestCases implements ITest {

	public static final String FIREFOX = "firefox";
	public static final String IE = "IE";
	public static final String CHROME = "chrome";
	String cbrowserName = "BROWSER_NAME";
	String curl = "URL";
	String cdriver = "IEDRIVER";
	ReadConfigProperty config = new ReadConfigProperty();
	ReadElementLocators read = new ReadElementLocators();
	static ExcelLibrary lib = new ExcelLibrary();
	static String dir = "user.dir";
	static WebDriver driver = null;
	static ExcelAction act = new ExcelAction();
	public static String selectedApplication = null;
	public static String selectedScreen = null;
	public static List<String> selectedScreenList;


	static List list;
	protected String mTestCaseName = "";
	/**
	 * In this class, this is the first method to be executed.
	 * Reading testsuite , test case sheet and data sheet and storing the values in Hashmap
	 * @throws Exception 
	 **/
	@BeforeClass
	public void setup() throws Exception {
		MainTestNG.LOGGER.info(ExecuteTestCases.class.getName()
				+ "   setup() method called");


		try {
			
			for(String selectedScreen:selectedScreenList) { 
				
				ExecuteTestCases.selectedScreen = selectedScreen; 
				ExcelAction.selectedScreen = selectedScreen; 
				
				act.readTestSuite();
				act.readTestCaseInExcel();
				act.readTestDataSheet();
				act.readCapturedObjectProperties();
			}
			
			
			/**
			 * Selecting which browser to be executed
			 **/
			String applicationURL = LoginService.applicationDtlsMap.get(selectedApplication).get("applicationURL");
			String defaultBrowser = LoginService.applicationDtlsMap.get(selectedApplication).get("applicationBrowser");
			if(defaultBrowser==null || defaultBrowser.equals("")) {
				defaultBrowser = config.getConfigValues(cbrowserName);
			}
			selectBrowser(defaultBrowser);
			
			MainTestNG.LOGGER.info("defaultBrowser: "+defaultBrowser);
			MainTestNG.LOGGER.info("applicationURL: "+applicationURL);
			/**
			 * launching the url
			 **/
			driver.get(applicationURL);
			//driver.get(config.getConfigValues("http://localhost:9080/Login.action"));
			WebDriverClass.setDriver(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw (e);
		}
	}
	
	/**
	 *Select the browser on which you want to execute tests 
	 **/
	private void selectBrowser(String browserName) {

		switch (browserName) {
			case FIREFOX:
				firefoxProfile();
				break;
	
			case IE:
				ie();
				break;
	
			case CHROME:
				chrome();
				break;
	
			default:
				chrome();
				break;
			}

	}

	private void chrome() {
		System.setProperty("webdriver.chrome.driver",config.getConfigValues(cdriver));
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	private void ie() {
		System.setProperty("webdriver.ie.driver",config.getConfigValues(cdriver));
		driver = new InternetExplorerDriver();
		driver.manage().window().maximize();
		MainTestNG.LOGGER.info("IE launch");

	}

	/**
	 * Firefox profile will help in automatic download of files
	 */
	private void firefoxProfile() {
		
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("browser.download.folderList", 1);
		profile.setPreference("browser.download.manager.showWhenStarting",false);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
				"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		driver = new FirefoxDriver(profile);
		driver.manage().window().maximize();

	}

	/**
	 * To override the test case name in the report
	 */
	@BeforeMethod(alwaysRun = true)
	public void testData(Object[] testData) {
		String testCase = "";
		if (testData != null && testData.length > 0) {
			String testName = null;
			// Check if test method has actually received required parameters
			for (Object tstname : testData) {
				if (tstname instanceof String) {
					testName = (String) tstname;
					break;
				}
			}
			testCase = testName;
			System.out.println("Testcase Name..."+testCase);
		}
		this.mTestCaseName = testCase;

	}

	public String getTestName() {
		return this.mTestCaseName;
	}

	public void setTestName(String name) {

		this.mTestCaseName = name;

	}

	/**
	 * All the test cases execution will start from here, which will take the input from the data provider
	 */
	@Test(dataProvider = "dp",alwaysRun = true)
	public void testSample1(String testName) {
		
		String actionType = null;
		String Status = null;
		String objectLocators = null;
		
		MainTestNG.LOGGER.info(ExecuteTestCases.class.getName()
				+ "  @Test method called" + "    " + testName);

		try {
			this.setTestName(testName);
			MainTestNG.LOGGER.info("testSuiteIterate() calling");
			act.testSuiteIterate(testName);
			/*Reporter.log("executed....." + getTestName());
			Map<String,Object> temp=(Map<String, Object>)ExcelAction.testCaseStatus.get(mTestCaseName);
			Status = (String)temp.get("Status");
			objectLocators = (String)temp.get("objectLocators");
			Reporter.log("Status........."+Status);
			Reporter.log("objectLocators........."+objectLocators);*/
			if (ExcelAction.fail!=null){
				/*Reporter.log("Action Name====" + actionType);
				Reporter.log("objectLocators====" + objectLocators);*/
				Assert.fail();
				
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			/*Map<String,Object> temp=(Map<String, Object>)ExcelAction.testCaseStatus.get(mTestCaseName);
			Status = (String)temp.get("Status");
			objectLocators = (String)temp.get("objectLocators");
			Reporter.log("objectLocators....." + objectLocators);*/
			Assert.fail();
						
		}
	}

	@AfterMethod
	public void setResultTestName(ITestResult result) {
		try {
			
			BaseTestMethod bm = (BaseTestMethod) result.getMethod();
	//		result.setAttribute(name, value);
			Field f = bm.getClass().getSuperclass().getDeclaredField("m_methodName");
			f.setAccessible(true);
			f.set(bm, mTestCaseName);
			Reporter.log(bm.getMethodName());
			this.mTestCaseName = " ";
			System.out.println("In ExecuteTestCases.@AfterMethod().....");
		} catch (Exception ex) {
			stack(ex);
			Reporter.log("ex..." + ex.getMessage());
		}
	}

	/**
	 * Parameterization in testng
	 */
	@DataProvider(name = "dp")
	public Object[][] regression() {
		List list = (ArrayList) ExcelAction.listOfTestCases;
		Object[][] data = new Object[list.size()][1];
		MainTestNG.LOGGER.info(ExecuteTestCases.class.getName()
				+ " TestCases to be executed" + "    " + data);
		for (int i = 0; i < data.length; i++) {
			data[i][0] = (String) list.get(i);
			MainTestNG.LOGGER.info(".."+ list.get(i));
		}
		
		return data;
	}

	@AfterClass
	public void cleanup() {
		System.out.println("In ExecuteTestCases.Cleanup().....");
		try{
			act.clean();
			lib.clean();
			driver.close();
			driver.quit();
			ExcelAction.listOfTestCases.clear();
			//SwingTest.frame.dispose();
			//SwingTest.frame.dispatchEvent(new WindowEvent(SwingTest.frame,
					//WindowEvent.WINDOW_CLOSING));
		}catch (Exception e){
            System.out.println("exception caught while closing driver" +e);
        }finally{
            driver.quit();
        }

	}
	

	public static void stack(Exception e) {
		e.printStackTrace();
		MainTestNG.LOGGER.info(ExecuteTestCases.class.getName()
				+ " Exception occured" + "    " + e.getCause());
	}
}
