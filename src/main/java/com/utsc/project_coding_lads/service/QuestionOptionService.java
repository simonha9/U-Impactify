package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.QuizQuestionOption;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface QuestionOptionService {

	public Integer createQuestionOption(QuizQuestionOption op) throws ValidationFailedException;
	
	public QuizQuestionOption findQuestionOptionById(Integer id) throws ValidationFailedException;
	
	public Integer updateQuestionOption(QuizQuestionOption op) throws ValidationFailedException;
	
	public void deleteQuestionOption(Integer id) throws ValidationFailedException;
	
	public List<QuizQuestionOption> findQuestionsByQuestionId(Integer id) throws ValidationFailedException;
	
	public Boolean existsById(Integer id);
}
