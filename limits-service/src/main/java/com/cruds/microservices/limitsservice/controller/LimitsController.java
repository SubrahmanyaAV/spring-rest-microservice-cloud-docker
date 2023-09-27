package com.cruds.microservices.limitsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cruds.microservices.limitsservice.bean.Limits;
import com.cruds.microservices.limitsservice.configuration.Configuration;

@RestController
public class LimitsController {
	
	@Autowired
	private Configuration config;
	
	@GetMapping("/limits")
	public Limits retrieve() {
	//	return new Limits(1, 1000);
		return new Limits(config.getMinimum(), config.getMaximum());
	}

}
