package com.utsc.project_coding_lads.test.UserServiceTest;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.controller.UserController;
import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.enums.PostingEnum;
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
public class TestImpactConsultantApply {
	
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

	@Test
	public void applyToConsulting() throws Exception {
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
		posting.setPostingType(PostingEnum.CONSULTING.name());
		posting.setPostingCreator(savedUser);
		posting.setSocialInit(savedSI);
		Posting savedPosting = postingService.savePosting(posting);
		Assert.assertNotNull(savedPosting.getId());
		Integer savedPostingId = savedPosting.getId();
		
		User consultant = new User();
		consultant.setAge(90);
		consultant.setFirstName("firstName");
		consultant.setLastName("lastname");
		consultant.setUsername("usernameConsultant1");
		consultant.setHashedPassword("pw");
		Role role = new Role("IMPACT_CONSULTANT");
		Role savedRole = roleRepo.save(role);
		consultant.setRole(savedRole);
		
		Integer savedConsultantId = userService.storeUser(consultant);
		User savedConsultant = userService.findUserById(savedConsultantId);
		Assert.assertNotNull(savedConsultant);
		
		String email = "learner@gmail.com";
		
		com.utsc.project_coding_lads.domain.Application app = new com.utsc.project_coding_lads.domain.Application();
		app.setApplicant(savedConsultant);
		app.setPosting(savedPosting);
		app.setEmail(email);
		
		com.utsc.project_coding_lads.domain.Application savedApp = appService.storeApplication(app);
		Assert.assertNotNull(savedApp);
		
		Boolean exists = appService.existsById(savedApp.getId());
		Assert.assertTrue(exists);
		
		List<com.utsc.project_coding_lads.domain.Application> getApp = appService.findAllApplicationsByUserId(savedConsultantId);
		Assert.assertFalse(getApp.isEmpty());
		
		savedConsultant.setFirstName("newFirstName");
		User newSavedLearner = userService.updateUser(savedConsultant);
		
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
