package com.utsc.project_coding_lads.exception;

public class EntityNotFoundException extends ValidationFailedException {
	public EntityNotFoundException(String message) {
		super(message);
	}
}
