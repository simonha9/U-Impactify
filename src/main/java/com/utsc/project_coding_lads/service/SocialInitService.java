package com.utsc.project_coding_lads.service;

import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface SocialInitService {

	public SocialInitiative storeSocialInit(SocialInitiative socialInit) throws ValidationFailedException;
	
	public SocialInitiative findSocialInitByName(String name) throws EntityNotExistException;
}