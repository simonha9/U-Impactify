package com.utsc.project_coding_lads.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.service.PostingService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/" + PostingService.SERVICE_NAME)
public class PostingController extends BaseController {

	@Autowired
	PostingService postingService;
	
	final static Logger log = LoggerFactory.getLogger(PostingController.class);
	
	@GetMapping(path = "/getAllPostings")
	@ApiOperation(value = "get all postings", response = Posting.class, responseContainer = "List")
	public List<Posting> getAllPostings() {
		List<Posting> postings = new ArrayList<>();
		try {
			postings = postingService.getAllPostings();
		} catch (Exception e) {
			log.info("Could not get all postings", e.getMessage());
		}
		return postings;
	}
	
	
}
