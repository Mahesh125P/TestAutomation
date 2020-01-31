package com.testautomation.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.testautomation.model.Application;
import com.testautomation.model.Screen;
import com.testautomation.repositories.ScreenRepository;


/**
 * @author Mahesh Kumar P
 *
 */

@Service
public class ScreenService {

	@Autowired
	ScreenRepository screenRepository;

	public Screen getScreenByName(String screenName) {
		return screenRepository.getScreenByName(screenName);
	}
	
	public void persistScreen() { //Sample for Test
		Screen scr1 = new Screen();
		Screen scr2 = new Screen();
		
		Application app = new Application();
		app.setApplicationID(13);
		app.setApplicationName("VDS3");
		app.setApplicationURL("http://localhost:9088/Login.action");
		app.setApplicationBrowser("CHROME");
		
		scr1.setScreenName("Shipment Plan Gen3");
		scr1.setCreatedBy("Mahesh");
		scr2.setScreenName("Shipment Plan Amdmt4");
		scr2.setCreatedBy("Mahesh"); 
		scr1.setApplication(app);
		scr2.setApplication(app);
		app.setCreatedBy("Manual");
		app.setLastupdatedBy("Manual");
		app.setCreatedDate(new Date());
		app.setLastupdatedDate(new Date());
		screenRepository.save(scr1);
		screenRepository.save(scr2);		
		
	}
}
