package com.testautomation.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestSuiteController {

	final static Logger logger = LoggerFactory.getLogger(TestSuiteController.class);

	@RequestMapping(value = "/uploadTestSuite", method = RequestMethod.POST)
	public String uploadTestSuite(@RequestParam("file") MultipartFile file,
			@RequestParam("application") String application, @RequestParam("screen") String screen) {
		logger.info("Entering @TestSuiteController - ::::");
		String projectfilePath = Paths.get("").toAbsolutePath().toString();

		String filePath = projectfilePath + File.separator + application + File.separator + screen;
		try {
			File saveFile = new File(filePath, "TestSuite_" + application + "_" + screen + ".xlsx");
			saveFile.mkdir();
			FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "exception";
		}
		return "success";
	}

	@RequestMapping(value = "/sampleTestSuite", method = RequestMethod.GET)
	public String getSampleTestSuite() {
		logger.info("Entering @TestSuiteController - ::::");
		return "sf";
	}

}
