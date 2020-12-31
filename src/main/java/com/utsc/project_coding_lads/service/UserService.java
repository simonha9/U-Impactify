package com.utsc.project_coding_lads.service;

import java.util.List;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactLearner;
import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface UserService {

	final static String SERVICE_NAME = "users";
	
	public Integer storeUser(User user) throws Exception;
	
	public User findUserById(Integer id) throws EntityNotExistException;
	
	public Boolean existsById(Integer id);
	
	public User updateUser(User user) throws ValidationFailedException;
	
}