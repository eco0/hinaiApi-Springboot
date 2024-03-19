package com.api.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.api.restapi.controller.IMTACdataExecution;

@RestController
public class IMTACsendNotifController {
	@Autowired
	private IMTACdataExecution ide;
	
	@GetMapping("/dumpsitbapi")
	public String getDumpApi(@RequestParam("url") String url) {
		String returnValue = "Success";
		try {
			return ide.uploadSITBDataFiles(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnValue;
	}
}
