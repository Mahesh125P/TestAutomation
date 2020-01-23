
package com.testautomation.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GlobalVariables {

	public static HashMap<String, String> configData = new HashMap<String, String>();
	public static Properties Config, Object, Database = null;
	public static List<String> allModules = new ArrayList<String>();
	//public static TestVerification testVerification = new TestVerification();
	public static String errorTrace;
	public static String workingDir = System.getProperty("user.dir");
	public static WebDriverWait wait;
	//public static ReportUtils reportUtils;
	public static RemoteWebDriver windriver = null;
	// public static MobileElement sMobObject = null;
	public static Select listBox = null;
	public static WebElement sObject = null;
	public static Set<String> windows = null;
	public static Actions builder = null;
	public static String refNo = null;
	// public static WebDriver webDriver1;
	//public static Screen screen = new Screen();

	public static String winHandleBefore = null;

}
