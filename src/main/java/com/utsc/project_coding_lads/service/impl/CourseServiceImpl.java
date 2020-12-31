package com.utsc.project_coding_lads.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.utsc.project_coding_lads.domain.ClassSession;
import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ImpactConsultant;
import com.utsc.project_coding_lads.domain.SocialInitiative;
import com.utsc.project_coding_lads.exception.BadRequestException;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.repository.CourseRepository;
import com.utsc.project_coding_lads.service.ClassSessionService;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.ImpactConsultantService;
import com.utsc.project_coding_lads.validator.ClassSessionValidator;
import com.utsc.project_coding_lads.validator.CourseValidator;
import com.utsc.project_coding_lads.validator.ImpactConsultantValidator;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

	@Autowired
	CourseRepository courseRepo;
	
	@Autowired
	ImpactConsultantService impactConsultantService;
	
	@Autowired
	ClassSessionService classSessionService;
	
	@Autowired
	CourseValidator courseValidator;

	@Autowired
	ClassSessionValidator classSessionValidator;

	@Autowired
	ImpactConsultantValidator impactConsultantValidator;
	
	@Override
	public Boolean existsById(Integer id) {
		return courseRepo.existsById(id);
	}

	@Override
	public Course storeCourse(Course course) throws ValidationFailedException {
		if (course == null)
			throw new MissingInformationException("Course body is null");
		courseValidator.init(course);
		courseValidator.validate();
		return courseRepo.save(course);
	}

	@Override
	public Course findCourseById(Integer id) throws ValidationFailedException {
		if (id == null)
			throw new MissingInformationException("Course id cannot be null");
		if (!existsById(id))
			throw new EntityNotExistException("The course does not exist.");
		return courseRepo.findById(id).get();
	}

	@Override
	public Course updateCourse(Course course) throws ValidationFailedException {
		if (course == null)
			throw new MissingInformationException("Course body is null");
		courseValidator.init(course);
		courseValidator.validateExist();
		ImpactConsultant savedImpactConsultant = impactConsultantService.findImpactConsultantById(course.getInstructor().getId());
		course.setInstructor(savedImpactConsultant);
		// batch update class sessions
		course.setSessions(classSessionService.batchUpdateSession(course.getSessions()));
		return courseRepo.save(course);
	}

	@Override
	public void deleteCourseById(Integer id) throws Exception {
		// delete all the sessions of the course first
		classSessionService.deleteAllSessionByCourseId(id);
		courseRepo.deleteById(id);
	}

	@Override
	public List<Course> findAllCourseByInstructorId(Integer id) throws ValidationFailedException {
		if (id == null)
			throw new MissingInformationException("Instructor id is null");
		ImpactConsultant instructor = impactConsultantService.findImpactConsultantById(id);
		impactConsultantValidator.init(instructor);
		impactConsultantValidator.validateExist();
		return instructor.getCourses();
	}

	@Override
	public Course findCourseByClassSessionId(Integer id) throws ValidationFailedException {
		if (id == null)
			throw new MissingInformationException("Class session id is null");
		ClassSession classSession = classSessionService.findSessionById(id);
		Course course = classSession.getCourse();
		courseValidator.init(course);
		courseValidator.validateExist();
		return course;
	}

	@Override
	public List<Course> getAllCourses() throws Exception {
		return courseRepo.findAll();
	}

//	@Override
//	public List<Course> findAllCourseByInstructorIdDate(Integer id, LocalDateTime date)
//			throws ValidationFailedException {
//		if (id == null)
//			throw new MissingInformationException("Instructor id is null");
//		if (date == null)
//			throw new MissingInformationException("Date is null");
//		List<Course> courses = findAllCourseByInstructorId(id);
//		List<Course> courseByDate = new ArrayList<>();
//		for (Course course : courses) {
//			if (course.getSessions())
//		}
//		return null;
//	}

}