package com.utsc.project_coding_lads.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.ClassSession;
import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.exception.UnauthenticatedException;
import com.utsc.project_coding_lads.repository.ClassSessionRepository;
import com.utsc.project_coding_lads.repository.CourseRepository;
import com.utsc.project_coding_lads.service.ClassSessionService;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.validator.ClassSessionValidator;
import com.utsc.project_coding_lads.validator.CourseValidator;

@Service
@Transactional
public class ClassSessionServiceImpl implements ClassSessionService {
	
	@Autowired
	ClassSessionRepository classSessionRepo;

	@Autowired
	CourseService courseService;

	@Autowired
	ClassSessionValidator classSessionValidator;

	@Autowired
	CourseValidator courseValidator;

	@Override
	public Boolean existsById(Integer id) {
		return classSessionRepo.existsById(id);
	}

	@Override
	public ClassSession findSessionById(Integer id) throws ValidationFailedException {
		if (!existsById(id))
			throw new EntityNotExistException("This class session does not exist");
		return classSessionRepo.getOne(id);
	}

	@Override
	public ClassSession storeClassSession(ClassSession classSession) throws ValidationFailedException {
		if (classSession == null)
			throw new MissingInformationException("Class session is null");
		classSessionValidator.init(classSession);
		classSessionValidator.validate();
		return classSessionRepo.save(classSession);
	}

	@Override
	public ClassSession updateSingleSession(ClassSession classSession) throws ValidationFailedException {
		if (classSession == null)
			throw new MissingInformationException("Class session is null");
		classSessionValidator.init(classSession);
		classSessionValidator.validateExist(classSession);
		Course savedCourse = courseService.findCourseById(classSession.getCourse().getId());
		classSession.setCourse(savedCourse);
		return classSessionRepo.save(classSession);
	}

	@Override
	public List<ClassSession> batchUpdateSession(List<ClassSession> sessions) throws ValidationFailedException {
		List<ClassSession> savedSessions = new ArrayList<>();
		for (ClassSession session : sessions) {
			savedSessions.add(updateSingleSession(session));
		}
		return savedSessions;
	}

	@Override
	public void deleteSingleSessionById(Integer id) throws ValidationFailedException {
		if (existsById(id))
			classSessionRepo.deleteById(id);
	}

	@Override
	public void deleteAllSessionByCourseId(Integer id) throws ValidationFailedException {
		batchDeleteSession(courseService.findCourseById(id).getSessions());
	}

	@Override
	public void batchDeleteSession(List<ClassSession> sessions) throws ValidationFailedException {
		for (ClassSession session : sessions) {
			deleteSingleSessionById(session.getId());
		}
	}

	@Override
	public List<ClassSession> findAllSessionByCourseId(Integer id) throws ValidationFailedException {
		if (id == null)
			throw new MissingInformationException("Course id is null");
		Course course = courseService.findCourseById(id);
		courseValidator.init(course);
		courseValidator.validateExist();
		List<ClassSession> sessions = course.getSessions();
		return sessions;
	}

	@Override
	public List<ClassSession> findAllSessionByCourseIdPeriod(Integer id, LocalDateTime startTime, LocalDateTime endTime)
			throws ValidationFailedException {
		if (id == null)
			throw new MissingInformationException("Instructor id is null");
		if (startTime == null || endTime == null)
			throw new MissingInformationException("Date is null");
		if (!startTime.isBefore(endTime))
			throw new UnauthenticatedException("The end time should be after the start time");
		List<ClassSession> validSessions = new ArrayList<>();
		List<ClassSession> sessions = courseService.findCourseById(id).getSessions();
		for (ClassSession session : sessions) {
			if (session.getStartDate().compareTo(startTime) >= 0 && session.getEndDate().compareTo(endTime) <= 0)
				validSessions.add(session);
		}
		return validSessions;
	}

	@Override
	public List<ClassSession> findAllSessionByPeriod(LocalDateTime startTime, LocalDateTime endTime)
			throws ValidationFailedException {
		if (startTime == null || endTime == null)
			throw new MissingInformationException("Date is null");
		if (!startTime.isBefore(endTime))
			throw new UnauthenticatedException("The end time should be after the start time");
		List<ClassSession> validSessions = new ArrayList<>();
		List<ClassSession> sessions = getAllSession();
		for (ClassSession session : sessions) {
			if (session.getStartDate().compareTo(startTime) >= 0 && session.getEndDate().compareTo(endTime) <= 0)
				validSessions.add(session);
		}
		return validSessions;
	}

	@Override
	public List<ClassSession> getAllSession() {
		return classSessionRepo.findAll();
	}
}
