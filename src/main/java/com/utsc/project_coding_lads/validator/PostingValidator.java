package com.utsc.project_coding_lads.validator;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.UnauthenticatedException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.service.PostingService;
import com.utsc.project_coding_lads.service.SocialInitService;

@Component
@Transactional
public class PostingValidator implements Validator {

	private String name;
	private String desc;
	private User postingCreator;
	private String postingType;
	private LocalDateTime postingDate;
	private Integer postingId;
	private SocialInitiative socialInit;

	@Autowired
	SocialInitService socialInitService;

	@Autowired
	PostingService postingService;
	
	public void init(String name, String desc, User postingCreator, String postingType,
			LocalDateTime postingDate, SocialInitiative socialInit) {
		this.name = name;
		this.desc = desc;
		this.postingCreator = postingCreator;
		this.postingType = postingType;
		this.postingDate = postingDate;
		this.socialInit = socialInit;
	}

	public void init(String name, String desc, User postingCreator, String postingType, LocalDateTime postingDate,
			Integer postingId) {
		this.name = name;
		this.desc = desc;
		this.postingCreator = postingCreator;
		this.postingType = postingType;
		this.postingDate = postingDate;
		this.postingId = postingId;
	}
	
	public void init(String name, String desc, User postingCreator, String postingType, LocalDateTime postingDate,
			SocialInitiative socialInit, Integer postingId) {
		this.name = name;
		this.desc = desc;
		this.postingCreator = postingCreator;
		this.postingType = postingType;
		this.postingDate = postingDate;
		this.socialInit = socialInit;
		this.postingId = postingId;
	}

	@Override
	public void validate() throws ValidationFailedException {
		if (postingDate == null || desc == null || name == null || postingType == null)
			throw new MissingInformationException("Required fields are missing.");
		if (postingCreator == null)
			throw new EntityNotExistException("The posting creator does not exist.");
		if (postingCreator.getId() == null)
			throw new EntityNotExistException("The posting creator Id cannot be null");
		if (socialInit == null)
			throw new UnauthenticatedException("This user is not an employee.");
		SocialInitiative savedSocialInit = socialInitService.findSocialInitByName(socialInit.getName());
		if (savedSocialInit == null)
			throw new UnauthenticatedException("This user is not an employee.");
	}

	public void validateExists() throws ValidationFailedException {
		validate();
		if (!postingService.existsById(postingId))
			throw new EntityNotExistException("That posting does not exist.");
	};

}
