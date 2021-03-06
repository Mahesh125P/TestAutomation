package com.testautomation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.testautomation.model.ComponentMapping;
import com.testautomation.model.Screen;
import com.testautomation.model.TestComponent;
import com.testautomation.repositories.ComponentMappingRepository;
import com.testautomation.repositories.ScreenRepository;
import com.testautomation.repositories.TestComponentRepository;

@Service
@Transactional
public class TestComponentService {

	@Autowired
	private TestComponentRepository testComponentRepository;

	@Autowired
	private ComponentMappingRepository componentMappingRepository;
	
	@Autowired
	ScreenRepository screenRepository;
	
	final static String projectfilePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator;

	public ArrayList<LookupDTO> getTestComponentByAppId(Integer appid) {
		return testComponentRepository.findTestComponentByAppId(appid);
	}

	public TestComponent saveTestComponent(TestComponent testComponent,String userName) {
		testComponent.setCreatedBy(userName);
		testComponent.setLastupdatedBy(userName);
		testComponent.setCreatedDate(new Date());
		testComponent.setLastupdatedDate(new Date());
		return testComponentRepository.save(testComponent);

	}
	/*
	 * public ComponentMapping saveTestSuiteMapping(ComponentMapping mapping){
	 * mapping.setCreatedBy("Mahesh4"); mapping.setLastupdatedBy("Mahesh4");
	 * mapping.setCreatedDate(new Date()); mapping.setLastupdatedDate(new Date());
	 * return testComponentRepository.save(mapping);
	 * 
	 * }
	 */

