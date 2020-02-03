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
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestSuiteController {

	final static Logger logger = LoggerFactory.getLogger(TestSuiteController.class);

	final static String projectfilePath = Paths.get("").toAbsolutePath().toString();

	@RequestMapping(value = "/uploadTestSuite", method = RequestMethod.POST)
	public String uploadTestSuite(@RequestParam("file") MultipartFile file,
			@RequestParam("application") String application, @RequestParam("screen") String screen) {
		logger.info("Entering @TestSuiteController - ::::");
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

}
