package com.utsc.project_coding_lads.validator;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.service.InvoiceService;
import com.utsc.project_coding_lads.service.UserService;

@Component
@Transactional
public class InvoiceValidator implements Validator {

	private Integer cost;
	private User InvoiceCreator;
	private Course course;
	private Integer invoiceId;
	
	@Autowired
	UserService userService;
	@Autowired
	InvoiceService InvoiceService;
	

	public void init(Integer cost, User InvoiceCreator, Course course, Integer invoiceId) {
		this.InvoiceCreator = InvoiceCreator;
		this.course = course;
		this.invoiceId = invoiceId;
		this.cost = cost;
	}
	
	public void init(Integer cost, User InvoiceCreator, Course course) {
		this.InvoiceCreator = InvoiceCreator;
		this.course = course;
		this.cost = cost;
	}

	@Override
	public void validate() throws ValidationFailedException {
		if (cost == null)
			throw new MissingInformationException("Required fields are missing.");
		if (InvoiceCreator == null)
			throw new EntityNotExistException("The invoice creator cannot be null.");
		if (course == null)
			throw new EntityNotExistException("The course cannot be null.");
		if (course.getId() == null)
			throw new EntityNotExistException("The course Id cannot be null");
		if (InvoiceCreator.getId() == null)
			throw new EntityNotExistException("The invoice creator Id cannot be null");
		if (!userService.existsById(InvoiceCreator.getId()))
			throw new EntityNotExistException("That user does not exist in the db.");
	}
	
	public void validateExists() throws ValidationFailedException {
		validate();
		if (!InvoiceService.existsById(invoiceId))
			throw new EntityNotExistException("That invoice does not exist.");
	}
}

