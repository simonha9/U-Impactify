package com.utsc.project_coding_lads.validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.service.CourseService;

@Component
@Transactional
public class QuizValidator implements Validator {


	@Autowired
	CourseService courseService;
	
	private Course course;
	private LocalDateTime quizStartDate;
	private LocalDateTime quizEndDate;
	private List<QuizQuestion> quizQuestions = new ArrayList<>();
	
	public void init(Course course, LocalDateTime quizStartDate, LocalDateTime quizEndDate, List<QuizQuestion> questions) {
		this.course = course;
		this.quizStartDate = quizStartDate;
		this.quizEndDate = quizEndDate;
		this.quizQuestions.addAll(quizQuestions);
	}
	
	@Override
	public void validate() throws ValidationFailedException {
		if (course == null || course.getId() == null || quizStartDate == null || quizEndDate == null) 
			throw new MissingInformationException("Required information is missing.");
		if (!courseService.existsById(course.getId())) throw new EntityNotExistException("That course does not exist");
	}

	
	
}
