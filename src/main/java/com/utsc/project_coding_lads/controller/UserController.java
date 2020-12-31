package com.utsc.project_coding_lads.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.utsc.project_coding_lads.domain.Application;
import com.utsc.project_coding_lads.domain.ClassSession;
import com.utsc.project_coding_lads.domain.Course;
import com.utsc.project_coding_lads.domain.Event;
import com.utsc.project_coding_lads.domain.ImpactLearnerCourse;
import com.utsc.project_coding_lads.domain.Invoice;
import com.utsc.project_coding_lads.domain.Posting;
import com.utsc.project_coding_lads.domain.Quiz;
import com.utsc.project_coding_lads.domain.StudentAnswer;
import com.utsc.project_coding_lads.domain.QuizQuestion;
import com.utsc.project_coding_lads.domain.User;
import com.utsc.project_coding_lads.exception.BadRequestException;
import com.utsc.project_coding_lads.exception.EntityAlreadyExistsException;
import com.utsc.project_coding_lads.exception.EntityNotExistException;
import com.utsc.project_coding_lads.exception.ValidationFailedException;
import com.utsc.project_coding_lads.security.PasswordHash;
import com.utsc.project_coding_lads.security.SecurityConfig;
import com.utsc.project_coding_lads.service.ApplicationService;
import com.utsc.project_coding_lads.service.ClassSessionService;
import com.utsc.project_coding_lads.service.CourseService;
import com.utsc.project_coding_lads.service.EventService;
import com.utsc.project_coding_lads.service.ImpactLearnerService;
import com.utsc.project_coding_lads.service.InvoiceService;
import com.utsc.project_coding_lads.service.PostingService;
import com.utsc.project_coding_lads.service.QuizQuestionService;
import com.utsc.project_coding_lads.service.QuizService;
import com.utsc.project_coding_lads.service.StudentAnswerService;
import com.utsc.project_coding_lads.service.UserService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/" + UserService.SERVICE_NAME)
public class UserController extends BaseController {

	@Autowired
	SecurityConfig security;
	@Autowired
	UserService userService;
	@Autowired
	PostingService postingService;
	@Autowired
	EventService eventService;
	@Autowired
	ApplicationService appService;
	@Autowired
	ImpactLearnerService learnerService;
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	QuizService quizService;
	@Autowired
	CourseService courseService;
	@Autowired
	ClassSessionService classSessionService;
	@Autowired
	QuizQuestionService quizQuestionService;
	@Autowired
	StudentAnswerService studentAnswerService;

	final static Logger log = LoggerFactory.getLogger(UserController.class);

	@PostMapping(path = "/signup")
	@ApiOperation(value = "create a new user", response = User.class)
	public Integer storeUser(@RequestBody User user) throws Exception {
		try {
			PasswordHash encoder = new PasswordHash();
			user.setHashedPassword(encoder.passwordEncoder(user.getHashedPassword()));
			Integer id = userService.storeUser(user);
//			log.info("userid: " + id);
			return id;
		} catch (NullPointerException e) {
			log.info("Could not store user: ", e);
			throw new BadRequestException("Request cannot be null");
		} catch (DataIntegrityViolationException e) {
			throw new EntityAlreadyExistsException("Username already exists");
		}
	}

	@PostMapping(path = "/createPosting")
	@ApiOperation(value = "create a new posting", response = Posting.class)
	public Posting createPosting(@RequestBody Posting posting) throws ValidationFailedException {
		Posting savedPosting = null;
		savedPosting = postingService.savePosting(posting);
		return savedPosting;
	}

	@GetMapping(path = "/getUser/{id}")
	@ApiOperation(value = "find a user by id", response = User.class)
	public User getUser(@PathVariable("id") Integer id) throws EntityNotExistException {
		return userService.findUserById(id);
	}

	@PostMapping(path = "/updatePosting")
	@ApiOperation(value = "update a posting", response = Posting.class)
	public Posting updatePosting(@RequestBody Posting posting) throws ValidationFailedException {
		return postingService.updatePosting(posting);
	}

	@PostMapping(path = "/deletePosting/{id}")
	@ApiOperation(value = "Delete a posting", response = Boolean.class)
	public Boolean deletePosting(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		postingService.deletePostingById(id);
		return ok;
	}

