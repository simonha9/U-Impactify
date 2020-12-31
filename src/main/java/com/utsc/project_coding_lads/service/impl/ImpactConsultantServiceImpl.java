package com.utsc.project_coding_lads.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.exception.BadRequestException;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.ImpactConsultantRepository;
import com.utsc.project_coding_lads.service.ImpactConsultantService;

@Service
@Transactional
public class ImpactConsultantServiceImpl implements ImpactConsultantService {

	@Autowired
	ImpactConsultantRepository impactConsultantRepo;
	
	@Override
	public ImpactConsultant findImpactConsultantById(Integer id) throws ValidationFailedException {
		if (!impactConsultantRepo.existsById(id))
			throw new EntityNotExistException("This impact consultant does not exist");
		return impactConsultantRepo.findById(id).get();
	}
	
	@Override
	public List<Course> findAllCoursesByInstructorId(Integer id) throws Exception {
		if (id == null)
			throw new BadRequestException("Instructor id cannot be null");
		ImpactConsultant impactConsultant = findImpactConsultantById(id);
		if (impactConsultant != null) {
			return impactConsultant.getCourses();
		} else {
			throw new EntityNotExistException("This impact consultant does not exist");
		}
	}
	
	@Override
	public Integer storeImpactConsultantService(ImpactConsultant impactConsultant) throws Exception {
		return impactConsultantRepo.save(impactConsultant).getId();
	}

	@Override
	public Boolean existsById(Integer id) {
		return impactConsultantRepo.existsById(id);
	}

}