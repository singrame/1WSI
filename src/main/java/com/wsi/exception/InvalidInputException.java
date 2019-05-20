package com.wsi.exception;

public class InvalidInputException extends Exception {

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String message) {
		super(message);
	}

}
