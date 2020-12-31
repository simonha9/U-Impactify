package com.utsc.project_coding_lads.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsc.project_coding_lads.domain.Solution;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.SolutionRepository;
import com.utsc.project_coding_lads.service.SolutionService;

@Service
@Transactional
public class SolutionServiceImpl implements SolutionService {

	@Autowired
	SolutionRepository solutionRepo;
	
	@Override
	public Integer createSolution(Solution solution) throws ValidationFailedException {
		return solutionRepo.save(solution).getId();
	}

	@Override
	public Solution findSolutionById(Integer id) throws ValidationFailedException {
		return solutionRepo.findById(id).get();
	}

	@Override
	public Integer updateSolution(Solution solution) throws ValidationFailedException {
		if (existsById(solution.getId())) 
			return solutionRepo.save(solution).getId();
		return -1;
	}

	@Override
	public void deleteSolutionById(Integer id) throws ValidationFailedException {
		if (existsById(id)) 
			solutionRepo.deleteById(id);
	}

	@Override
	public boolean existsById(Integer id) {
		return solutionRepo.existsById(id);
	}
	
	

}
