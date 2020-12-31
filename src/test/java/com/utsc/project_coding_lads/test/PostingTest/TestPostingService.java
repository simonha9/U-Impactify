package com.utsc.project_coding_lads.test.PostingTest;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.domain.Role;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.enums.PostingEnum;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.repository.RoleRepository;
import com.utsc.project_coding_lads.service.PostingService;
import com.utsc.project_coding_lads.service.SocialInitService;
import com.utsc.project_coding_lads.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestPostingService {

	@Autowired
	PostingService postingService;
	@Autowired
	UserService userService;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	SocialInitService socialInitService;
	
	@Test
	public void testCRUD() throws Exception {
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
		SocialInitiative saved = socialInitService.storeSocialInit(socialInit);
		
		user.setSocialInit(saved);
		Integer id = userService.storeUser(user);
		User savedUser = userService.findUserById(id);
		
		Posting posting = new Posting();
		posting.setPostingDate(LocalDateTime.now());
		posting.setPostingDesc("desc");
		posting.setName("name");
		posting.setPostingType(PostingEnum.EMPLOYMENT.name());
		posting.setPostingCreator(savedUser);
		posting.setSocialInit(saved);
		Posting savedPosting = postingService.savePosting(posting);
		Assert.assertNotNull(savedPosting.getId());
		
		Posting getPosting = postingService.findPostingById(savedPosting.getId());
		Assert.assertNotNull(getPosting);
		Assert.assertEquals(savedPosting.getId(), getPosting.getId());
		
		List<Posting> postingsOld = postingService.findAllPostingsByUserId(savedUser.getId());
		Assert.assertFalse(postingsOld.isEmpty());
		Posting posting0 = postingsOld.get(0);
		Assert.assertEquals(getPosting.getName(), posting0.getName());
		
		savedPosting.setName("new name");
		savedPosting = postingService.updatePosting(savedPosting);
		Assert.assertEquals("new name", savedPosting.getName());
		
		List<Posting> postings = postingService.findAllPostingsByUserId(savedUser.getId());
		Assert.assertFalse(postings.isEmpty());
		Posting posting1 = postings.get(0);
		Assert.assertEquals("new name", posting1.getName());
		
		postingService.deletePostingById(savedPosting.getId());
		
		Boolean exist = postingService.existsById(id);
		Assert.assertFalse(exist);
	}
	
	@Test
	public void testSocialInit() throws EntityNotExistException {
		SocialInitiative find = socialInitService.findSocialInitByName("test");
	}

}
