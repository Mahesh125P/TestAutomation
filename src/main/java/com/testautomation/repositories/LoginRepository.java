package com.testautomation.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.testautomation.model.Application;
import com.testautomation.model.Login;
import com.testautomation.model.Screen;

/**
 * @author Mahesh Kumar P
 *
 */

@Repository
public interface LoginRepository extends JpaRepository<Login, String>{

	@Query("select applicationName from Application")
	public ArrayList<String> getAllApplicationNames();

	@Query("from Application")
	public ArrayList<Application> getApplicationDetails();	
	
	@Query("select distinct userName from Login")
	public ArrayList<String> getAllUsers();
	
	@Query("select userApplications from Login where userName = :userId")
	public String getAppsByUserId(@Param("userId") String userId);
}
