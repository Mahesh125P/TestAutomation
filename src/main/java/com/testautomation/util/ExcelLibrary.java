package com.testautomation.util;
/**
 * The ExcelLibrary class is used to read the data from the excel sheet
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.testautomation.MainTestNG;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.Reporter;

public class ExcelLibrary {
	static Map<String, Workbook> workbooktable = new HashMap<String, Workbook>();
	public static Map<String, Integer> dict = new Hashtable<String, Integer>();
	public static List list = new ArrayList();
	static ReadConfigProperty config = new ReadConfigProperty();

	/**
	 * To get the excel sheet workbook
	 */
	public static Workbook getWorkbook(String path) {
		Workbook workbook = null;
		if (workbooktable.containsKey(path)) {
			workbook = workbooktable.get(path);
		} else {

			try {
				File file = new File(path);
				workbook = WorkbookFactory.create(file);
				workbooktable.put(path, workbook);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				MainTestNG.LOGGER.info("FileNotFoundException" + e);
			} catch (IOException e) {
				e.printStackTrace();
				MainTestNG.LOGGER.info("IOException" + e);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return workbook;
	}

	/**
	 * To get the number of sheets in excel suite
	 */
	public static List<String> getNumberOfSheetsinSuite(String testPath) {
		List<String> listOfSheets = new ArrayList<String>();
		try{
			Workbook workbook = getWorkbook(testPath);
	
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				listOfSheets.add(workbook.getSheetName(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return listOfSheets;
	}

	/**
	 * To get the number of sheets in test data sheet
	 */
	public static List<String> getNumberOfSheetsinTestDataSheet(String testPath) {
		List<String> listOfSheets = new ArrayList<String>();
		try{
			Workbook workbook = getWorkbook(testPath);
			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				if (!(workbook.getSheetName(i)).equalsIgnoreCase(config
						.getConfigValues("TestCase_SheetName"))) {
					listOfSheets.add(workbook.getSheetName(i));
	
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listOfSheets;

	}

	/**
	 * Get the total rows present in excel sheet
	 */
	public static int getRows(String testSheetName, String pathOfFile)
			throws InvalidFormatException, Exception {
		Workbook workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);
		Reporter.log("Total number of rows in the sheet..."+sheet.getLastRowNum());
		return sheet.getLastRowNum();

	}

	/**
	 * Get the total columns inside excel sheet
	 */
	public static int getColumns(String testSheetName, String pathOfFile)
			throws InvalidFormatException, Exception {
		Workbook workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);
		Reporter.log("Total number of columns in the Sheet...."+ sheet.getRow(0).getLastCellNum());
	//	System.out.println("sheet.getRow(0).getLastCellNum()...."+sheet);
		return sheet.getRow(0).getLastCellNum();

	}

	/**
	 * Get the column names inside excel sheet
	 */
	public static List getColumnNames(String testSheetName, String pathOfFile,
			int j) throws InvalidFormatException, IOException {
		Workbook workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);

		for (int i = 0; i <= j; i++) {
			if (sheet.getRow(0).getCell(i) != null) {
				list.add(sheet.getRow(0).getCell(i).getStringCellValue()
						.toString());
				System.out.println("...."+sheet.getRow(0).getCell(i).getStringCellValue().toString());
						
				
			}
		}

		return list;

	}

	/**
	 * Get the total number of rows for each column inside excel sheet
	 */
	public static void getNumberOfRowsPerColumn(String testSheetName,
			String pathOfFile, int j) throws InvalidFormatException,
			IOException {
		Workbook workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);
		int totColumns = sheet.getRow(0).getLastCellNum();
		for (int i = 0; i <= totColumns; i++) {
			if (sheet.getRow(0).getCell(i) != null) {
				list.add(sheet.getRow(0).getCell(i).getStringCellValue()
						.toString());
			}
		}
	}

	/**
	 * Read the content of the cell
	 */
	public static String readCell(int rowNum, int colNum, String testSheetName,
			String pathOfFile) {
		Workbook workbook;
		String cellValue = null;

		workbook = getWorkbook(pathOfFile);
		Sheet sheet = workbook.getSheet(testSheetName);
		Row row = sheet.getRow(rowNum);
		if (row != null) {
			Cell cell = row.getCell(colNum);
			if (cell != null) {
				if (cell.getCellType() == CellType.FORMULA) {
					String data = cell.getStringCellValue();
					cellValue = data;
				} else {
					DataFormatter dataFormatter = new DataFormatter();
					String data = dataFormatter.formatCellValue(cell);
					cellValue = data;
				}
			}
		}
	//	System.out.println("Cellvalue..."+cellValue);
		return cellValue;
	}

	/**
	 * To clear the worktable and list
	 */
	public void clean() {
		workbooktable.clear();
		list.clear();
		
	}

}