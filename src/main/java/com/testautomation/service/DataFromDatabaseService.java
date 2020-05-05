package com.testautomation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.testng.Reporter;

import com.google.common.io.Files;
import com.testautomation.MainTestNG;
import com.testautomation.repositories.ScreenRepository;
import com.testautomation.util.ExcelAction;
import com.testautomation.util.ReadConfigProperty;

/**
 * @author sowmiya.r
 *
 */
@Service
public class DataFromDatabaseService {

	
	
	@Autowired
	@Qualifier("jdbcOracleDb")
	JdbcTemplate jdbcOracleTemplate; 

	@Autowired
	@Qualifier("jdbcSqlServer")
	JdbcTemplate jdbcSqlSeverTemplate;
	
	@Autowired
	@Qualifier("jdbcMySql")
	JdbcTemplate jdbcMySqlTemplate;
	
	@Autowired
	ScreenRepository scrRepository;
	
	final static Logger logger = LoggerFactory.getLogger(DataFromDatabaseService.class);
	
	static ReadConfigProperty config = new ReadConfigProperty();
	static Map<String, Workbook> workbooktable = new HashMap<String, Workbook>();
	
	final static String projectfilePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator;

	public static HashMap<String,String> userNDataFromDBMap = new HashMap<String,String>();	
	/**
	 * Read test data sheet
	 * @throws Exception 
	 */
	public void readLoadTestDataSheet(String from,String screenName) throws Exception {

		String sheetName;
		StringBuffer dynamicFilePath = new StringBuffer();			
		dynamicFilePath.append(from);
		
		String pathOFFile = dynamicFilePath.toString();
		List<String> listOfSheets = new ArrayList<String>();
		Map<String, List> columnsBySheets = new HashMap<String, List>();;
		
		Workbook workbook = null;
		try {
			if (workbooktable.containsKey(dynamicFilePath.toString())) {
				workbook = workbooktable.get(dynamicFilePath.toString());
			} else {
				File file = new File(dynamicFilePath.toString());
				workbook = WorkbookFactory.create(file);
				workbooktable.put(dynamicFilePath.toString(), workbook);

			}for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
					if (!(workbook.getSheetName(i)).equalsIgnoreCase(config
							.getConfigValues("TestCase_SheetName"))) {
						listOfSheets.add(workbook.getSheetName(i));
					}	
				}
			
				for (int i = 0; i < listOfSheets.size(); i++) {
					sheetName = listOfSheets.get(i);					
					Reporter.log("Test Case Sheet Name...." + sheetName + "----" + "pathOFFile" + pathOFFile);
					List listColumnNames = getColumnNames(sheetName, workbook,workbook.getSheet(sheetName).getRow(0).getLastCellNum());
					columnsBySheets.put(sheetName, listColumnNames);
				}
				
		} catch (InvalidFormatException | IOException e) {
			MainTestNG.LOGGER.info("InvalidFormatException,IOException,FileNotFoundException...." + e);
		} catch (Exception e) {
			MainTestNG.LOGGER.info("Exception....." + e);
			throw (e);
		}finally {
			workbook.close();				
		}
		
