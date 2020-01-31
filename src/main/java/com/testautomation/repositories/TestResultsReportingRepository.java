package com.testautomation.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testautomation.model.Application;
import com.testautomation.model.TestResultsReporting;

@Repository
public interface TestResultsReportingRepository extends JpaRepository<TestResultsReporting, Integer> {

	
	
	
	@Query("from Application where applicationID = ?1")
	public Application getApplicationById(Integer applicationID);
	
	@Query("from Screen where applicationID = ?1")
	public Application getScreenById(Integer applicationName);
	
	@Query("select distinct testedBy from TestResultsReporting where applicationID = :applicationID")
	public ArrayList<String> getAllTestedUsersByApp(@Param("applicationID") Integer applicationID);
}
