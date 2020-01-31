package com.testautomation.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.testautomation.model.Application;

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
}
