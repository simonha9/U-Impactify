package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface QuizQuestionService {

	public Integer createQuizQuestion(QuizQuestion question) throws ValidationFailedException;
	
	public QuizQuestion findQuizQuestionById(Integer id) throws ValidationFailedException;
	
	public Integer updateQuizQuestion(QuizQuestion question) throws ValidationFailedException;
	
	public void deleteQuizQuestionById(Integer id) throws ValidationFailedException;
	
	public List<QuizQuestion> findQuestionsByQuizId(Integer id) throws ValidationFailedException;
}
