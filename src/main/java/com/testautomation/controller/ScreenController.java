package com.testautomation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.testautomation.service.ScreenService;

@RestController
public class ScreenController {

	@Autowired ScreenService screenService;
	
	@PostMapping(value = "/saveScreen")
	public void persistScreen() { // Sample for Test
		screenService.persistScreen();
	}
	
}
