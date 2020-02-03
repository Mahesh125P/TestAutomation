package com.testautomation.view;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView;

import com.testautomation.model.TestResultsReporting;

public class ExportXlsView extends AbstractXlsxStreamingView {


	final static Logger logger = LoggerFactory.getLogger(ExportXlsView.class);
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("Entering @ExportXlsView - buildExcelDocument::::");
		
		try{
			LocalDateTime localDate = LocalDateTime.now();  
            
            ZonedDateTime zdt = localDate.atZone(ZoneId.systemDefault());
            Date output = Date.from(zdt.toInstant());
                    
                    
            //String fileName = "TestResultReports-" + localDate.toString() + ".xlsx"; 
            String fileName = "TestResultReports-" + output.toString() + ".xlsx"; 
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
	        
	
	        @SuppressWarnings("unchecked")
	        List<TestResultsReporting> resultReports = (List<TestResultsReporting>) model.get("testResultReports");
	
	        // create excel xls sheet
	        Sheet sheet = workbook.createSheet("TestResults Report");
	
	        
	        // Create a Font for styling header cells
	        Font headerFont = setHeaderFont(workbook);
	 
	        // Create a CellStyle with the font
	        CellStyle headerCellStyle = setHeaderCellStyle(workbook, headerFont);
	         
	        // Create a CellStyle for CampaignSummary mian object with Grey Color
	        CellStyle styleGrey = workbook.createCellStyle();
	        styleGrey.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
	        styleGrey.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
	        
	        Row header = sheet.createRow(0);
	        header.createCell(0).setCellValue("Application");
	        header.createCell(1).setCellValue("Screen");
	        header.createCell(2).setCellValue("TestCase");
	        header.createCell(3).setCellValue("Tested From");
	        header.createCell(4).setCellValue("Tested To");
	        header.createCell(5).setCellValue("TestedBy");
	        header.createCell(6).setCellValue("TestInput");
	        header.createCell(7).setCellValue("TestResult Output");
	        // Create data cells
	        int rowCount = 1;
	        if(resultReports != null && resultReports.size() > 0) {
	        	logger.info("Entering @ExportXlsView - resultReports.size()::::"+resultReports.size());
	        	for (TestResultsReporting resultReport : resultReports){
	        		logger.info("Entering @ExportXlsView - resultReport::::"+resultReport);
		            Row reportRow = sheet.createRow(rowCount++);
		            reportRow.createCell(0).setCellValue(resultReport.getTestRAppName());
		            reportRow.createCell(1).setCellValue(resultReport.getTestRScreenName());
		            reportRow.createCell(2).setCellValue(resultReport.getTestedCaseName());
		            reportRow.createCell(3).setCellValue(resultReport.getTestFromDate());
		            reportRow.createCell(4).setCellValue(resultReport.getTestToDate());
		            reportRow.createCell(5).setCellValue(resultReport.getTestedBy());
		            reportRow.createCell(6).setCellValue(resultReport.getTestInputs());
		            reportRow.createCell(7).setCellValue(resultReport.getTestOutput() != null && resultReport.getTestOutput().equalsIgnoreCase("P") ? "Pass" :"Fail");
		            
		        }
	        	//OutputStream out = response.getOutputStream();
	        	//workbook.write(out); 
	        	
	        }
		} catch(Exception e) {
			logger.error("Error at @ExportXlsView --buildExcelDocumen:::");
			e.printStackTrace();
		}finally {
			
			//workbook.close();
		}
	}

	
	public Font setHeaderFont(Workbook workbook) {
		
		Font font = workbook.createFont();  
        font.setFontHeightInPoints((short)11);  
        font.setFontName("Courier New");  
        font.setItalic(true);  
        font.setStrikeout(true);  
       return font;
	}
	
	
	public CellStyle setHeaderCellStyle(Workbook workbook, Font headerFont) {
		
		CellStyle style = workbook.createCellStyle(); // Creating Style 
		 // Applying font to the style  
        style.setFont(headerFont);  
		
		return style;
	}
}
