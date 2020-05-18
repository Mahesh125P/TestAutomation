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
	
	/*
	 * public void persistApplication() { //Sample Persist Application app =
	 * applicationrepository.getApplicationById(12);
	 * app.setApplicationBrowser("CHROME1"); Screen scr1 = new Screen(); //Add new
	 * Screen scr1.setScreenName("Shipment Plan Gen9");
	 * scr1.setCreatedBy("Mahesh9"); ArrayList<Screen> screenList = new
	 * ArrayList<Screen>(); for(Screen screens: app.getScreen()) { //Update existing
	 * Screen screens.setScreenName(screens.getScreenName() + ".");
	 * screenList.add(screens); } screenList.add(scr1); app.setScreen(screenList);
	 * applicationrepository.save(app);
	 * 
	 * }
	 */
	
	public ArrayList<LookupDTO> getAllApplicationNames() {
		return applicationrepository.getAllApplicationNames();
	}
	
	public List<ApplicationDTO> getApplicationDetails(Integer id) {
		return applicationrepository.getApplicationDetails(id);
	}

	@Transactional
	public boolean saveDetails(MultipartFile file,String appName, String appURL, String appBrowser) {
		
		Workbook workbook = getWorkBook(file);
		Application application = new Application();
		Screen scr = new Screen(); //Add new Screen
		ArrayList<Screen> screenList = new ArrayList<Screen>(); 
		ArrayList<String> screenNamesList = new ArrayList<String>(); 
		
		application = applicationrepository.getApplicationValues(appName);
		
		if(application!= null) {
			for(Screen screens: application.getScreen()) { //Update existing Screen
				screenNamesList.add(screens.getScreenName());
				screenList.add(screens);
			}
		} else {
			application = new Application();
		}
		 
		if(workbook != null) {
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rows = sheet.iterator();
			rows.next();
			if (sheet.getRow(0) != null) {
				while(rows.hasNext()) {
			
					Row row = rows.next();
						
					if(row.getCell(1).getCellType() != null) {
						if(!screenNamesList.contains(row.getCell(1).getStringCellValue())) {
							scr = new Screen();
							scr.setScreenName(row.getCell(1).getStringCellValue());
							scr.setCreatedBy("Manual");
							screenList.add(scr);
						} 
					}
				} 
				application.setApplicationBrowser(appBrowser);
				application.setApplicationName(appName);
				application.setApplicationURL(appURL);
				application.setCreatedBy("Manual");
				
				application.setScreen(screenList);		
				applicationrepository.save(application);
			} 
		} else {
			//application = applicationrepository.getApplicationValues(appName);
			application.setApplicationBrowser(appBrowser);
			application.setApplicationName(appName);
			application.setApplicationURL(appURL);
			application.setCreatedBy("Manual");
			applicationrepository.save(application);
		}
		return true;
	}	

	private Workbook getWorkBook(MultipartFile file) {
		
		Workbook workbook = null;
		if(file != null) {
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
		}
		return workbook;
	}
	
}
