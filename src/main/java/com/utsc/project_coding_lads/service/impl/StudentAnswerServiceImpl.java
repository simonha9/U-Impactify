package com.utsc.project_coding_lads.service.impl;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.StudentAnswerRepository;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.QuizQuestionService;
import com.utsc.project_coding_lads.service.StudentAnswerService;
import com.utsc.project_coding_lads.service.UserService;
import com.utsc.project_coding_lads.validator.QuizQuestionValidator;
import com.utsc.project_coding_lads.validator.UserValidator;

@Service
@Transactional
public class StudentAnswerServiceImpl implements StudentAnswerService {

	@Autowired
	StudentAnswerRepository studentAnswerRepo;
	@Autowired
	QuizQuestionService questionService;
	@Autowired
	UserService userService;
	@Autowired
	ImpactLearnerService learnerService;
	@Autowired
	QuizQuestionValidator questionValidator;
	@Autowired
	UserValidator userValidator;
	
	@Override
	public Integer createBlankStudentAnswer(StudentAnswer studentAnswer) throws ValidationFailedException {
		studentAnswer.setStudentAnswer("");
		return studentAnswerRepo.save(studentAnswer).getId();
	}

	@Override
	public StudentAnswer findStudentAnswerById(Integer id) throws ValidationFailedException {
		return studentAnswerRepo.findById(id).get();
	}

	@Override
	public Integer updateStudentAnswer(StudentAnswer studentAnswer) throws ValidationFailedException {
		if (!existsById(studentAnswer.getId()))
			throw new EntityNotExistException("That answer does not exist for that student and course.");
		return studentAnswerRepo.save(studentAnswer).getId();
	}

	@Override
	public void deleteStudentAnswerById(Integer id) throws ValidationFailedException {
		if (!existsById(id)) 
			throw new EntityNotExistException("That answer does not exist for that student and course.");
		studentAnswerRepo.deleteById(id);
		
	}

	@Override
	public Boolean existsById(Integer id) {
		return studentAnswerRepo.existsById(id);
	}

	@Override
	public StudentAnswer findByStudentAndQuestion(Integer quizQuestionId, Integer studentId) throws ValidationFailedException {
		questionValidator.init(questionService.findQuizQuestionById(quizQuestionId).getQuestionType(), new ArrayList<QuizQuestionOption>());
		questionValidator.validate();
		userValidator.init(userService.findUserById(studentId));
		userValidator.validate();
		userValidator.validateExists();
		userValidator.validateHasRole();
		QuizQuestion savedQuestion = questionService.findQuizQuestionById(quizQuestionId);
		ImpactLearner savedStudent = learnerService.findLearnerById(studentId);
		for (StudentAnswer studentAnswer: savedQuestion.getStudentAnswers()) {
			if (studentAnswer.getStudent().equals(savedStudent)) {
				return studentAnswer;
			}
		}
		throw new EntityNotExistException("There does not exist a Student Answer record with this quiz question and this impact learner");
	}

	
}
