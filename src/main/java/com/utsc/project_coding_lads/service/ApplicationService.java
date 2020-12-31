package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.Application;

public interface ApplicationService {

	public Application storeApplication(Application app) throws Exception;
	
	public Application findApplicationById(Integer appId) throws Exception;
	
	public Boolean existsById(Integer appId);
	
	public void deleteApplicationById(Integer appId) throws Exception;
	
	public Application updateApplication(Application app) throws Exception;
	
	public List<Application> findAllApplicationsByUserId(Integer userId) throws Exception;
	
	public List<Application> findAllApplicationsByPostingId(Integer postingId) throws Exception;
	
	public List<Application> getAllApplications() throws Exception;
	
}
