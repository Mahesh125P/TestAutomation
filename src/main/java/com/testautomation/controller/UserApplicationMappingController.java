package com.testautomation.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.testautomation.model.Login;
import com.testautomation.model.TestAutomationModel;
import com.testautomation.service.LoginService;
import com.testautomation.service.ResponseDTO;
import com.testautomation.service.TestResultsReportingService;
import com.testautomation.service.UserApplicationMappingService;

/**
 * @author sowmiya.r
 *
 */

@RestController
public class UserApplicationMappingController {

	
	@Autowired
	UserApplicationMappingService usermapping;
	
	@Autowired
	TestResultsReportingService testReportService;

	@Autowired
	LoginService loggedUserDetails;
	
	final static Logger logger = LoggerFactory.getLogger(UserApplicationMappingController.class);
	
	final static String templatefilePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src"
			+ File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator
			+ "template" + File.separator;

	@RequestMapping(value = "/downloadUserAppMappingTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {

		logger.info("Entering @UserApplicationMappingController - downloadTemplate()::::");
		File templateFile = new File(templatefilePath + "UserApplicationMapping.xlsx");
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
		logger.info("Exiting @UserApplicationMappingController - downloadTemplate()::::");
	}

	@RequestMapping(value = "/downloadUserAppMappingDetails")
	public void downloadUserAppMappingDetails(HttpServletRequest request,
            HttpServletResponse response, @RequestBody(required = false) Login userMapDetails) {

		logger.info("Entering @UserApplicationMappingController - downloadUserAppMappingDetails()::::");
		 OutputStream out = null;
	        XSSFWorkbook wb = null;
	        try {
	        	List<Login> userAppMappingsResult = usermapping.getUserApplicationMappings(userMapDetails);	        	
	        	LocalDateTime localDate = LocalDateTime.now();      
	 	        ZonedDateTime zdt = localDate.atZone(ZoneId.systemDefault());
	 	        Date output = Date.from(zdt.toInstant());
	 	        SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");       
	 	        String outputDate = format2.format(output);	 	        
	 	        
	        	String fileName = "UserAndApplicationMappingDetails.xlsx";
	        	
	 	       	response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		        wb = new XSSFWorkbook();
		 
		        Sheet sheet = wb.createSheet("UserApplicationMappings");
		        
		        Row header = sheet.createRow(0);
		        header.createCell(0).setCellValue("User ID");
		        header.createCell(1).setCellValue("Application Name");
		        
		        int rowCount = 1;
		        if(userAppMappingsResult != null && userAppMappingsResult.size() > 0) {
		            for (Login mapping : userAppMappingsResult){
		                Row reportRow = sheet.createRow(rowCount++);
		                reportRow.createCell(0).setCellValue(mapping.getUserName());
			            reportRow.createCell(1).setCellValue(mapping.getApplicationsMappedToUser());
		            }
		        }	        	
	        	out = response.getOutputStream();
	   	        wb.write(out);  
	           }catch(Exception e) {
	           	logger.error("Exception @TestResultsReportingController - downloadExcelTestResults::::");
	       		e.printStackTrace();
	           } finally {
		           	try {
						out.flush();
						out.close();
			            wb.close();
					} catch (IOException e) {
						e.printStackTrace();
					}	            
	           }
		logger.info("Exiting @UserApplicationMappingController - downloadUserAppMappingDetails()::::");
	}

	
	@PostMapping(value = "/uploadUserAppMappingDetails")
	public ResponseEntity<ResponseDTO> updateApplicationDetails(@RequestParam("file") MultipartFile file) {
		
		logger.info("Entering @UserApplicationMappingController - updateApplicationDetails()::::");
		ResponseDTO response = new ResponseDTO();
		try {
			boolean isFlag = usermapping.saveMappingDetails(file);			
		} catch (IllegalStateException e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.setStatus("success");
		logger.info("Exiting @UserApplicationMappingController - updateApplicationDetails()::::");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	
	@RequestMapping(value = "/loadUserAppMappings/{userName}")
	public TestAutomationModel loadUserAppMappings(HttpServletRequest request,@PathVariable String userName) {
		logger.info("Entering @UserApplicationMappingController - loadUserAppMappings()::::");
		
		logger.info("UserName::::"+userName);
		
		TestAutomationModel tAModel = null;
		
		ArrayList<String> allAppsList = usermapping.getAppsByUser(userName);//usermapping.getAllApplicationNames();
		ArrayList<String> allUsersList = usermapping.getAllUsers();
		
		tAModel = new TestAutomationModel();
		tAModel.applicationsList = allAppsList;
		tAModel.testUsersList = allUsersList;
		logger.info("Exiting @UserApplicationMappingController - loadUserAppMappings()::::");
		return tAModel;
	}
}
