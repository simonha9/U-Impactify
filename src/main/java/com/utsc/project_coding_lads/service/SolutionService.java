package com.utsc.project_coding_lads.service;

import com.utsc.project_coding_lads.domain.Solution;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface SolutionService {

	public Integer createSolution(Solution solution) throws ValidationFailedException;
	
	public Solution findSolutionById(Integer id) throws ValidationFailedException;
	
	public Integer updateSolution(Solution solution) throws ValidationFailedException;
	
	public void deleteSolutionById(Integer id) throws ValidationFailedException;
	
	public boolean existsById(Integer id);
}
