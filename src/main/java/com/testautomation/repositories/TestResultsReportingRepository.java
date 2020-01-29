package com.testautomation.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.testautomation.model.TestResultsReporting;

@Repository
public interface TestResultsReportingRepository extends JpaRepository<TestResultsReporting, Integer> {

	
	
	@Query("select distinct screenTestReport.screenName from TestResultsReporting where applicationTestReport.applicationName = :applicationName")
	public ArrayList<String> getAllScreenNamesByApp(@Param("applicationName") String applicationName);
	
//	@Query("select distinct screenTestReport.screenName from TestResultsReporting")
//	public ArrayList<String> getAllDistinctScreenNames();
}
