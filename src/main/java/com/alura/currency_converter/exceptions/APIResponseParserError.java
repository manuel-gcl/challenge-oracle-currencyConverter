package com.alura.currency_converter.exceptions;

/**
 * Custom exception class for handling API response parsing errors
 **/

public class APIResponseParserError extends APIException {
	public APIResponseParserError(final String errorMessage) {
		super(errorMessage);
	}

	public APIResponseParserError(String message, Throwable cause) {
		super(message, cause);
	}
}
