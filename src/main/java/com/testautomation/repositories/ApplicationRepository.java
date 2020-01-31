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
	
	@Query("select new com.testautomation.service.LookupDTO(applicationID,applicationName) from Application")
	public ArrayList<LookupDTO> getAllApplicationNames();

	
	@Query("select distinct applicationID from Application where applicationName = :applicationName") 
	public Integer getApplicationId(@Param("applicationName") String applicationName);
	
	@Query("Select new com.testautomation.model.ApplicationDTO(application.applicationID,application.applicationName,application.applicationURL,screenName) from Screen where application.applicationID = :id")
	public List<ApplicationDTO> getApplicationDetails(@Param("id") Integer id);
}
