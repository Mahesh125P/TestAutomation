package com.testautomation.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.testautomation.model.Screen;
import com.testautomation.service.LookupDTO;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer>{

	
	@Query("from Screen where screenName = ?1")
	public Screen getScreenByName(String screenName);
	
	@Query("select new com.testautomation.service.LookupDTO(screenID,screenName) from Screen where application.applicationID = :applicationID")
	public ArrayList<LookupDTO> getAllScreensByApp(@Param("applicationID") Integer applicationID );
	
	@Query("select screenID from Screen where application.applicationID = :applicationID order by screenName")
	public ArrayList<Integer> getAllScreensByAppList(@Param("applicationID") Integer applicationID );
	
	@Query("select screenQuery from Screen where screenID = :screenID")
	public String getQueryDetailsByScreen(@Param("screenID") Integer screenID );
	
	@Query("select screenQuery from Screen where screenName = :screenName")
	public String getQueryDetailsByScreenName(@Param("screenName") String screenName );
	
	@Query("select  application.applicationDataBase from Screen where screenName = :screenName")
	public String getApplicationDbByScreenName(@Param("screenName") String screenName );
}
