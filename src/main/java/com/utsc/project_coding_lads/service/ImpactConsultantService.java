package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface ImpactConsultantService {

	public Integer storeImpactConsultantService(ImpactConsultant impactConsultant) throws Exception;

	public ImpactConsultant findImpactConsultantById(Integer id) throws ValidationFailedException;
	
	public Boolean existsById(Integer id);

	public List<Course> findAllCoursesByInstructorId(Integer id) throws Exception;
	
}