	public List<ComponentMapping> saveComponentMapping(ComponentMappingDTO componentMappingDTO,String userName) {
		List<ComponentMapping> componentMappingList = new ArrayList<>();
		TestComponent testComponent = new TestComponent();
		testComponent.setTestComponentID(Integer.parseInt(componentMappingDTO.componentId));
		Screen screen = new Screen();
		Set<String> appliationNameList = new HashSet<>();
		Set<String> screenNameList = new HashSet<>();
		appliationNameList.add(componentMappingDTO.applicationName);
		String testComponentName = testComponentRepository.findComponentName(testComponent.getTestComponentID());
		
		List<String> selectedScreenList = new ArrayList<>();
		selectedScreenList = componentMappingRepository.findScreenNameByComponentId(testComponent.getTestComponentID());
		
		String automaticPath = projectfilePath + "TestSuite"+File.separator + 
				componentMappingDTO.applicationName + File.separator + "Automatic" + File.separator;
		
		for(String selectedScreen:selectedScreenList) { 
			File deletefile = new File(automaticPath +File.separator + "TestSuite_" + componentMappingDTO.applicationName + "_" + selectedScreen + ".xlsx"); 
			deletefile.delete();
			//delete folder recursively
	        //recursiveDelete(deletefile);
		}
		
		Multimap<String,List<String>> testCaseMap = ArrayListMultimap.create();
		componentMappingDTO.getComponentMapping().stream().forEach(data -> {
			List<String> values = new ArrayList<String>();
			
			ComponentMapping componentMapping = data;
			componentMapping.setCreatedBy(userName);
			componentMapping.setLastupdatedBy(userName);
			componentMapping.setCreatedDate(new Date());
			componentMapping.setLastupdatedDate(new Date());
			componentMapping.setTestComponent(testComponent);
			Integer screenID = screenRepository.getScreenID(data.getScreen().getScreenName());
			screen.setScreenID(screenID);
			screen.setScreenName(data.getScreen().getScreenName());
			componentMapping.setScreen(screen);
			ComponentMapping result = componentMappingRepository.saveAndFlush(componentMapping);
			componentMappingList.add(result);
			
			screenNameList.add(data.getScreen().getScreenName());
			values.add(data.getTestcase());
			testCaseMap.put(data.getScreen().getScreenName(), values);
		});
		
		
		appliationNameList.forEach(appliationName -> { 
			
			String manualPath = projectfilePath + "TestSuite"+File.separator + 
					appliationName + File.separator;
	
			screenNameList.forEach(screenName -> { 
			try {
				 File directory = new File(String.valueOf(automaticPath +File.separator + testComponentName));

				 if(!directory.exists()){
		            directory.mkdir();
				}
				
				File file = new File(automaticPath + File.separator + testComponentName + 
						File.separator + "TestSuite_" + appliationName + "_" + screenName + ".xlsx"); 
				if(file.exists()){
					file.delete();
				}
				
				Files.copy 
						 (Paths.get(manualPath + File.separator + "TestSuite_" + appliationName + "_" + screenName + ".xlsx"),  
								 Paths.get(automaticPath + File.separator + testComponentName + File.separator + "TestSuite_" + appliationName + "_" + screenName + ".xlsx")); 
				
				FileInputStream fip;
				fip = new FileInputStream(file);
				Workbook workbook = new XSSFWorkbook(fip);
				Sheet sheet = workbook.getSheetAt(0);
				
				sheet.forEach(row -> {
					    row.getCell(1).setCellValue("NO");
				});
				
				Set<String> keys = testCaseMap.keySet();
			
				sheet.forEach(row -> {
					if(!row.getCell(0).getStringCellValue().equals("TestCase Name")) {
						for (String key : keys) {
							if(key.equals(screenName)) {
						    	Iterator<List<String>> testcase = testCaseMap.get(key).iterator();
								while(testcase.hasNext()) {
								    	if(('[' +row.getCell(0).getStringCellValue() + ']').equals(testcase.next().toString())) {
								    		row.getCell(1).setCellValue("YES");
								    	} 
							    	}
								}
						    }
					}
		        });
				
				fip.close();
				FileOutputStream output_file =new FileOutputStream(file);//Open FileOutputStream to write updates
				workbook.write(output_file); //write changes
		        output_file.close();  //close the stream
		        workbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		return componentMappingList;
	}
	
	public List<ComponentMapping> deleteComponentMapping(ComponentMappingDTO componentMappingDTO) {
		List<ComponentMapping> componentMappingList = new ArrayList<>();
		TestComponent testComponent = new TestComponent();
		testComponent.setTestComponentID(Integer.parseInt(componentMappingDTO.componentId));
		Screen screen = new Screen();
		Set<String> appliationNameList = new HashSet<>();
		Set<String> screenNameList = new HashSet<>();
		appliationNameList.add(componentMappingDTO.applicationName);
		String testComponentName = testComponentRepository.findComponentName(testComponent.getTestComponentID());
		
		String automaticPath = projectfilePath + "TestSuite"+File.separator + 
				componentMappingDTO.applicationName + File.separator + "Automatic" + File.separator;
		
		Multimap<String,List<String>> testCaseMap = ArrayListMultimap.create();
		componentMappingDTO.getComponentMapping().stream().forEach(data -> {
			List<String> values = new ArrayList<String>();
			
			ComponentMapping componentMapping = data;
			componentMapping.setTestComponent(testComponent);
			Integer screenID = screenRepository.getScreenID(data.getScreen().getScreenName());
			screen.setScreenID(screenID);
			screen.setScreenName(data.getScreen().getScreenName());
			componentMapping.setScreen(screen);
			componentMappingRepository.delete(componentMapping);
			int testOrderNo = data.getTestOrder();
			componentMappingRepository.updateTestOrder(data.getTestComponent().getTestComponentID(),testOrderNo);
			
			//File deletefile = new File(automaticPath +File.separator + "TestSuite_" + componentMappingDTO.applicationName + "_" + data.getScreen().getScreenName() + ".xlsx"); 
			//deletefile.delete();
		
			screenNameList.add(data.getScreen().getScreenName());
			values.add(data.getTestcase());
			testCaseMap.put(data.getScreen().getScreenName(), values);
		});
		
	     appliationNameList.forEach(appliationName -> { 
			
			screenNameList.forEach(screenName -> { 
			try {
				
				FileInputStream fip;
				File file = new File(automaticPath + File.separator + testComponentName + File.separator + "TestSuite_" 
							+ appliationName + "_" +
							screenName + ".xlsx");
				fip = new FileInputStream(file);
				Workbook workbook = new XSSFWorkbook(fip);
				Sheet sheet = workbook.getSheetAt(0);
				
				Set<String> keys = testCaseMap.keySet();
			
				sheet.forEach(row -> {
					if(!row.getCell(0).getStringCellValue().equals("TestCase Name")) {
						for (String key : keys) {
							if(key.equals(screenName)) {
						    	Iterator<List<String>> testcase = testCaseMap.get(key).iterator();
								while(testcase.hasNext()) {
								    	if(('[' +row.getCell(0).getStringCellValue() + ']').equals(testcase.next().toString())) {
								    		row.getCell(1).setCellValue("NO");
								    	} 
							    	}
								}
						    }
					}
		        });
				
				fip.close();
				FileOutputStream output_file =new FileOutputStream(file);//Open FileOutputStream to write updates
				workbook.write(output_file); //write changes
		        output_file.close();  //close the stream
		        workbook.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
		return componentMappingList;
	}
	
	 public static void recursiveDelete(File file) {
	        //if directory, go inside and call recursively
	        if (file.isDirectory()) {
	            for (File f : file.listFiles()) {
	            	 f.delete();
	            }
	        }
	        //call delete to delete files and empty directory
	       
	        System.out.println("Deleted file/folder: "+file.getAbsolutePath());
	    }

	private boolean getRows(int i) {
		// TODO Auto-generated method stub
		return false;
	}

	public List<ComponentMapping> getComponentMapping(Integer componentId) {
		return componentMappingRepository.findAllByComponentId(componentId);
	}

}
