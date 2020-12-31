package com.utsc.project_coding_lads.exception;

public class UnauthenticatedException extends ValidationFailedException {
	
	private static final long serialVersionUID = 5082281334138814967L;

	public UnauthenticatedException(String message) {
		super(message);
	}
	
}