	@GetMapping(path = "/getPosting/{id}")
	@ApiOperation(value = "find a posting by id", response = Posting.class)
	public Posting getPosting(@PathVariable("id") Integer id) throws ValidationFailedException {
		return postingService.findPostingById(id);
	}

	@GetMapping(path = "/getPostings/{id}")
	@ApiOperation(value = "find all postings by userId", response = Posting.class, responseContainer = "List")
	public List<Posting> getPostings(@PathVariable("id") Integer userId) throws ValidationFailedException {
		return postingService.findAllPostingsByUserId(userId);
	}

	@GetMapping(path = "/getPostingsByDate/{id}")
	@ApiOperation(value = "find all postings by userId", response = Posting.class, responseContainer = "List")
	public List<Posting> getPostingsByDate(@PathVariable("id") Integer userId, @RequestBody LocalDateTime date)
			throws ValidationFailedException {
		return postingService.findAllPostingsByUserIdDate(userId, date);
	}

	@PostMapping(path = "/createEvent")
	@ApiOperation(value = "create a new event", response = Event.class)
	public Event createEvent(@RequestBody Event event) throws ValidationFailedException {
		return eventService.saveEvent(event);
	}

	@PostMapping(path = "/updateEvent")
	@ApiOperation(value = "update a event", response = Event.class)
	public Event updateEvent(@RequestBody Event event) throws Exception {
		return eventService.updateEvent(event);
	}

	@PostMapping(path = "/deleteEvent/{id}")
	@ApiOperation(value = "Delete a event", response = Boolean.class)
	public Boolean deleteEvent(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		eventService.deleteEventById(id);
		return ok;
	}

	@GetMapping(path = "/getEvent/{id}")
	@ApiOperation(value = "find a event by id", response = Event.class)
	public Event getEvent(@PathVariable("id") Integer id) throws ValidationFailedException {
		return eventService.findEventById(id);
	}

	@GetMapping(path = "/getEvents/{id}")
	@ApiOperation(value = "find all events by userId", response = Event.class, responseContainer = "List")
	public List<Event> getEvents(@PathVariable("id") Integer userId) throws ValidationFailedException {
		return eventService.findAllEventsByUserId(userId);
	}

