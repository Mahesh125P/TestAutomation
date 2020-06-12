package com.testautomation.actions;
/**
 * The MethodType class is used to identify the method specified in the testcase 
 * excel and to perform the same action
 * 
 */
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.testautomation.MainTestNG;
import com.testautomation.models.MethodParameters;
import com.testautomation.util.GlobalVariables;
import com.testautomation.util.ReadElementLocators;
import com.testautomation.util.WebDriverClass;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class MethodType extends GlobalVariables{

	List<WebElement> listOfElements = new ArrayList<WebElement>();
	WebElement element;
	ReadElementLocators read = new ReadElementLocators();

	String alertText = null;
	String titleOfPage = null;
//	String winHandleBefore = null; 
	/**
	 * @param methodType
	 * @param objectLocators
	 * @param actionType
	 * @param data
	 * 
	 * Object locators
	 * @throws Exception 
	 */
	public void methodExecutor(String methodType, String objectLocators,
			String actionType, String data, String header) throws Exception {
		MethodParameters mModel = new MethodParameters();
		mModel.setMethodType(methodType);
		mModel.setObjectLocators(objectLocators);
		mModel.setActionType(actionType);
		mModel.setData(data);
		mModel.setHeaderValue(header);
		MainTestNG.LOGGER
				.info("methodType= " + methodType + ",objectLocators="
						+ objectLocators + ",actionType=" + actionType
						+ ",data= " + data + ",header= " + header);

		switch (methodType) {
		
		case "ID":
			findElementById(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "NAME":
			findElementByName(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "XPATH":
			findElementByXpath(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "CSS":
			findElementByCssSelector(objectLocators);
			mModel.setElement(listOfElements);
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "SWITCH":
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "ALERT":
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		case "Select":
			//	findElementByCssSelector(objectLocators);
			//	mModel.setElement(listOfElements);String winHandleBefore = driver.getWindowHandle();
				findMethod(methodType, objectLocators, actionType, data, mModel);
				break;
		default:
			if (actionType.contains(":")) {
				String[] actsplit = actionType.split(":");
				mModel.setActionType(actsplit[1]);
				actionType = actsplit[0];
				System.out.println(actsplit[1]);
				System.out.println(actsplit[0]);
			}
			findMethod(methodType, objectLocators, actionType, data, mModel);
			break;
		}

	}

	/**
	 *Identifying the method at run time
	 * @throws Exception 
	 */
	public void findMethod(String methodType, String objectLocators,
			String actionType, String data, MethodParameters model) throws Exception {
		Class cl = null;
		try {
			cl = (Class) Class.forName("com.testautomation.actions.MethodType");
			com.testautomation.actions.MethodType clName = (MethodType) cl.newInstance();
			Method[] methods = cl.getMethods();
			
			Method methodName = findMethods(actionType, methods);
			methodName.invoke(clName, (Object) model);

		} catch (InvocationTargetException e) {
			Throwable cause = e.getCause();

			MainTestNG.LOGGER
					.info("exception occured in finding methods, method name is incorrect"
							+ e);
			e.printStackTrace();
			throw (e);
		} catch (Exception e) {

			com.testautomation.MainTestNG.LOGGER
					.info("exception occured in finding methods, method name is incorrect"
							+ e);
			e.printStackTrace();
			throw (e);
		}

	}

	/**
	 *Find Element By CSS
	 */
	private void findElementByCssSelector(String objectLocators) {

		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(objectLocators)));

		List<WebElement> list1 = WebDriverClass.getInstance().findElements(
				By.cssSelector(objectLocators));
		listOfElements = list1;

	}

	/**
	 *Find Element By ID
	 */
	public void findElementById(String objectLocators) {

		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		List<WebElement> list1 = wait.until(ExpectedConditions
				.presenceOfAllElementsLocatedBy(By.id(objectLocators)));

		listOfElements = list1;

	}

	/**
	 *Find Element By Xpath
	 */
	public void findElementByXpath(String objectLocators) {

		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);

		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By
				.xpath(objectLocators)));

		List<WebElement> list1 = wait

		.until(ExpectedConditions.visibilityOfAllElements(WebDriverClass
				.getDriver().findElements(By.xpath(objectLocators))));

		listOfElements = list1;

	}

	/**
	 *Find Element By Name
	 */
	public void findElementByName(String objectLocators) {
		MainTestNG.LOGGER.info("findElementByName==" + objectLocators);

		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.visibilityOfAllElements(WebDriverClass
				.getDriver().findElements(By.name(objectLocators))));
		MainTestNG.LOGGER.info("element found==" + objectLocators);

		List<WebElement> list1 = WebDriverClass.getInstance().findElements(
				By.name(objectLocators));
		MainTestNG.LOGGER.info("list size" + list1.size());
		listOfElements = list1;

	}
	
	/**
	 *Find corresponding method name in existing methods
	 */
	public static Method findMethods(String methodName, Method[] methods) {

		for (int i = 0; i < methods.length; i++) {
			if (methodName.equalsIgnoreCase(methods[i].getName().toString())) {
				return methods[i];
			}
		}
		return null;
	}

	/**
	 *Click on button/checkbox/radio button
	 * @throws AWTException 
	 * @throws InterruptedException 
	 */
	public void click(MethodParameters model) throws AWTException, InterruptedException {
		
		wait1(100);
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(
				ExpectedConditions.elementToBeClickable(model.getElement().get(
						0))).click();
		MainTestNG.LOGGER.info("click method started"
				+ model.getObjectLocators());
		MainTestNG.LOGGER.info("click method completed");
		
		/*wait1(500);
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		JavascriptExecutor executor = (JavascriptExecutor) WebDriverClass.getDriver();
		//executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(By.xpath(model.getObjectLocators())));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(model.getObjectLocators()))); 
		//executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(By.xpath(model.getObjectLocators())));
		executor.executeScript("arguments[0].click();", wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath(model.getObjectLocators()))));
		//WebDriverClass.getDriver().manage().window().maximize();	
		executor.executeScript("window.scrollBy(0,600)");
		wait.until(
				ExpectedConditions.elementToBeClickable(model.getElement().get(
						0))).click();
		//wait1(500);
		MainTestNG.LOGGER.info("click method started"
				+ model.getObjectLocators());
		MainTestNG.LOGGER.info("click method completed");*/
	}
	
	public void popupClickChrome(MethodParameters model) throws AWTException, InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		
		wait.until(
				ExpectedConditions.elementToBeClickable(model.getElement().get(
						0))).click();
		//wait1(100);
		MainTestNG.LOGGER.info("click method started"
				+ model.getObjectLocators());
		MainTestNG.LOGGER.info("click method completed");
	}
	
	/**
	 *Click on Submit button
	 */
	public void submit(MethodParameters model) {
		MainTestNG.LOGGER.info("submit method started"
				+ model.getObjectLocators());
		//model.getElement().get(0).submit();
		model.getElement().get(0).click();
		MainTestNG.LOGGER.info("submit method end");
	}

	/**
	 *Enter data into text field/text area
	 */
	public void enterText(MethodParameters model) {

		MainTestNG.LOGGER.info(" inside enterText(), data to entered into the text=="+ model.getData());
		System.out.println("model.getElement().get(0)=="+ model.getElement().get(0));
	//	WebDriverWait wait = new WebDriverWait(driver, 100);
	//	time.sleep(5);
		WebElement element = model.getElement().get(0);
		element.clear();
		wait1(5);
		model.getElement().get(0).sendKeys(model.getData());
		MainTestNG.LOGGER.info("enterText() exit");
	}

	/**
	 *Read the value present in the text field
	 */
	public void readTextFieldValue(MethodParameters model) {
		MainTestNG.LOGGER.info("inside readTextFieldValue()"
				+ model.getObjectLocators());
		model.getElement().get(0).getAttribute("value");
		MainTestNG.LOGGER.info("end of readTextFieldValue");
	}

	/**
	 *Alert accept meaning click on OK button
	 */
	public void alertAccept(MethodParameters model) {

		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.alertIsPresent());

		MainTestNG.LOGGER.info("inside alertAccept()");

		wait1(1000);

		Alert alert = WebDriverClass.getInstance().switchTo().alert();
		wait1(1000);

		alert.accept();
		MainTestNG.LOGGER.info("completed alertAccept()");
	}
	
	/**
	 *Alert dismiss meaning click on Cancel button
	 */
	public void alertDismiss(MethodParameters model) {
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.alertIsPresent());

		MainTestNG.LOGGER.info("inside alertDismiss()");
		wait1(1000);
		model.getElement().get(0).click();
		Alert alert = WebDriverClass.getInstance().switchTo().alert();
		wait1(1000);
		alert.dismiss();
	}
	
	/**
	 *Alert accept meaning click on OK button as optional
	 */
	public void alertAcceptOptional(MethodParameters model) {

		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait1(1000);
		MainTestNG.LOGGER.info("inside alertAcceptOptional()");
		//wait.until(ExpectedConditions.alertIsPresent());
		try {
			Alert alert = WebDriverClass.getInstance().switchTo().alert();
			wait1(1000);
	
			alert.accept();
		} catch(NoAlertPresentException e) {
			
		}
		MainTestNG.LOGGER.info("completed alertAcceptOptional()");
	}
	
	/**
	 *Alert dismiss meaning click on Cancel button as optional
	 */
	public void alertDismissOptional(MethodParameters model) {
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		MainTestNG.LOGGER.info("inside alertDismissOptional()");
		try {
			Alert alert = WebDriverClass.getInstance().switchTo().alert();
			wait1(1000);
	
			alert.dismiss();
		} catch(NoAlertPresentException e) {
			
		}
		MainTestNG.LOGGER.info("completed alertDismissOptional()");
	}
	
	public void clickWithAlertAcceptIE(MethodParameters model) {

		JavascriptExecutor executor = (JavascriptExecutor) WebDriverClass.getDriver();
		executor.executeScript("window.confirm = function(){ return true;}");
		executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(By.xpath(model.getObjectLocators())));
	}
	
	public void clickWithAlertDismissIE(MethodParameters model) {

		JavascriptExecutor executor = (JavascriptExecutor) WebDriverClass.getDriver();
		executor.executeScript("window.confirm = function(){return false;}");
		executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(By.xpath(model.getObjectLocators())));
	}

	/**
	 *Get the title of the page and verify the title
	 */
	public void verifyTitleOfPage(MethodParameters model) {
		MainTestNG.LOGGER.info("inside verifyTitleOfPage()" + "title=="
				+ WebDriverClass.getInstance().getTitle() + "data from excel="
				+ model.getData());

		wait1(2000);
		String actual = WebDriverClass.getInstance().getTitle().toString();
		String expected = model.getData().toString();
		Assert.assertEquals(actual, expected);
		MainTestNG.LOGGER
				.info("assert verification successful verifyTitleOfPage()");

	}
	/**
	 *Make the driver to wait for specified amount of time
	 */
	public void wait1(long i) {
		try {
			Thread.sleep(i);
		} catch (InterruptedException e) {
			MainTestNG.LOGGER.severe("InvalidFormatException" + e);
		}
	}

	/**
	 *Select from the drop down list,if the drop down element tag is "SELECT" then use this method
	 */
	public void selectDropDownByVisibleText(MethodParameters model) {
		wait1(1000);
		MainTestNG.LOGGER.info("inside selectDropDownByVisibleText");

		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.pollingEvery(2, TimeUnit.SECONDS).until(
				ExpectedConditions.elementToBeClickable(model.getElement().get(
						0)));
		Select sel = new Select(model.getElement().get(0));
		sel.selectByVisibleText(model.getData());
		wait1(1000);
		
	}
	
	/**
	 *Select multiple values from the drop down list,if the drop down element tag is "SELECT" then use this method
	 */
	public void selectFromListDropDownByVisibleText(MethodParameters model) {
		MainTestNG.LOGGER.info("inside selectFromListDropDown");
		wait1(1000);
		
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.pollingEvery(2, TimeUnit.SECONDS).until(
				ExpectedConditions.elementToBeClickable(model.getElement().get(
						0)));
		Select sel = new Select(model.getElement().get(0));
		String[] selectedValue = model.getData().split(",");
		for (String valueToBeSelected : selectedValue) {
			sel.selectByVisibleText(valueToBeSelected);
			wait1(1000);
		}
	}
			
	
	/**
	 *Select the value from a dropdown list by its index
	 */
	public void selectDropDownByIndex(MethodParameters model) {
		MainTestNG.LOGGER.info("inside selectDropDownByIndex");
		Select sel = new Select(model.getElement().get(0));
		sel.selectByIndex(Integer.parseInt(model.getData()));
	}

	/**
	 *Select the value from a dropdown list by its value
	 */
	public void selectDropDownByValue(MethodParameters model) {
		MainTestNG.LOGGER.info("inside selectDropDownByValue");
		Select sel = new Select(model.getElement().get(0));
		sel.selectByValue(model.getData());
	}

	/**
	 *Switch To frame( html inside another html)
	 */
	public void switchToFrame(MethodParameters model) {
		MainTestNG.LOGGER.info("inside switchToFrame");
		WebDriverClass.getInstance().switchTo()
				.frame(model.getObjectLocators());

	}

	/**
	 *Switch back to previous frame or html
	 */
	public void switchOutOfFrame(MethodParameters model) {
		MainTestNG.LOGGER.info("inside switchOutOfFrame");
		WebDriverClass.getInstance().switchTo().defaultContent();

	}

	/**
	 *Select the multiple value from a dropdown list
	 */
	public void selectFromListDropDown(MethodParameters model) {
		MainTestNG.LOGGER.info("inside selectFromListDropDown");
		wait1(1000);
		for (WebElement element1 : model.getElement()) {

			if (element1.getText().equals(model.getData())) {
				element1.click();
				break;
			}
		}

		wait1(1000);
	}

	/**
	 *Navigate to next page
	 */
	public void moveToNextPage(MethodParameters model) {
		WebDriverClass.getInstance().navigate().forward();
	}

	/**
	 *Navigate to previous page
	 */
	public void moveToPreviousPage(MethodParameters model) {
		WebDriverClass.getInstance().navigate().back();
	}

	/**
	 *Maximize the window
	 */
	public void maximizeWindow(MethodParameters model) {
		WebDriverClass.getInstance().manage().window().maximize();
	}

	/**
	 *Reads the text present in the web element
	 */
	public void readText(MethodParameters model) {
		MainTestNG.LOGGER
				.info("getText() method called  and value of getText==*************"
						+ model.getElement().get(0).getText());
		model.getElement().get(0).getText();
		MainTestNG.LOGGER.info("readText completed");
	}
	/**
	 *Quit the application
	 */
	public void quit(MethodParameters model) {
		WebDriverClass.getInstance().quit();
	}

	/**
	 *Closes the driver
	 */
	public void close(MethodParameters model) {
		WebDriverClass.getInstance().close();
	}

	/**
	 *Checks that the element is displayed in the current web page
	 */
	public void isDisplayed(MethodParameters model) {
		model.getElement().get(0).isDisplayed();
	}

	/**
	 *Checks that the element is enabled in the current web page
	 */
	public void isEnabled(MethodParameters model) {
		model.getElement().get(0).isEnabled();
	}

	/**
	 *Selects a radio button
	 */
	public void selectRadioButton(MethodParameters model) {
		model.getElement().get(0).click();
	}

	/**
	 *Refresh the current web page
	 */
	public void refreshPage(MethodParameters model) {
		WebDriverClass.getInstance().navigate().refresh();
	}

	/**
	 *Switch back to the parent window
	 */
	public void switchToParentWindow(MethodParameters model) {
		String parent = WebDriverClass.getInstance().getWindowHandle();
		Set<String> windows = WebDriverClass.getInstance().getWindowHandles();
		if (windows.size() >= 1) {
			for (String child : windows) {
				if (!child.equals(parent)) {
					if (WebDriverClass.getInstance().switchTo()
							.window(child).getTitle()
							.equals(model.getData())) {

						WebDriverClass.getInstance().switchTo()
								.window(parent);
					}

				}
			}
		}
		//WebDriverClass.getInstance().switchTo().window(winHandleBefore);
	}

	/**
	 *Switche to the child window
	 * @throws Exception 
	 */
	public void switchToChildWindow(MethodParameters model) throws Exception {

	//	model.getElement().get(0).click();
		winHandleBefore =  WebDriverClass.getInstance().getWindowHandle();
		String parent = WebDriverClass.getInstance().getWindowHandle();
		Set<String> windows = WebDriverClass.getInstance().getWindowHandles();

		try {
			if (windows.size() > 1) {
				for (String child : windows) {
					if (!child.equals(parent)) {
						if (WebDriverClass.getInstance().switchTo()
								.window(child).getTitle()
								.equals(model.getData())) {

							WebDriverClass.getInstance().switchTo()
									.window(child);
						}

					}
				}
			}
		} catch (Exception e) {

			throw (e);
			
		}

	}

	/**
	 *Scrolls down the page till the element is visible
	 */
	public void scrollElementIntoView(MethodParameters model) {
		wait1(1000);

		MainTestNG.LOGGER.info("scrollElementIntoView started");
		((JavascriptExecutor) WebDriverClass.getDriver())
				.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(By.xpath(model.getObjectLocators())));
		
		((JavascriptExecutor) WebDriverClass.getDriver()).executeScript("window.scrollBy(0,-100)");
		
		wait1(1000);

	}

	/**
	 *Scrolls down the page till the element is visible and clicks on the 
	 *element
	 */
	public void scrollElementIntoViewClick(MethodParameters model) {
		Actions action = new Actions(WebDriverClass.getDriver());
		action.moveToElement(model.getElement().get(0)).click().perform();
	}

	/**
	 *Reads the url of current web page
	 */
	public void readUrlOfPage(MethodParameters model) {
		WebDriverClass.getInstance().getCurrentUrl();
	}
	

	/**
	 *Navigates to the specified url
	 */
	public void navigateToURL(MethodParameters model) {
		WebDriverClass.getInstance().navigate().to(model.getData());
	}

	public static WebElement waitForElement(By by) {
		int count = 0;
		WebDriverWait wait = null;
		while (!(wait.until(ExpectedConditions.presenceOfElementLocated(by))
				.isDisplayed())) {
			wait = new WebDriverWait(WebDriverClass.getInstance(), 60);
			wait.pollingEvery(5, TimeUnit.MILLISECONDS);
			wait.until(ExpectedConditions.visibilityOfElementLocated(by))
					.isDisplayed();
			wait.until(ExpectedConditions.presenceOfElementLocated(by))
					.isDisplayed();
			count++;
			if (count == 100) {
				break;
			}
			return wait.until(ExpectedConditions.presenceOfElementLocated(by));
		}
		return wait.until(ExpectedConditions.presenceOfElementLocated(by));
	}

	/**
	 *Provide Login name for window authentication
	 */
	public static void windowAuthenticationLoginName(MethodParameters model) {

		Alert alert = WebDriverClass.getDriver().switchTo().alert();
		alert.sendKeys(model.getData());
	}

	/**
	 *Provide password for window authentication
	 */
	public static void windowAuthenticationPassword(MethodParameters model) {
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			String letter = model.getData();
			for (int i = 0; i < letter.length(); i++) {
				boolean upperCase = Character.isUpperCase(letter.charAt(i));
				String KeyVal = Character.toString(letter.charAt(i));
				String variableName = "VK_" + KeyVal.toUpperCase();
				Class clazz = KeyEvent.class;
				Field field = clazz.getField(variableName);
				int keyCode = field.getInt(null);

				if (upperCase){
					robot.keyPress(KeyEvent.VK_SHIFT);
				}

				robot.keyPress(keyCode);
				robot.keyRelease(keyCode);

				if (upperCase){
					robot.keyRelease(KeyEvent.VK_SHIFT);
				}
			}
			robot.keyPress(KeyEvent.VK_ENTER);
		} catch (AWTException e) {

			MainTestNG.LOGGER.severe(e.getMessage());

		} catch (NoSuchFieldException e) {

			MainTestNG.LOGGER.severe(e.getMessage());
		} catch (SecurityException e) {

			MainTestNG.LOGGER.severe(e.getMessage());
		} catch (IllegalArgumentException e) {

			MainTestNG.LOGGER.severe(e.getMessage());
		} catch (IllegalAccessException e) {

			MainTestNG.LOGGER.severe(e.getMessage());
		}
	}

	/**
	 * @param model
	 * Lets say there is header menu bar, on hovering the mouse, drop down should be displayed
	 */
	public void dropDownByMouseHover(MethodParameters model) {
		Actions action = new Actions(WebDriverClass.getInstance());

		action.moveToElement(model.getElement().get(0)).perform();
		WebElement subElement = WebDriverClass.getInstance().findElement(
				By.linkText(model.getData()));
		action.moveToElement(subElement);
		action.click().build().perform();
	}
	
	/**
	 * @param model
	 * Lets say there is pagination, on mentioned page to click to move forward
	 */
	public void clickPaginationPage(MethodParameters model) {
		Actions action = new Actions(WebDriverClass.getInstance());

		WebElement page = WebDriverClass.getInstance().findElement(
				By.linkText(model.getData()));
		action.moveToElement(page);
		action.click().build().perform();
	}

	/**
	 *verifies the data present in the text field
	 */
	public void verifyTextFieldData(MethodParameters model) {
		Assert.assertEquals(model.getElement().get(0).getAttribute("value"),
				model.getData());
	}

	
	/**
	 * @param model
	 * Read title of the page and verify it
	 */
	public void readTitleOfPage(MethodParameters model) {
		if (!(titleOfPage == null)) {
			titleOfPage = null;
		}
		titleOfPage = WebDriverClass.getInstance().getTitle();
	}

	
	/**
	 * @param model
	 * File upload in IE browser.
	 * @throws AWTException 
	 */
	public void fileUploadinIE(MethodParameters model) throws AWTException {
		//model.getElement().get(0).click();
		
		WebDriverClass.getDriver().findElement(By.xpath(model.getObjectLocators())).sendKeys("C:\\Users\\jeyaprakash.s\\Downloads\\CompoundTransfer_XPENG2.xls");
		
		/*StringSelection ss = new StringSelection(model.getData());
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Robot r;
		try {
			r = new Robot();
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
			r.keyPress(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_V);
			r.keyRelease(KeyEvent.VK_CONTROL);
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		} catch (AWTException e) {
			MainTestNG.LOGGER.severe(e.getMessage());
			throw(e);
			
		}*/

	}
	
	public void uploadFileChrome(MethodParameters model)
	{
		ChromeDriver driver = null;
		try
		{	
			this.uploadFile(model);
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}finally
		{
			if(driver!=null)
			{
				driver.close();
				driver = null;
			}
		}
	}
	
	/* This is the common method that implement file upload action. */
	public void uploadFile(MethodParameters model)
	{		
		try
		{
			model.getElement().get(0).click();
			//WebDriverClass.getDriver().findElement(By.xpath(model.getObjectLocators()));
			
			Thread.sleep(1000);
			
			// Get Robot Object.
			Robot robotObj = new Robot();
			
			// This is the upload file directory, it should be exist.
			String uploadFileDir = "C:/Workspace";			
			// Navigate to upload file located directory input text box.
			this.pressTabKey(robotObj, 4);
			// Copy file directory.
			this.copyStringToTargetInputBox(robotObj, uploadFileDir);
			
			// This is the upload file name, it should be exist also.
			String uploadFile = model.getData();
			// Click tab key six time to goto upload file input text box.
			this.pressTabKey(robotObj, 6);
			// Copy file name
			this.copyStringToTargetInputBox(robotObj, uploadFile);
				
			
			// Navigate to Open button.
			this.pressTabKey(robotObj, 2);
			// Click the Open button.
			this.pressEnterKey(robotObj);
			
			// Click the form submit button. 
			//driver.findElement(By.name("uploadFileSubmitBtn")).click();
			
			Thread.sleep(1000);
			
			//Get the return message in the returned web page. 
			/*String checkText = driver.findElement(By.id("returnMessage")).getText();
			
			//Check whether the action success or fail by verify the return message.
			if("Your file has been uploaded successful".equals(checkText))
			{
				System.out.println("Upload file success.");
			}else
			{
				System.out.println("Upload file fail.");
			}*/
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/* This method use Robot and system clipboard to copy text to the input text box.*/
	private void copyStringToTargetInputBox(Robot robot, String text) throws InterruptedException
	{
		// First copy text to system clipboard.
		StringSelection stringSel = new StringSelection(text);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Clipboard cb = tk.getSystemClipboard();
		cb.setContents(stringSel, stringSel);

		// Press enter to make it focus before copy text to it.
		this.pressEnterKey(robot);
		
		// Copy the text from system clipboard to input text box.
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

	}
	
	/* Implement press enter key keyboard action.*/
	private void pressEnterKey(Robot robotObj) throws InterruptedException
	{
		robotObj.keyPress(KeyEvent.VK_ENTER);
		robotObj.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(1000);
	}
	
	/* Implement press tab key keyboard action.*/
	private void pressTabKey(Robot robotObj, int pressCount) throws InterruptedException 
	{
		for(int i=0;i<pressCount;i++)
		{
			Thread.sleep(500);
			robotObj.keyPress(KeyEvent.VK_TAB);
		}
	}

	
	/**
	 * @param model
	 * Verify the alert text
	 */
	public void verifyalertText(MethodParameters model) {
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(ExpectedConditions.alertIsPresent());
		wait1(1000);
		Alert alert = WebDriverClass.getInstance().switchTo().alert();
		wait1(1000);
		if (!(alertText == null)) {
			alertText = null;
		}
		alertText = alert.getText();
		Assert.assertTrue(alertText.toString().contains(model.getData()));
		//Assert.assertEquals(alertText.toString(), model.getData());
		alert.accept();
	}


	/**
	 * @param model
	 * SSL errors that appear on IE browser can be resolved
	 */
	public void certificateErrorsIE(MethodParameters model) {

		WebDriverClass
				.getDriver()
				.navigate()
				.to("javascript:document.getElementById('overridelink').click()");
	}

	/**
	 * @param model
	 * Not tested
	 */
	public void DragAndDrop(MethodParameters model) {
		String[] actType = model.getActionType().split("$");
		WebElement sourceElement = WebDriverClass.getDriver().findElement(By.xpath(actType[0]));
		WebElement destinationElement = WebDriverClass.getDriver().findElement(By.xpath(actType[1]));
		Actions action = new Actions(WebDriverClass.getDriver());
		action.dragAndDrop(sourceElement, destinationElement).build().perform();
	}

	/**
	 * @param model
	 * clear the content of the text field
	 */
	public void clear(MethodParameters model) {
		wait1(2000);
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 60);
		wait.until(ExpectedConditions.visibilityOf(model.getElement().get(0)));
		model.getElement().get(0).clear();
	}

	/**
	 *Makes the driver to sleep for specified time
	 * @throws InterruptedException 
	 */
	public void sleep(MethodParameters model) throws Exception {
		try {
			Integer i = Integer.parseInt(model.getData());
			System.out.println(i);
			Thread.sleep(i);
		} catch (Exception e) {
			MainTestNG.LOGGER.info("InterruptedException" + e.getMessage());
			throw(e);
		}
	}

	/**
	 *Verifies the Partial Text present in the element
	 * @throws Exception 
	 */
	public void verifyText(MethodParameters model) throws Exception {
		try{
			MainTestNG.LOGGER.info("model.getElement().get(0).getText()**********"+ model.getElement().get(0).getText());
			MainTestNG.LOGGER.info("model.getData()**********" + model.getData());
			//Assert.assertEquals(model.getData(), model.getElement().get(0).getText().toString());
			Assert.assertTrue(model.getElement().get(0).getText().toString().contains(model.getData()));
			MainTestNG.LOGGER.info("verify text completed");
	   	}catch (Exception e) {
	   		MainTestNG.LOGGER.info("Exception..." + e.getMessage());
			throw(e);
		}
	}
	
	/**
	 *Verifies the Start Text present in the element
	 * @throws Exception 
	 */
	public void verifyStartText(MethodParameters model) throws Exception {
		try{
			MainTestNG.LOGGER.info("model.getElement().get(0).getText()**********"+ model.getElement().get(0).getText());
			MainTestNG.LOGGER.info("model.getData()**********" + model.getData());
			//Assert.assertEquals(model.getData(), model.getElement().get(0).getText().toString());
			Assert.assertTrue(model.getElement().get(0).getText().toString().startsWith(model.getData()));
			MainTestNG.LOGGER.info("verify text completed");
	   	}catch (Exception e) {
	   		MainTestNG.LOGGER.info("Exception..." + e.getMessage());
			throw(e);
		}
	}
	
	/**
	 *Verifies the End Text present in the element
	 * @throws Exception 
	 */
	public void verifyEndText(MethodParameters model) throws Exception {
		try{
			MainTestNG.LOGGER.info("model.getElement().get(0).getText()**********"+ model.getElement().get(0).getText());
			MainTestNG.LOGGER.info("model.getData()**********" + model.getData());
			//Assert.assertEquals(model.getData(), model.getElement().get(0).getText().toString());
			Assert.assertTrue(model.getElement().get(0).getText().toString().endsWith(model.getData()));
			MainTestNG.LOGGER.info("verify text completed");
	   	}catch (Exception e) {
	   		MainTestNG.LOGGER.info("Exception..." + e.getMessage());
			throw(e);
		}
	}

	/**
	 *Verifies that the particular file is exists or not 
	 * @throws Exception 
	 */
	public void verifyFileExists(MethodParameters model) throws Exception {
		try {
			File file = new File(model.getData());
			if (file.exists() && !(file.isDirectory() && file.isFile())) {
				Assert.assertEquals(file.getAbsoluteFile(), model.getData());
			}
		} catch (Exception e) {
			MainTestNG.LOGGER.info("exception occured");
			throw e;
		}

	}

	/**
	 *Downloads a file from IE browser
	 * @throws Exception 
	 */
	public void downloadFileIE(MethodParameters model) throws Exception {
		FileDownloader downloadTestFile = new FileDownloader(WebDriverClass.getDriver());
		String downloadedFileAbsoluteLocation;
		try {
			downloadedFileAbsoluteLocation = downloadTestFile.downloadFile(model.getElement().get(0));
			Assert.assertTrue(new File(downloadedFileAbsoluteLocation).exists());
		} catch (Exception e) {
			MainTestNG.LOGGER.info("exception occured");
			throw e;
		}

	}

	
	/**
	 * @param model
	 * Not tested
	 */
	public void webTableClick(MethodParameters model) {
		String[] actType = model.getObjectLocators().split("\\$");

		WebElement mytable = WebDriverClass.getDriver().findElement(
				By.xpath(actType[0]));

		List<WebElement> rowstable = mytable.findElements(By.tagName("tr"));

		int rows_count = rowstable.size();

		for (int row = 0; row < rows_count; row++) {

			List<WebElement> Columnsrow = rowstable.get(row).findElements(
					By.tagName("td"));

			int columnscount = Columnsrow.size();

			for (int column = 0; column < columnscount; column++) {

				String celtext = Columnsrow.get(column).getText();
				celtext.getClass();
				if(Columnsrow.get(column).getText().equals(model.getData()))
				{
					/*List<WebElement> actions = Columnsrow.get(column).findElements(By.tagName("td"));
					actions.get(0).click();*/
					JavascriptExecutor executor = (JavascriptExecutor) WebDriverClass.getDriver();
					executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(
							By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td/input[@type='checkbox']")));
					/*WebDriverClass.getDriver().findElement(
							By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td/input[@type='checkbox']")).click();
				*/
				}
			}
		}
	}
	
	/**
	 * @param model
	 * Click and open URL link based on primary input value
	 */
	public void dynamicTableLink(MethodParameters model) {
		JavascriptExecutor executor = (JavascriptExecutor) WebDriverClass.getDriver();
		executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(
				By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td/a")));
	/*	WebDriverClass.getDriver().findElement(
				By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td/a")).click();*/
	}
	
	/**
	 * @param model
	 * Update the multiple column on on same row
	 * Based on Static input value name
	 */
	public void dynamicTableEditValue(MethodParameters model) {

		List<WebElement> rowsHeader =  WebDriverClass.getDriver().findElements(By.tagName("th"));
		
		for(int i=0;i<rowsHeader.size();i++) {
			if (rowsHeader.get(i).getText().equals(model.getHeaderValue())) {
				String[] data = model.getData().split(",");
				//int index = i + 1;
				WebDriverClass.getDriver().findElement(
								By.xpath(".//tr/td[contains(text(), '"+ data[0] + "')]/ancestor::tr[1]/td/input[@name='"+model.getObjectLocators()+"']")).clear();
				WebDriverClass.getDriver().findElement(
							    By.xpath(".//tr/td[contains(text(), '"+ data[0] + "')]/ancestor::tr[1]/td/input[@name='"+model.getObjectLocators()+"']")).sendKeys(data[1]);
			}
		}
	}
	
	/**
	 * @param model
	 * Click which row wants to edit
	 * Based on Static input value 
	 */
	public void dynamicTableEdit(MethodParameters model) {
		wait1(1000);
		WebDriverClass.getDriver().findElement(
				By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td/a[contains(text(), '"+ model.getHeaderValue() + "')]")).click();
	}
	
	/**
	 * @param model
	 * Update the drop down column on same row
	 * Based on Static input value name
	 */
	public void dynamicTableDropDownUpdate(MethodParameters model) {

		List<WebElement> rowsHeader =  WebDriverClass.getDriver().findElements(By.tagName("th"));
		
		for(int i=0;i<rowsHeader.size();i++) {
			if (rowsHeader.get(i).getText().equals(model.getHeaderValue())) {
				String[] data = model.getData().split(",");
				//int index = i + 1;
				WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
				wait.pollingEvery(2, TimeUnit.SECONDS).until(
						ExpectedConditions.elementToBeClickable(WebDriverClass.getDriver().findElement(
							    By.xpath(".//tr/td[contains(text(), '"+ data[0] + "')]/ancestor::tr[1]/td/select[@name='"+model.getObjectLocators()+"']"))));
				Select sel = new Select(WebDriverClass.getDriver().findElement(
					    By.xpath(".//tr/td[contains(text(), '"+ data[0] + "')]/ancestor::tr[1]/td/select[@name='"+model.getObjectLocators()+"']")));
				sel.selectByVisibleText(data[1]);
			}
		}
	}
	
	/**
	 * @param model
	 * Validate error message on same row
	 * Based on Static input value name
	 */
	public void dynamicTableVerifyErrorText(MethodParameters model) {

		List<WebElement> rowsHeader =  WebDriverClass.getDriver().findElements(By.tagName("th"));
		
		for(int i=0;i<rowsHeader.size();i++) {
			if (rowsHeader.get(i).getText().equals(model.getHeaderValue())) {
				String[] data = model.getData().split(",");
				//int index = i + 1;
				WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
				wait.pollingEvery(2, TimeUnit.SECONDS).until(
						ExpectedConditions.elementToBeClickable(WebDriverClass.getDriver().findElement(
							    By.xpath(".//tr/td[contains(text(), '"+ data[0] + "')]/ancestor::tr[1]/td/input[@name='"+model.getObjectLocators()+"']"))));
				String errorMessage = WebDriverClass.getDriver().findElement(
					    By.xpath(".//tr/td[contains(text(), '"+ data[0] + "')]/ancestor::tr[1]/td/input[@name='"+model.getObjectLocators()+"']")).getText();
				Assert.assertTrue(errorMessage.contains(data[1]));
				MainTestNG.LOGGER.info("verify error text completed");
			}
		}
	}
	
	/**
	 * @param model
	 * Validate display result
	 * Based on Static input value name
	 */
	public void dynamicTableVerifyResult(MethodParameters model) {
		
		List<WebElement> rowsHeader = WebDriverClass.getDriver().findElements(By.tagName("th"));
		int columnToVerify= -1;
		for (int header = 0; header < rowsHeader.size(); header++) {
			if (rowsHeader.get(header).getText().equals(model.getHeaderValue())) {
				columnToVerify = header;
			}
		}
		
		String[] actType = model.getObjectLocators().split("\\$");

		WebElement mytable = WebDriverClass.getDriver().findElement(
				By.xpath(actType[0]));
		
		List<WebElement> rowstable = mytable.findElements(By.tagName("tr"));

		int rows_count = rowstable.size();
		
		for (int row = 0; row < rows_count; row++) {
			
			List<WebElement> Columnsrow = rowstable.get(row).findElements(
					By.tagName("td"));
		
			int columnscount = Columnsrow.size();

			for (int column = 0; column < columnscount; column++) {
		
					String celtext = Columnsrow.get(column).getText();
					
					if(column == columnToVerify) {
						//String errorMessage = WebDriverClass.getDriver().findElement(
							   // By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td[" +index+ "]")).getText();
						Assert.assertTrue(model.getData().contains(celtext));
			        }
		     }
	    }
		MainTestNG.LOGGER.info("verify text result completed");
	}
	
	/**
	 * @param model
	 * Click Check box row based on primary input value
	 */
	public void dynamicTableCheck(MethodParameters model) {
		JavascriptExecutor executor = (JavascriptExecutor) WebDriverClass.getDriver();
		executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(
				By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td/input[@type='checkbox']")));
	
	/*	WebDriverClass.getDriver().findElement(
				By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td/input[@type='checkbox']")).click();*/
	}
	
	/**
	 * @param model
	 * Click and open URL link based on primary input value if the values having multiple URL Link presented on same row
	 * Based on header input value 
	 */
	public void dynamicTableMultiLink(MethodParameters model) {
		List<WebElement> rowsHeader =  WebDriverClass.getDriver().findElements(By.tagName("th"));
		
		for(int i=0;i<rowsHeader.size();i++) {
			if (rowsHeader.get(i).getText().equals(model.getHeaderValue())) {
				int index = i + 1;
				JavascriptExecutor executor = (JavascriptExecutor) WebDriverClass.getDriver();
				executor.executeScript("arguments[0].click();", WebDriverClass.getDriver().findElement(
						By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td[" +index+ "]/a")));
				
				/*WebDriverClass.getDriver().findElement(
						By.xpath(".//tr/td[contains(text(), '"+ model.getData() + "')]/ancestor::tr[1]/td[" +index+ "]/a")).click();*/
			}
		}
	}
	
	/**
	 * @param model
	 * Select date from date picker
	 */
	public void selectDateFromCalendar(MethodParameters model) {

		String[] actionType = model.getActionType().split("$$");

		String[] data = model.getData().split("/");

		List<String> monthList = Arrays.asList("January", "February", "March",
				"April", "May", "June", "July", "August", "September",
				"October", "November", "December");

		int expMonth;
		int expYear;
		String expDate = null;
		// Calendar Month and Year
		String calMonth = null;
		String calYear = null;
		boolean dateNotFound;

		// WebDriverClass.getDriver()
		// .findElement(By.xpath(".//*[@id='ui-datepicker-div']")).click();
		WebDriverWait wait = new WebDriverWait(WebDriverClass.getDriver(), 30);
		wait.until(
				ExpectedConditions.elementToBeClickable(model.getElement().get(
						0))).click();
		//WebDriverClass.getDriver().findElement(By.xpath(actionType[0])).click();

		dateNotFound = true;

		// Set your expected date, month and year.
		expDate = data[0];
		expMonth = Integer.parseInt(data[1]);
		expYear = Integer.parseInt(data[2]);

		// This loop will be executed continuously till dateNotFound Is true.
		while (dateNotFound) {
			// Retrieve current selected month name from date picker popup.
			calMonth = WebDriverClass.getDriver()
					.findElement(By.className("ui-datepicker-month")).getText();

			// Retrieve current selected year name from date picker popup.
			calYear = WebDriverClass.getDriver()
					.findElement(By.className("ui-datepicker-year")).getText();

			/*
			 * If current selected month and year are same as expected month and
			 * year then go Inside this condition.
			 */
			if (monthList.indexOf(calMonth) + 1 == expMonth
					&& (expYear == Integer.parseInt(calYear))) {
				/*
				 * Call selectDate function with date to select and set
				 * dateNotFound flag to false.
				 */
				//WebDriverClass.getDriver().findElement(By.xpath("//td[text()='"+expMonth+"']/../..//a[text()='"+expDate+"']")).click();
				selectDate(expDate);
				dateNotFound = false;
			}
			// If current selected month and year are less than expected month
			// and year then go Inside this condition.
			else if (monthList.indexOf(calMonth) + 1 < expMonth
					&& (expYear == Integer.parseInt(calYear))
					|| expYear > Integer.parseInt(calYear)) {

				// Click on next button of date picker.
				
				 WebDriverClass .getDriver() .findElement(
				 By.xpath(".//*[@id='ui-datepicker-div']/div[2]/div/a/span"))
				 .click();
				 

				//WebDriverClass.getDriver().findElement(By.xpath(actionType[1]))
					//	.click();
			}
			// If current selected month and year are greater than expected
			// month and year then go Inside this condition.
			else if (monthList.indexOf(calMonth) + 1 > expMonth
					&& (expYear == Integer.parseInt(calYear))
					|| expYear < Integer.parseInt(calYear)) {

				// Click on previous button of date picker.

				
				  WebDriverClass .getDriver() .findElement(
				  By.xpath(".//*[@id='ui-datepicker-div']/div[1]/a[1]/span"))
				  .click();
				 

				//WebDriverClass.getDriver().findElement(By.xpath(actionType[2]))
						//.click();
			}
		}
		wait1(1000);
	}

	/**
	 *Selects the Date
	 */
	public void selectDate(String date) {
		String DateValue = null;
		WebElement datePicker = WebDriverClass.getDriver().findElement(
				By.id("ui-datepicker-div"));

		List<WebElement> noOfColumns = datePicker
				.findElements(By.tagName("td"));

		// Loop will rotate till expected date not found.
		for (WebElement cell : noOfColumns) {
			// Select the date from date picker when condition match.
			if(date.startsWith("0"))
				DateValue = date.replaceAll("0", "");
			else 
				DateValue = date;
			if (cell.getText().equals(DateValue)) {
				cell.findElement(By.linkText(DateValue)).click();
				break;
			}
		}
	}

	/**
	 *Double clicks on the particular element
	 */
	public void doubleClick(MethodParameters model) {
		Actions action = new Actions(WebDriverClass.getDriver());
		action.doubleClick((WebElement) model.getElement()).perform();

	}

	/**
	 *Mouse hovering on the element is performed
	 */
	public void singleMouseHover(MethodParameters model) {
		Actions action = new Actions(WebDriverClass.getDriver());
		action.moveToElement(model.getElement().get(0)).perform();

	}

	/**
	 *Right clicks on the element
	 */
	public void rightClick(MethodParameters model) {
		Actions action = new Actions(WebDriverClass.getDriver());
		action.contextClick((WebElement) model.getElement()).perform();

	}
	/**
	 *Select the Single check boxes
	 */
	public void selectSingleCheckBox(MethodParameters model) {
		boolean res = true;
		
		WebElement element = model.getElement().get(0);
		element.click();
		
	}

	/**
	 *Select the check boxes
	 */
	public void selectCheckBox(MethodParameters model) {
		boolean res = true;

		while (!model.getElement().get(0).isSelected()) {
			model.getElement().get(0).click();
			if (model.getElement().get(0).isSelected()) {
				res = false;
				break;
			}

		}

	}
	/**
	 *Un-check the check box
	 */
	public void deselectCheckBox(MethodParameters model) {
		boolean res = true;

		while (model.getElement().get(0).isSelected()) {
			model.getElement().get(0).click();
			if (!model.getElement().get(0).isSelected()) {
				res = false;
				break;
			}

		}

	}

	/**
	 *Un-check the all check boxes
	 */
	public void deselectAllCheckbox(MethodParameters model) {
		List<WebElement> list = model.getElement();

		for (WebElement element : list) {
			if (element.isSelected()) {
				element.click();
			}
		}
	}

	/**
	 *Selects all the check boxes
	 */
	public void selectAllCheckbox(MethodParameters model) {
		List<WebElement> list = model.getElement();

		for (WebElement element : list) {
			if (!element.isSelected()) {
				element.click();
			}
		}
	}

	/**
	 *Verifies that the particular check box is selected 
	 */
	public void verifyCheckBoxSelected(MethodParameters model) {

		Assert.assertTrue(model.getElement().get(0).isSelected());

	}

	/**
	 *Verifies whether all the check box is selected
	 */
	public void verifyAllCheckBoxSelected(MethodParameters model) {
		for (WebElement element : model.getElement()) {
			Assert.assertTrue(element.isSelected(), "check box is selected");
		}

	}

	/**
	 *Verifies that all the check boxes is not selected
	 */
	public void verifyAllCheckBoxNotSelected(MethodParameters model) {
		for (WebElement element : model.getElement()) {
			Assert.assertFalse(element.isSelected(), "check box not selected");
		}

	}
	
	/**
	 * @param model
	 * File download from Auto It
	 */
	public void filedownloadAUTOIT(MethodParameters model){
		try {
			Runtime.getRuntime().exec(model.getData());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean isElemantPresent(String elementXpath) {
		int count = WebDriverClass.getDriver().findElements(By.xpath(elementXpath)).size();
		
		if(count==0)
			return false;
		else
			return true;
	}

}