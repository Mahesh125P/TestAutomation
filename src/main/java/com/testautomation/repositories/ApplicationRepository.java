package com.testautomation.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.testautomation.model.Application;
import com.testautomation.service.LookupDTO;
import com.testautomation.model.ApplicationDTO;
import com.testautomation.model.Screen;

/**
 * @author Mahesh Kumar P
 *
 */

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer>{

	@Query("from Application")
	public ArrayList<String> getAllApplications();
	
	@Query("from Application where applicationID = ?1")
	public Application getApplicationById(Integer applicationName);
	
	boolean save(Screen screen);
	
	@Query("select new com.testautomation.service.LookupDTO(applicationID,applicationName) from Application order by applicationName")
	public ArrayList<LookupDTO> getAllApplicationNames();

	
	@Query("from Application where applicationName = :applicationName") 
	public Application getApplicationValues(@Param("applicationName") String applicationName);
	
	@Query("Select new com.testautomation.model.ApplicationDTO(application.applicationID,application.applicationName,application.applicationURL,application.applicationBrowser,"
			+ "application.applicationDataBase,application.dataBaseURL,application.dataBaseUserName,application.dataBasePassword,screenName) from Screen where application.applicationID = :id")
	public List<ApplicationDTO> getApplicationDetails(@Param("id") Integer id);
	
	@Query("select applicationID from Application order by applicationName")
	public ArrayList<Integer> getAllAppsList();
	
	@Query("select new com.testautomation.service.LookupDTO(applicationID,applicationName) from Application where applicationName in ( :app) order by applicationName")
	public ArrayList<LookupDTO> getAllAppsByUserDTO(@Param("app")String app);
	
	@Query("select applicationDataBase,dataBaseURL,dataBaseUserName,dataBasePassword from Application where applicationName = :applicationName")
	public String getApplicationDbDetails(@Param("applicationName") String applicationName );
}
