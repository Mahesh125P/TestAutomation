package com.testautomation.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.testautomation.model.Login;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.repositories.ApplicationRepository;
import com.testautomation.repositories.LoginRepository;

@Service
public class UserApplicationMappingService {

	@Autowired
	LoginRepository loginrepository;
	
	@Autowired
	ApplicationRepository applicationrepository;
	
	@PersistenceContext
    private EntityManager em;
	
	final static Logger logger = LoggerFactory.getLogger(UserApplicationMappingService.class);
	
	public ArrayList<String> getAllUsers(){		
		return loginrepository.getAllUsers();
	}
	
	public ArrayList<String> getAllApplicationNames(){		
		return loginrepository.getAllApplicationNames();
	}
	
	public ArrayList<String> getAppsByUser(String user){
		
		String userApps = loginrepository.getAppsByUserId(user);
		ArrayList<String> userAppList = new ArrayList<String>(Arrays.asList(userApps.split(",")));
		return userAppList;
	}
	
	public boolean saveMappingDetails(MultipartFile file) {
		
		logger.info("Entering @UserApplicationMappingService - saveMappingDetails()::::");
		Workbook workbook = getWorkBook(file);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet.iterator();
		Login userAppMapping = new Login();
		ArrayList<String> allUsersList = getAllUsers();
		if (sheet.getRow(0) != null) {
			rows.next();
			while(rows.hasNext()) {
				Row row = rows.next();
				if(row.getCell(0).getCellType() != null) {
					if(allUsersList.contains(row.getCell(0).getStringCellValue())) {
						userAppMapping.setUserName(row.getCell(0).getStringCellValue());
						userAppMapping.setUserApplications(row.getCell(1).getStringCellValue());
						loginrepository.save(userAppMapping);
					}
				}
		 }			
			
		}
		logger.info("Exiting @UserApplicationMappingService - saveMappingDetails()::::");
		return true;
	}
	
	
	public Workbook getWorkBook(MultipartFile file) {
		Workbook workbook = null;
		try {
			workbook = new XSSFWorkbook(file.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return workbook;
	}
	
	public List<Login> getUserApplicationMappings(Login mappingDetails){
		
		logger.info("Entering @UserApplicationMappingService - getUserApplicationMappings()::::");
		List<Login> userAppMappings = new ArrayList<Login>();
		
		List<Login> mappingResults = getAllMappingsResults(mappingDetails);
		Iterator it = mappingResults.iterator();
		while(it.hasNext()){
		     Object[] rowObj = (Object[]) it.next();
		     Login userAppMaps = new Login();
		     userAppMaps.setUserName(rowObj[0].toString());
		     userAppMaps.setApplicationsMappedToUser(rowObj[1].toString());
		     userAppMappings.add(userAppMaps);
		}
		logger.info("Exiting @UserApplicationMappingService - getUserApplicationMappings()::::");
		return userAppMappings;
	}
	
	
	public List<Login> getAllMappingsResults(Login userMap){
		
		logger.info("Entering @UserApplicationMappingService - getAllMappingsResults::::");
		List<Login> results = null;
		try {
			StringBuilder appBuilder = new StringBuilder();
			if (userMap.getApplicationsList() != null && userMap.getApplicationsList().size() > 0) {
				for(String app : userMap.getApplicationsList()) {
					if(appBuilder.length()==0){
						appBuilder.append(" TAM01_APPLICATIONS like '%").append(app).append("%'");
					}else{
						appBuilder.append("  OR TAM01_APPLICATIONS like '%").append(app).append("%'");
					}
				}
			}
			StringBuilder userBuilder = new StringBuilder();
			if (userMap.getUsersList() != null && userMap.getUsersList().size() > 0) {
				for(String user : userMap.getUsersList()) {
					if(userBuilder.length()==0){
						userBuilder.append("'").append(user).append("'");
					}else{
						userBuilder.append(",'").append(user).append("'");
					}
				}
			}
			StringBuffer searchQuery = new StringBuffer(" SELECT  TAM01_USER_ID,TAM01_APPLICATIONS FROM KTAM01_USER WHERE 1=1  ");
			
			if (userMap.getApplicationsList() != null && userMap.getApplicationsList().size() > 0) {
				searchQuery.append(" AND (" +appBuilder+ ")");
			}
			
			if (userMap.getUsersList() != null && userMap.getUsersList().size() > 0) {
				searchQuery.append(" AND TAM01_USER_ID IN(" + userBuilder+ ")");
			}
			logger.info("@UserApplicationMappingService - getAllMappingsResults::searchQuery::"+searchQuery);
			Query qry = em.createNativeQuery(searchQuery.toString());
			results =qry.getResultList();
			logger.info("Exiting @UserApplicationMappingService - getAllMappingsResults::::"+searchQuery.toString());
		
		}catch(Exception e) {
        	logger.error("Exception @UserApplicationMappingService - getAllMappingsResults::::");
    		e.printStackTrace();
        }
		return results;	
	
	}
	
	public ArrayList<LookupDTO> getAllAppsByUserDTO(String user) {
		
		ArrayList<LookupDTO> userAppListDTO = new ArrayList<LookupDTO>();
		try {
			String userApps = loginrepository.getAppsByUserId(user);
			List<LookupDTO> results = null;
			ArrayList<String> userAppList = new ArrayList<String>(Arrays.asList(userApps.split(",")));
			userApps = "'" + userApps.replace(",", "','") + "'";
			for(String app : userAppList) {
				System.out.println("app::::"+app);
				userAppListDTO.addAll(applicationrepository.getAllAppsByUserDTO(app));
				for(LookupDTO app1 : userAppListDTO) {
					System.out.println(app1.getName());
				}
			}
			
			
		}catch(Exception e) {
        	logger.error("Exception @UserApplicationMappingService - getAllAppsByUserDTO::::");
    		e.printStackTrace();
        }
		return userAppListDTO;
	}
}