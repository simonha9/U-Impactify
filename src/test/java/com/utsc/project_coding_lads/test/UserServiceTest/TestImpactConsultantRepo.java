package com.utsc.project_coding_lads.test.UserServiceTest;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.repository.ImpactConsultantRepository;
import com.utsc.project_coding_lads.repository.UserRepository;
import com.utsc.project_coding_lads.service.ImpactConsultantService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestImpactConsultantRepo {

	@Autowired
	ImpactConsultantRepository impactConsultantRepo;
	@Autowired
	UserRepository userRepo;
	@Autowired
	ImpactConsultantService icService;
	
	@Test
	public void testCRUD() {
		
		ImpactConsultant il = new ImpactConsultant();
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username1");
		user.setHashedPassword("pw");
		
		il.setUser(user);

//		user.setRole(new Role("impact_learner"));
//		user.setSocialInitiative(null);
		
		ImpactConsultant savedIl = impactConsultantRepo.save(il);
		Assert.assertNotNull(savedIl.getId());
//		User getUser = userRepo.getOne(savedUser.getId());
//		Assert.assertNotNull(getUser.getId());
	}
	
	@Test
	public void testCRUDService() throws Exception {
		
		ImpactConsultant il = new ImpactConsultant();
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username2");
		user.setHashedPassword("pw");
		
		il.setUser(user);

//		user.setRole(new Role("impact_learner"));
//		user.setSocialInitiative(null);
		
		Integer savedIl = icService.storeImpactConsultantService(il);
		Assert.assertNotNull(savedIl);
		User getUser = userRepo.getOne(savedIl);
		Assert.assertNotNull(getUser.getId());
	}

}
