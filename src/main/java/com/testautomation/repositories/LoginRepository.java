package com.testautomation.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

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
	
}
