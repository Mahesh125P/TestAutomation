package com.testautomation.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.testautomation.model.Application;
import com.testautomation.model.ApplicationDTO;
import com.testautomation.service.ApplicationService;
import com.testautomation.service.LookupDTO;
import com.testautomation.service.ResponseDTO;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mahesh Kumar P
 *
 */

@RestController
public class ApplicationController {

	@Autowired
	ApplicationService applicationService;
	
	 final static Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	/*
	 * @PostMapping(value = "/saveApplication") public void startTest1() { //Sample
	 * Persist applicationService.persistApplication();
	 * 
	 * }
	 */

	@GetMapping(value = "/applicationNames")
	public ArrayList<LookupDTO> loadApplicationNames() {
		return this.applicationService.getAllApplicationNames();
	}

	@GetMapping(value = "/applicationDetails/{id}")
	public List<ApplicationDTO> applicationDetails(@PathVariable Integer id) {
		return this.applicationService.getApplicationDetails(id);
	}

	@PostMapping(value = "/updateApplicationDetails")
	public ResponseEntity<ResponseDTO> updateApplicationDetails(@RequestParam(name = "file", required = false) MultipartFile file,
			@RequestParam("appName") String appName, @RequestParam("appURL") String appURL,
			@RequestParam("appBrowser") String appBrowser) throws IOException {
		
		logger.info("Entering @ApplicationController - updateApplicationDetails::::");
	      
		ResponseDTO response = new ResponseDTO();
		boolean isFlag = applicationService.saveDetails(file, appName, appURL, appBrowser);
		if (isFlag) {
			response.setStatus("error");
//			return "FileUpload suceesfully";
		} else {
			response.setStatus("success");
//			return "FileUpload not done successfully";
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/downloadExcel/{id}")
	public void downloadExcel(@PathVariable Integer id, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		logger.info("Entering @ApplicationController - downloadExcel::::");

		XSSFWorkbook wb = null;
		OutputStream out = null;
		try {
			Application application = new Application();
			application.setApplicationID(id);
			List<ApplicationDTO> screenNameReports = applicationService.getApplicationDetails(id);
	
			String fileName = "ApplicationScreenDetails" + ".xlsx";
	
			response.setContentType("application/vnd.ms-excel");
	
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			wb =new XSSFWorkbook();
	
			Sheet sheet = wb.createSheet("Screen Name Report");
	
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Application Name");
			header.createCell(1).setCellValue("Screen Name");
	
			// Create data cells
			int rowCount = 1;
			if (screenNameReports != null && screenNameReports.size() > 0) {
				for (ApplicationDTO resultReport : screenNameReports) {
					Row reportRow = sheet.createRow(rowCount++);
					reportRow.createCell(0).setCellValue(resultReport.getApplicationName());
					reportRow.createCell(1).setCellValue(resultReport.getScreenName());
				}
			}
	
			out = response.getOutputStream();
	
			wb.write(out);
		 }catch(Exception e) {
        	logger.error("Exception @ApplicationController - downloadExcelScreenResults::::");
    		e.printStackTrace();
         } finally {
        	out.flush();
    		out.close();
    		wb.close();
         }
	}

	@RequestMapping(value = "/downloadTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {

		File templateFile = new File(Paths.get("").toAbsolutePath().toString() + File.separator + "src"
	            + File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "template" + File.separator + "ApplicationScreenDetails.xlsx");
	            
		if (templateFile.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(templateFile.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + templateFile.getName() + "\""));
			response.setContentLength((int) templateFile.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(templateFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}
