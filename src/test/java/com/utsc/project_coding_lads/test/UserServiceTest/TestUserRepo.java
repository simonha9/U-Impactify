package com.utsc.project_coding_lads.test.UserServiceTest;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.repository.UserRepository;
import com.utsc.project_coding_lads.service.SocialInitService;
import com.utsc.project_coding_lads.service.UserService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestUserRepo {

	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserService userService;
	@Autowired
	SocialInitService socialInitService;
	
	@Test
	public void testCRUD() {
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username");
		user.setHashedPassword("pw");

//		user.setRole(new Role("impact_learner"));
//		user.setSocialInitiative(null);
		
		User savedUser = userRepo.save(user);
		Assert.assertNotNull(savedUser.getId());
		User getUser = userRepo.getOne(savedUser.getId());
		Assert.assertNotNull(getUser.getId());
	}

	@Test
	public void testCRUDService() throws Exception {
		
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username");
		user.setHashedPassword("pw");
		
		Role role1 = new Role("IMPACT_LEARNER");
		Role role_1 = roleRepo.save(role1);

		user.setRole(role_1);
		SocialInitiative socialInit = new SocialInitiative();
		socialInit.setName("org");
		user.setSocialInit(socialInit);
		
		Integer id = userService.storeUser(user);
		Assert.assertNotNull(id);
	}

}
