package com.utsc.project_coding_lads.test.socialInitTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.SocialInitRepository;
import com.utsc.project_coding_lads.service.SocialInitService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestSocialInitRepo {

	@Autowired
	SocialInitRepository socialInitRepo;
	
	@Autowired
	SocialInitService socialInitService;
	
	@Test
	public void testCRUD() {
		SocialInitiative socialInit = new SocialInitiative();
		socialInit.setName("name");
		
		SocialInitiative savedSocialInit = socialInitRepo.save(socialInit);
		Assert.assertNotNull(savedSocialInit.getId());
		
		SocialInitiative get = socialInitRepo.getOne(savedSocialInit.getId());
		Assert.assertNotNull(get.getId());
		
		socialInitRepo.deleteById(get.getId());
	}
	
	@Test
	public void testCRUDService() throws ValidationFailedException {
		SocialInitiative socialInit = new SocialInitiative();
		socialInit.setName("name");
		
		SocialInitiative savedSocialInit = socialInitService.storeSocialInit(socialInit);
		Assert.assertNotNull(savedSocialInit.getId());
		
		SocialInitiative get = socialInitRepo.getOne(savedSocialInit.getId());
		Assert.assertNotNull(get.getId());
		
		socialInitRepo.deleteById(get.getId());
	}
	
	@Test
	public void testFindByName() throws EntityNotExistException {
		SocialInitiative socialInit = new SocialInitiative();
		socialInit.setName("name");
		SocialInitiative savedSocialInit = socialInitRepo.save(socialInit);
		Assert.assertNotNull(savedSocialInit.getId());
		
		SocialInitiative get = socialInitService.findSocialInitByName("name");
		Assert.assertEquals(savedSocialInit.getId(), get.getId());
	}
	
	

}
