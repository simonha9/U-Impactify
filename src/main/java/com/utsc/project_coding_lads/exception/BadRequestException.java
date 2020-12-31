package com.utsc.project_coding_lads.exception;

public class BadRequestException extends Exception {
	
	private static final long serialVersionUID = -8919506720767445927L;

	public BadRequestException(String message) {
		super(message);
	}
	
}