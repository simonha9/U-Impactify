package com.utsc.project_coding_lads.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.QuestionOptionRepository;
import com.utsc.project_coding_lads.service.QuestionOptionService;
import com.utsc.project_coding_lads.service.QuizQuestionService;

@Service
@Transactional
public class QuestionOptionServiceImpl implements QuestionOptionService {

	@Autowired
	QuestionOptionRepository qnOpRepo;
	@Autowired
	QuizQuestionService quizQuestionService;
	
	@Override
	public Integer createQuestionOption(QuizQuestionOption op) throws ValidationFailedException {
		return qnOpRepo.save(op).getId();
	}

	@Override
	public QuizQuestionOption findQuestionOptionById(Integer id) throws ValidationFailedException {
		return qnOpRepo.findById(id).get();
	}

	@Override
	public Integer updateQuestionOption(QuizQuestionOption op) throws ValidationFailedException {
		if (!existsById(op.getId())) throw new EntityNotExistException("That question option does not exist."); 
		return qnOpRepo.save(op).getId();
	}

	@Override
	public void deleteQuestionOption(Integer id) throws ValidationFailedException {
		if (!existsById(id)) throw new EntityNotExistException("That question option does not exist."); 
		qnOpRepo.deleteById(id);
	}

	@Override
	public List<QuizQuestionOption> findQuestionsByQuestionId(Integer id) throws ValidationFailedException {
		QuizQuestion savedQuestion = quizQuestionService.findQuizQuestionById(id);
		savedQuestion.getQuestionOptions().size();
		return savedQuestion.getQuestionOptions();
	}

	@Override
	public Boolean existsById(Integer id) {
		return qnOpRepo.existsById(id);
	}

}
