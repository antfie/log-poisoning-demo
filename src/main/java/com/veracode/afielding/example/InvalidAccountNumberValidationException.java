package com.veracode.afielding.example;

public class InvalidAccountNumberValidationException extends Exception {
	public InvalidAccountNumberValidationException(String input) {
		super(String.format("Expected input to be 8-digit account number. Input was: '%s'", input));
	}
}