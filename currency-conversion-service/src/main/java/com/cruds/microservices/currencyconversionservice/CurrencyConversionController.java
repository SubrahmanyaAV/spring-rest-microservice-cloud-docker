package com.cruds.microservices.currencyconversionservice;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CurrencyConversionController {
	
	@Autowired
	private CurrencyExchangeProxy proxy;
	
	 @Autowired
	 private RestTemplate restTemplate;
	
	//using Rest template
	//http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retrieve(@PathVariable String from,
								       @PathVariable String to,
								       @PathVariable BigDecimal quantity) {
	//	return new CurrencyConversion(1001L, from, to, quantity, BigDecimal.ONE, BigDecimal.ONE, "");
		
		HashMap<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
//		ResponseEntity<CurrencyConversion> responseEntity = new RestTemplate().getForEntity(
//				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
//				CurrencyConversion.class, uriVariables);
		ResponseEntity<CurrencyConversion> responseEntity = restTemplate.getForEntity(
				"http://localhost:8000/currency-exchange/from/{from}/to/{to}", 
				CurrencyConversion.class, uriVariables);

		CurrencyConversion currencyConversion = responseEntity.getBody();
		
		return new CurrencyConversion(currencyConversion.getId(),
				from, to, quantity, 
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),
				currencyConversion.getEnvironment() + " Rest template");
	}
	
	//Same Method as above but using feign framework
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion retrieveUsingFeign(@PathVariable String from,
								       @PathVariable String to,
								       @PathVariable BigDecimal quantity) {
		CurrencyConversion currencyConversion = proxy.retrieveExchangeValues(from, to);
		
		return new CurrencyConversion(currencyConversion.getId(),
				from, to, quantity, 
				currencyConversion.getConversionMultiple(),
				quantity.multiply(currencyConversion.getConversionMultiple()),
				currencyConversion.getEnvironment() +  " feign");
	}
}
