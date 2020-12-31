package com.utsc.project_coding_lads.test.UserServiceTest;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.controller.UserController;
import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.enums.PostingEnum;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.repository.ApplicationRepository;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.ApplicationService;
import com.utsc.project_coding_lads.service.PostingService;
import com.utsc.project_coding_lads.service.SocialInitService;
import com.utsc.project_coding_lads.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class TestImpactLearnerApply {

	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	SocialInitService siService;
	@Autowired
	PostingService postingService;
	@Autowired
	ApplicationRepository appRepo;
	@Autowired
	ApplicationService appService;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	WebApplicationContext webApplicationContext;
	@Autowired
	UserController controller;
	
	@Test(expected = MissingInformationException.class)
	public void applyNull() throws Exception {
		controller.apply(null);
	}

	@Test
	public void applyToVolunteering() throws Exception {
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstNameCreator");
		user.setLastName("lastname");
		user.setUsername("username1");
		user.setHashedPassword("pw");
		SocialInitiative socialInit = new SocialInitiative();
		socialInit.setName("org a");
		SocialInitiative savedSI = siService.storeSocialInit(socialInit);
		
		user.setSocialInit(savedSI);
		Integer savedUserId = userService.storeUser(user);
		User savedUser = userService.findUserById(savedUserId);
		
		Posting posting = new Posting();
		posting.setPostingDate(LocalDateTime.now());
		posting.setPostingDesc("desc");
		posting.setName("name");
		posting.setPostingType(PostingEnum.VOLUNTEERING.name());
		posting.setPostingCreator(savedUser);
		posting.setSocialInit(savedSI);
		Posting savedPosting = postingService.savePosting(posting);
		Assert.assertNotNull(savedPosting.getId());
		Integer savedPostingId = savedPosting.getId();
		
		User learner = new User();
		learner.setAge(90);
		learner.setFirstName("firstName");
		learner.setLastName("lastname");
		learner.setUsername("usernameLearner1");
		learner.setHashedPassword("pw");
		Role role = new Role("IMPACT_LEARNER");
		Role savedRole = roleRepo.save(role);
		learner.setRole(savedRole);
		
		Integer savedLearnerId = userService.storeUser(learner);
		User savedLearner = userService.findUserById(savedLearnerId);
		Assert.assertNotNull(savedLearner);
		
		String email = "learner@gmail.com";
		
		com.utsc.project_coding_lads.domain.Application app = new com.utsc.project_coding_lads.domain.Application();
		app.setApplicant(savedLearner);
		app.setPosting(savedPosting);
		app.setEmail(email);
		
		com.utsc.project_coding_lads.domain.Application savedApp = appService.storeApplication(app);
		Assert.assertNotNull(savedApp);
		
		Boolean exists = appService.existsById(savedApp.getId());
		Assert.assertTrue(exists);
		
		savedLearner.setFirstName("newFirstName");
		User newSavedLearner = userService.updateUser(savedLearner);
		
		savedPosting.setName("newPostingName");
		Posting newSavedPosting = postingService.updatePosting(savedPosting);
		
		savedApp.setApplicant(newSavedLearner);
		savedApp.setPosting(savedPosting);
		com.utsc.project_coding_lads.domain.Application newSavedApp = appService.updateApplication(savedApp);
		
		Assert.assertNotNull(newSavedApp);
		Assert.assertEquals("newFirstName", newSavedApp.getApplicant().getFirstName());
		Assert.assertEquals("newPostingName", newSavedApp.getPosting().getName());
	}
	
	@Test
	public void applyToEmployment() throws Exception {
		User user = new User();
		user.setAge(90);
		user.setFirstName("firstName");
		user.setLastName("lastname");
		user.setUsername("username2");
		user.setHashedPassword("pw");
		SocialInitiative socialInit = new SocialInitiative();
		socialInit.setName("org b");
		SocialInitiative savedSI = siService.storeSocialInit(socialInit);
		
		user.setSocialInit(savedSI);
		Integer savedUserId = userService.storeUser(user);
		User savedUser = userService.findUserById(savedUserId);
		
		Posting posting = new Posting();
		posting.setPostingDate(LocalDateTime.now());
		posting.setPostingDesc("desc");
		posting.setName("name");
		posting.setPostingType(PostingEnum.EMPLOYMENT.name());
		posting.setPostingCreator(savedUser);
		posting.setSocialInit(savedSI);
		Posting savedPosting = postingService.savePosting(posting);
		Assert.assertNotNull(savedPosting.getId());
		Integer savedPostingId = savedPosting.getId();
		
		User learner = new User();
		learner.setAge(90);
		learner.setFirstName("firstName");
		learner.setLastName("lastname");
		learner.setUsername("usernameLearner2");
		learner.setHashedPassword("pw");
		Role savedRole = roleRepo.findRoleByName("IMPACT_LEARNER");
		learner.setRole(savedRole);
		
		Integer savedLearnerId = userService.storeUser(learner);
		User savedLearner = userService.findUserById(savedLearnerId);
		Assert.assertNotNull(savedLearner);

		String email = "learner@gmail.com";
		
		com.utsc.project_coding_lads.domain.Application app = new com.utsc.project_coding_lads.domain.Application();
		app.setApplicant(savedLearner);
		app.setPosting(savedPosting);
		app.setEmail(email);
		
		com.utsc.project_coding_lads.domain.Application savedApp = appService.storeApplication(app);
		Assert.assertNotNull(savedApp);
		
		Boolean exists = appService.existsById(savedApp.getId());
		Assert.assertTrue(exists);
		
		savedLearner.setFirstName("newFirstName");
		User newSavedLearner = userService.updateUser(savedLearner);
		
		savedPosting.setName("newPostingName");
		Posting newSavedPosting = postingService.updatePosting(savedPosting);
		
		savedApp.setApplicant(newSavedLearner);
		savedApp.setPosting(savedPosting);
		com.utsc.project_coding_lads.domain.Application newSavedApp = appService.updateApplication(savedApp);
		
		Assert.assertNotNull(newSavedApp);
		Assert.assertEquals("newFirstName", newSavedApp.getApplicant().getFirstName());
		Assert.assertEquals("newPostingName", newSavedApp.getPosting().getName());
	}

}
