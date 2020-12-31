package com.utsc.project_coding_lads.test.PostingTest;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.utsc.project_coding_lads.Application;
import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.enums.PostingEnum;
import com.utsc.project_coding_lads.repository.PostingRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class TestPostingRepo {

	@Autowired
	PostingRepository postingRepo;
	
	@Test
	public void testCRUD() {
		Posting posting = new Posting();
		
		posting.setPostingDate(LocalDateTime.now());
		posting.setPostingDesc("desc");
		posting.setName("name");
		posting.setPostingType(PostingEnum.EMPLOYMENT.name());
		
		Posting savedPosting = postingRepo.save(posting);
		Assert.assertNotNull(savedPosting.getId());
		
		Posting getPosting = postingRepo.getOne(savedPosting.getId());
		Assert.assertNotNull(getPosting);
		
		savedPosting.setName("new name");
		savedPosting = postingRepo.save(savedPosting);
		Assert.assertEquals("new name", savedPosting.getName());
		Integer id = savedPosting.getId();
		postingRepo.delete(savedPosting);
		
		Boolean exist = postingRepo.existsById(id);
		Assert.assertFalse(exist);
	}

}
