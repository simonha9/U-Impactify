package com.utsc.project_coding_lads.service;

import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;

public interface ImpactLearnerCourseService {

	public void deleteById(Integer learnerCourseId) throws Exception;
	
	public Integer saveLearnerCourse(ImpactLearnerCourse learnerCourse);
	
	public ImpactLearnerCourse findLearnerCourseById(Integer id);
}
