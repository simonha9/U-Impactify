package com.utsc.project_coding_lads.exception;

public class EntityNotExistException extends ValidationFailedException {
	
	private static final long serialVersionUID = 5082281334138814967L;

	public EntityNotExistException(String message) {
		super(message);
	}
	
}