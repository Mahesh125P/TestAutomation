package com.testautomation.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testautomation.model.Application;
import com.testautomation.model.Screen;
import com.testautomation.model.TestResultsReporting;
import com.testautomation.service.LookupDTO;

@Repository
public interface TestResultsReportingRepository extends JpaRepository<TestResultsReporting, Integer> {

	
	
	
	@Query("from Application where applicationID = ?1")
	public Application getApplicationById(Integer applicationID);
	
	@Query("from Application where applicationName = ?1")
	public Application getApplicationByName(String applicationName);
	
	@Query("from Screen where applicationID = ?1")
	public Application getScreenById(Integer applicationID);
	
	@Query("from Screen where screenName = ?1")
	public Screen getScreenByName(String screenName);
	

	//@Query("select distinct testedBy from TestResultsReporting where applicationID = :applicationID")
	//public ArrayList<String> getAllTestedUsersByApp(@Param("applicationID") Integer applicationID);
}
