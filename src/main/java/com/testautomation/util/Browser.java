
package com.testautomation.util;

import java.io.File;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Browser {

	static String dir = "user.dir";
	public DesiredCapabilities d;
	private DesiredCapabilities oCapability;
	public static WebDriver driver = null;
	public WebDriver existingFireFox, existingChrome, existingInternetExplorer;

	public WebDriver getWebDriver() { return driver; }
	 

	/*
	 * Purpose : This method 1. launches an instance of the specified browser 2.
	 * Uses an existing browser instance if already open Parameters :
	 * browserName (Possible values are firefox,chrome,ie) Returns : None Sample
	 * Method call : launch("firefox"); Pre-conditions (if any) : None
	 */
	public void launch(String browserName) {

		try {
			
			String browser = browserName.toLowerCase().trim();
			int waitTime = 10;

			// This code block is invoked when there is an existing browser
			// instance already available
			// Connects to an existing browser instance instead of launching a
			// new one everytime

			if (browser.equalsIgnoreCase("firefox") & existingFireFox != null) {
				driver = existingFireFox;
				return;

			} else if (browser.equalsIgnoreCase("chrome") & existingChrome != null) {
				driver = existingChrome;
				return;

			} else if (browser.equalsIgnoreCase("ie") & existingInternetExplorer != null) {
				driver = existingInternetExplorer;
				return;
			}

			if (browser.equalsIgnoreCase("firefox")) {
				driver = new FirefoxDriver();
				existingFireFox = driver;

			} else if (browser.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty(dir)+"//src//Drivers//chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				options.addArguments("start-maximized");
				options.addArguments("--enable-automation");
				options.addArguments("test-type=browser");
				options.addArguments("disable-infobars");
				options.addArguments("--no-sandbox");
			//	driver = new ChromeDriver(options);
				existingChrome = driver;

			} else if (browser.equalsIgnoreCase("ie")) {
				
			//	File file = new File("."));
				System.setProperty("webdriver.ie.driver", System.getProperty(dir)+"//Drivers//IEDriverServer.exe");
				d = DesiredCapabilities.internetExplorer();
				d.setCapability("ie.ensureCleanSession", true);
				driver = new InternetExplorerDriver(d);
				existingInternetExplorer = driver;
			}

		//	driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Purpose : This method navigates the browser to the specified url
	 * Parameters : url (The url should start with http or https) Returns : None
	 * Sample Method call : goTo("https://www.google.co.in/"); Pre-conditions
	 * (if any) : None
	 */

	/**
	 * Go to.
	 *
	 * @param url
	 *            the url
	 */
	public void goTo(String url) {
		driver.get(url.trim());
	}

		
	/**
	 * Close.
	 */
	/*
	 * Purpose : This method closes the current browser window Parameters : None
	 * Returns : None Sample Method call : closeWindow(); Pre-conditions (if
	 * any) : None
	 */
	public void close() {
		driver.quit();
	}

	/**
	 * Close all.
	 */
	/*
	 * Purpose : This method closes all the open browsers Parameters : None
	 * Returns : None Sample Method call : closeAllBrowsers(); Pre-conditions
	 * (if any) : None
	 */
	public void closeAll() {
		driver.quit();
	}

} // end class
