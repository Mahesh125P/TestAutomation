package com.testautomation.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.testautomation.model.Application;
import com.testautomation.model.ApplicationDTO;
import com.testautomation.model.Screen;
import com.testautomation.repositories.ApplicationRepository;

/**
 * @author Mahesh Kumar P
 *
 */

@Service
@Transactional
public class ApplicationService {
	
	@Autowired
	ApplicationRepository applicationrepository;
	
	public List<Application> findAllApplication() {
		return applicationrepository.findAll();
	}
	
	public void persistApplication() { //Sample Persist
		Application app = applicationrepository.getApplicationById(12);
		app.setApplicationBrowser("CHROME1");
		Screen scr1 = new Screen(); //Add new Screen
		scr1.setScreenName("Shipment Plan Gen9");
		scr1.setCreatedBy("Mahesh9");
		ArrayList<Screen> screenList = new ArrayList<Screen>(); 
		for(Screen screens: app.getScreen()) { //Update existing Screen
			screens.setScreenName(screens.getScreenName() + ".");
			screenList.add(screens);
		}
		screenList.add(scr1);
		app.setScreen(screenList);
		applicationrepository.save(app);
		
	}
	
	public ArrayList<LookupDTO> getAllApplicationNames() {
		return applicationrepository.getAllApplicationNames();
	}
	
	public List<ApplicationDTO> getApplicationDetails(Integer id) {
		return applicationrepository.getApplicationDetails(id);
	}
	
	
	/*
	 * public boolean saveDataFromApplication(Application applicationobj) { return
	 * applicationrepository.save(applicationobj); }
	 */

	
	public boolean saveDataFromFileUpload(MultipartFile file) {
		boolean isFlag = false;
		//String extension  = FilenameUtils.getExtension(file.getOriginalFilename());
		//if(extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
		isFlag = ReadDataFromExcel(file);
		
		return false;
	}

	@Transactional
	private boolean ReadDataFromExcel(MultipartFile file) {
		Workbook workbook = getWorkBook(file);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		rows.next();
		while(rows.hasNext()) {
			Row row = rows.next();
			Screen screen = new Screen();
			Application application = new Application();
			
			
			
			if(row.getCell(0).getCellType() != null) {
				 application.setApplicationName(row.getCell(0).getStringCellValue());
				 application.setApplicationURL("http://localhost:9080/Login.action");
				 application.setCreatedBy("Manual");
			}
			application.setApplicationID(
					applicationrepository.getApplicationId(application.getApplicationName()));
			//application.setApplicationID(1);
			if(row.getCell(1).getCellType() != null) {
				screen.setScreenName(row.getCell(1).getStringCellValue());
			}
			screen.setCreatedBy("XZHU26");
			//application.getScreenList().add(screen);
			screen.setApplication(application);
			
			applicationrepository.save(screen);
		}
		
		return true;
	}	

	private Workbook getWorkBook(MultipartFile file) {
		Workbook workbook = null;
		//String extension  = FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
			/*
			 * if(extension.equalsIgnoreCase("xls")) { workbook = new
			 * HSSFWorkbook(file.getInputStream()); } else if
			 * (extension.equalsIgnoreCase("xlsx")) { workbook = new
			 * XSSFWorkbook(file.getInputStream()); }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return workbook;
	}
	
}
