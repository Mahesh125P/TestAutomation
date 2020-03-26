package com.testautomation.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import org.testng.Reporter;

import com.testautomation.MainTestNG;
import com.testautomation.models.TestCase;
import com.testautomation.util.ExcelLibrary;
import com.testautomation.util.ReadConfigProperty;

@Service
public class ReadDataFromDatabaseService {

	static ReadConfigProperty config = new ReadConfigProperty();
	static Map<String, Object> testDataSheet = new HashMap<String, Object>();
	
	/**
	 * Read test data sheet
	 * @throws Exception 
	 */
	public void readTestDataSheet(String from) throws Exception {

		String sheetName;
		StringBuffer dynamicFilePath = new StringBuffer();			
		dynamicFilePath.append(from);
		
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
			
			testDataSheet.put(sheetName, temp1);
		}
	}
}
