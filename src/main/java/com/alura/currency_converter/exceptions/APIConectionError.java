package com.alura.currency_converter.exceptions;

/**
 * Custom exception class for handling API connection errors
 **/

public class APIConectionError extends APIException {
	public APIConectionError(String message) {
		super(message);
	}

	public APIConectionError(String message, Throwable cause) {
		super(message, cause);
	}

}
