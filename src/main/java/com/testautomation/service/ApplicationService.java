package com.testautomation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testautomation.model.Application;
import com.testautomation.model.Screen;
import com.testautomation.repositories.ApplicationRepository;

/**
 * @author Mahesh Kumar P
 *
 */

@Service
public class ApplicationService {
	
	@Autowired
	ApplicationRepository applicationrepository;
	
	public List<Application> findAllApplication() {
		return applicationrepository.findAll();
	}
	
	public void persistApplication() { //Sample Persist
		Application app = applicationrepository.getApplicationById(12);
		app.setApplicationBrowser("CHROME1");
		Screen scr1 = new Screen(); //Add new Screen
		scr1.setScreenName("Shipment Plan Gen9");
		scr1.setCreatedBy("Mahesh9");
		ArrayList<Screen> screenList = new ArrayList<Screen>(); 
		for(Screen screens: app.getScreen()) { //Update existing Screen
			screens.setScreenName(screens.getScreenName() + ".");
			screenList.add(screens);
		}
		screenList.add(scr1);
		app.setScreen(screenList);
		applicationrepository.save(app);
		
	}
	
}
