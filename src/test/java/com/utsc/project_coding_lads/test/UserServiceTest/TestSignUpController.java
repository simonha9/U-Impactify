package com.utsc.project_coding_lads.test.UserServiceTest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.controller.UserController;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.BadRequestException;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.repository.UserRepository;
import com.utsc.project_coding_lads.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration
@AutoConfigureMockMvc
public class TestSignUpController {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	UserController controller;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	WebApplicationContext webApplicationContext;
	
	@Before
	public void setUp() {
		Role impactLearner = new Role("impact_learner");
		Role impactConsultant = new Role("impact_consultant");
	
//		if (roleRepo.findRoleIdByName("impact_learner") == null && roleRepo.findRoleIdByName("impact_consultant") == null) {
//			roleRepo.save(impactLearner);
//			roleRepo.save(impactConsultant);
//		}
	}
	
	@Test(expected = BadRequestException.class)
	public void nullRequest() throws Exception {
		controller.storeUser(null);
	}
	
	@Test
	public void addSocialOrgPublicUser() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
								.contentType(MediaType.APPLICATION_JSON).content("{\n"
										+ "  \"firstName\" : \"first\",\n"
										+ "  \"lastName\" : \"last\",\n"
										+ "  \"username\" : \"1\",\n"
										+ "  \"password\" : \"asdf\",\n"
										+ "  \"age\" : 18,\n"
										+ "  \"role\": {},\n"
										+ "  \"socialInit\": {\"name\": \"Org B\"}\n"
										+ "}"))
								.andReturn();

		boolean found = userRepo.existsById(Integer.parseInt(mvc.getResponse().getContentAsString()));
		int status = mvc.getResponse().getStatus();
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void addImpactLearnerNoSocialOrg() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"2\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_learner\"},\n"
						+ "  \"socialInit\": null\n"
						+ "}"))
				.andReturn();
		String id = mvc.getResponse().getContentAsString();
		boolean found = false;
		if (id != null && !id.isEmpty()) {
			found = userRepo.existsById(Integer.parseInt(id));
		}
		int status = mvc.getResponse().getStatus();
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void addImpactConsultantNoSocialOrg() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"3\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_consultant\"},\n"
						+ "  \"socialInit\": null\n"
						+ "}"))
				.andReturn();

		boolean found = userRepo.existsById(Integer.parseInt(mvc.getResponse().getContentAsString()));
		int status = mvc.getResponse().getStatus();
		
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void addImpactLearnerExistingSocialOrg() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"4\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_learner\"},\n"
						+ "  \"socialInit\": {\"name\": \"Org B\"}\n"
						+ "}"))
				.andReturn();

		boolean found = userRepo.existsById(Integer.parseInt(mvc.getResponse().getContentAsString()));
		int status = mvc.getResponse().getStatus();
		
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void addImpactConsultantExistingSocialOrg() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"5\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_consultant\"},\n"
						+ "  \"socialInit\": {\"name\": \"Org B\"}\n"
						+ "}"))
				.andReturn();

		boolean found = userRepo.existsById(Integer.parseInt(mvc.getResponse().getContentAsString()));
		int status = mvc.getResponse().getStatus();
		
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void addImpactLearnerNewSocialOrg() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"6\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_learner\"},\n"
						+ "  \"socialInit\": {\"name\": \"Org C\"}\n"
						+ "}"))
				.andReturn();

		boolean found = userRepo.existsById(Integer.parseInt(mvc.getResponse().getContentAsString()));
		int status = mvc.getResponse().getStatus();
		
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void addImpactConsultantNewSocialOrg() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"7\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_learner\"},\n"
						+ "  \"socialInit\": {\"name\": \"Org D\"}\n"
						+ "}"))
				.andReturn();

		boolean found = userRepo.existsById(Integer.parseInt(mvc.getResponse().getContentAsString()));
		int status = mvc.getResponse().getStatus();
		
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void addImpactLearnerEmptySocialInit() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"8\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_learner\"},\n"
						+ "  \"socialInit\": null\n"
						+ "}"))
				.andReturn();

		boolean found = userRepo.existsById(Integer.parseInt(mvc.getResponse().getContentAsString()));
		int status = mvc.getResponse().getStatus();
		
		Assert.assertTrue(found);
		Assert.assertEquals(200, status);
	}
	
	@Test
	public void roleJsonInvalidField() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"9\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"abc\": \"impact_learner\"},\n"
						+ "  \"socialInit\": null\n"
						+ "}"))
				.andReturn();

		int status = mvc.getResponse().getStatus();
		
//		Assert.assertTrue(mvc.getResponse().getContentAsString().contains("Improper format of role or socialInit field values"));
		Assert.assertEquals(400, status);
	}
	
	@Test
	public void roleJsonAdditionalFields() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"first\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"10\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_learner\", \"a\"},\n"
						+ "  \"socialInit\": null\n"
						+ "}"))
				.andReturn();

		int status = mvc.getResponse().getStatus();
		
//		Assert.assertTrue(mvc.getResponse().getContentAsString().contains("Improper format of role or socialInit field values"));
		Assert.assertEquals(400, status);
	}
	
	@Test
	public void missingRequiredInfo() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		MvcResult mvc = mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/users/signup")
				.contentType(MediaType.APPLICATION_JSON).content("{\n"
						+ "  \"firstName\" : \"\",\n"
						+ "  \"lastName\" : \"last\",\n"
						+ "  \"username\" : \"\",\n"
						+ "  \"password\" : \"asdf\",\n"
						+ "  \"age\" : 18,\n"
						+ "  \"role\": {\"name\": \"impact_learner\"},\n"
						+ "  \"socialInit\": null\n"
						+ "}"))
				.andReturn();

		int status = mvc.getResponse().getStatus();
		System.out.println(mvc.getResponse().getContentAsString());
		Assert.assertTrue(mvc.getResponse().getContentAsString().contains("Required information is missing"));
		Assert.assertEquals(400, status);
	}

}