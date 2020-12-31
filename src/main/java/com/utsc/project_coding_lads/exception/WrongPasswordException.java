package com.utsc.project_coding_lads.exception;

public class WrongPasswordException extends Exception {

	private static final long serialVersionUID = 5298965892802139412L;

	public  WrongPasswordException(String message) {
		super(message);
	}
}
