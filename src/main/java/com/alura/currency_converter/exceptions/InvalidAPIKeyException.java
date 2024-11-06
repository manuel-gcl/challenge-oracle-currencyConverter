package com.alura.currency_converter.exceptions;

/**
 * Custom exception class for handling API credentials errors
 **/
public class InvalidAPIKeyException extends APIException {
	public InvalidAPIKeyException(final String errorMessage) {
		super(errorMessage);
	}

	public InvalidAPIKeyException(String message, Throwable cause) {
		super(message, cause);
	}
}
