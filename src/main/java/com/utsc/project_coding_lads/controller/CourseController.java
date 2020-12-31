package com.utsc.project_coding_lads.controller;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.ClassSession;
import com.utsc.project_coding_lads.domain.Quiz;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.service.ClassSessionService;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.QuizService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/" + CourseService.SERVICE_NAME)
public class CourseController extends BaseController {

	@Autowired
	CourseService courseService;
	@Autowired
	ClassSessionService classSessionService;
	@Autowired
	QuizService quizService;

	final static Logger log = LoggerFactory.getLogger(CourseController.class);
	
	@GetMapping(path = "/getAllCourses")
	@ApiOperation(value = "get all courses", response = Course.class, responseContainer = "List")
	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();
		try {
			courses = courseService.getAllCourses();
		} catch (Exception e) {
			log.info("Could not get all courses", e.getMessage());
		}
		return courses;
	}
	
	// @GetMapping(path = "/getAllSessions")
	// @ApiOperation(value = "get all sessions", response = ClassSession.class, responseContainer = "List")
	// public List<ClassSession> getAllSessions() {
	// 	List<ClassSession> sessions = new ArrayList<>();
	// 	try {
	// 		sessions = classSessionService.getAllSession();
	// 	} catch (Exception e) {
	// 		log.info("Could not get all sessions", e.getMessage());
	// 	}
	// 	return sessions;
	// }

	@GetMapping(path = "/getSession/{id}")
	@ApiOperation(value = "find a class session by id", response = ClassSession.class)
	public ClassSession getSession(@PathVariable("id") Integer id) throws ValidationFailedException {
		return classSessionService.findSessionById(id);
	}

	@GetMapping(path = "/getAllSessionByCourse/{id}")
	@ApiOperation(value = "find all class sessions of a course", response = ClassSession.class, responseContainer = "List")
	public List<ClassSession> getAllSessionByCourse(@PathVariable("id") Integer courseId) throws ValidationFailedException {
		return classSessionService.findAllSessionByCourseId(courseId);
	}

	@GetMapping(path = "/getAllQuizzesByCourse/{id}")
	@ApiOperation(value = "find all quizzes of a course", response = Quiz.class, responseContainer = "List")
	public List<Quiz> getAllQuizzesByCourse(@PathVariable("id") Integer courseId) throws ValidationFailedException {
		return quizService.findAllQuizzesByCourseId(courseId);
	}

	@GetMapping(path = "/getAllSessionByTime/{id}")
	@ApiOperation(value = "find all class sessions of a course within a period", response = ClassSession.class, responseContainer = "List")
	public List<ClassSession> getAllSessionByCourseIdPeriod(@PathVariable("id") Integer CourseId,
			@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) throws ValidationFailedException {
		return classSessionService.findAllSessionByCourseIdPeriod(CourseId, startTime, endTime);
	}

	@GetMapping(path = "/getAllSessionByTime")
	@ApiOperation(value = "find all class sessions within a period", response = ClassSession.class, responseContainer = "List")
	public List<ClassSession> getAllSessionByPeriod(@RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
			@RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime) throws ValidationFailedException {
		return classSessionService.findAllSessionByPeriod(startTime, endTime);
	}

	@PostMapping(path = "/createSession")
	@ApiOperation(value = "create a class session", response = ClassSession.class)
	public ClassSession createSession(@RequestBody ClassSession session) throws ValidationFailedException {
		return classSessionService.storeClassSession(session);
	}

	@PostMapping(path = "/updateSession")
	@ApiOperation(value = "update a single class session", response = ClassSession.class)
	public ClassSession updateSession(@RequestBody ClassSession session) throws ValidationFailedException {
		return classSessionService.updateSingleSession(session);
	}

	@PostMapping(path = "/batchUpdateSession")
	@ApiOperation(value = "update multiple class sessions", response = ClassSession.class, responseContainer = "List")
	public List<ClassSession> batchUpdateSession(@RequestBody List<ClassSession> sessions) throws ValidationFailedException {
		return classSessionService.batchUpdateSession(sessions);
	}

	@PostMapping(path = "/deleteSession/{id}")
	@ApiOperation(value = "delete a single class session", response = Boolean.class)
	public Boolean deleteSession(@PathVariable("id") Integer id) throws ValidationFailedException {
		Boolean ok = true;
		classSessionService.deleteSingleSessionById(id);
		return ok;
	}

	@PostMapping(path = "/deleteAllSessionByCourseId/{id}")
	@ApiOperation(value = "delete all class sessions of a course", response = Boolean.class)
	public Boolean deleteAllSessionByCourseId(@PathVariable("id") Integer id) throws ValidationFailedException {
		Boolean ok = true;
		classSessionService.deleteAllSessionByCourseId(id);
		return ok;
	}
	
}