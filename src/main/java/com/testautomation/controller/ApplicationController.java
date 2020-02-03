package com.testautomation.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.testautomation.model.Application;
import com.testautomation.model.ApplicationDTO;
import com.testautomation.model.Screen;
import com.testautomation.service.ApplicationService;
import com.testautomation.service.LookupDTO;

/**
 * @author Mahesh Kumar P
 *
 */

@RestController
public class ApplicationController {

	@Autowired
	ApplicationService applicationService;
	
	@PostMapping(value = "/saveApplication")
	public void startTest1() {  //Sample Persist
		applicationService.persistApplication();
		
	}
	
	@GetMapping(value = "/applicationNames")
	public ArrayList<LookupDTO> loadApplicationNames() {
		return this.applicationService.getAllApplicationNames();
	}
	   
	@GetMapping(value = "/applicationDetails/{id}")
    public List<ApplicationDTO> applicationDetails(@PathVariable Integer id) {
		return this.applicationService.getApplicationDetails(id);
	}

	/*
	 * @PutMapping(value = "/update/{id}") public RedirectAttributes
	 * updateApplicationDatails(@PathVariable int id, @RequestBody Application
	 * applicationobj, RedirectAttributes redirectAttributes) { boolean isFlag =
	 * applicationService.saveDataFromApplication(applicationobj); if(isFlag) {
	 * return redirectAttributes.addFlashAttribute(
	 * "successMessage","Application details update suceesfully"); } else { return
	 * redirectAttributes.addFlashAttribute(
	 * "errorMessage","Application details not done successfully"); } }
	 */
	   
	@PutMapping(value = "/fileUpload")
	public RedirectAttributes uploadFile(@ModelAttribute Screen screen, RedirectAttributes  redirectAttributes) {
		boolean isFlag = applicationService.saveDataFromFileUpload(screen.getFile());
		if(isFlag) {
			return redirectAttributes.addFlashAttribute("successMessage","FileUpload suceesfully");
		} else {
			return redirectAttributes.addFlashAttribute("errorMessage","FileUpload not done successfully");
		}
	}
		
	@GetMapping(value = "/downloadExcel/{id}")
	public void downloadExcelTestResults(@PathVariable Integer id,
	       HttpServletRequest request,
	       HttpServletResponse response) throws IOException {

		//logger.info("Entering @TestResultsReportingController - downloadExcelTestFile::::");
	       
		Application application = new Application();
		application.setApplicationID(id);
		List<ApplicationDTO> screenNameReports = applicationService.getApplicationDetails(id);
	
		     
		String fileName = "ApplicationScreenDetails" +  ".xlsx";
		
		response.setContentType("application/vnd.ms-excel");
		 
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		XSSFWorkbook wb = new XSSFWorkbook();
	
		Sheet sheet = wb.createSheet("Screen Name Report");
	
		Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Application Name");
        header.createCell(1).setCellValue("Screen Name");
		   
        // Create data cells
        int rowCount = 1;
        if(screenNameReports != null && screenNameReports.size() > 0) {
      
	        for (ApplicationDTO resultReport : screenNameReports){
	        
	           Row reportRow = sheet.createRow(rowCount++);
	         //  reportRow.createCell(0).setCellValue(resultReport.getApplicationName());
	           reportRow.createCell(1).setCellValue(resultReport.getScreenName());
             }
        }
		   
		 OutputStream out = response.getOutputStream();
		 wb.write(out);
		   
		 
		 out.flush();
		 out.close();
	}
}
