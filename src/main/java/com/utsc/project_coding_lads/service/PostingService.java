package com.utsc.project_coding_lads.service;

import java.time.LocalDateTime;
import java.util.List;

import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface PostingService {
	
	final String SERVICE_NAME = "postings";

	public Posting savePosting(Posting posting) throws ValidationFailedException;
	
	public Posting findPostingById(Integer postingId) throws ValidationFailedException;
	
	public Boolean existsById(Integer postingId);
	
	public void deletePostingById(Integer postingId) throws Exception;
	
	public Posting updatePosting(Posting posting) throws ValidationFailedException;
	
	public List<Posting> findAllPostingsByUserId(Integer userId) throws ValidationFailedException;
	
	public List<Posting> findAllPostingsByUserIdDate(Integer userId, LocalDateTime date) throws ValidationFailedException;
	
	public List<Posting> getAllPostings();
}