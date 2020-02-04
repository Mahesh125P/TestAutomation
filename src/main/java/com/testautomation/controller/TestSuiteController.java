package com.testautomation.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Paths;

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

	final static String projectfilePath = Paths.get("").toAbsolutePath().toString();

	@RequestMapping(value = "/uploadTestSuite", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> uploadTestSuite(@RequestParam("file") MultipartFile file,
			@RequestParam("application") String application, @RequestParam("screen") String screen) {
		logger.info("Entering @TestSuiteController - ::::");
		String filePath = projectfilePath + File.separator + application + File.separator + screen;
		ResponseDTO response = new ResponseDTO();
		try {
			File saveFile = new File(filePath, "TestSuite_" + application + "_" + screen + ".xlsx");
			saveFile.mkdir();
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

	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public void downloadTestSuiteTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File templateFile = new File(projectfilePath + File.separator + "esip.xlsx");
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

	@RequestMapping(value = "/uploadTestCase", method = RequestMethod.POST)
	public ResponseEntity<ResponseDTO> uploadTestCase(@RequestParam("file") MultipartFile file,
			@RequestParam("application") String application, @RequestParam("screen") String screen) {
		logger.info("Entering @TestSuiteController - ::::");
		String filePath = projectfilePath + File.separator + "src" + File.separator + "main" + File.separator
				+ "resources" + File.separator + "TestCase" + application + File.separator;
		ResponseDTO response = new ResponseDTO();
		try {
			File saveFile = new File(filePath, "TestCase_" + application + "_" + screen + ".xlsx");
			saveFile.mkdir();
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
}
