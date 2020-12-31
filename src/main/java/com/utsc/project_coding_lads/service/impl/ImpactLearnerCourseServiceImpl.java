package com.utsc.project_coding_lads.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.repository.ImpactLearnerCourseRepository;
import com.utsc.project_coding_lads.service.ImpactLearnerCourseService;

@Service
@Transactional
public class ImpactLearnerCourseServiceImpl implements ImpactLearnerCourseService {

	@Autowired
	ImpactLearnerCourseRepository learnerCourseRepo;
	
	@Override
	public void deleteById(Integer learnerCourseId) throws Exception {
		learnerCourseRepo.deleteById(learnerCourseId);
	}

	@Override
	public Integer saveLearnerCourse(ImpactLearnerCourse learnerCourse) {
		return learnerCourseRepo.save(learnerCourse).getId();
	}

	@Override
	public ImpactLearnerCourse findLearnerCourseById(Integer id) {
		return learnerCourseRepo.findById(id).get();
	}

}
