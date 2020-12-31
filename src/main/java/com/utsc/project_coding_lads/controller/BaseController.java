package com.utsc.project_coding_lads.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.utsc.project_coding_lads.exception.BadRequestException;
import com.utsc.project_coding_lads.exception.EntityAlreadyExistsException;
import com.utsc.project_coding_lads.exception.EntityNotFoundException;
import com.utsc.project_coding_lads.exception.InvalidSocialInitNameException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.UserTypeInvalidException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public class BaseController {
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> generic(HttpMessageNotReadableException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserTypeInvalidException.class)
	public ResponseEntity<Object> handleUserTypeInvalidException(UserTypeInvalidException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(EntityAlreadyExistsException.class)
	public ResponseEntity<Object> handleEntityAlreadyExistsException(EntityAlreadyExistsException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MissingInformationException.class)
	public ResponseEntity<Object> handleMissingInformationException(MissingInformationException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);
		
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);
		
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({MismatchedInputException.class, JsonParseException.class})
	public ResponseEntity<Object> handleMismatchedInputException(Exception e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);
		
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidSocialInitNameException.class)
	public ResponseEntity<Object> handleInvalidSocialInitNameException(InvalidSocialInitNameException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);

		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ValidationFailedException.class)
	public ResponseEntity<Object> handleValidationFailedException(ValidationFailedException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 400);
		return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({EntityNotFoundException.class, NoSuchElementException.class})
	public ResponseEntity<Object> handleEntityNotFoundException(ValidationFailedException e) {
		Map<String, Object> body = new HashMap<>();
		body.put("message", e.getMessage());
		body.put("timestamp", LocalDate.now());
		body.put("status", 404);
		
		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
	}
}
