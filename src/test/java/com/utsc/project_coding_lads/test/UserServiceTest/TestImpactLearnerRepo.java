package com.utsc.project_coding_lads.test.UserServiceTest;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.repository.ImpactLearnerRepository;
import com.utsc.project_coding_lads.repository.UserRepository;
import com.utsc.project_coding_lads.service.ImpactLearnerService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestImpactLearnerRepo {

	@Autowired
	ImpactLearnerRepository impactLearnerRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ImpactLearnerService ilService;
	
	@Test
	public void testCRUD() {
		
		ImpactLearner il = new ImpactLearner();
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username");
		user.setHashedPassword("pw");
		
		il.setUser(user);

//		user.setRole(new Role("impact_learner"));
//		user.setSocialInitiative(null);
		
		ImpactLearner savedIl = impactLearnerRepo.save(il);
		Assert.assertNotNull(savedIl.getId());
//		User getUser = userRepo.getOne(savedUser.getId());
//		Assert.assertNotNull(getUser.getId());
	}
	
	@Test
	public void testCRUDService() throws Exception {
		
		ImpactLearner il = new ImpactLearner();
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username1");
		user.setHashedPassword("pw");
		
		il.setUser(user);

//		user.setRole(new Role("impact_learner"));
//		user.setSocialInitiative(null);
		
		Integer savedIl = ilService.storeImpactLearner(il);
		Assert.assertNotNull(savedIl);
		User getUser = userRepo.getOne(savedIl);
		Assert.assertNotNull(getUser.getId());
	}

}