	@GetMapping(path = "/getEventsByDate/{id}")
	@ApiOperation(value = "find all events by userId after date", response = Event.class, responseContainer = "List")
	public List<Event> getEventsByDate(@PathVariable("id") Integer userId,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
			throws ValidationFailedException {
		return eventService.findAllEventsByUserIdDate(userId, date);
	}

	@PostMapping(path = "/login")
	@ApiOperation(value = "login user", response = User.class)
	public Integer login(@RequestBody User user) throws Exception {
		try {
			PasswordHash encoder = new PasswordHash();
			user.setHashedPassword(encoder.passwordEncoder(user.getHashedPassword()));

		} catch (Exception e) {
			log.info("Could not load user: ", e);
		}
		return security.authentication(user);
	}

	@PostMapping(path = "/createApplication")
	@ApiOperation(value = "user apply posting", response = Application.class)
	public Application apply(@RequestBody Application app) throws Exception {
		Application savedApp = appService.storeApplication(app);
		return savedApp;
	}

	@PostMapping(path = "/updateApplication")
	@ApiOperation(value = "updating an application", response = Application.class)
	public Application updateApp(@RequestBody Application app) throws Exception {
		return appService.updateApplication(app);
	}

	@PostMapping(path = "/deleteApplication/{id}")
	@ApiOperation(value = "Delete an application", response = Boolean.class)
	public Boolean deleteApp(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		appService.deleteApplicationById(id);
		return ok;
	}

	@GetMapping(path = "/getApplication/{id}")
	@ApiOperation(value = "find a posting by id", response = Application.class)
	public Application getApp(@PathVariable("id") Integer id) throws Exception {
		return appService.findApplicationById(id);
	}

	@GetMapping(path = "/getApplicationsByUser/{id}")
	@ApiOperation(value = "find all postings by userId", response = Application.class, responseContainer = "List")
	public List<Application> getUserApps(@PathVariable("id") Integer userId) throws Exception {
		return appService.findAllApplicationsByUserId(userId);
	}

	@GetMapping(path = "/getApplicationsByPosting/{id}")
	@ApiOperation(value = "find all postings by userId", response = Application.class, responseContainer = "List")
	public List<Application> getPostingApps(@PathVariable("id") Integer postingId) throws Exception {
		return appService.findAllApplicationsByPostingId(postingId);
	}

	@PostMapping(path = "/addCourseToStudent")
	@ApiOperation(value = "add a course to a student's load", response = ImpactLearnerCourse.class)
	public ImpactLearnerCourse addCourseToStudent(@RequestBody ImpactLearnerCourse impactLearnerCourse)
			throws Exception {
		return learnerService.addCourseToLearner(impactLearnerCourse.getStudent(), impactLearnerCourse.getCourse());
	}

	@PostMapping(path = "/removeCourseFromStudent")
	@ApiOperation(value = "remove a course to a student's load", response = Boolean.class)
	public Boolean removeCourseFromStudent(@RequestBody ImpactLearnerCourse impactLearnerCourse) throws Exception {
		return learnerService.removeCourseFromLearner(impactLearnerCourse.getStudent(),
				impactLearnerCourse.getCourse());
	}

	@GetMapping(path = "/getAllCoursesFromStudent/{id}")
	@ApiOperation(value = "retrieving all courses from a student's load", response = ImpactLearnerCourse.class, responseContainer = "List")
	public List<ImpactLearnerCourse> getAllCoursesFromStudent(@PathVariable("id") Integer studentId) throws Exception {
		return learnerService.findCoursesByLearnerId(studentId);
	}

	@GetMapping(path = "/getAllCoursesFromStudentByInstructor/{id}")
	@ApiOperation(value = "retrieving all courses from a student's load that were taught by this instructor", response = ImpactLearnerCourse.class, responseContainer = "List")
	public List<ImpactLearnerCourse> getAllCoursesFromStudentByInstructor(@PathVariable("id") Integer studentId,
			@RequestParam("instructor") Integer instructorId) throws Exception {
		return learnerService.findCoursesByInstructorId(studentId, instructorId);
	}

	@GetMapping(path = "/getInvoice")
	@ApiOperation(value = "retrieves unpaid invoices for specific user", response = Invoice.class)
	public List<Invoice> getInvoiceForLearner(@RequestParam("userId") Integer userId) throws Exception {
		return invoiceService.getUnpaidInvoice(userId);
	}

	@GetMapping(path = "/getInvoiceByCourseId")
	@ApiOperation(value = "returns all invoices of a course", response = Invoice.class)
	public List<Invoice> getInvoiceByCourseId(@RequestParam Integer courseId) throws ValidationFailedException {
		return invoiceService.getAllInvoiceByCourseId(courseId);
	}

	@GetMapping(path = "/getInvoiceByUserIdCourseId")
	@ApiOperation(value = "returns the invoice for a user of a course", response = Invoice.class)
	public Invoice getInvoiceByUserIdCourseId(@RequestParam Integer userId, @RequestParam Integer courseId) throws ValidationFailedException {
		return invoiceService.findInvoiceByUserIdAndCourseId(userId, courseId);
	}

	@GetMapping(path = "/payInvoice")
	@ApiOperation(value = "pays specific course based on invoiceId", response = Invoice.class)
	public Integer payInvoice(@RequestParam("invoiceId") Integer invoiceId) throws Exception {
		return invoiceService.payInvoice(invoiceId);
	}

	@GetMapping(path = "/getPaid")
	@ApiOperation(value = "sets amount to paid for specific invoiceId for instructor", response = Invoice.class)
	public Integer getPaid(@RequestParam("invoiceId") Integer invoiceId) throws Exception {
		return invoiceService.payInvoice(invoiceId);
	}

	@PostMapping(path = "/setInvoice")
	@ApiOperation(value = "sets invoice", response = Invoice.class)
	public Invoice setPayable(@RequestBody Invoice inv) throws ValidationFailedException {
		return invoiceService.saveInvoice(inv);

	}

	@GetMapping(path = "/allInvoices")
	@ApiOperation(value = "returns all invoices for user, both paid and unpaid", response = Invoice.class)
	public List<Invoice> allInvoices(@RequestParam Integer userId) throws ValidationFailedException {
		return invoiceService.getAllInvoicesByUserId(userId);

	}
	
	@PostMapping(path = "/updateInvoice")
	@ApiOperation(value = "update an Invoice", response = Invoice.class)
	public Invoice updateInvoice(@RequestBody Invoice invoice) throws ValidationFailedException {
		return invoiceService.updateInvoice(invoice);
	}

	@PostMapping(path = "/deleteInvoice/{id}")
	@ApiOperation(value = "Delete an invoice", response = Boolean.class)
	public Boolean deleteInvoice(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		invoiceService.deleteInvoiceById(id);
		return ok;
	}

	@PostMapping(path = "/createCourse")
	@ApiOperation(value = "create a new course", response = Course.class)
	public Course createCourse(@RequestBody Course course) throws ValidationFailedException {
		Course savedCourse = null;
		savedCourse = courseService.storeCourse(course);
		return savedCourse;
	}

	@PostMapping(path = "/updateCourse")
	@ApiOperation(value = "update a course", response = Course.class)
	public Course updateCourse(@RequestBody Course course) throws ValidationFailedException {
		return courseService.updateCourse(course);
	}

	@PostMapping(path = "/deleteCourse/{id}")
	@ApiOperation(value = "Delete a course", response = Boolean.class)
	public Boolean deleteCourse(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		courseService.deleteCourseById(id);
		return ok;
	}

	@GetMapping(path = "/getCourse/{id}")
	@ApiOperation(value = "find a course by id", response = Course.class)
	public Course getCourse(@PathVariable("id") Integer id) throws ValidationFailedException {
		return courseService.findCourseById(id);
	}

	@GetMapping(path = "/getCoursesByInstructor/{id}")
	@ApiOperation(value = "find all courses by instructor id", response = Course.class, responseContainer = "List")
	public List<Course> getCoursesByInstructor(@PathVariable("id") Integer instructorId)
			throws ValidationFailedException {
		return courseService.findAllCourseByInstructorId(instructorId);
	}

	@GetMapping(path = "/getCourseByClassSession/{id}")
	@ApiOperation(value = "find the course by a class session id", response = Course.class)
	public Course getCourseByClassSession(@PathVariable("id") Integer classSessionId) throws ValidationFailedException {
		return courseService.findCourseByClassSessionId(classSessionId);
	}
	
	@PostMapping(path = "/submitQuizByStudent")
	@ApiOperation(value = "answer this quiz question", response = StudentAnswer.class)
	public List<StudentAnswer> answerLongQuizQuestion(@RequestBody List<StudentAnswer> studentAnswers)
			throws Exception {
		return learnerService.answerQuizQuestions(studentAnswers);
	}

	@GetMapping(path = "/getQuizQuestions/{id}")
	@ApiOperation(value = "find quiz questions by quiz id", response = QuizQuestion.class, responseContainer = "List")
	public List<QuizQuestion> getQuizQuestionsByQuizId(@PathVariable("id") Integer id)
			throws ValidationFailedException {
		return quizQuestionService.findQuestionsByQuizId(id);
	}

	@PostMapping(path = "/createQuiz")
	@ApiOperation(value = "create a new quiz", response = Integer.class)
	public Integer createQuiz(@RequestBody Quiz quiz) throws ValidationFailedException {
		return quizService.createQuiz(quiz);
	}

	@PostMapping(path = "/updateQuiz")
	@ApiOperation(value = "update a quiz", response = Integer.class)
	public Integer updateQuiz(@RequestBody Quiz quiz) throws ValidationFailedException {
		return quizService.updateQuiz(quiz);
	}

	@PostMapping(path = "/deleteQuiz/{id}")
	@ApiOperation(value = "Delete a quiz", response = Boolean.class)
	public Boolean deleteQuiz(@PathVariable("id") Integer id) throws Exception {
		Boolean ok = true;
		quizService.deleteQuizById(id);
		return ok;
	}

	@GetMapping(path = "/getQuiz/{id}")
	@ApiOperation(value = "find a quiz by id", response = Quiz.class)
	public Quiz getQuiz(@PathVariable("id") Integer id) throws ValidationFailedException {
		return quizService.findQuizById(id);
	}

	@GetMapping(path = "/{uid}/getQuizQAnswer/{qid}")
	@ApiOperation(value = "retrieving all courses from a student's load", response = StudentAnswer.class)
	public StudentAnswer getAllCoursesFromStudent(@PathVariable("qid") Integer quizQId, @PathVariable("uid") Integer studentId) throws Exception {
		return studentAnswerService.findByStudentAndQuestion(quizQId, studentId);
	}
}