		try {
			
			ArrayList<HashMap<String,String>> data = getDataFromDB(screenName);
			boolean iswrite = false;
			File file = null;
			FileInputStream fip = null ;
			//delete the existing old records from excel except headers
			
			
			//if(!columnsBySheets.isEmpty() && data.size()>0) {
				file = new File(dynamicFilePath.toString());
				fip = new FileInputStream(file);
				workbook = new XSSFWorkbook(fip);
				for (Map.Entry<String, List> currentSheetName : columnsBySheets.entrySet()) {
					Sheet sheet = workbook.getSheet(currentSheetName.getKey());
					List listColumnNames = currentSheetName.getValue();
					if(!currentSheetName.getKey().equalsIgnoreCase("CapturedObjectProperties") && !currentSheetName.getKey().equalsIgnoreCase("TestCaseSheet") ) {
						 for(int i=1; i<= sheet.getLastRowNum(); i++){
					            Row row = sheet.getRow(i);
					            deleteRow(sheet, row);
					        }
					}
					 if(!columnsBySheets.isEmpty() && data.size()>0) { 
						// Create data cells
				        int rowCount = 1;
				        	for(int db_data = 0; db_data < data.size(); db_data++ ) {
					        	Row excelRow = sheet.createRow(rowCount++);	
					        	for (int j = 0; j < listColumnNames.size(); j++) {
					        	if(!currentSheetName.getKey().equalsIgnoreCase("CapturedObjectProperties")) {
										excelRow.createCell(j).setCellValue(getValueFromMap(data.get(db_data),listColumnNames.get(j).toString()));
										iswrite = true;
									}
								}
						}
					 }
					System.out.println(currentSheetName.getKey() + " = " + currentSheetName.getValue());
				}
			//}
			fip.close();
			if(iswrite) {
				FileOutputStream outputStream = new FileOutputStream(file);
				workbook.write(outputStream);
				outputStream.close();
			}
		} catch (Exception e) {
			MainTestNG.LOGGER.info("Exception....." + e);
			throw (e);
		}finally {
			workbook.close();				
		}
	}
	
	
	public ArrayList<HashMap<String,String>>  getDataFromDB(String screenName) {		
		
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		try {			
			  // To get Data from DB 
			String application_db = getApplicationDb(screenName);
			String queryToGetData = getQueryFromDB(screenName);
			List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
			if(!application_db.equals("") && application_db.equalsIgnoreCase("Oracle")) {
				rows = jdbcOracleTemplate.queryForList(queryToGetData);
			}else if(!application_db.equals("") && application_db.equalsIgnoreCase("MySql")) {
				rows = jdbcMySqlTemplate.queryForList(queryToGetData);
			}else if(!application_db.equals("") && application_db.equalsIgnoreCase("SqlSever")) {
				rows = jdbcSqlSeverTemplate.queryForList(queryToGetData);
			}
			for (Map<String, Object> map : rows) {
				HashMap<String, String> eachRow = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
				    System.out.println(entry.getKey() + ":" + entry.getValue().toString());
				    eachRow.put(entry.getKey(), entry.getValue().toString());
				}			
				data.add(eachRow);
			}
			 
			 //Compound Transfer - Hot Coded Values
			/*
			 * HashMap<String,String> eachRow = new HashMap<String,String>();
			 * eachRow.put("EnterDestinationLoc","WUHUVSC");
			 * eachRow.put("EnterRoute","R0016"); eachRow.put("EnterSourceLoc","CQ2VDC1");
			 * eachRow.put("EnterModel","All"); eachRow.put("EnterStatus","RFS");
			 * eachRow.put("EnterTransferUnit","1");
			 * eachRow.put("OfflineFromDate","15/09/2015");
			 * eachRow.put("UploadFilePath",""); eachRow.put("VIN","LVSHCFAN6FE000001");
			 * eachRow.put("Username","XPENG2"); eachRow.put("Password","ss");
			 * eachRow.put("EnterTransferPlanNo","CQT20040801");
			 * eachRow.put("EnterApprovalComments","Ok");
			 * eachRow.put("VerifyText","Selected VIN(s) Approved. Routed to Approval");
			 * data.add(eachRow);
			 */
			 
			
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	
	public List getColumnNames(String testSheetName, Workbook workbook,
			int j) throws InvalidFormatException, IOException {
		
		List list = new ArrayList();
		Sheet sheet = workbook.getSheet(testSheetName);

		for (int i = 0; i <= j; i++) {
			if (sheet.getRow(0).getCell(i) != null) {
				list.add(sheet.getRow(0).getCell(i).getStringCellValue().toString());
				System.out.println("...."+sheet.getRow(0).getCell(i).getStringCellValue().toString());
			}
		}
		return list;
	}
	
	
	public String getValueFromMap(HashMap<String,String> dataValues,String forKey) {
		String currentValue = "";
		for (Map.Entry<String, String> currentKey : dataValues.entrySet()) {
			if(forKey.equalsIgnoreCase(currentKey.getKey())) {
				currentValue = currentKey.getValue();
			}
		}
		return currentValue;
	}
	
	
	public String getQueryFromDB(String screenName) {
		
		return scrRepository.getQueryDetailsByScreenName(screenName);
	}
	
	public String getApplicationDb(String screenName) {
		
		return scrRepository.getApplicationDbByScreenName(screenName);
	}
	
	
	public void doCopyFileToDbData(String userName, String application, List<String> screen) {
			
			String from = null;
			String to = null;
			try {			
				for(String scr : screen) {
					from = projectfilePath + "TestCase" + File.separator + application + File.separator + "TestCase_" + application + "_" + scr + ".xlsx";
					to =  projectfilePath + "DBData"+ File.separator  + application + File.separator ;
					copyFile(application,scr,from,to);
					readLoadTestDataSheet(to + "DbData_TestCase_" + application + "_" + scr + ".xlsx",scr);				
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
	}
	
	public void copyFile(String application, String scr, String from, String to) {
		
		Path src = Paths.get(from); 
		Path dest = Paths.get(to + "DbData_TestCase_" + application + "_" + scr + ".xlsx");
		try {
			
			File directory = new File(to);
			if (directory.exists()) {
				System.out.println("Directory already exists ...");
				Files.copy(src.toFile(), dest.toFile());
			} else {
				System.out.println("Directory not exists, creating now");
				File saveFile = new File(to,  "DbData_TestCase_" + application + "_" + scr + ".xlsx");
				saveFile.mkdir();
				Files.copy(src.toFile(), dest.toFile());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRow(Sheet sheet, Row row) {
		int lastRowNum = sheet.getLastRowNum();
		int rowIndex = row.getRowNum();
		/*
		 * if (rowIndex >= 0 && rowIndex < lastRowNum) { sheet.shiftRows(rowIndex + 1,
		 * lastRowNum, -1); }
		 */
		if (rowIndex == lastRowNum) {
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
				System.out.println("Deleting.... ");
			}
		}
	}
	
	public void setuserNDataFromDBMap(String username,String isDbData){
		
		//ExcelAction.currentTestAutoUser = username;
		//userNDataFromDBMap.put(userName, "Yes");
		ExcelAction.currentTestAutoUser = "sowmiya";
		userNDataFromDBMap.put("sowmiya", isDbData);
	}
	
	public String getDataFromDBForScenarioBuilding(String screenName, String queryToGetData) {

		String data = "";
		try {
			// To get Data from DB
			String application_db = getApplicationDb(screenName);
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			if (!application_db.equals("") && application_db.equalsIgnoreCase("Oracle")) {
				 rows = jdbcOracleTemplate.queryForList(queryToGetData);
			} else if (!application_db.equals("") && application_db.equalsIgnoreCase("MySql")) {
				 rows = jdbcMySqlTemplate.queryForList(queryToGetData);
			} else if (!application_db.equals("") && application_db.equalsIgnoreCase("SqlSever")) {
				// rows = jdbcSqlSeverTemplate.queryForList(queryToGetData);
			}
			for (Map<String, Object> map : rows) {
				HashMap<String, String> eachRow = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					System.out.println(entry.getKey() + ":" + entry.getValue().toString());
					data = entry.getValue().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
	
}
