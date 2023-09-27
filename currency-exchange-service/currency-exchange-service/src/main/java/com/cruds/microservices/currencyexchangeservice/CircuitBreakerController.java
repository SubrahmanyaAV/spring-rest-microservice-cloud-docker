package com.cruds.microservices.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {

	private Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/test-api")
//	@Retry(name = "test-api" , fallbackMethod = "hardcodedResponse")
//	@CircuitBreaker(name = "default",fallbackMethod = "hardcodedResponse" )
	@RateLimiter(name = "sample-api")
	@Bulkhead(name = "test")
	public String testApi() {
	//	return "Test Sample Api";
		logger.info("Sample Api call recieved");
//		ResponseEntity<String> forEntity = new RestTemplate().getForEntity("http://localhost:8080/dummy", String.class);
//		return forEntity.getBody();
		return "test-api";
	}
	
	public String hardcodedResponse(Exception e) {
		return "fallback-response";
	}
}
