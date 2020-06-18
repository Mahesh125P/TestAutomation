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
import java.util.Map.Entry;

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
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.testng.Reporter;

import com.google.common.io.Files;
import com.testautomation.MainTestNG;
import com.testautomation.repositories.ApplicationRepository;
import com.testautomation.repositories.ComponentMappingRepository;
import com.testautomation.repositories.ScreenRepository;
import com.testautomation.repositories.TestComponentRepository;
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
	
	@Autowired
	ApplicationRepository applicationRepository;
	
	@Autowired
	private ComponentMappingRepository componentMappingRepository;
	
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
	public void readLoadTestDataSheet(String from,String screenName, String applicationName) throws Exception {

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
					logger.info("Test Case Sheet Name...." + sheetName + "----" + "pathOFFile" + pathOFFile);
					List listColumnNames = getColumnNames(sheetName, workbook,workbook.getSheet(sheetName).getRow(0).getLastCellNum());
					columnsBySheets.put(sheetName, listColumnNames);
				}
				
		} catch (InvalidFormatException | IOException e) {
			logger.info("InvalidFormatException,IOException,FileNotFoundException...." + e);
		} catch (Exception e) {
			logger.info("Exception....." + e);
			throw (e);
		}finally {
			workbook.close();				
		}
		
		try {
			
			HashMap<String, ArrayList<HashMap<String,String>>> dataBySheets = getDataFromDB(screenName, applicationName);
			boolean iswrite = false;
			File file = null;
			FileInputStream fip = null ;
			//delete the existing old records from excel except headers
			
			
			//if(!columnsBySheets.isEmpty() && data.size()>0) {
				file = new File(dynamicFilePath.toString());
				fip = new FileInputStream(file);
				workbook = new XSSFWorkbook(fip);
				for (Map.Entry<String, List> currentSheetName : columnsBySheets.entrySet()) {
					logger.info("Current Sheet Name....." + currentSheetName);
					ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
					for (Entry<String, ArrayList<HashMap<String, String>>> currentKey : dataBySheets.entrySet()) {			
						if (currentSheetName.getKey().equalsIgnoreCase(currentKey.getKey())) {
							data = currentKey.getValue();
							Sheet sheet = workbook.getSheet(currentSheetName.getKey());
							List listColumnNames = currentSheetName.getValue();
							if (!currentSheetName.getKey().equalsIgnoreCase("CapturedObjectProperties")
									&& !currentSheetName.getKey().equalsIgnoreCase("TestCaseSheet")) {
								for (int i = data.size() + 1; i <= sheet.getLastRowNum(); i++) {
									Row row = sheet.getRow(i);
									deleteRow(sheet, row);
								}
							}
							if (!columnsBySheets.isEmpty() && data.size() > 0) {
								// Create data cells
								int rowCount = 1;
								for (int db_data = 0; db_data < data.size(); db_data++) {
									//Row excelRow = sheet.createRow(rowCount++); //to create new row
									Row excelRow = sheet.getRow(rowCount++); // to get the existing row 
									for (int j = 0; j < listColumnNames.size(); j++) {
										if (!currentSheetName.getKey().equalsIgnoreCase("CapturedObjectProperties")) {
											logger.info("Column Value....." +listColumnNames.get(j)+ ".....::" + getValueFromMap(data.get(db_data), listColumnNames.get(j).toString()));
											if(!getValueFromMap(data.get(db_data), listColumnNames.get(j).toString()).equals("")){
												//excelRow.createCell(j).setCellValue(getValueFromMap(data.get(db_data), listColumnNames.get(j).toString()));//to set values to new row
												excelRow.getCell(j).setCellValue(getValueFromMap(data.get(db_data), listColumnNames.get(j).toString())); // to update the existing cell value
											}
											iswrite = true;
										}
									}
								}
							}
							logger.info(currentSheetName.getKey() + " = " + currentSheetName.getValue());
						}
					}						
				}
			//}
			fip.close();
			if(iswrite) {
				FileOutputStream outputStream = new FileOutputStream(file);
				workbook.write(outputStream);
				outputStream.close();
			}
		} catch (Exception e) {
			logger.info("Exception....." + e);
			throw (e);
		}finally {
			workbook.close();				
		}
	}
	
	
	public HashMap<String, ArrayList<HashMap<String,String>>>  getDataFromDB(String screenName,String applicationName) {	
		
		
		HashMap<String, ArrayList<HashMap<String,String>>> eachSheet = new HashMap<String, ArrayList<HashMap<String,String>>>();
		try {			
			  // To get Data from DB 
			//String application_db = getApplicationDb(screenName);
			String application_db = getApplicationDbDetails(applicationName);
			String[] application_db_Details = application_db.split(",");
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl(application_db_Details[1]);
			dataSource.setUsername(application_db_Details[2]);
			dataSource.setPassword(application_db_Details[3]);
			
			String queryToGetData = getQueryFromDB(screenName);
			String[] queryArrays = getQueryFromDB(screenName).split("@_@");
			for(int i = 0; i < queryArrays.length; i++){
				String[] sheetNameQueries = queryArrays[i].split("@#@"); //0=Sheetname,1=query TC001@#@SELECT@_@TC002@#@SELECT@_@
				logger.info("sheetNameQueries_sheetName....." + sheetNameQueries[0] + "    sheetNameQueries_Queries....." + sheetNameQueries[1]);
				List<Map<String, Object>> rows = new ArrayList<Map<String,Object>>();
				if(!application_db_Details[0].equals("") && application_db_Details[0].equalsIgnoreCase("Oracle")) {
					//rows = jdbcOracleTemplate.queryForList(queryToGetData);
					jdbcOracleTemplate.setDataSource(dataSource);
					rows = jdbcOracleTemplate.queryForList(sheetNameQueries[1]);
				}else if(!application_db_Details[0].equals("") && application_db_Details[0].equalsIgnoreCase("MySql")) {
					//rows = jdbcMySqlTemplate.queryForList(queryToGetData);
					jdbcMySqlTemplate.setDataSource(dataSource);
					rows = jdbcMySqlTemplate.queryForList(sheetNameQueries[1]);
				}else if(!application_db_Details[0].equals("") && application_db_Details[0].equalsIgnoreCase("SqlSever")) {
					//rows = jdbcSqlSeverTemplate.queryForList(queryToGetData);
					jdbcSqlSeverTemplate.setDataSource(dataSource);
					rows = jdbcSqlSeverTemplate.queryForList(sheetNameQueries[1]);
				}
				
				ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
				for (Map<String, Object> map : rows) {
					HashMap<String, String> eachRow = new HashMap<String, String>();
					for (Map.Entry<String, Object> entry : map.entrySet()) {
					    System.out.println(sheetNameQueries[0] +":"+entry.getKey() + ":" + entry.getValue().toString());
					    eachRow.put(entry.getKey(), entry.getValue().toString());
					}
					data.add(eachRow);
				}
				eachSheet.put(sheetNameQueries[0], data);
				
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
		
		return eachSheet;
	}
	
	
	public List getColumnNames(String testSheetName, Workbook workbook,
			int j) throws InvalidFormatException, IOException {
		
		List list = new ArrayList();
		Sheet sheet = workbook.getSheet(testSheetName);

		for (int i = 0; i <= j; i++) {
			if (sheet.getRow(0).getCell(i) != null) {
				list.add(sheet.getRow(0).getCell(i).getStringCellValue().toString());
				logger.info("...."+sheet.getRow(0).getCell(i).getStringCellValue().toString());
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
					readLoadTestDataSheet(to + "DbData_TestCase_" + application + "_" + scr + ".xlsx",scr,application);				
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
				logger.info("Directory already exists ...");
				Files.copy(src.toFile(), dest.toFile());
			} else {
				logger.info("Directory not exists, creating now");
				directory.mkdirs();
				File saveFile = new File(to,"DbData_TestCase_" + application + "_" + scr + ".xlsx");
				//saveFile.mkdirs();
				saveFile.createNewFile();
				Files.copy(src.toFile(), dest.toFile());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void deleteRow(Sheet sheet, Row row) {
		int lastRowNum = sheet.getLastRowNum();
		int rowIndex = row.getRowNum();
		
		if (rowIndex > 0 && rowIndex <= lastRowNum) {
			//sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
			Row removingRow = sheet.getRow(rowIndex);
			if (removingRow != null) {
				sheet.removeRow(removingRow);
				logger.info("Deleting.... "+rowIndex);
			}
		}
		 
		/*
		 * if (rowIndex == lastRowNum) { Row removingRow = sheet.getRow(rowIndex); if
		 * (removingRow != null) { sheet.removeRow(removingRow);
		 * logger.info("Deleting.... "+rowIndex); } }
		 */
	}
	
	public void setuserNDataFromDBMap(String username,String isDbData){
		
		ExcelAction.currentTestAutoUser = username;
		userNDataFromDBMap.put(username, isDbData);
		//ExcelAction.currentTestAutoUser = "sowmiya";
		//userNDataFromDBMap.put("sowmiya", isDbData);
	}
	
	public String getDataFromDBForScenarioBuilding(String applicationName, String queryToGetData) {

		String data = "";
		try {
			// To get Data from DB
			String application_db = getApplicationDbDetails(applicationName);
			String[] application_db_Details = application_db.split(",");
			List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
			dataSource.setUrl(application_db_Details[1]);
			dataSource.setUsername(application_db_Details[2]);
			dataSource.setPassword(application_db_Details[3]);
			if (!application_db_Details[0].equals("") && application_db_Details[0].equalsIgnoreCase("Oracle")) {
				jdbcOracleTemplate.setDataSource(dataSource);
				rows = jdbcOracleTemplate.queryForList(queryToGetData);
			} else if (!application_db_Details[0].equals("") && application_db_Details[0].equalsIgnoreCase("MySql")) {
				jdbcMySqlTemplate.setDataSource(dataSource);
				rows = jdbcMySqlTemplate.queryForList(queryToGetData);
			} else if (!application_db_Details[0].equals("") && application_db_Details[0].equalsIgnoreCase("SqlSever")) {
				jdbcSqlSeverTemplate.setDataSource(dataSource);
				rows = jdbcSqlSeverTemplate.queryForList(queryToGetData);
			}
			for (Map<String, Object> map : rows) {
				HashMap<String, String> eachRow = new HashMap<String, String>();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					logger.info(entry.getKey() + ":" + entry.getValue().toString());
					data = entry.getValue().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
	
    public String getApplicationDbDetails(String applicationName) {
		
		return applicationRepository.getApplicationDbDetails(applicationName);
	}
	
	public List<String> getScreensByComponentId(Integer componentId) {
		
		List<String> screenComponentList = new ArrayList<String>();
		screenComponentList = componentMappingRepository.findScreenNameByComponentId(componentId);
		return screenComponentList;
	}
}
