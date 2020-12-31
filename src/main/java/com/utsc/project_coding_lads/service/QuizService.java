package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.Quiz;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface QuizService {

	public Integer createQuiz(Quiz quiz) throws ValidationFailedException;
	
	public Quiz findQuizById(Integer id) throws ValidationFailedException;

	public List<Quiz> findAllQuizzesByCourseId(Integer id) throws ValidationFailedException;

	public Integer updateQuiz(Quiz quiz) throws ValidationFailedException;
	
	public void deleteQuizById(Integer id) throws ValidationFailedException;
	
	public Boolean existsById(Integer id);
	
}
