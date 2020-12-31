package com.utsc.project_coding_lads.exception;

public class MissingInformationException extends ValidationFailedException {
	
	private static final long serialVersionUID = -5043072901311413962L;

	public MissingInformationException(String message) {
		super(message);
	}
	
}