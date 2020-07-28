package com.testautomation.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.testautomation.service.ResponseDTO;

@RestController
public class TestSuiteController {

	final static Logger logger = LoggerFactory.getLogger(TestSuiteController.class);

	final static String projectfilePath = Paths.get("").toAbsolutePath().toString() + File.separator + "src"
			+ File.separator + "main" + File.separator + "resources" + File.separator;

	@RequestMapping(value = "/uploadTestSuite", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> uploadTestSuite(@RequestParam("file") MultipartFile file,
			@RequestParam("application") String application, @RequestParam("screen") String screen) {
		logger.info("Entering @TestSuiteController - ::::");
		String filePath = projectfilePath + "TestSuite"+File.separator + application + File.separator;
		ResponseDTO response = new ResponseDTO();
		try {
			File saveFile = new File(filePath, "TestSuite_" + application + "_" + screen + ".xlsx");
			//saveFile.mkdir();
			saveFile.createNewFile();
			FileUtils.writeByteArrayToFile(saveFile,file.getBytes());
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.setStatus("success");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/downloadTestSuite", method = RequestMethod.GET)
	public void downloadTestSuiteFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("application") String application, @RequestParam("screen") String screen) throws IOException {
		String filePath = projectfilePath + "TestSuite" + File.separator + application + File.separator;
		File testSuiteFile = new File(filePath, "TestSuite_" + application + "_" + screen + ".xlsx");
		if (testSuiteFile.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(testSuiteFile.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + testSuiteFile.getName() + "\""));
			response.setContentLength((int) testSuiteFile.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(testSuiteFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}

	}

	@RequestMapping(value = "/uploadTestCase", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> uploadTestCase(@RequestParam("file") MultipartFile file,
			@RequestParam("application") String application, @RequestParam("screen") String screen) {
		logger.info("Entering @TestSuiteController - ::::");
		String filePath = projectfilePath + "TestCase" + File.separator + application + File.separator;
		ResponseDTO response = new ResponseDTO();
		try {
			File saveFile = new File(filePath, "TestCase_" + application + "_" + screen + ".xlsx");
			//saveFile.mkdir();
			saveFile.createNewFile();
			FileUtils.writeByteArrayToFile(saveFile, file.getBytes());
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			response.setStatus("error");
			response.setMessage(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.setStatus("success");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/downloadTestCase", method = RequestMethod.GET)
	public void downloadTestCaseFile(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("application") String application, @RequestParam("screen") String screen) throws IOException {
		String filePath = projectfilePath + "TestCase" + File.separator + application + File.separator;
		File testCaseFile = new File(filePath, "TestCase_" + application + "_" + screen + ".xlsx");
		if (testCaseFile.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(testCaseFile.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + testCaseFile.getName() + "\""));
			response.setContentLength((int) testCaseFile.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(testCaseFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}

	}
	
	@RequestMapping(value = "/downloadTestCaseTemplate")
	public void downloadTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {

		File templateFile = new File(Paths.get("").toAbsolutePath().toString() + File.separator + "src"
	            + File.separator + "main" + File.separator + "webapp" + File.separator + "WEB-INF" + File.separator + "template" + File.separator + "TestCase_Project_Screen.xlsx");
	            
		if (templateFile.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(templateFile.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					String.format("inline; filename=\"" + templateFile.getName() + "\""));
			response.setContentLength((int) templateFile.length());
			InputStream inputStream = new BufferedInputStream(new FileInputStream(templateFile));
			FileCopyUtils.copy(inputStream, response.getOutputStream());
		}
	}
}
