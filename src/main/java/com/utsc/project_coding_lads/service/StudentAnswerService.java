package com.utsc.project_coding_lads.service;

import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface StudentAnswerService {

	public Integer createBlankStudentAnswer(StudentAnswer studentAnswer) throws ValidationFailedException;
	
	public StudentAnswer findStudentAnswerById(Integer id) throws ValidationFailedException;
	
	public Integer updateStudentAnswer(StudentAnswer studentAnswer) throws ValidationFailedException;
	
	public void deleteStudentAnswerById(Integer id) throws ValidationFailedException;
	
	public Boolean existsById(Integer id);
	
	public StudentAnswer findByStudentAndQuestion(Integer quizQuestionId, Integer studentId) throws ValidationFailedException;
}
