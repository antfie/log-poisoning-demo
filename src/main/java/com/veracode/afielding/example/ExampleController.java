package com.veracode.afielding.example;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

import java.lang.Throwable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.owasp.encoder.Encode;

@RestController
public class ExampleController {
	private static final String ACCOUNT_NUMBER_VALIDATION_PATTERN = "^\\d{8}$";
	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class.getName());

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(String untrusted) {
		logger.info(String.format("Untrusted value: '%s'", Encode.forJava(untrusted)));

		try {
			validate(untrusted);
		} catch (InvalidAccountNumberValidationException ex) {
			logError("Invalid input", ex);
		}

		return Encode.forJava(untrusted);
	}

	private void validate(String untrusted) throws InvalidAccountNumberValidationException {
		Pattern p = Pattern.compile(ACCOUNT_NUMBER_VALIDATION_PATTERN);
		if (!p.matcher(untrusted).matches()) {
			throw new InvalidAccountNumberValidationException(untrusted);
		}
	}

	private void logError(String msg, Throwable t) {
		logger.error(msg, t);
	}
}