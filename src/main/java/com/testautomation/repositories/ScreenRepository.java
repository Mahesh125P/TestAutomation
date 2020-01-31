package com.testautomation.repositories;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.testautomation.model.Screen;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, Integer>{

	
	@Query("from Screen where screenName = ?1")
	public Screen getScreenByName(String screenName);
	
	
}
