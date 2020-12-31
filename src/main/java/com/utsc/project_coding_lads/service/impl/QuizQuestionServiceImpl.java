package com.utsc.project_coding_lads.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.domain.Solution;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.QuizQuestionRepository;
import com.utsc.project_coding_lads.repository.SolutionRepository;
import com.utsc.project_coding_lads.service.QuestionOptionService;
import com.utsc.project_coding_lads.service.QuizQuestionService;
import com.utsc.project_coding_lads.service.QuizService;
import com.utsc.project_coding_lads.validator.QuizQuestionValidator;

@Service
@Transactional
public class QuizQuestionServiceImpl implements QuizQuestionService {

	@Autowired
	QuizQuestionRepository quizQnRepo;
	@Autowired
	SolutionRepository solnRepo;
	@Autowired
	QuestionOptionService qnOpService;
	@Autowired
	QuizQuestionValidator questionValidator;
	@Autowired
	QuizService quizService;
	
	@Override
	public Integer createQuizQuestion(QuizQuestion question) throws ValidationFailedException {
		questionValidator.init(question.getQuestionType(), question.getQuestionOptions());
		questionValidator.validate();
		QuizQuestion savedQuestion = quizQnRepo.save(question);
		
		List<QuizQuestionOption> savedOptions = new ArrayList<>();
		for (QuizQuestionOption op : question.getQuestionOptions()) {
			op.setQuestion(savedQuestion);
			Integer savedOpId =  qnOpService.createQuestionOption(op);
			QuizQuestionOption savedOp = qnOpService.findQuestionOptionById(savedOpId);
			savedOptions.add(savedOp);
		}
		savedQuestion.getQuestionOptions().clear();
		savedQuestion.getQuestionOptions().addAll(savedOptions);
		
		Solution soln = question.getSolution();
		soln.setQuestion(savedQuestion);
		soln = solnRepo.save(soln);
		savedQuestion.setSolution(soln);
		return quizQnRepo.save(savedQuestion).getId();
	}

	@Override
	public QuizQuestion findQuizQuestionById(Integer id) throws ValidationFailedException {
		return quizQnRepo.findById(id).get();
	}

	@Override
	public Integer updateQuizQuestion(QuizQuestion question) throws ValidationFailedException {
		return quizQnRepo.save(question).getId();
	}

	@Override
	public void deleteQuizQuestionById(Integer id) throws ValidationFailedException {
		quizQnRepo.deleteById(id);
	}

	@Override
	public List<QuizQuestion> findQuestionsByQuizId(Integer id) throws ValidationFailedException {
//		Quiz savedQuiz = quizService.findQuizById(id);
//		savedQuiz.getQuizQuestions().size();
//		for (QuizQuestion qn : savedQuiz.getQuizQuestions()) {
//			qn.getQuestionOptions().size();
//			qn.getStudentAnswers().size();
//		}
		
//		return savedQuiz.getQuizQuestions();
		List<QuizQuestion> savedQuestions = quizQnRepo.findQuizQuestionsByQuizId(id);
		for (QuizQuestion qn : savedQuestions) {
			qn.getQuestionOptions().size();
			qn.getStudentAnswers().size();
		}
		return savedQuestions;
	}

}