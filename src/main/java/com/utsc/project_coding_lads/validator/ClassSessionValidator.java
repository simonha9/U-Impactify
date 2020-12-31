package com.utsc.project_coding_lads.validator;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.utsc.project_coding_lads.exception.*;
import com.utsc.project_coding_lads.domain.ClassSession;
import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.service.ClassSessionService;
import com.utsc.project_coding_lads.service.CourseService;

@Component
@Transactional
public class ClassSessionValidator implements Validator {
	
	private Integer id;
	private Course course;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	@Autowired
	ClassSessionService classSessionService;
	
	@Autowired
	CourseService courseService;
	
	// public ClassSessionValidator() {
	// 	super();
	// }
	
	// public ClassSessionValidator(ClassSession session) {
	// 	super();
	// 	this.id = session.getId();
	// 	this.course = session.getCourse();
	// 	this.startDate = session.getStartDate();
	// 	this.endDate = session.getEndDate();
	// }

	public void init(ClassSession session) {
		this.id = session.getId();
		this.course = session.getCourse();
		this.startDate = session.getStartDate();
		this.endDate = session.getEndDate();
	}

	@Override
	public void validate() throws ValidationFailedException {
		if (course == null || startDate == null || endDate == null)
			throw new MissingInformationException("The required field is missing");
		if (!courseService.existsById(course.getId()))
			throw new UnauthenticatedException("The course does not exist");
		if (!startDate.isBefore(endDate))
			throw new UnauthenticatedException("The end time should be after the start time");
	}
	
	public void validateExist(ClassSession session) throws ValidationFailedException {
		validate();
		if (id == null)
			throw new MissingInformationException("The class session id field is missing");
		if (!classSessionService.existsById(id))
			throw new EntityNotExistException("The class session does not exist");
	}

}
