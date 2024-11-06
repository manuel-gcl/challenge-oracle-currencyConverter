package com.alura.currency_converter.exceptions;

/**
 * Parent class for API's exception's errors
 **/

public class APIException extends Exception {
	public APIException(String message) {
		super(message);
	}

	public APIException(String message, Throwable cause) {
		super(message, cause);
	}
}
