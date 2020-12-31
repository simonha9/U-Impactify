package com.utsc.project_coding_lads.service;

import java.time.LocalDateTime;
import java.util.List;

import com.utsc.project_coding_lads.domain.ClassSession;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface ClassSessionService {

	final String SERVICE_NAME = "sessions";

	public Boolean existsById(Integer id);

	public ClassSession findSessionById(Integer id) throws ValidationFailedException;
	
	public List<ClassSession> findAllSessionByCourseId(Integer id) throws ValidationFailedException;
	
	public List<ClassSession> findAllSessionByCourseIdPeriod(Integer id, LocalDateTime startTime, LocalDateTime endTime) throws MissingInformationException, ValidationFailedException;
	
	public List<ClassSession> findAllSessionByPeriod(LocalDateTime startTime, LocalDateTime endTime) throws ValidationFailedException;
	
	public List<ClassSession> getAllSession();
	
	public ClassSession updateSingleSession(ClassSession classSession) throws ValidationFailedException;
	
	public List<ClassSession> batchUpdateSession(List<ClassSession> sessions) throws ValidationFailedException;
	
	public void deleteSingleSessionById(Integer id) throws ValidationFailedException;

	public void deleteAllSessionByCourseId(Integer id) throws ValidationFailedException;
	
	public void batchDeleteSession(List<ClassSession> sessions) throws ValidationFailedException;
	
	public ClassSession storeClassSession(ClassSession classSession) throws ValidationFailedException;
}
