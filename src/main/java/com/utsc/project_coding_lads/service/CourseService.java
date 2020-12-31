package com.utsc.project_coding_lads.service;

import java.time.LocalDateTime;
import java.util.List;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.exception.MissingInformationException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;

public interface CourseService {

	final String SERVICE_NAME = "courses";
	
	public Course findCourseById(Integer id) throws ValidationFailedException;
	
	public Course updateCourse(Course course) throws ValidationFailedException;
	
	public void deleteCourseById(Integer id) throws Exception;
	
	public List<Course> findAllCourseByInstructorId(Integer id) throws ValidationFailedException;

	public Course findCourseByClassSessionId(Integer id) throws MissingInformationException, ValidationFailedException;

	public List<Course> getAllCourses() throws Exception;
	
//	public List<Course> findAllCourseByInstructorIdDate(Integer id, LocalDateTime date) throws ValidationFailedException;
	
	public Course storeCourse(Course course) throws ValidationFailedException;

	public Boolean existsById(Integer id);
	
}
