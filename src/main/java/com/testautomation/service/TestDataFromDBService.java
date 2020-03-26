package com.testautomation.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.testautomation.model.Screen;
import com.testautomation.repositories.ScreenRepository;

@Service
public class TestDataFromDBService {

	@Autowired
	ScreenRepository scrRepository;
	
	final static Logger logger = LoggerFactory.getLogger(TestDataFromDBService.class);
	
	public String getQueryDetails( Integer screenID){
		
		return scrRepository.getQueryDetailsByScreen(screenID);	
	}
	
	@Transactional
	public boolean saveQueryDetails( Integer screenID,String screenQuery){
			
		boolean isSaved = false;
		
		Optional<Screen> screenAll = scrRepository.findById(screenID);
		Screen screen = screenAll.get();
		
		screen.setScreenID(screen.getScreenID());
		screen.setScreenName(screen.getScreenName());
		screen.setFile(screen.getFile());
		screen.setCreatedBy(screen.getCreatedBy());
		screen.setApplication(screen.getApplication());
		
		screen.setScreenQuery(screenQuery);
		scrRepository.save(screen);
		/*
		 * int count = scrRepository.updateScreenQueryById(screenID, screenQuery); if
		 * (count > 0) { isSaved = true; }
		 */
		return true;	
	}
}
