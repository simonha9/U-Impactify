package com.utsc.project_coding_lads.test.course;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Solution;
import com.utsc.project_coding_lads.repository.SolutionRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@Transactional
public class TestSolutionRepo {

	@Autowired
	SolutionRepository solutionRepo;
	
	@Test
	public void testCRUD() throws Exception {
		Solution solution = new Solution();
		solution.setAnswer("answer");
		
		Solution savedSoln = solutionRepo.save(solution);
		Assert.assertNotNull(savedSoln.getId());
		Assert.assertEquals(solution.getAnswer(), savedSoln.getAnswer());
		
		Solution getSoln = solutionRepo.findById(savedSoln.getId()).get();
		Assert.assertEquals(savedSoln.getId(), getSoln.getId());
		
		savedSoln.setAnswer("other answer");
		Solution updatedSoln = solutionRepo.save(savedSoln);
		Assert.assertEquals(savedSoln.getAnswer(), updatedSoln.getAnswer());
		
		solutionRepo.deleteById(updatedSoln.getId());
		Assert.assertFalse(solutionRepo.existsById(updatedSoln.getId()));
	}

}
