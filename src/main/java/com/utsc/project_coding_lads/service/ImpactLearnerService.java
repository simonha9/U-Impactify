package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface ImpactLearnerService {

	public Integer storeImpactLearner(ImpactLearner impactLearner) throws Exception;
	
	public Boolean existsById(Integer id);
	
	public ImpactLearner findLearnerById(Integer id) throws ValidationFailedException;
	
	public ImpactLearnerCourse addCourseToLearner(ImpactLearner student, Course course) throws Exception;
	
	public List<ImpactLearnerCourse> findCoursesByLearnerId(Integer id) throws Exception;
	
	public Boolean removeCourseFromLearner(ImpactLearner student, Course course) throws Exception;
	
	public List<ImpactLearnerCourse> findCoursesByInstructorId(Integer studentId, Integer instructorId) throws Exception;
	
	public Integer updateImpactLearner(ImpactLearner impactLearner) throws ValidationFailedException;
	
	public List<StudentAnswer> answerQuizQuestions(List<StudentAnswer> studentAnswers) throws Exception;
	
}
