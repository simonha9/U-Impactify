package com.utsc.project_coding_lads.exception;

public class ValidationFailedException extends Exception {

	private static final long serialVersionUID = 4628367677387287752L;

	public ValidationFailedException(String message) {
		super(message);
	}
}
